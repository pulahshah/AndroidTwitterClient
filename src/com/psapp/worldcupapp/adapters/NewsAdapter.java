package com.psapp.worldcupapp.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.models.News;
import com.psapp.worldcupapp.models.Fixture;

public class NewsAdapter extends ArrayAdapter<News>{
	public NewsAdapter (Context context, List<News> news) {
		super(context, 0, news);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_news, null);
		}

		final News news = getItem(position);
		
		TextView headline = (TextView)view.findViewById(R.id.tvNewsHeadline);
		TextView snippet = (TextView)view.findViewById(R.id.tvNewsSnippet);
//		TextView date = (TextView)view.findViewById(R.id.tvNewsDate);
		
		headline.setText(Html.fromHtml(news.getHeadline()));
		snippet.setText(Html.fromHtml(news.getSnippet()));
		//date.setText(news.getDate());
		
		
//		String formatterName = "<b>" + tweet.getUser().getName() + "</b>"
//				+ " <small><font color='#777777'>" 
//				+ "@" + tweet.getUser().getScreenName() + "</font></small>";
//
//		nameView.setText(Html.fromHtml(formatterName));
//
//		TextView bodyView = (TextView)view.findViewById(R.id.tvBody);
//		bodyView.setText(Html.fromHtml(tweet.getBody()));

		return view;
	}
}
