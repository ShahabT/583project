import java.sql.*;

public class client {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/github";

   //  Database credentials
   static final String USER = "user";
   static final String PASS = "password";

   public static Connection getConn()
   {
     Connection conn = null;
     try {
       Class.forName(JDBC_DRIVER);
       System.out.println("Connecting to db...");
       conn = DriverManager.getConnection(DB_URL, USER, PASS); 
       return conn;
     } catch(Exception e) {
        //Handle errors for Class.forName
        e.printStackTrace();
     }
     return null;
   }

   public static ResultSet execSql(Connection conn, String sql) 
   {
     Statement stm = null;
     ResultSet rs = null;
     try {
       stm = conn.createStatement();
       rs = stm.executeQuery(sql);
       return rs;
      } catch (SQLException se){
        //Handle errors for JDBC
        se.printStackTrace();
      } 
      return null;
   }

   public static void main(String[] args) {
     Connection conn = null;
     String[] sqls = { 
      "SELECT * FROM output ", 
      "SELECT * FROM output WHERE user_email like '%.com'",
      "SELECT * FROM output WHERE user_email like '%.edu'"
     };
     for (int k = 0; k < sqls.length; k++) 
     {
       try {
         conn = getConn();
         System.out.println("Executing statement: " + sqls[k]);

         long startTime = System.currentTimeMillis();
         ResultSet rs = execSql(conn, sqls[k]);
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

         rs.close();
         conn.close();
        } catch (SQLException se) {
          se.printStackTrace();
        } finally {
          try{
             if(conn!=null)
                conn.close();
          } catch(SQLException se) {
             se.printStackTrace();
          }
        }
     }
   }
}
