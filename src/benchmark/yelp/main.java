package test;

import java.io.Closeable;
import java.lang.reflect.WildcardType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.text.ElementIterator;

import com.mysql.jdbc.DatabaseMetaData;


public class main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String database = "583a";
		String usrname = "root";
		String passwrd = "19920930";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?user=" + usrname + "&password=" + passwrd);
			System.out.println("Succsee");
			statement = conn.createStatement();
			// delay query executions
			//Thread.sleep(10000);
			// most given reviews (rate by stars)
			//resultSet = statement.executeQuery("SELECT stars, COUNT(stars) AS star_occurrence FROM review GROUP BY stars DESC LIMIT 1;");
			//String mostStar = resultSet.getString(1);
			//System.out.println(mostStar);
			// number of businesses with highest ratings
			//String mostStarCount = resultSet.getString(2);
			//System.out.println(mostStarCount);
			// users with most review counts
			resultSet = statement.executeQuery("SELECT name, review_count FROM business WHERE review_count = (SELECT MAX(review_count) FROM business);");
			while(resultSet.next()){
			String mostReviewUser = resultSet.getString(1);
			System.out.println(mostReviewUser);
			}
			// top 10 users with most fans
			resultSet = statement.executeQuery("SELECT name FROM user ORDER BY fans DESC LIMIT 10;");
			String[] topUsers = new String[10];
			int i = 0;
			while(resultSet.next()){
				topUsers[i++] = resultSet.getString(1);
			}
			System.out.println(topUsers);
			// users getting most likes of their tips
			resultSet = statement.executeQuery("SELECT name, likes FROM user INNER JOIN tip on user.user_id = tip.user_id WHERE likes = (SELECT MAX(likes) from user INNER JOIN tip on user.user_id = tip.user_id)");
			while(resultSet.next()){
			String mostLikedUser = resultSet.getString(1);
			System.out.println(mostLikedUser);
			}
			// business with highest rating
			resultSet = statement.executeQuery("SELECT name, stars FROM business ORDER BY stars DESC LIMIT 1;");
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
			conn.close();
		}
		
	}

}
