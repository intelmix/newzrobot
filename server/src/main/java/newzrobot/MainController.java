package newzrobot;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    @RequestMapping("/search/{query}")
    public List<NewsItem> search(@PathVariable("query") String query) {
        System.out.println("Request received for search: "+query);
        List<NewsItem> allNews = news();
        List<NewsItem> result = new ArrayList<NewsItem>();

        for(int i=0;i<allNews.size();i++) {
            if ( allNews.get(i).getTitle().contains(query) ) {
                result.add(allNews.get(i));
            }
        }

        return result;
    }

    @RequestMapping("/news")
    public List<NewsItem> news() {
        System.out.println("Request received for news");
        
        List<NewsItem> result = new ArrayList<NewsItem>();

        result.add(new NewsItem("Along with Trump’s rhetoric, the stakes for 2016 have risen dramatically", "Washington Post", "https://www.washingtonpost.com/politics/along-with-trumps-rhetoric-the-stakes-for-2016-have-risen-dramatically/2015/12/08/43e64592-9dd8-11e5-a3c5-c77f2cc5a43c_story.html", 1123213213));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));

        result.add(new NewsItem("Along with Trump’s rhetoric, the stakes for 2016 have risen dramatically", "Washington Post", "https://www.washingtonpost.com/politics/along-with-trumps-rhetoric-the-stakes-for-2016-have-risen-dramatically/2015/12/08/43e64592-9dd8-11e5-a3c5-c77f2cc5a43c_story.html", 1123213213));
        result.add(new NewsItem("Along with Trump’s rhetoric, the stakes for 2016 have risen dramatically", "Washington Post", "https://www.washingtonpost.com/politics/along-with-trumps-rhetoric-the-stakes-for-2016-have-risen-dramatically/2015/12/08/43e64592-9dd8-11e5-a3c5-c77f2cc5a43c_story.html", 1123213213));
        result.add(new NewsItem("Along with Trump’s rhetoric, the stakes for 2016 have risen dramatically", "Washington Post", "https://www.washingtonpost.com/politics/along-with-trumps-rhetoric-the-stakes-for-2016-have-risen-dramatically/2015/12/08/43e64592-9dd8-11e5-a3c5-c77f2cc5a43c_story.html", 1123213213));
        result.add(new NewsItem("Along with Trump’s rhetoric, the stakes for 2016 have risen dramatically", "Washington Post", "https://www.washingtonpost.com/politics/along-with-trumps-rhetoric-the-stakes-for-2016-have-risen-dramatically/2015/12/08/43e64592-9dd8-11e5-a3c5-c77f2cc5a43c_story.html", 1123213213));
        result.add(new NewsItem("Along with Trump’s rhetoric, the stakes for 2016 have risen dramatically", "Washington Post", "https://www.washingtonpost.com/politics/along-with-trumps-rhetoric-the-stakes-for-2016-have-risen-dramatically/2015/12/08/43e64592-9dd8-11e5-a3c5-c77f2cc5a43c_story.html", 1123213213));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        result.add(new NewsItem("San Bernardino shooting: Attackers may have left bomb to kill police, used loan to buy arms", "LA Times", "http://www.latimes.com/local/lanow/la-me-ln-san-bernardino-shooting-attackers-bomb-mainbar-20151208-story.html", 149123321));
        result.add(new NewsItem("Yahoo Shelves Plans to Spin Off Alibaba Stake", "WSJ", "http://www.wsj.com/articles/yahoo-shelves-plans-to-spin-off-alibaba-stake-1449662720", 149123121));
        result.add(new NewsItem("Drop in futures use signals Fed rate level uncertainties", "Reuters", "http://www.reuters.com/article/usa-fed-markets-futures-idUSL1N13Y23H20151209#2GOBJX3CQroeeU36.97", 149423321));
        result.add(new NewsItem("Middle-class families, pillar of the American dream, are no longer in the majority, study finds", "LA Times", "http://www.latimes.com/nation/la-fi-middle-class-erosion-20151209-story.html", 149123921));
        return result;
    }

}

