package benchmark.github;

import queryManager.QueryExecutor;

import java.sql.*;

public class Main {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://bigdata.eecs.umich.edu/583proj";

    //  Database credentials
    static final String USER = "user";
    static final String PASS = "password";

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        int tableSize = 4027, lastPk = 29764052;
        int size = args.length > 1 ? Integer.parseInt(args[1]) : tableSize;
        long maxPk = (int) (lastPk * ((double) size) / tableSize);

        int wait = args.length > 0 ? Integer.parseInt(args[0]) : 10000;

        QueryExecutor executor = null;
        String[] sqls = {
                "SELECT * FROM github where pk < " + (maxPk * 0.05),
                "SELECT max(user) FROM github WHERE MOD(id, 173) = 0 and pk between " + (maxPk * .1) + " and " + (maxPk * .5),
                "SELECT org, count(*) FROM github WHERE user_email like '%.edu' and pk between " + (maxPk * .7) + " and " + (maxPk * .9) + " group by org",
                "SELECT type, count(distinct user) FROM github WHERE TIME_TO_SEC(time(created_at)) - TIME_TO_SEC(time('2016-01-01')) > 0 and pk between " + (maxPk * .8) + " and " + maxPk,
                "SELECT HOUR(created_at), COUNT(*) FROM github where pk < " + maxPk + " GROUP BY HOUR(created_at)"
        };

        try {
            executor = QueryExecutor.getInstance(DB_URL, USER, PASS);
            for (int k = 0; k < sqls.length; k++) {
                Thread.sleep(wait);

                long qStart = System.currentTimeMillis();
                ResultSet rs = executor.executeQuery(sqls[k]);
                long qEnd = System.currentTimeMillis();
                System.out.println("#>>> Q" + (k + 1) + " TIME: " + (qEnd - qStart));

//                ResultSetMetaData rsmd = rs.getMetaData();
//                int colNum = rsmd.getColumnCount();
                int counter = 0;

                while (rs.next()) {
                    // for (int i = 1; i <= colNum; i++) {
                    //   if (i != 1)
                    //     System.out.print("\t | ");
                    //   System.out.print(rs.getObject(i));
                    // }
                    counter++;
                    // System.out.println();
                }
                System.out.println(counter);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (executor != null)
                executor.close();
        }

        long end = System.currentTimeMillis();
        System.out.println("#>>> RUN TIME: " + (end - start));
    }
}
