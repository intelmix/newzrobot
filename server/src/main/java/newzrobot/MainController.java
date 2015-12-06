package newzrobot;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class MainController {
    private static final String template = "HEllo %s";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/news")
    public NewsItem news(@RequestParam(value="name", defaultValue="world") String name ) {
        System.out.println("Configured for "+name);

        return new NewsItem(counter.incrementAndGet(), String.format(template, name));
    }

}

