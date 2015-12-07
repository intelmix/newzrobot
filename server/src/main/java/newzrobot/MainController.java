package newzrobot;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    private int counter = 0;

    @RequestMapping("/news")
    public List<NewsItem> news() {
        System.out.println("Configured for news");
        
        List<NewsItem> result = new ArrayList<NewsItem>();
        counter++;

        result.add(new NewsItem(Integer.toString(counter), "title1", "source1", 10));
        result.add(new NewsItem(Integer.toString(counter), "title2", "source2", 20));

        return result;
    }

}

