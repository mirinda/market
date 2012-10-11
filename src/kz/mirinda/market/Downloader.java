package kz.mirinda.market;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Downloader {
	public Bitmap downloadImage(String urlString) {
		Bitmap bitmap=null;
		try {
			URL url = new URL(urlString);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection();
			 bitmap = BitmapFactory.decodeStream(con.getInputStream());
		} catch (IOException e) {
			Log.e("mirinda1", "Cannot open connection!");
		}
		return bitmap;
	}
}
