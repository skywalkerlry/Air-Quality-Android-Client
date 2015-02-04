package com.airquality.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtils {

	public static String getJsonContent(String url_path) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(url_path);
			connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(3000);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			int code = connection.getResponseCode();
			Log.i("Code", String.valueOf(code));
			if (code == 200) {
				
				return convertToString(connection.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
		
		return null;
	}

	private static String convertToString(InputStream inputStream) {
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return jsonString;
	}

}
