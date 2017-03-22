package com.study.whutwf.doubanmovie.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whutwf on 17-3-22.
 */

public class MovieItem {

    private String mTitle;
    private List<String> mGenres = new ArrayList<>();
    //rating用什么结构存储
    private String mRating;
    //演员表结构用什么结构存储
    private String mCasts;
    //导演用什么结构存储,两者有关系
    private String mDirectors;

    private String mOriginalTitle;
    private String mYear;

    private List<String> mImageUrls = new ArrayList<>();

    private String mId;
    private String mAlt;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<String> getGenres() {
        return mGenres;
    }

    public void setGenres(List<String> genres) {
        mGenres = genres;
    }

    public String getAlt() {
        return mAlt;
    }

    public void setAlt(String alt) {
        mAlt = alt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public List<String> getImageUrls() {
        return mImageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        mImageUrls = imageUrls;
    }

}
