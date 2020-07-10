package com.bricboys.zxapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class ZxartQuery {

    private final static String ZXART_URL =
            "https://zxart.ee/api/export:zxPicture/filter:zxPictureSearch=";

    private ZxartQuery() {};

    public static List<ZxArt> extractZxart(String parametro) {
        URL url = createUrl(ZXART_URL,parametro);

        String jsonResponse = null;
        //Bitmap jsonImage = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error http request", e.getMessage());
        }

        ArrayList<ZxArt> zxarts = new ArrayList<>();
        JSONObject root;

        try {
            root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("responseData");
            JSONArray pictures = response.getJSONArray("zxPicture");
            for(int i=0; i<pictures.length(); i++) {
                JSONObject obj = pictures.getJSONObject(i);
                String urlPic = obj.optString("imageUrl");
                String title = obj.optString("title");
                String year = obj.optString("year");
                String rating = obj.optString("rating");
                Log.e("Arts: ", urlPic);
                zxarts.add(new ZxArt(urlPic,title,rating,year,null) );
            }
        } catch (JSONException e) {
            Log.e("Error parsing json ", e.getMessage());
        }

        return zxarts;
    }

    private static URL createUrl(String zxartUrl, String parametro) {
        URL url = null;
        //String parametroFormatted = parametro.replaceAll(" ", "%20");
        String newUrl = zxartUrl + parametro;
        Log.e("URL PIC: ", newUrl);

        try {
            url = new URL(newUrl);
        } catch (MalformedURLException e) {
            Log.e("Error in URL ", e.getMessage());
        }
        return  url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(150000);
            urlConnection.connect();

            Log.e("response: ", urlConnection.getResponseCode() + "");
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("LOG", "Error response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("LOG", "Problem retrieving json response ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
