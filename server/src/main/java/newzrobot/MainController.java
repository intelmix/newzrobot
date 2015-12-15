package newzrobot;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@RestController
public class MainController {

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String register(@RequestBody AuthRequest req) {
        //remove Bearer: from the beginning of the string
        //access_token = access_token.substring(7);

        String access_token = req.getToken();
        
        System.out.println("Register request received: "+access_token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        User user = restTemplate.getForObject("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + access_token, User.class);

        System.out.println("Response received and parsed!");
        System.out.println("id is: "+user.getId());
        System.out.println("name is: "+user.getName());

        //Sample response:
        //{
        // "id": "108051250147721565705",
        //  "name": "Mahdi Mohammadi",
        //   "given_name": "Mahdi",
        //    "family_name": "Mohammadi",
        //     "link": "https://plus.google.com/+MahdiMohammadinasab",
        //      "picture": "https://lh3.googleusercontent.com/-SAD9UVfwQZU/AAAAAAAAAAI/AAAAAAAAAMA/r-4abH4SfD8/photo.jpg",
        //       "gender": "male",
        //        "locale": "en"
        //        }
        //}
        //

        //TODO: make sure this token is issues for my application by clling: https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=?
        //expected result:
        //{
        //  "issued_to": "407408718192.apps.googleusercontent.com", 
        //    "user_id": "108051250147721565705", 
        //      "expires_in": 3573,  #is in seconds
        //        "access_type": "offline", 
        //          "audience": "407408718192.apps.googleusercontent.com", 
        //            "scope": "https://www.googleapis.com/auth/userinfo.profile"
        //            }
        //}
        //
        
        
        //now we have to save information to db

        return "true";
    }

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

