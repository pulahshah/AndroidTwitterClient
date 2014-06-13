package com.psapp.worldcupapp.adapters;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psapp.worldcupapp.PrettyDate;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.models.Result;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class ResultsAdapter extends ArrayAdapter<Result>{

	public ResultsAdapter (Context context, List<Result> results) {
		super(context, 0, results);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_result, null);
		}

		final Result result = getItem(position);
		
		String homeTeam = result.getHomeTeam().toString();
		String awayTeam = result.getAwayTeam().toString();
		
		ImageView ivHomeFlag = (ImageView) view.findViewById(R.id.ivHomeIcon);
		ImageView ivAwayFlag = (ImageView) view.findViewById(R.id.ivAwayIcon);
		
		ivHomeFlag.setBackground(getFlag(homeTeam));
		ivAwayFlag.setBackground(getFlag(awayTeam));
		
		TextView homeTeamName = (TextView)view.findViewById(R.id.tvHomeName);
		TextView awayTeamName = (TextView)view.findViewById(R.id.tvAwayName);
		
		homeTeamName.setText(homeTeam);
		awayTeamName.setText(awayTeam);
		
		TextView tvHomeScore = (TextView) view.findViewById(R.id.tvHomeScore);
		TextView tvAwayScore = (TextView) view.findViewById(R.id.tvAwayScore);
		tvHomeScore.setText(result.getHomeScore().toString());
		tvAwayScore.setText(result.getAwayScore().toString());
		
		TextView date = (TextView)view.findViewById(R.id.tvLiveTime);
		date.setText(PrettyDate.getPrettyDate(result.getDate()));
		
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

	private Drawable getFlag(String country){
		if(country.equals("Cameroon")){
			return getContext().getResources().getDrawable(R.drawable.ic_cameroon_256);
		}
		if(country.equals("Croatia")){
			return getContext().getResources().getDrawable(R.drawable.ic_croatia_256);
		}
		if(country.equals("Mexico")){
			return getContext().getResources().getDrawable(R.drawable.ic_mexico_256);
		}
		if(country.equals("Brazil")){
			return getContext().getResources().getDrawable(R.drawable.ic_brazil_256);
		}
		if(country.equals("Chile")){
			return getContext().getResources().getDrawable(R.drawable.ic_chile_256);
		}
		if(country.equals("Australia")){
			return getContext().getResources().getDrawable(R.drawable.ic_australia_256);
		}
		if(country.equals("Spain")){
			return getContext().getResources().getDrawable(R.drawable.ic_spain_256);
		}
		if(country.equals("Netherlands")){
			return getContext().getResources().getDrawable(R.drawable.ic_netherlands_256);
		}
		if(country.equals("Greece")){
			return getContext().getResources().getDrawable(R.drawable.ic_greece_256);
		}
		if(country.equals("Japan")){
			return getContext().getResources().getDrawable(R.drawable.ic_japan_256);
		}
		if(country.equals("Ivory Coast")){
			return getContext().getResources().getDrawable(R.drawable.ic_ivorycoast_256);
		}
		if(country.equals("Colombia")){
			return getContext().getResources().getDrawable(R.drawable.ic_columbia_256);
		}
		if(country.equals("Uruguay")){
			return getContext().getResources().getDrawable(R.drawable.ic_uruguay_256);
		}
		if(country.equals("England")){
			return getContext().getResources().getDrawable(R.drawable.ic_england_256);
		}
		if(country.equals("Costa Rica")){
			return getContext().getResources().getDrawable(R.drawable.ic_costarica_256);
		}
		if(country.equals("Italy")){
			return getContext().getResources().getDrawable(R.drawable.ic_italy_256);
		}
		if(country.equals("Honduras")){
			return getContext().getResources().getDrawable(R.drawable.ic_honduras_256);
		}
		if(country.equals("Ecuador")){
			return getContext().getResources().getDrawable(R.drawable.ic_ecuador_256);
		}
		if(country.equals("France")){
			return getContext().getResources().getDrawable(R.drawable.ic_france_256);
		}
		if(country.equals("Switzerland")){
			return getContext().getResources().getDrawable(R.drawable.ic_switzerland_256);
		}
		if(country.equals("Nigeria")){
			return getContext().getResources().getDrawable(R.drawable.ic_nigeria_256);
		}
		if(country.equals("Iran")){
			return getContext().getResources().getDrawable(R.drawable.ic_iran_256);
		}
		if(country.contains("Bosnia")){
			return getContext().getResources().getDrawable(R.drawable.ic_bosnia_256);
		}
		if(country.equals("Argentina")){
			return getContext().getResources().getDrawable(R.drawable.ic_argentina_256);
		}
		if(country.equals("Germany")){
			return getContext().getResources().getDrawable(R.drawable.ic_germany_256);
		}
		if(country.equals("Ghana")){
			return getContext().getResources().getDrawable(R.drawable.ic_ghana_256);
		}
		if(country.equals("Portugal")){
			return getContext().getResources().getDrawable(R.drawable.ic_portugal_256);
		}
		if(country.equals("USA")){
			return getContext().getResources().getDrawable(R.drawable.ic_usa_256);
		}
		if(country.equals("Belgium")){
			return getContext().getResources().getDrawable(R.drawable.ic_belgium_256);
		}
		if(country.equals("Algeria")){
			return getContext().getResources().getDrawable(R.drawable.ic_algeria_256);
		}
		if(country.equals("Russia")){
			return getContext().getResources().getDrawable(R.drawable.ic_russia_256);
		}
		if(country.contains("Korea")){
			return getContext().getResources().getDrawable(R.drawable.ic_southkorea_256);
		}
		
		return getContext().getResources().getDrawable(R.drawable.ic_football_256);
	}
	
}

