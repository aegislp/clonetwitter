package com.example.minitwitter.ui.tabs;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.minitwitter.R;
import com.example.minitwitter.common.Constantes;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.example.minitwitter.retrofit.response.Like;
import com.example.minitwitter.retrofit.response.Tweet;

import java.util.List;


public class MyTweetRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder> {

    private List<Tweet> mValues;
    private Context ctx;
    private String logUsername;


    public MyTweetRecyclerViewAdapter( Context context,List<Tweet> items) {
        mValues = items;
        ctx = context;
        logUsername = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USERNAME);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvUsername.setText(holder.mItem.getUser().getUsername());
        holder.tvMessage.setText(holder.mItem.getMensaje());
        holder.tvLikes.setText(String.valueOf(holder.mItem.getLikes().size()));

        String photo = holder.mItem.getUser().getPhotoUrl();
        if(!photo.equals("")){

            Glide.with(ctx).load("https://minitwitter.com/apiv1/uploads/photos/"+photo)
                    .into(holder.ivAvatar);
        }

        for (Like like : holder.mItem.getLikes()){

            if(like.getUsername() == logUsername){
                Glide.with(ctx).load(R.drawable.ic_like_pink).into(holder.ivLike);

                holder.tvLikes.setTextColor(ctx.getResources().getColor(R.color.pink));
                holder.tvLikes.setTypeface(null, Typeface.BOLD);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {

        if(mValues != null){
            return mValues.size();
        }else{
            return 0;
        }

    }

    public void setData(List<Tweet> tweets){
        mValues = tweets;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivAvatar;
        public final ImageView ivLike;
        public final TextView tvUsername;
        public final TextView tvMessage;
        public final TextView tvLikes;
        public Tweet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivAvatar = (ImageView) view.findViewById(R.id.imageViewTweetAvatar);
            ivLike = (ImageView) view.findViewById(R.id.imageViewLike);
            tvUsername = (TextView) view.findViewById(R.id.textViewNickname);
            tvMessage = (TextView) view.findViewById(R.id.textViewMessage);
            tvLikes = (TextView) view.findViewById(R.id.textViewLikes);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvMessage.getText() + "'";
        }
    }
}
