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
import java.util.Date;
import java.util.List;

public class WallpaperQuery {

    private final static String RANDOM_URL = "https://api.zxinfo.dk/v3/games/random/1?mode=full";
            //"https://api.zxinfo.dk/api/zxinfo/games/random/1?mode=full";

    private WallpaperQuery(){}

    public static Wallpaper extractInfo(String mMode, String mTotal) {
        URL url = createUrl(RANDOM_URL, mTotal, mMode);

        String jsonResponse = null;
        //Bitmap jsonImage = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error http request", e.getMessage());
        }

        Wallpaper wallpaper = null ;
        JSONObject root;

        try {
            root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("hits");
            JSONArray hits = response.getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                JSONObject obj = hits.getJSONObject(i);
                JSONObject source = obj.getJSONObject("_source");
                JSONArray additionals = source.getJSONArray("screens");
                String loadingId = "";
                String runningId = "";
                for (int y = 0; y < additionals.length(); y++) {
                    JSONObject screen = additionals.getJSONObject(y);
                    String format = screen.optString("format");
                    String typeScreen = screen.optString("type");
                    if (format.substring(0, 7).equals("Picture") &&
                            (typeScreen.equals("Loading screen") ||
                                    typeScreen.equals("Running screen"))) {

                        if (typeScreen.equals("Loading screen") &&
                                  loadingId.isEmpty()) {
                            loadingId = screen.optString("url");
                            //break;
                        }
                        if(typeScreen.equals("Running screen") &&
                                 runningId.isEmpty()) {
                            runningId = screen.optString("url");
                            //break;
                        }
                    }
                }

                wallpaper = new Wallpaper(runningId, loadingId);

            }
        } catch (JSONException e) {
            Log.e("Error parsing json ", e.getMessage());
        }

        return wallpaper;
    }

    private static URL createUrl(String magazineUrl, String mTotal, String mMode) {
        URL url = null;
        //String parametroFormatted = mMode.replaceAll(/" ", "%20");
        String newUrl = magazineUrl; // + "?timestamp=" + new Date(); // + parametroFormatted + "&mode=full&sort=title_asc&size=1000&offset=0";
        //Log.e("URL random: ", newUrl);
        try {
            url = new URL(newUrl);
        } catch (MalformedURLException e) {
            Log.e("Error in URL ", e.getMessage());
        }
        return url;
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
            urlConnection.setRequestProperty("User-Agent", "ZX App/1.0");
            urlConnection.connect();

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
