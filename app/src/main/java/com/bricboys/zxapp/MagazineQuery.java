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

public final class MagazineQuery {

    private final static String MAGAZINE_URL =
            "https://api.zxinfo.dk/api/zxinfo/v2/magazines/";

    private MagazineQuery() {};

    public static List<Magazine> extractMagazine(String parametro) {
        URL url = createUrl(MAGAZINE_URL, parametro);

        String jsonResponse = null;
        //Bitmap jsonImage = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error http request", e.getMessage());
        }

        ArrayList<Magazine> magazines = new ArrayList<>();
        JSONObject root;

        try {
            root = new JSONObject(jsonResponse);
            //JSONObject response = root.getJSONObject("name");
            JSONArray issues = root.getJSONArray("issues");
            for(int i=0; i<issues.length(); i++) {
                JSONObject obj = issues.getJSONObject(i);
                String number = obj.optString("number");
                String year = obj.optString("date_year");
                String img = obj.optString("cover_image");
                magazines.add(new Magazine(parametro, number, year, img) );
            }
        } catch (JSONException e) {
            Log.e("Error parsing json ", e.getMessage());
        }

        return magazines;
    }

    private static URL createUrl(String magazineUrl, String parametro) {
        URL url = null;
        String parametroFormatted = parametro.replaceAll(" ", "%20");
        String newUrl = magazineUrl + parametroFormatted + "/issues";
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
