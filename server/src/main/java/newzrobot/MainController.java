package newzrobot;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

@RestController
public class MainController {
    private static final String template = "HEllo %s";
    private static final String template2 = "another hello %s";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/news")
    public ArrayList<NewsItem> news(@RequestParam(value="name", defaultValue="world") String name ) {
        System.out.println("Configured for "+name);
        
        ArrayList<NewsItem> result = new ArrayList<NewsItem>();

        result.add(new NewsItem(counter.incrementAndGet(), String.format(template, name)));
        result.add(new NewsItem(counter.incrementAndGet(), String.format(template2, name)));

        return result;
    }

}

