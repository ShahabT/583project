import java.sql.*;

public class client {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/temp";

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
     // finally {
     //    try {
     //      if (stm != null)
     //        stm.close();
     //    } catch (SQLException se2) { }

     //    try { 
     //      if (conn != null)
     //        conn.close();
     //    } catch (SQLException se) {
     //       se.printStackTrace();
     //    }
     //  }
     return null;
   }

   public static void main(String[] args) {
     Connection conn = null;
     String sql_state = "MI";
     String sql1 = "SELECT * FROM output"; 
     // WHERE STATE='" + sql_state + "'";
     // String sql2 = sql1 + "AND ENROLLMENT >= 1000";
     // String sql3 = sql2 + "AND END_GRADE=12";
     try {
       conn = getConn();
       System.out.println("Creating statement...");

       ResultSet rs = execSql(conn, sql1);
       ResultSetMetaData rsmd = rs.getMetaData();
       int colNum = rsmd.getColumnCount();
       while (rs.next()) {
         for (int i = 1; i <= colNum; i++) {
           if (i != 1)
             System.out.print("\t | ");
           System.out.print(rs.getObject(i));
         }
         System.out.println();
       }
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
