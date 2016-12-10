package queryManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Scheduler {
    HashMap<String, ArrayList<QueryProfile>> profile;
    public Scheduler(HashMap<String, ArrayList<QueryProfile>> profile){
        this.profile = profile;
    }

    public void dumpSchedule(String template, String fileName) {
        String[] queries = getSchedule();

        try {
            FileReader in = new FileReader(template);
            Scanner sc = new Scanner(in);

            StringBuilder buf = new StringBuilder();
            for(String sql: queries)
                buf.append("queue.add(\"").append(sql).append("\");\n");

            String queriesStr = buf.toString();

            buf = new StringBuilder();
            while (sc.hasNext())
                buf.append(sc.nextLine()).append("\n");

            String code = buf.toString()
                    .replace("/* #QUERY_COUNT# */", queries.length+"")
                    .replace("/* #QUERIES# */", queriesStr);

            FileWriter out = new FileWriter(fileName);

            out.write(code);

            out.close();
        } catch (IOException e) {
            System.out.println("couldn't write into file "+fileName);
            e.printStackTrace();
        }
    }

    public abstract String[] getSchedule();
}
