package com.bricboys.zxapp.details;

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

public class DetailsQuery {
    private final static String GENERAL_URL =
            "https://api.zxinfo.dk/api/zxinfo/games/";

    private DetailsQuery() {};

    public static Details extractDetails(String parametro) {

        Details detail = null;

        URL url = createUrl(GENERAL_URL, parametro);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error http request", e.getMessage());
        }

        JSONObject root;

        String mInlay = "";
        String mInstruction = "";
        String mRunningScreen = "";
        String mLoadingScreen = "";
        String mYouTube = "";

        try {
            root = new JSONObject(jsonResponse);
            JSONObject _source = root.getJSONObject("_source");
            JSONArray additionals = _source.getJSONArray("additionals");
            for(int i=0;i<additionals.length();i++) {
                JSONObject obj = additionals.getJSONObject(i);
                String type = obj.optString("type");
                if(type.equals("Cassette inlay")) {
                    mInlay = obj.optString("url");
                    Log.e("Inlay url: ", mInlay);
                    //break;
                }
            }
            JSONArray screens = _source.getJSONArray("screens");
            for(int i=0;i<screens.length();i++) {
                JSONObject obj = screens.getJSONObject(i);
                String type = obj.optString("type");
                if(type.equals("Running screen")) {
                    mRunningScreen = obj.optString("url");
                    break;
                }
            }
            for(int i=0;i<screens.length();i++) {
                JSONObject obj = screens.getJSONObject(i);
                String type = obj.optString("type");
                Log.e("Tipo: ", type);
                if(type.equals("Loading screen")||
                        type.equals("Running screen") ||
                        type.equals("Hardware thumbnail")) {
                    mLoadingScreen = obj.optString("url");
                    break;
                }
            }
            JSONArray youtubelinks = _source.getJSONArray("youtubelinks");
            for(int i=0;i<youtubelinks.length();i++) {
                JSONObject obj = youtubelinks.getJSONObject(i);
                String link = obj.optString("link");
                mYouTube = link;
            }
        } catch (JSONException e) {
            Log.e("Error parsing JSON", e.getMessage().toString());
        }

        detail = new Details(mRunningScreen,mInstruction,mInlay, mLoadingScreen, mYouTube);

        return detail;

    }

    private static URL createUrl(String gameUrl, String parametro) {
        URL url = null;
        String newUrl = gameUrl + parametro + "?mode=full";
        //Log.e("Nova URL: ", newUrl);
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
