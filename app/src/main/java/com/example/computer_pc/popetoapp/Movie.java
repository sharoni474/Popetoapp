package com.example.computer_pc.popetoapp;

/**
 *
 * Created by Tomer on 15/04/2016.
 */
public class Movie {
    //Todo: Critics consensus, critics score, posters- original is featured
    private final String mTitle;
    private final String mCritics;
    private final String mPosterURL;
    private final int mRate;
    private final int mID;
    private final String[] mGenres;
    private final String mSynopsis;

    public Movie(int id, String title, String critics, String posterURL,int rate, String[] genres, String synopsis) {
        mID = id;
        mTitle = title;
        mCritics = critics;
        mPosterURL = posterURL;
        mRate = rate;
        mGenres = cloneArray(genres);
        mSynopsis = synopsis;
    }

    private String[] cloneArray(String[] oArray){
        if(oArray!= null && oArray.length>0) {
            String[] array = new String[oArray.length];
            for (int i = 0; i < oArray.length; i++)
                array[i] = oArray[i];
            return array;
        }
        return null;
    }
    public String getTitle() {
        return mTitle;
    }

    public String getCritics() {
        return mCritics;
    }

    public String getPosterURL() {
        return mPosterURL;
    }

    public int getRate() {
        return mRate;
    }

    public int getID() {
        return mID;
    }

    public String[] getGenres() {
        return cloneArray(mGenres);
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie Movie = (Movie) o;

        if (!mTitle.equals(Movie.mTitle)) return false;

        return true;
    }


    @Override
    public int hashCode() {
        return mTitle.hashCode();
    }
}
