package tweets;

import gui.MainPanel;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetStreamer {

	private TweetListener _listener;
	private TwitterStream _twitterStream;
	private MainPanel _mp;
	
	public TweetStreamer(MainPanel mp) {
		_mp = mp;
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("kv5gG7oF0rTkoqGior0oX60gl");
		builder.setOAuthConsumerSecret("Jfk7mFcxR7xZMCpaJNKtGhYt7ULlCnjXO6f5qXZFMOzdjGowkp");
		builder.setOAuthAccessToken("2466574248-mFi3jjVekBgLtnwNWz0Hp9RhSf7EoOeFYz7G3P6");
		builder.setOAuthAccessTokenSecret("314CgMwjiI5qp2cda7wjdUytMPikgAQOCWF2NEdGqKrS5");
		//builder.setJSONStoreEnabled(true);
		builder.setDebugEnabled(false);
	
		_listener = new TweetListener(_mp);
		_twitterStream = new TwitterStreamFactory(builder.build()).getInstance();
		_twitterStream.addListener(_listener);
	}
	
	public void streamTweets(String s) {
		FilterQuery fq = new FilterQuery();
		String[] track = {s};
		fq.track(track);
		_twitterStream.filter(fq);
	}

	
	public void close() {
		_twitterStream.cleanUp();
		_twitterStream.shutdown();
	}
}
