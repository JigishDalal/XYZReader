package com.example.thinkcreator.xyzreaderapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkcreator.xyzreaderapp.ArticleDetailActivity;
import com.example.thinkcreator.xyzreaderapp.DataLoader.ArticleLoader;
import com.example.thinkcreator.xyzreaderapp.DatabaseDb.ItemsContract;
import com.example.thinkcreator.xyzreaderapp.MainActivity;
import com.example.thinkcreator.xyzreaderapp.R;
import com.example.thinkcreator.xyzreaderapp.Ui.DynamicHeightNetworkImageView;
import com.example.thinkcreator.xyzreaderapp.Ui.ImageLoaderHelper;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Cursor mCursor;

    Context mcontext;

    public Adapter(Cursor cursor,Context context) {
        mCursor = cursor;
        mcontext=context;

    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ArticleLoader.Query._ID);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        final ViewHolder vh = new ViewHolder(view);
      
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent = new Intent(mcontext, ArticleDetailActivity.class);
                intent.putExtra(ArticleDetailActivity.ARG_ITEM_POSITION,ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition())));
                mcontext.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation((Activity) mcontext,
                        vh.thumbnailView,vh.thumbnailView.getTransitionName()).toBundle());*/


                Intent intent = new Intent(Intent.ACTION_VIEW,
                        ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition())));
                intent.putExtra(ArticleDetailActivity.ARG_ITEM_POSITION, vh.getAdapterPosition());
                mcontext.startActivity(intent);
            }
        });
        return  vh;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCursor.moveToPosition(position);
        holder.titleView.setText(mCursor.getString(ArticleLoader.Query.TITLE));
        holder.dateView.setText(
                DateUtils.getRelativeTimeSpanString(
                        mCursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL).toString());
        holder.authorView.setText(mCursor.getString(ArticleLoader.Query.AUTHOR));
        Log.e("url"," url"+ArticleLoader.Query.THUMB_URL+"mcontext"+mcontext);
        holder.thumbnailView.setImageUrl(
                mCursor.getString(ArticleLoader.Query.THUMB_URL),
                ImageLoaderHelper.getInstance(mcontext).getImageLoader());
        holder.thumbnailView.setAspectRatio(mCursor.getFloat(ArticleLoader.Query.ASPECT_RATIO));


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public DynamicHeightNetworkImageView thumbnailView;
        public TextView titleView;
        public TextView dateView;
        public TextView authorView;

        public ViewHolder(View view) {
            super(view);
            view=itemView;
        thumbnailView = (DynamicHeightNetworkImageView) view.findViewById(R.id.thumbnail);
            titleView = (TextView) view.findViewById(R.id.article_title);
            dateView = (TextView) view.findViewById(R.id.article_date);
            authorView = (TextView) view.findViewById(R.id.article_author);

            // set fonts
            titleView.setTypeface(Typeface.createFromAsset(view.getContext().getResources().getAssets(),
                    "Montserrat-Bold.ttf"));
            dateView.setTypeface(Typeface.createFromAsset(view.getContext().getResources().getAssets(),
                    "Montserrat-Regular.ttf"));
            authorView.setTypeface(Typeface.createFromAsset(view.getContext().getResources().getAssets(),
                    "Montserrat-Regular.ttf"));
        }

    }


}
