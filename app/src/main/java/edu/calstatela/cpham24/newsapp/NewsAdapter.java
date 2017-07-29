package edu.calstatela.cpham24.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.calstatela.cpham24.newsapp.data.Contract;
import edu.calstatela.cpham24.newsapp.data.NewsItem;
import com.squareup.picasso.Picasso;

/**
 * Created by bill on 6/29/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private Cursor mCursor;
    private ItemClickListener mListener;
    private Context mContext;
    public static final String TAG = "NewsAdapter";

    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        mCursor = cursor;
        mListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int id = R.layout.news_list_item;
        View view = LayoutInflater.from(mContext).inflate(id, parent, false);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        NewsItem item = new NewsItem(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_TITLE)),
                                     mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_AUTHOR)),
                                     mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION)),
                                     mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DATE)),
                                     mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_URL)),
                                     mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_IMGURL)));
        holder.loadNewsContent(item);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
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
            mListener.onItemClick(mCursor, pos);
        }

        public void loadNewsContent(NewsItem item) {
            mNewsTitleTextView.setText(item.title);
            mNewsDescTextView.setText(item.description);
            mNewsDateTextView.setText(item.date);

            // replaced AsyncTask image loader with Picasso (so much easier!)
            if(item.imgurl != null) {
                Picasso.with(mContext).load(item.imgurl).into(mNewsImageView);
            }
        }
    }
}
