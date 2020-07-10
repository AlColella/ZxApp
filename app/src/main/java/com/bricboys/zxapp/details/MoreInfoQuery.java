package com.bricboys.zxapp.details;

import android.util.Log;

import com.bricboys.zxapp.ParameterClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


public class MoreInfoQuery {
    private final static String GENERAL_URL = "https://api.zxinfo.dk/v3/games/";
           // "https://api.zxinfo.dk/api/zxinfo/games/";

    private MoreInfoQuery(){}

    public static MoreInfo extractInfo(String parametro) {

        //tapes = null;

        URL url = createUrl(GENERAL_URL, parametro);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error http request", e.getMessage());
        }

        JSONObject root;

        String mControls = "";
        String mNumberOfPlayers = "";
        String mOtherSystems = "";
        String mOriginalPrice = "";
        //String mReleasePrice = "";
        String mAdvertisement = "";
        String newUrl = "";

        MoreInfo info = null;

        try {
            root = new JSONObject(jsonResponse);
            JSONObject _source = root.getJSONObject("_source");

            if(_source.optJSONArray("controls") != null) {
                JSONArray controls = _source.getJSONArray("controls");
                for (int i = 0; i < controls.length(); i++) {
                    JSONObject obj = controls.getJSONObject(i);
                    if (i == 0) {
                        mControls = mControls + obj.optString("control");
                    } else {
                        mControls = mControls + " , " + obj.optString("control");
                    }
                }
            }

            mNumberOfPlayers = _source.optString("numberOfPlayers");

            if(_source.optJSONArray("otherSystems") != null) {
                JSONArray other = _source.getJSONArray("otherSystems");
                for (int i = 0; i < other.length(); i++) {
                    JSONObject obj = other.getJSONObject(i);
                    if (i == 0) {
                        mOtherSystems = mOtherSystems + obj.optString("name");
                    } else {
                        mOtherSystems = mOtherSystems + " , " + obj.optString("name");
                    }
                }
            }


            /*if(_source.optJSONArray("releases") != null) {
                JSONArray price = _source.getJSONArray("releases");
                for (int i = 0; i < price.length(); i++) {
                    JSONObject obj = price.getJSONObject(i);
                    if (i == 0) {
                        JSONObject mReleasePrice = obj.getJSONObject("releasePrice.amount");
                      //  mOriginalPrice = mOriginalPrice + obj.optString("releasePrice.currency") + " " +
                       //         obj.optString("releasePrice.amount");
                    } else {
                        JSONObject mReleasePrice = obj.getJSONObject("releasePrice.amount");
                        //mOriginalPrice = mOriginalPrice + " , " + obj.optString("releasePrice.currency") + " " +
                         //       obj.optString("releasePrice.amount");
                    }
                }
            }*/

            if(_source.optJSONArray("additionalDownloads") != null) {
                JSONArray addit = _source.getJSONArray("additionalDownloads");
                for (int i = 0; i < addit.length(); i++) {
                    JSONObject obj = addit.getJSONObject(i);
                    String type;
                    type = obj.optString("type");
                    if (type.equalsIgnoreCase("Advertisement")) {
                        mAdvertisement = obj.optString("path");
                        break;
                    }
                }
            }

            if (!mAdvertisement.isEmpty()) {
                String Url = mAdvertisement;
                if (Url.substring(0, 14).equals("/pub/sinclair/")) {
                    newUrl = Url.replaceAll("/pub/sinclair/", ParameterClass.pubSinclair);
                } else {
                    newUrl = Url.replaceAll("/zxdb/sinclair/", ParameterClass.zxdbSinclair);
                }

            }
            Log.e("Info: ", mControls + ":" + mNumberOfPlayers);

            info = (new MoreInfo(mControls, mNumberOfPlayers, mOtherSystems, mOriginalPrice, newUrl));
        } catch (JSONException e) {
            Log.e("Error parsing JSON", e.getMessage().toString());
        }

        return info;

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
