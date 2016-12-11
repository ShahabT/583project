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
        long maxId = args.length>1 ? Long.parseLong(args[1]):4949797974L;

        int wait = args.length > 0 ? Integer.parseInt(args[0]) : 10000;

        QueryExecutor executor = null;
        String[] sqls = {
                "SELECT * FROM output where id < "+maxId,
                "SELECT * FROM output WHERE MOD(id, 173) = 0 and id < "+maxId,
                "SELECT * FROM output WHERE user_email like '%.edu' and id < "+maxId,
                "SELECT * FROM output WHERE TIME_TO_SEC(time(created_at)) - TIME_TO_SEC(time(now())) > 0 and id < "+maxId,
                "SELECT HOUR(created_at), COUNT(*) FROM output GROUP BY HOUR(created_at) and id < "+maxId
        };

        try {
            executor = QueryExecutor.getInstance(DB_URL, USER, PASS);
            for (int k = 0; k < sqls.length; k++) {
                Thread.sleep(wait);

                long qStart = System.currentTimeMillis();
                ResultSet rs = executor.executeQuery(sqls[k]);
                long qEnd = System.currentTimeMillis();
                System.out.println(">>> Q" + (k + 1) + " TIME: " + (qEnd - qStart));

                ResultSetMetaData rsmd = rs.getMetaData();
                int colNum = rsmd.getColumnCount();
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
        System.out.println(">>> RUN TIME: "+(end-start));
    }
}
