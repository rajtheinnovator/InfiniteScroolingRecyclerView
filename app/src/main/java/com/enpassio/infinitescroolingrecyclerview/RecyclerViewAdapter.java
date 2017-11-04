package com.enpassio.infinitescroolingrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 11/4/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    /* Store a member variable for the popularMovies */
    private static ArrayList<String> mPopularMovie;
    /* Store the context for easy access */
    private Context mContext;

    /* Pass in the popularMovies array into the constructor */
    public RecyclerViewAdapter(Context context, ArrayList<String> movies) {
        mPopularMovie = movies;
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View moviesView = inflater.inflate(R.layout.item_recyclerview, parent, false);

        /* Return a new holder instance */
        ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(context, moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        /* Get the data model based on position */
        String currentMovie = mPopularMovie.get(position);
        // Log.i("my_tag", "currentId is: " + currentMovie);
        /*
        Set item views based on your views and data model
         */
        viewHolder.movieTitleTextView.setText(currentMovie);
    }

    @Override
    public int getItemCount() {
        //  Log.i("my_tag", "getItemCount is: " + mPopularMovie.size());
        return mPopularMovie.size();
    }

    public void setMovieData(ArrayList<String> movieData) {
        Log.v("my_tag", "setMovieData called with size: " + movieData.size());
        mPopularMovie = movieData;
        notifyDataSetChanged();
    }

    /*
     Provide a direct reference to each of the views within a data item
     Used to cache the views within the item layout for fast access
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*
        Your holder should contain a member variable
        for any view that will be set as you render a row
        */
        public final TextView movieTitleTextView;

        private Context context;

        /*
        We also create a constructor that accepts the entire item row
        and does the view lookups to find each subview
        */
        public ViewHolder(Context context, View itemView) {
            /*
            Stores the itemView in a public final member variable that can be used
            to access the context from any ViewHolder instance.
            */
            super(itemView);
            movieTitleTextView = (TextView) itemView.findViewById(R.id.idTextView);

            this.context = context;
        }
    }
}