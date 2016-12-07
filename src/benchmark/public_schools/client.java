import java.sql.*;

public class client {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/db";

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
      } finally {
        try {
          if (stm != null)
            stm.close();
        } catch (SQLException se2) { }

        try { 
          if (conn != null)
            conn.close();
        } catch (SQLException se) {
           se.printStackTrace();
        }
      }
     return null;
   }

   public static void main(String[] args) {
     Connection conn = null;

     /*
      * X,Y,FID,NCESID,NAME,ADDRESS,ADDRESS2,CITY,STATE,ZIP,ZIP4,TELEPHONE,TYPE,STATUS,
      * POPULATION,COUNTY,COUNTYFIPS,COUNTRY,LATITUDE,LONGITUDE,NAICS_CODE,NAICS_DESC,
      * SOURCE,SOURCEDATE,VAL_METHOD,VAL_DATE,WEBSITE,LEVEL_,ENROLLMENT,STARTGRADE,
      * END_GRADE,DISTRICTID,FT_TEACHER,SHELTER_ID
      */
     String sql_state = "MI";
     String sql1 = "SELECT * FROM Public_Schools WHERE STATE='" + sql_state + "'";
     String sql2 = sql1 + "AND ENROLLMENT >= 1000";
     String sql3 = sql2 + "AND END_GRADE=12";
     try {
       conn = getConn();
       System.out.println("Creating statement...");

       ResultSet rs = execSql(conn, sql1);
       while (rs.next()) {
         String name = rs.getString("NAME");
         String addr = rs.getString("ADDRESS");
         String addr2 = rs.getString("ADDRESS2");
         String city = rs.getString("CITY");
         String state = rs.getString("STATE");
         int zip = rs.getInt("ZIP");
         int zip4 = rs.getInt("ZIP4");
         String phone = rs.getString("TELEPHONE");
         String type = rs.getString("TYPE");
         double lat = rs.getDouble("LATITUDE");
         double lon = rs.getDouble("LONGITUDE");
         int enroll = rs.getInt("ENROLLMENT");
         String start = rs.getString("STARTGRADE");
         String end = rs.getString("END_GRADE");

         System.out.println("Name: " + name);
         System.out.println("Address: " + addr + ", " + addr2 + ", " +city + ", " +state + ", " + zip + "-" + zip4);
         System.out.println("Phone: " + phone);
         System.out.println("Type: " + type);
         System.out.println("Geo: " + lat + ", " + lon );
         System.out.println("Enrollment: " + enroll);
         System.out.println("Grades: " + start + " to " + end);
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
