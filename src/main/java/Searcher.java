import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

public class Searcher {
    public static void main(String[] args) {
        UserAgent userAgent = new UserAgent("bot", "ru.search", "v0.1", "Kamperk_1908");
        Credentials credentials = Credentials.script(
            "Kamperk_1908",
            "Xxx412334!$",
            "ZRFxvN0RiRsLnA",
            "d5uyfmI4JhDKLG2gV0zHRi2p6-YC6w"
        );
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient reddit = OAuthHelper.automatic(adapter, credentials);
        DefaultPaginator<Submission> posts = reddit.subreddit("wallstreetbets").posts().timePeriod(TimePeriod.DAY)
            .build();
        List<String> listsum = new ArrayList<>();
        for (Listing<Submission> s : posts) {
            for (Submission post : s) {
                List<String> tickers = getTickersBig(Objects.requireNonNull(post.getSelfText()));
                listsum.addAll(tickers);
            }
        }
        HashMap<String, Integer> result = new HashMap<>();
        for (int i = 0; i < listsum.size(); i++) {
            result.put(listsum.get(i), Collections.frequency(listsum, listsum.get(i)));
        }
        Set<String> list = new HashSet<>();
        list.add("I");
        list.add("HERE");
        list.add("FOR");
        list.add("THE");
        list.add("S.");
        list.add("NOT");
        list.add("M");
        list.add("CEO");
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("P");
        list.add("S");



        result.entrySet().stream().sorted(Comparator.comparing(Entry::getValue))
            .filter(it -> !list.contains(it.getKey())).filter(it-> it.getValue()>10).forEach(System.out::println);


    }

    public static List<String> getTickersBig(String s ){
        List<String> tickers = new ArrayList<>();
        if(s.matches("([A-Z]{1,4})[ ,.]"));

        Pattern pattern = Pattern.compile("([A-Z]{1,4})[ ,.]");
        Matcher matcher = pattern.matcher(s);
        while(matcher.find()){
            String tick = matcher.group();
            tickers.add(tick.substring(0,tick.length()-1));
        }
        return tickers;
    }

}
