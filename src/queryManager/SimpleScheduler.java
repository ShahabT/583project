package queryManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class SimpleScheduler extends Scheduler {
    public SimpleScheduler(HashMap<String, ArrayList<QueryProfile>> profile) {
        super(profile);
    }

    public String[] getSchedule() {
        profile.remove("START");
        String[] queries = profile.keySet().toArray(new String[]{});
        Arrays.sort(queries, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (int)(profile.get(o1).get(0).start-profile.get(o2).get(0).start);
            }
        });
        return queries;
    }
}
