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

        result.add(new NewsItem("Along with Trump’s rhetoric, the stakes for 2016 have risen dramatically", "Washington Post", "https://www.washingtonpost.com/politics/along-with-trumps-rhetoric-the-stakes-for-2016-have-risen-dramatically/2015/12/08/43e64592-9dd8-11e5-a3c5-c77f2cc5a43c_story.html", 1123213213));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));

        return result;
    }

}

