package com.example.twitterapp;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

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

		Tweet tweet = getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfileImage);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);

		TextView nameView = (TextView)view.findViewById(R.id.tvName);
		String formatterName = "<b>" + tweet.getUser().getName() + "</b>"
				+ " <small><font color='#777777'>"
				+ tweet.getUser().getScreenName() + "</font></small>";

		nameView.setText(Html.fromHtml(formatterName));

		TextView bodyView = (TextView)view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));

		return view;
	}
}
