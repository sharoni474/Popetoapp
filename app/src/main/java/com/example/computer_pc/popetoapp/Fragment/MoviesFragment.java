package com.example.computer_pc.popetoapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.computer_pc.popetoapp.Activity.VolleyApplication;
import com.example.computer_pc.popetoapp.Utils.Mock;
import com.example.computer_pc.popetoapp.Movie;
import com.example.computer_pc.popetoapp.MoviesAdapter;
import com.example.computer_pc.popetoapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {

    private static final String EXTRA_MOVIES = "extra_movies";
    private static final String ARG_POSITION = "position";

    private ProgressBar pbLoading;
    private GridView gvMovies;
    private int mSelectedPos;
    private MoviesAdapter mAdapter;
  //  private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position
     * @return A new instance of fragment MoviesFragment.
     */
    public static MoviesFragment newInstance(int position) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupArgs();

    }

    private void setupArgs() {
        if (getArguments() != null) {
            try {
                mSelectedPos = Integer.parseInt(getArguments().getString(ARG_POSITION));
            } catch (Exception e) {
                Log.e("Movies Fragment Pos", "could not parse position argument");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        instantiateViews(view);
        initialize();

    }

    private void initialize() {
        mAdapter = new MoviesAdapter(getContext());
        gvMovies.setAdapter(mAdapter);
        showLoading();

        //Todo: Pull Movies and call showMovies!  send the movieitemfragment the data, onResume..
        fetchMovies();
    }

    private void instantiateViews(View view) {
        pbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
        gvMovies = (GridView) view.findViewById(R.id.gv_tv_shows);

    }

    private void fetchMovies() {
        //TODO: use RottenTomatoes here
        JsonObjectRequest request = new JsonObjectRequest(
                "http://cblunt.github.io/blog-android-volley/response2.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // TODO: Parse the JSON
                        try {
                            //Making a mock RottenTomatoes API Json Object
                            JSONObject json = new JSONObject(Mock.JSONobject);
                            List<Movie> movies = parse(json);

                            showMovies(movies);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(),"Unable to parse data: " + e.getStackTrace(), Toast.LENGTH_SHORT);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyApplication.getsInstance().getRequestQueue().add(request);
    }

    private List<Movie> parse(JSONObject json) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<Movie>();

      //  for (int i = 0; i < json.length(); i++) {
            int mID = json.getInt("id");
            String mTitle = json.getString("title");
            String mCritics = json.getString("critics_consensus");
            //Critics_score picked
            int mRate = json.getJSONObject("ratings").getInt("critics_score");
            String mSynopsis = json.getString("synopsis");
            String mPosters = json.getJSONObject("posters").getString("original");
            String[] mGenres = convertJsonStringArray(json.getJSONArray("genres"));

            Movie movie = new Movie(mID, mTitle, mCritics, mPosters, mRate, mGenres, mSynopsis);
            movies.add(movie);
   //     }
        return movies;
    }

    private String[] convertJsonStringArray(JSONArray strArray) throws JSONException {
        if (strArray == null || strArray.length() == 0) return null;

        String[] newArr = new String[strArray.length()];
        for (int i = 0; i < strArray.length(); i++) {
            newArr[i] = strArray.getString(i);
        }

        return newArr;
    }


    private void showMovies(Collection<Movie> movies) {
        if (isAdded()) {
            mAdapter.fetchMovies(movies);
            //add the movies to the collection
            hideLoading();
        }
    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    */

    private void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

}
