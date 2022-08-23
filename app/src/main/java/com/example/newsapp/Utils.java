package com.example.newsapp;

import android.text.TextUtils;
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

public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<Article> fetchArticlesData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Article> articles = extractFeatureFromJson(jsonResponse);

        return articles;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
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
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the articles JSON results.", e);
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

    private static List<Article> extractFeatureFromJson(String articlesJSON) {
        if (TextUtils.isEmpty(articlesJSON)) {
            return null;
        }

        List<Article> articles = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(articlesJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray articlesArray = response.getJSONArray("results");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject currentArticle = articlesArray.getJSONObject(i);
                String webTitle = currentArticle.getString("webTitle");
                String webUrl = currentArticle.getString("webUrl");
                String webPublicationDate = currentArticle.getString("webPublicationDate");
                String publicationDateModified = webPublicationDate.substring(0, 10);
                String sectionName = currentArticle.getString("sectionName");
                JSONObject fields = currentArticle.getJSONObject("fields");
                String body = fields.getString("body");
                String thumbnailUrl = fields.getString("thumbnail");
                JSONArray tags = currentArticle.getJSONArray("tags");
                String authorName;
                try {
                    JSONObject firstTag = tags.getJSONObject(0);
                    authorName = firstTag.getString("webTitle");
                } catch (JSONException e) {
                    authorName = "";
                    e.printStackTrace();
                }

                String authorImageUrl;
                try {
                    JSONObject firstTag = tags.getJSONObject(0);
                    authorImageUrl = firstTag.getString("bylineImageUrl");
                } catch (JSONException e) {
                    authorImageUrl = "";
                    e.printStackTrace();
                }

                Article article = new Article(webTitle, authorName, sectionName, thumbnailUrl, authorImageUrl, webUrl, publicationDateModified, body);
                articles.add(article);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return articles;
    }
}
