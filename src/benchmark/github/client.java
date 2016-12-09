package benchmark.github;

import queryManager.QueryExecutor;
import java.sql.*;

public class client {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/github";

   //  Database credentials
   static final String USER = "user";
   static final String PASS = "password";

   public static void main(String[] args) throws Exception{
     QueryExecutor executor = null;
     String[] sqls = { 
      "SELECT * FROM output ", 
      "SELECT * FROM output WHERE MOD(id, 173) = 0", 
      "SELECT * FROM output WHERE user_email like '%.edu'",
      "SELECT * FROM output WHERE TIME_TO_SEC(time(created_at)) - TIME_TO_SEC(time(now())) > 0",
      "SELECT HOUR(created_at), COUNT(*) FROM output GROUP BY HOUR(created_at)"
     };

     for (int k = 0; k < sqls.length; k++) {
       try {
         executor = QueryExecutor.getInstance(DB_URL, USER, PASS);
         System.out.println("Executing statement: " + sqls[k]);

         long startTime = System.currentTimeMillis();
         ResultSet rs = executor.executeQuery(sqls[k]);
         long estimatedTime = System.currentTimeMillis() - startTime;
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
         System.out.println("Time: " + estimatedTime + " ms");

         if (k < sqls.length)
           Thread.sleep(10000);

        } catch (SQLException se) {
          se.printStackTrace();
        } 
     }
   }
}
