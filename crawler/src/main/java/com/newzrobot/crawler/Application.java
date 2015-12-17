package com.newzrobot.crawler;
//TODO: change package names to include intelmix

import com.rometools.rome.io.*;
import com.rometools.rome.feed.synd.*;
import java.net.URL;
import java.util.List;

public class Application {
    public static void main(String[] args) throws java.net.MalformedURLException,java.io.IOException, com.rometools.rome.io.FeedException  {
        URL feedUrl = new URL("https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss");

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        System.out.println("Feed Title: " + feed.getTitle());
        int counter=0;

        // Get the entry items...
        for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
            counter++;
            if ( counter > 5) break;
            System.out.println("Title: " + entry.getTitle());
            System.out.println("Unique Identifier: " + entry.getUri());
            System.out.println("Updated Date: " + entry.getUpdatedDate());

            // Get the Links
            for (SyndLink link : (List<SyndLink>) entry.getLinks()) {
                System.out.println("Link: " + link.getHref());
            }            

            // Get the Contents
            for (SyndContent content : (List<SyndContent>) entry.getContents()) {
                System.out.println("Content: " + content.getValue());
            }

            // Get the Categories
            for (SyndCategory category : (List<SyndCategory>) entry.getCategories()) {
                System.out.println("Category: " + category.getName());
            }
        }

        System.out.println("Done!");
    }
}
