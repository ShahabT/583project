package queryManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class PrefetcherQueryExecutor extends QueryExecutor {
    QueryPrefetcher prefetcher;

    protected PrefetcherQueryExecutor(String connectionStr, String username, String password) throws ClassNotFoundException, SQLException {
        super(connectionStr, username, password);
        prefetcher =new QueryPrefetcher(this.connection.createStatement());
        prefetcher.start();
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        sql = sql.replace('\n', ' ').replace('\r', ' ');

        System.out.println("RECEIVED QUERY: "+sql);

        if(prefetcher.isAlive())
            prefetcher.pause(sql);

        ResultSet result = statement.executeQuery(sql);

        if(prefetcher.isAlive())
            prefetcher.go();

        return result;
    }

    @Override
    public void close() throws SQLException {
        prefetcher.close();
        super.close();
    }
}

