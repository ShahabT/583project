package benchmark.yelp;

import java.sql.ResultSet;

import queryManager.QueryExecutor;


public class Main {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        int wait = args.length > 0 ? Integer.parseInt(args[0]) : 10000;

        ResultSet resultSet = null;
        String usrname = "root";
        String passwrd = "password";
        QueryExecutor executor = null;
        try {
            executor = QueryExecutor.getInstance("jdbc:mysql://bigdata.eecs.umich.edu/583proj", usrname, passwrd);
//			System.out.println("Succsee");
//			statement = conn.createStatement();
            // delay query executions
            Thread.sleep(wait);

            long qStart = System.currentTimeMillis();
            // most given reviews (rate by stars)
            resultSet = executor.executeQuery("SELECT stars, COUNT(stars) AS star_occurrence FROM review GROUP BY stars DESC LIMIT 1;");
            long qEnd = System.currentTimeMillis();
            System.out.println(">>> Q1 TIME: "+(qEnd-qStart));
            while (resultSet.next()) {
                String mostStar = resultSet.getString(1);
                System.out.println(mostStar);
                // number of businesses with highest ratings
                String mostStarCount = resultSet.getString(2);
                System.out.println(mostStarCount);
            }

            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            // users with most review counts
            resultSet = executor.executeQuery("SELECT name, review_count FROM business WHERE review_count = (SELECT MAX(review_count) FROM business);");
            qEnd = System.currentTimeMillis();
            System.out.println(">>> Q2 TIME: "+(qEnd-qStart));
            while (resultSet.next()) {
                String mostReviewUser = resultSet.getString(1);
                System.out.println(mostReviewUser);
            }

            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            // top 10 users with most fans
            resultSet = executor.executeQuery("SELECT name FROM user ORDER BY fans DESC LIMIT 10;");
            String[] topUsers = new String[10];
            qEnd = System.currentTimeMillis();
            System.out.println(">>> Q3 TIME: "+(qEnd-qStart));
            int i = 0;
            while (resultSet.next()) {
                topUsers[i++] = resultSet.getObject(1).toString();
                System.out.println(topUsers);
            }

            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            // users getting most likes of their tips
            //resultSet = statement.executeQuery("CREATE TABLE user_tip AS INNER JOIN tip on user.user_id = tip.user_id");
            //resultSet = statement.executeQuery("SELECT name, likes FROM (INNER JOIN tip on user.user_id = tip.user_id WHERE likes = (SELECT MAX(likes) from user_tip)");
            resultSet = executor.executeQuery("SELECT user_tip.name, MAX(user_tip.likes) FROM (SELECT name, likes FROM user INNER JOIN tip on user.user_id = tip.user_id) AS user_tip GROUP BY user_tip.name;");
            qEnd = System.currentTimeMillis();
            System.out.println(">>> Q4 TIME: "+(qEnd-qStart));
            while (resultSet.next()) {
                String mostLikedUser = resultSet.getString(1);
                System.out.println(mostLikedUser);
            }

            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            // business with highest rating
            resultSet = executor.executeQuery("SELECT name, stars FROM business ORDER BY stars DESC LIMIT 1;");
            qEnd = System.currentTimeMillis();
            System.out.println(">>> Q5 TIME: "+(qEnd-qStart));
            while (resultSet.next()) {
                String mostLikedBusiness = resultSet.getString(1);
                System.out.println(mostLikedBusiness);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (executor != null)
                executor.close();
        }

        long end = System.currentTimeMillis();
        System.out.println(">>> RUN TIME: "+(end-start));
    }

}
