package edu.calstatela.cpham24.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import edu.calstatela.cpham24.newsapp.data.NewsItem;
import com.squareup.picasso.Picasso;

/**
 * Created by bill on 6/29/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private ArrayList<NewsItem> mNewsData;
    ItemClickListener listener;
    Context context;
    public static final String TAG = "NewsAdapter";

    public NewsAdapter(ArrayList<NewsItem> newsData, ItemClickListener listener) {
        mNewsData = newsData;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int id = R.layout.news_list_item;
        View view = LayoutInflater.from(context).inflate(id, parent, false);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        NewsItem item = mNewsData.get(position);
        holder.loadNewsContent(item);
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
        public final ImageView mNewsImageView;
        public final TextView mNewsTitleTextView;
        public final TextView mNewsDescTextView;
        public final TextView mNewsDateTextView;

        // DONE (19) Create a constructor for this class that accepts a View as a parameter
        // DONE (20) Call super(view) within the constructor for ForecastAdapterViewHolder
        // DONE (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsImageView = (ImageView)itemView.findViewById(R.id.iv_news_img);
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

        public void loadNewsContent(NewsItem item) {
            mNewsTitleTextView.setText(item.title);
            mNewsDescTextView.setText(item.description);
            mNewsDateTextView.setText(item.date);
            if(item.imgurl != null) {
                Picasso.with(context).load(item.imgurl).into(mNewsImageView);
            }
        }
    }
}
