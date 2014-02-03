package com.example.twitterapp;

import java.util.List;

import org.json.JSONArray;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter2 extends BaseAdapter {
	
	List<Tweet> tweets;
	LayoutInflater inflater;
	
	static class ViewHolder {
		ImageView image;
		TextView text;
		TextView body;
	}
	
	public TweetAdapter2(Context context, List<Tweet> tweets) {
		this.tweets = tweets;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tweets == null ? 0 : tweets.size();
	}

	@Override
	public Tweet getItem(int position) {
		// TODO Auto-generated method stub
		return tweets.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		
		if(view == null){
			view = inflater.inflate(R.layout.tweet_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.ivProfileImage);
			holder.text = (TextView) view.findViewById(R.id.tvName);
			holder.body = (TextView) view.findViewById(R.id.tvBody);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		Tweet tweet = getItem(position);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(),  holder.image);
		
		holder.text.setText(tweet.getUser().getName());
		holder.body.setText(tweet.getBody());
		
		return view;
	}

	public void swapList(List<Tweet> newTweets) {
		tweets = newTweets;
		notifyDataSetChanged();
	}

	public void addAll(JSONArray jsonTweets) {
		// TODO Auto-generated method stub
		
	}
}
