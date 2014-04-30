package tweets;

import gui.MainPanel;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TweetListener implements StatusListener {

	private int _counter = 0;
	private MainPanel _mp;
	
	public TweetListener(MainPanel mp) {
		_mp = mp;
	}

	@Override
	public void onException(Exception arg0) {
		System.out.println("EXCEPTION: " +arg0.getMessage());
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		System.out.println("STALL WARNING: " +arg0.getMessage());
		
	}

	@Override
	public void onStatus(Status arg0) {
		_mp.receiveTweet(arg0);
		
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		System.out.println("TRACK LIMITATION NOTICE: " +arg0);
		
	}
	
}
