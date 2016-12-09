package test;

import java.io.Closeable;
import java.lang.reflect.WildcardType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.text.ElementIterator;

import com.mysql.jdbc.DatabaseMetaData;
import test.QueryExecutor;
public class main {

	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		Connection conn = null;
//		Statement statement = null;
		ResultSet resultSet = null;
		String database = "583a";
		String usrname = "root";
		String passwrd = "19920930";
		QueryExecutor executor;
		try{
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?user=" + usrname + "&password=" + passwrd);
			executor = QueryExecutor.getInstance("jdbc:mysql://localhost:3306/" + database ,usrname , passwrd);
//			System.out.println("Succsee");
//			statement = conn.createStatement();
			// delay query executions
			Thread.sleep(10000);
			// most given reviews (rate by stars)
			resultSet = executor.executeQuery("SELECT stars, COUNT(stars) AS star_occurrence FROM review GROUP BY stars DESC LIMIT 1;");
			while(resultSet.next()){
			String mostStar = resultSet.getString(1);
				System.out.println(mostStar);
			// number of businesses with highest ratings
			String mostStarCount = resultSet.getString(2);
				System.out.println(mostStarCount);
			}
			Thread.sleep(10000);
			// users with most review counts
			resultSet = executor.executeQuery("SELECT name, review_count FROM business WHERE review_count = (SELECT MAX(review_count) FROM business);");
			while(resultSet.next()){
				String mostReviewUser = resultSet.getString(1);
				System.out.println(mostReviewUser);
			}
			Thread.sleep(10000);
			// top 10 users with most fans
			resultSet = executor.executeQuery("SELECT name FROM user ORDER BY fans DESC LIMIT 10;");
			String[] topUsers = new String[10];
			int i = 0;
			while(resultSet.next()){
				topUsers[i++] = resultSet.getObject(1).toString();
				System.out.println(topUsers);
			}
			Thread.sleep(10000);
			// users getting most likes of their tips
			//resultSet = statement.executeQuery("CREATE TABLE user_tip AS INNER JOIN tip on user.user_id = tip.user_id");
			//resultSet = statement.executeQuery("SELECT name, likes FROM (INNER JOIN tip on user.user_id = tip.user_id WHERE likes = (SELECT MAX(likes) from user_tip)");
			resultSet = executor.executeQuery("SELECT user_tip.name, MAX(user_tip.likes) FROM (SELECT name, likes FROM user INNER JOIN tip on user.user_id = tip.user_id) AS user_tip GROUP BY user_tip.name;");
			while(resultSet.next()){
				String mostLikedUser = resultSet.getString(1);
				System.out.println(mostLikedUser);
			}
			Thread.sleep(10000);
			// business with highest rating
			resultSet = executor.executeQuery("SELECT name, stars FROM business ORDER BY stars DESC LIMIT 1;");
			while(resultSet.next()){
				String mostLikedBusiness = resultSet.getString(1);
				System.out.println(mostLikedBusiness);
			}
			/*
			while(resultSet.next()){
				String id = resultSet.getString(1);
				String email = resultSet.getString(2);
				System.out.println("DB:" + id + ":" + email);
			}
			*/
		}catch(Exception e){
			throw e;
		}finally {
			
		}
		
	}

}
