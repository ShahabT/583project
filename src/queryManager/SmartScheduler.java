package queryManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class SmartScheduler extends Scheduler {
    public SmartScheduler(HashMap<String, ArrayList<QueryProfile>> profile) {
        super(profile);
    }

    public String[] getSchedule() {
        ArrayList<QueryProfile> runsList = new ArrayList<>();
        for(ArrayList<QueryProfile> ps: profile.values())
            runsList.addAll(ps);

        QueryProfile[] runs = runsList.toArray(new QueryProfile[0]);
        Arrays.sort(runs, new Comparator<QueryProfile>() {
            @Override
            public int compare(QueryProfile o1, QueryProfile o2) {
                return (int) (o1.start - o2.start);
            }
        });

        long runtime=0, start=profile.get("START").get(0).start;
        ArrayList<String> queries = new ArrayList<>();

        for(QueryProfile p: runs){
            if(p.sql.equals("START")){
                continue;
            }
            long qStart= p.start-start, qEnd = p.end-start;
            if(!queries.contains(p.sql) && p.getDuration()+runtime<qStart){
                queries.add(p.sql);
                runtime+=p.getDuration();
            }
            runtime+=p.getDuration();
        }

        return queries.toArray(new String[0]);
    }
}
