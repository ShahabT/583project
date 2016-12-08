package queryManager;

class QueryProfile {
    String sql;
    long start, end;

    public QueryProfile(String sql, long start) {
        this.sql = sql;
        this.start = start;
    }

    public long getDuration() {
        return end - start;
    }
}
