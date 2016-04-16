package com.example.computer_pc.popetoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.computer_pc.popetoapp.Activity.VolleyApplication;
import com.example.computer_pc.popetoapp.Utils.BitmapLruCache;

import java.util.Collection;

/**
 * Created by Tomer on 15/04/2016.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;

    public MoviesAdapter(Context context) {
        super(context, R.layout.movie_item);
        this.context = context;

        mImageLoader = new ImageLoader(VolleyApplication.getsInstance().getRequestQueue(), new BitmapLruCache());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            context = parent.getContext();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        ViewHolder holder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.movie_item, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);

        holder.ivPoster.setImageUrl(movie.getPosterURL(), mImageLoader);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvRate.setText(movie.getRate()+"/100");

        return convertView;
    }

    public void fetchMovies(Collection<Movie> movies) {
        clear();
        addAll(movies);
        notifyDataSetChanged();

    }

    static class ViewHolder {
        NetworkImageView ivPoster;
        TextView tvTitle;
        TextView tvRate;

        private ViewHolder(View rootView){
            ivPoster = (NetworkImageView)rootView.findViewById(R.id.iv_thumbnail);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvRate = (TextView) rootView.findViewById(R.id.tv_rates);
        }
    }
}
