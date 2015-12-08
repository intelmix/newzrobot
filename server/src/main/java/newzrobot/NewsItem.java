package newzrobot;

public class NewsItem {
    private final String link;
    private final String title;
    private final String source;
    private final int timestamp;

    public NewsItem(String title, String source, String link, int timestamp) {
        this.title = title;
        this.source = source;
        this.link = link;
        this.timestamp = timestamp;
    }

    public String getLink() {
        return this.link;
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




