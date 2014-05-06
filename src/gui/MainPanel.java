package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JPanel;

import tweets.TweetStreamer;
import twitter4j.Status;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class MainPanel extends JPanel {

	private TweetStreamer _ts;
	private DrawingPanel _dp;
	private SearchField _sf;
	private SearchButton _sb;
	private String _currQuery;
	private HashMap<String, DrawingPanel> _history;
	
	public MainPanel() {
		super();
		_dp = new DrawingPanel();
		_ts = new TweetStreamer(this);
		_sf = new SearchField(this);
		_sb = new SearchButton(this);
		this.setLayout(new BorderLayout());
		JPanel newP = new JPanel(new GridLayout(1,2));
		newP.add(_sf);
		newP.add(_sb);
		this.add(newP, BorderLayout.NORTH);
		this.add(_dp, BorderLayout.SOUTH);
		_sf.grabFocus();
		_currQuery = "";
		_history = new HashMap<String, DrawingPanel>();
		
	}
	
	
	public void receiveTweet(Status tweet) {
		String polarity = this.getSentiment(tweet.getText());
		_dp.sendUpdate(polarity);
//		_dp.sendUpdate(polarity);
//		_dp.sendUpdate(polarity);
//		_dp.sendUpdate(polarity);
//		_dp.sendUpdate(polarity);
	}
	
	public String getSentiment(String s) {
		try {
			URI uri = new URI("https", 
					"api.sentigem.com", 
					"/external/get-sentiment", 
					"api-key=82da342099b3b13a83b53df12c3199f77IgUio5wP1sWA2Mad-vOVG3S4TjeYcDQ&text="+s,
					null);
			URL url = uri.toURL();
			//URL url = new URL("https://api.sentigem.com/external/get-sentiment?api-key=82da342099b3b13a83b53df12c3199f77IgUio5wP1sWA2Mad-vOVG3S4TjeYcDQ&text=love");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			String responseString =  response.toString();
			String polarity = responseString.split(",")[4].split(":")[1].replaceAll("^\"|\"$", "");
			return polarity;

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "neutral";
		
		
	}


	public void search() {
		_sf.grabFocus();		
		_ts.streamTweets(_sf.getText());
		
		
	}
	
	
	
}
