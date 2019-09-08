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

public class GeneralQuery {

    private final static String GENERAL_URL =
            "https://api.zxinfo.dk/api/zxinfo/v2/search?query=";

    private GeneralQuery() {};

    public static List<General> extractGeneral(String parametro) {
        URL url = createUrl(GENERAL_URL, parametro);

        String jsonResponse = null;
        //Bitmap jsonImage = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error http request", e.getMessage());
        }

        ArrayList<General> generals = new ArrayList<>();
        JSONObject root;

        try {
            root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("hits");
            JSONArray hits = response.getJSONArray("hits");
            for(int i=0; i<hits.length(); i++) {
                JSONObject obj = hits.getJSONObject(i);
                String id = obj.optString("_id");
                JSONObject source = obj.getJSONObject("_source");
                String release = source.optString("yearofrelease");
                String title = source.optString("fulltitle");
                JSONArray publisher = source.getJSONArray("publisher");
                String pub = "";
                for(int x=0; x<publisher.length(); x++) {
                    JSONObject obj2 = publisher.getJSONObject(x);
                    if (x==0) {
                        pub = obj2.optString("name");
                    } else {
                        pub = pub + ", " + obj2.optString("name");
                    }
                }
                String available = source.optString("availability");
                String type = source.optString("type");
                String machine = source.optString("machinetype");
                JSONArray additionals = source.getJSONArray("screens");
                String imageId = "";
                String typeImage = "";
                for(int y=0;y<additionals.length();y++) {
                    JSONObject screen = additionals.getJSONObject(y);
                    String format = screen.optString("format");
                    String typeScreen = screen.optString("type");
                    // Log.e("title: ", title);
                    // Log.e("format: ", format);
                    // Log.e("type: ", typeScreen);
                    // Log.e("substr", format.substring(0,7));
                    if(format.substring(0,7).equals("Picture") &&
                            (typeScreen.equals("Loading screen")||
                             typeScreen.equals("Running screen") ||
                              typeScreen.equals("Hardware thumbnail"))) {
                        imageId = screen.optString("url");
                        typeImage = typeScreen.toString();
                        //Log.e("Loading screen: ", imageId);
                        break;
                    }
                }
                String urlYoutube = "";
                JSONArray youtube = source.getJSONArray("youtubelinks");
                for(int b=0;b<youtube.length();b++) {
                    JSONObject you = youtube.getJSONObject(b);
                    urlYoutube = you.optString("link");
                    break;
                }

                JSONObject score = source.getJSONObject("score");
                String scoreText = score.optString("score");
                if (scoreText=="null") {
                    scoreText="";
                }
                String auth="";
                JSONArray authors = source.getJSONArray("authors");
                for(int x=0; x<authors.length(); x++) {
                    JSONObject authors2 = authors.getJSONObject(x);
                    JSONArray authors3 = authors2.getJSONArray("authors");
                    for(int  y=0; y<authors3.length(); y++) {
                        JSONObject at = authors3.getJSONObject(y);
                        if (y == 0) {
                            auth = at.optString("name");
                        } else {
                            auth = auth + ", " + at.optString("name");
                        }
                    }
                }
                generals.add(new General(title, release, pub, available, type, machine, id, imageId, urlYoutube, scoreText, auth, typeImage));
            }
        } catch (JSONException e) {
            Log.e("Error parsing json ", e.getMessage());
        }

        return generals;
    }

    private static URL createUrl(String magazineUrl, String parametro) {
        URL url = null;
        String parametroFormatted = parametro.replaceAll(" ", "%20");
        String newUrl = magazineUrl + parametroFormatted + "&mode=full&sort=title_asc&size=1000&offset=0";
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
