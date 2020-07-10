package com.bricboys.zxapp.details;

import android.util.Log;

import com.bricboys.zxapp.ParameterClass;

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

public class TapeQuery {
    private final static String GENERAL_URL =
            "https://api.zxinfo.dk/api/zxinfo/games/";

    private TapeQuery(){}

    public static List<Tapes> extractTapes(String parametro) {

        ArrayList<Tapes> tapes = new ArrayList<>();
        //tapes = null;

        URL url = createUrl(GENERAL_URL, parametro);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error http request", e.getMessage());
        }

        JSONObject root;

        String mSize = "";
        String mUrl = "";
        String mType = "";
        String mFormat = "";
        String mEncoding = "";
        String mPublisher = "";
        String mOrigin = "";

        try {
            root = new JSONObject(jsonResponse);
            JSONObject _source = root.getJSONObject("_source");
            JSONArray releases = _source.getJSONArray("releases");
            for(int i=0;i<releases.length();i++) {
                JSONObject obj = releases.getJSONObject(i);
                mSize = obj.optString("size");
                mUrl = obj.optString("url");
                mType = obj.optString("type");
                mFormat = obj.optString("format");
                mEncoding = obj.optString("encodingscheme");
                mPublisher = obj.optString("publisher");
                mOrigin = obj.optString("origin");

                if (!mUrl.isEmpty()) {
                    String Url = mUrl;
                    String newUrl = "";
                    if (Url.substring(0, 14).equals("/pub/sinclair/")) {
                        newUrl = Url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                    } else {
                        newUrl = Url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                    }
                    tapes.add(new Tapes(mSize, newUrl, mType, mFormat, mEncoding, mPublisher, mOrigin));
                }
            }
        } catch (JSONException e) {
            Log.e("Error parsing JSON", e.getMessage().toString());
        }

        return tapes;

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
