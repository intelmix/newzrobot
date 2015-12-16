package com.newzrobot.crawler;
//TODO: change package names to include intelmix

import java.net.URL;
import java.io.InputStreamReader;
import com.rometools.rome.io.*;
import com.rometools.rome.feed.synd.*;

public class Application {
    public static void main(String[] args) throws java.net.MalformedURLException,java.io.IOException, com.rometools.rome.io.FeedException  {
        URL feedUrl = new URL("https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss");

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
        System.out.println("Done!");
        System.out.println(feed.toString());
    }
}
