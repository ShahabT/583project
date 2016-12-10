package benchmark.sampleApp;

import queryManager.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        QueryExecutor executor;
        try {
            executor = QueryExecutor.getInstance("jdbc:mysql://bigdata.eecs.umich.edu/583proj", "root", "password");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Thread.sleep(20);

        try {
            ResultSet results = executor.executeQuery("select yr, sex, count(*) from nyc_death group by yr, sex");
            while (results.next())
                System.out.println(results.getObject(1)+"\t | "+results.getObject(2)+"\t | "+results.getObject(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            executor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
