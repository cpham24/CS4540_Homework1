package edu.calstatela.cpham24.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bill on 6/29/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private String[] mNewsData;

    public NewsAdapter() {

    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int id = R.layout.news_list_item;
        View view = LayoutInflater.from(context).inflate(id, parent, false);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.mNewsTextView.setText(mNewsData[position]);
    }

    @Override
    public int getItemCount() {
        if(mNewsData == null)
            return 0;
        return mNewsData.length;
    }

    public void setNewsData(String[] newsData) {
        // safer to clone
        mNewsData = newsData.clone();
        notifyDataSetChanged();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder {
        // DONE (18) Create a public final TextView variable called mWeatherTextView
        public final TextView mNewsTextView;

        // DONE (19) Create a constructor for this class that accepts a View as a parameter
        // DONE (20) Call super(view) within the constructor for ForecastAdapterViewHolder
        // DONE (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTextView = (TextView)itemView.findViewById(R.id.tv_news_data);
        }
    }
}
