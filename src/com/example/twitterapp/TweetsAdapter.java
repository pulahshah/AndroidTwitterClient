package com.example.twitterapp;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet>{

	public TweetsAdapter (Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}

		final Tweet tweet = getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfileImage);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		imageView.setTag(tweet.getUser());
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), ProfileActivity.class);
				
				Log.d("DEBUG", "application context: " + v.getContext());
				
				if(v.getContext().getClass() != ProfileActivity.class){	// to prevent firing up new activities from profile activity
					i.putExtra("id", tweet.getUser().getId());
					i.putExtra("screenName", tweet.getUser().getScreenName());
					v.getContext().startActivity(i);
				}
				
				Log.d("DEBUG", "image clicked -- " + tweet.getUser().getId());
			}
		});
		
		TextView nameView = (TextView)view.findViewById(R.id.tvName);
		String formatterName = "<b>" + tweet.getUser().getName() + "</b>"
				+ " <small><font color='#777777'>" 
				+ "@" + tweet.getUser().getScreenName() + "</font></small>";

		nameView.setText(Html.fromHtml(formatterName));

		TextView bodyView = (TextView)view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));

		return view;
	}

	
}
