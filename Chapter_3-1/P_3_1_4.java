import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_4 {
    public static class Time implements Comparable<Time> {
        private final int sec; // seconds since 00:00:00
        private final int SEC_TO_HR = 3600;
        private final int SEC_TO_MIN = 60;

        public Time(int h, int m, int s) {
            // validate hour, min, sec
            if (h < 0 || h > 23 || m < 0 || m > 59 || s < 0 || s > 59) {
                throw new IllegalArgumentException("invalid time: " + h + ":" + m + ":" + s);
            }
            this.sec = h * SEC_TO_HR + m * SEC_TO_MIN + s;
        }

        // Read a time-format string and construct Time
        public static Time parse(String hhmmss) {
            String[] p = hhmmss.trim().split(":");
            if (p.length != 3) throw new IllegalArgumentException("HH:MM:SS expected: " + hhmmss);
            return new Time(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
        }
    
        public int hour()   { return sec / 3600; }
        public int minute() { return (sec / 60) % 60; }
        public int second() { return sec % 60; }
        public int toSeconds() { return sec; }

        @Override
        public boolean equals(Object o) {
            return (o instanceof Time) && ((Time) o).sec == this.sec;
        }

        @Override
        public int compareTo(Time that) {
            return Integer.compare(this.sec, that.sec);
        }

        @Override public int hashCode() { return sec; }

        @Override public String toString() {
            return String.format("%02d:%02d:%02d", hour(), minute(), second());
        }
        
    }

    public static class Event {
        private final String des; // description
        public Event(String des) {
            this.des = des;
        }

        @Override 
        public String toString() { return des; }

        @Override 
        public boolean equals(Object o) {
            return (o instanceof Event) && ((Event) o).des.equals(this.des);
        }

        @Override 
        public int hashCode() { return des.hashCode(); }
    }

    public static void main(String[] args) {
        BinarySearchST<Time, Event> st = new BinarySearchST<>();

        String[][] data = {
            {"09:00:00","Chicago"}, {"09:00:03","Phoenix"}, {"09:00:13","Houston"},
            {"09:00:59","Chicago"}, {"09:01:10","Houston"}, {"09:03:13","Chicago"},
            {"09:10:11","Seattle"}, {"09:10:25","Seattle"}, {"09:14:25","Phoenix"},
            {"09:19:32","Chicago"}, {"09:19:46","Chicago"}, {"09:21:05","Chicago"},
            {"09:22:43","Seattle"}, {"09:22:54","Seattle"}, {"09:25:52","Chicago"},
            {"09:35:21","Chicago"}, {"09:36:14","Seattle"}, {"09:37:44","Phoenix"}
        };

        for (String[] pair : data) {
            st.put(Time.parse(pair[0]), new Event(pair[1]));
        }

        // min()
        Time t = st.min();
        StdOut.println("min() -> " + t + "  " + st.get(t));

        // get(09:00:13)
        Time tGet = Time.parse("09:00:13");
        StdOut.println("get(" + tGet + ") -> " + st.get(tGet));

        // floor(09:05:00)
        Time tFloor = Time.parse("09:05:00");
        Time f = st.floor(tFloor);
        StdOut.println("floor(" + tFloor + ") -> " + (f == null ? "null" : f + "  " + st.get(f)));

        // select(7)
        int k = 7;
        Time kth = st.select(k);
        StdOut.println("select(" + k + ") -> " + kth + "  " + st.get(kth));

        // keys(09:15:00, 09:25:00) and size in that range
        Time lo = Time.parse("09:15:00");
        Time hi = Time.parse("09:25:00");
        List<Time> range = new ArrayList<>();
        for (Time key : st.keys(lo, hi)) {
            range.add(key);
        }
        StdOut.println("keys(" + lo + ", " + hi + ") ->");
        for (Time key : range) StdOut.println("  " + key + "  " + st.get(key));
        StdOut.println("size(" + lo + ", " + hi + ") = " + range.size());

        // ceiling(09:30:00)
        Time cKey = Time.parse("09:30:00");
        Time c = st.ceiling(cKey);
        StdOut.println("ceiling(" + cKey + ") -> " + (c == null ? "null" : c + "  " + st.get(c)));

        // max()
        Time tMax = st.max();
        StdOut.println("max() -> " + tMax + "  " + st.get(tMax));

        // rank(09:10:25)
        Time rKey = Time.parse("09:10:25");
        StdOut.println("rank(" + rKey + ") = " + st.rank(rKey));

    }
}

// min() -> 09:00:00  Chicago
// get(09:00:13) -> Houston
// floor(09:05:00) -> 09:03:13  Chicago
// select(7) -> 09:10:25  Seattle
// keys(09:15:00, 09:25:00) ->
//   09:19:32  Chicago
//   09:19:46  Chicago
//   09:21:05  Chicago
//   09:22:43  Seattle
//   09:22:54  Seattle
// size(09:15:00, 09:25:00) = 5
// ceiling(09:30:00) -> 09:35:21  Chicago
// max() -> 09:37:44  Phoenix
// rank(09:10:25) = 7
