package com.intelmix.newzrobot.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

//TODO: make this class more tidy, not everything should be statis, move almost all code to their respective classes
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Application implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); 


    @Autowired
    private FeedCrawler crawler;

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(
                Application.class);
        application.setWebEnvironment(false);
        application.setBannerMode(Banner.Mode.OFF);
        ApplicationContext ctx =application.run(args);
        Environment env = ctx.getEnvironment();
        System.out.println("value of port is " + env.getProperty("server.port"));
    }

    //TODO: better logging for info, warn and exceptions
    //TODO: use LogEntries if it does not need much overhead
    //TODO: Move all moving parts to a config filec
    @Override
    public void run(String... args) {
        crawler.doCrawl();
    }
}
