package prova;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SoundcloudDownloader {
	
	private static final String CLIENT_ID = "4338d980b7b45b8f6d51583a1ccd2a91";
	private static final Gson gson = new Gson();
	
	private static JsonObject getTrackInfo(String resolveUrl) {
		JsonObject trackInfo = null;
		try {
			URL url = new URL(resolveUrl + "&client_id=" + CLIENT_ID);
			URLConnection connection = url.openConnection();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = in.readLine();
			in.close();
			
			if (response != null) {
				trackInfo = gson.fromJson(response, JsonObject.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trackInfo;
	}
	
	private static void saveMp3(String mp3Url, String filename) {
		try {
			URL url = new URL(mp3Url + "?client_id=" + CLIENT_ID);
			URLConnection connection = url.openConnection();
			
			InputStream is = connection.getInputStream();

		    OutputStream outstream = new FileOutputStream(new File("C:\\Users\\dariot\\Dropbox\\music\\" + filename + ".mp3"));
		    byte[] buffer = new byte[4096];
		    int len;
		    while ((len = is.read(buffer)) > 0) {
		        outstream.write(buffer, 0, len);
		    }
		    outstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getMp3(String trackUrl) {
		String resolveUrl = "https://api.soundcloud.com/resolve?url=" + trackUrl;
		JsonObject trackInfo = getTrackInfo(resolveUrl);
		if (trackInfo != null) {
			String title = trackInfo.get("title").toString().replaceAll("\"", "");
			String mp3Url = trackInfo.get("stream_url").toString().replaceAll("\"", "");
			saveMp3(mp3Url, title);
		}
	}
	
	private static void initUI() {
		JFrame mainFrame = new JFrame();
		
		JPanel mainPanel = new JPanel(new SpringLayout());
		
		JLabel clientIdLabel = new JLabel("Client ID", JLabel.TRAILING);
		JLabel songLabel = new JLabel("Song URL", JLabel.TRAILING);
		
		JTextField clientIdTF = new JTextField();
		JTextField songTF = new JTextField();
		
		JPanel panel = new JPanel(new SpringLayout());
		panel.add(clientIdLabel);
		panel.add(clientIdTF);
		panel.add(songLabel);
		panel.add(songTF);
		mainFrame.getContentPane().add(panel);
		
		SpringUtilities.makeCompactGrid(panel,
                2, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
//		
//		JPanel buttonPanel = new JPanel();
//		JButton downloadBtn = new JButton("Download");
//		buttonPanel.add(downloadBtn);
//		mainFrame.getContentPane().add(buttonPanel);
		
//		mainFrame.add(mainPanel);
		
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		String trackUrl = "https://soundcloud.com/sirius7/smoke-weed-everyday-rasmus";
		initUI();
		//getMp3(trackUrl);
	}

}
