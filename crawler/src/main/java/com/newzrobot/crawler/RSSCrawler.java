package com.newzrobot.crawler;

import java.net.URL;
import java.io.InputStreamReader;
import com.rometools.rome.io.*;
import com.rometools.rome.feed.synd.*;


public class RSSCrawler {
    public void test() throws java.net.MalformedURLException,java.io.IOException, com.rometools.rome.io.FeedException  {
        URL feedUrl = new URL("");

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
    }
}
