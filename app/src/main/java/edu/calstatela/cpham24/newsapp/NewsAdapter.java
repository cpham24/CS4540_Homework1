package edu.calstatela.cpham24.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bill on 6/29/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private ArrayList<NewsItem> mNewsData;
    ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> newsData, ItemClickListener listener) {
        mNewsData = newsData;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
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
        NewsItem item = mNewsData.get(position);
        holder.mNewsTitleTextView.setText(item.title);
        holder.mNewsDescTextView.setText(item.description);
        holder.mNewsDateTextView.setText(item.date);
    }

    @Override
    public int getItemCount() {
        if(mNewsData == null)
            return 0;
        return mNewsData.size();
    }

    public void setNewsData(ArrayList<NewsItem> newsData) {
        // safer to clone
        mNewsData = (ArrayList<NewsItem>) newsData.clone();
        notifyDataSetChanged();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // DONE (18) Create a public final TextView variable called mWeatherTextView
        public final TextView mNewsTitleTextView;
        public final TextView mNewsDescTextView;
        public final TextView mNewsDateTextView;

        // DONE (19) Create a constructor for this class that accepts a View as a parameter
        // DONE (20) Call super(view) within the constructor for ForecastAdapterViewHolder
        // DONE (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTitleTextView = (TextView)itemView.findViewById(R.id.tv_news_title);
            mNewsDescTextView = (TextView)itemView.findViewById(R.id.tv_news_desc);
            mNewsDateTextView = (TextView)itemView.findViewById(R.id.tv_news_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}
