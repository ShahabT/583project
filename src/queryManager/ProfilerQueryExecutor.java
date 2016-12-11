package queryManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfilerQueryExecutor extends QueryExecutor {
    HashMap<String, ArrayList<QueryProfile>> log = new HashMap<>();

    protected ProfilerQueryExecutor(String connectionStr, String username, String password) throws ClassNotFoundException, SQLException {
        super(connectionStr, username, password);
        log.put("START", new ArrayList<QueryProfile>());
        log.get("START").add(new QueryProfile("START", System.currentTimeMillis()));
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        sql = sql.replace('\n', ' ').replace('\r', ' ');

        System.out.println("### PROFILING QUERY: "+sql);

        QueryProfile profile = new QueryProfile(sql, System.currentTimeMillis());
        ResultSet result = statement.executeQuery(sql);
        profile.end = System.currentTimeMillis();

        if(!log.containsKey(sql))
            log.put(sql, new ArrayList<QueryProfile>());
        log.get(sql).add(profile);

        return result;
    }

    @Override
    public void close() throws SQLException {
        System.out.println("### GENERATING PREFETCH SCHEDULE...");

        Scheduler sch = new SmartScheduler(log);
        sch.dumpSchedule("src/queryManager/QueryPrefetcher.java.template", "src/queryManager/QueryPrefetcher.java");
        super.close();
    }
}


