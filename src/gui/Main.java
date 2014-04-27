package gui;

import tweets.TweetStreamer;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainPanel mp = new MainPanel();
		TweetStreamer ts = new TweetStreamer(mp);
		ts.streamTweets("obama");
	}

}
