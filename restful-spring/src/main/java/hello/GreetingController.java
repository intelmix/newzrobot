package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class GreetingController {
    private static final String template = "HEllo %s";
    private final AtomicLong counter = new AtomicLong();
    
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);


    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="world") String name ) {
        logger.debug("Creating handler for greeting for "+name);
        System.out.println("Configured for "+name);


        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}

