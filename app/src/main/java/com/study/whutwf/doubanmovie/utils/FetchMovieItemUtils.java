package com.study.whutwf.doubanmovie.utils;

import android.net.Uri;
import android.util.Log;

import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.support.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by whutw on 2017/3/21 0021.
 */

public class FetchMovieItemUtils {

    private static final String TAG = "FetchMovieItemUtils";

    public FetchMovieItemUtils() {}

    public List<MovieItem> fetchMovieItems(HashMap<String, String> params) {
        List<MovieItem> movieItems = new ArrayList<>();

        String url = Uri.parse(params.get("url"))
                .buildUpon()
                .appendQueryParameter(Constants.Params.DOUBAN_MOVIE_START, params.get(Constants.Params.DOUBAN_MOVIE_START))
                .appendQueryParameter(Constants.Params.DOUBAN_MOVIE_QUERY, params.get(Constants.Params.DOUBAN_MOVIE_QUERY))
                .appendQueryParameter(Constants.Params.DOUBAN_MOVIE_COUNT, params.get(Constants.Params.DOUBAN_MOVIE_COUNT))
                .appendQueryParameter(Constants.Params.DOUBAN_MOVIE_TAG, params.get(Constants.Params.DOUBAN_MOVIE_TAG))
                .build().toString();

        try {
            String movieJsonString = NetworkUtils.getUrlString(url);
            JSONObject movieJsonBody = new JSONObject(movieJsonString);
            parseMovieItems(movieItems, movieJsonBody);
        }  catch (JSONException e) {
            Log.e(TAG, "Failed to parse Json");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
            e.printStackTrace();
        }

        return movieItems;
    }

    private void parseMovieItems(List<MovieItem> movieItems, JSONObject movieJsonObj) throws JSONException {
        JSONArray movieSubjects = movieJsonObj.getJSONArray("subjects");

        for (int i = 0; i < movieSubjects.length(); ++i) {
            JSONObject movieSubject = movieSubjects.getJSONObject(i);

            MovieItem movieItem = new MovieItem();

            movieItem.setId(movieSubject.getString("id"));
            movieItem.setTitle(movieSubject.getString("title"));
            movieItem.setAlt(movieSubject.getString("alt"));
            movieItem.setYear(movieSubject.getString("year"));
            movieItem.setOriginalTitle(movieSubject.getString("original_title"));

            if (!movieSubject.has("images")) {
                continue;
            }

            JSONObject imagesObject = movieSubject.getJSONObject("images");
            List<String> imageUrls = new ArrayList<>();
            imageUrls.add(imagesObject.getString("small"));
            imageUrls.add(imagesObject.getString("large"));
            imageUrls.add(imagesObject.getString("medium"));
            movieItem.setImageUrls(imageUrls);

            JSONObject ratingObject = movieSubject.getJSONObject("rating");
            movieItem.setRating(ratingObject.getString("average"));

            movieItems.add(movieItem);
        }
    }


}
