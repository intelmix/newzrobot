package newzrobot;

public class NewsItem {
    private final String id;
    private final String title;
    private final String source;
    private final int timestamp;

    public NewsItem(String id, String title, String source, int timestamp) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.timestamp = timestamp;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSource() {
        return this.source;
    }

    public int getTimestamp() {
        return this.timestamp;
    }
}




