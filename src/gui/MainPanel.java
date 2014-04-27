package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JPanel;

import twitter4j.Status;

public class MainPanel extends JPanel {

	public MainPanel() {
		super();
		
	}
	
	
	public void receiveTweet(Status tweet) {
		this.getSentiment(tweet.getText());
	}
	
	public void getSentiment(String s) {
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
			
			System.out.println(response.toString());
			
		
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
