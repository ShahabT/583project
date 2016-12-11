package benchmark.yelp;

import java.sql.ResultSet;

import queryManager.QueryExecutor;


public class Main {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis(), qStart, qEnd;

        int wait = args.length > 0 ? Integer.parseInt(args[0]) : 10000;

        int tableSize = 2417, lastPk = 9028133;
        int size = args.length > 1 ? Integer.parseInt(args[1]) - 187 - 74 : tableSize;
        long maxPk = (int) (lastPk * ((double) size) / tableSize);

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


            qStart = System.currentTimeMillis();
            // users with most review counts
            resultSet = executor.executeQuery("SELECT name, review_count FROM business WHERE review_count = (SELECT MAX(review_count) FROM business);");
            qEnd = System.currentTimeMillis();
            System.out.println("#>>> Q1 TIME: " + (qEnd - qStart));
            while (resultSet.next()) {
                String mostReviewUser = resultSet.getString(1);
                System.out.println(mostReviewUser);
            }

            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            // business with highest rating
            resultSet = executor.executeQuery("SELECT name, stars FROM business ORDER BY stars DESC LIMIT 1;");
            qEnd = System.currentTimeMillis();
            System.out.println("#>>> Q2 TIME: " + (qEnd - qStart));
            while (resultSet.next()) {
                String mostLikedBusiness = resultSet.getString(1);
                System.out.println(mostLikedBusiness);
            }

            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            // top 10 users with most fans
            resultSet = executor.executeQuery("SELECT name FROM user ORDER BY fans DESC LIMIT 10;");
            String[] topUsers = new String[10];
            qEnd = System.currentTimeMillis();
            System.out.println("#>>> Q3 TIME: " + (qEnd - qStart));
            int i = 0;
            while (resultSet.next()) {
                topUsers[i++] = resultSet.getObject(1).toString();
                System.out.println(topUsers);
            }


            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            resultSet = executor.executeQuery("SELECT avg(stars) AS star_avg FROM review where pk < " + (maxPk * .4));
            qEnd = System.currentTimeMillis();
            System.out.println("#>>> Q4 TIME: " + (qEnd - qStart));
            while (resultSet.next()) {
                String mostLikedUser = resultSet.getString(1);
                System.out.println(mostLikedUser);
            }

            Thread.sleep(wait);

            qStart = System.currentTimeMillis();
            // most given reviews (rate by stars)
            resultSet = executor.executeQuery("SELECT stars, COUNT(stars) AS star_occurrence FROM review where pk < " + (maxPk * .5) + " GROUP BY stars DESC LIMIT 1;");
            qEnd = System.currentTimeMillis();
            System.out.println("#>>> Q5 TIME: " + (qEnd - qStart));
            while (resultSet.next()) {
                String mostStar = resultSet.getString(1);
                System.out.println(mostStar);
                // number of businesses with highest ratings
                String mostStarCount = resultSet.getString(2);
                System.out.println(mostStarCount);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (executor != null)
                executor.close();
        }

        long end = System.currentTimeMillis();
        System.out.println("#>>> RUN TIME: " + (end - start));
    }

}
