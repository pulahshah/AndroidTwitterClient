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

import com.activeandroid.util.Log;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.models.Standing;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class StandingsAdapter extends ArrayAdapter<Standing>{
	public StandingsAdapter (Context context, List<Standing> standings) {
		super(context, 0, standings);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//View view = convertView;
		view = null;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(position == 0 || position == 4 || position == 8 || 
					position == 12 || position == 16 || position == 20 ||
					position == 24 || position == 28){ 
				view = inflater.inflate(R.layout.item_standing_header, null);
			}
			else{
				view = inflater.inflate(R.layout.item_standing, null);
			}
		}

		final Standing standing = getItem(position);
		
		if(position == 0 || position == 4 || position == 8 || 
				position == 12 || position == 16 || position == 20 ||
				position == 24 || position == 28){ 
			TextView tvGroupName = (TextView) view.findViewById(R.id.tvGroupName);
			tvGroupName.setText(standing.getGroup());
		}
		
		ImageView ivFlag = (ImageView) view.findViewById(R.id.ivSmallFlag);
		ivFlag.setBackground(getFlag(standing.getTeam()));
		
		TextView tvTeamName = (TextView) view.findViewById(R.id.tvTeamName);
		tvTeamName.setText(standing.getTeam());
		
		TextView tvPlayed = (TextView) view.findViewById(R.id.tvPlayed);
		tvPlayed.setText(standing.getPlayed());
		
		TextView tvWon = (TextView) view.findViewById(R.id.tvWon);
		tvWon.setText(standing.getWon());
		
		TextView tvDraw = (TextView) view.findViewById(R.id.tvDraw);
		tvDraw.setText(standing.getDraw());
		
		TextView tvLost = (TextView) view.findViewById(R.id.tvLost);
		tvLost.setText(standing.getLost());
		
		TextView tvGD = (TextView) view.findViewById(R.id.tvGD);
		tvGD.setText(standing.getGoalDifference());
		
		TextView tvPoints = (TextView) view.findViewById(R.id.tvPoints);
		tvPoints.setText(standing.getPoints());
		
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
			return getContext().getResources().getDrawable(R.drawable.ic_cameroon_72);
		}
		if(country.equals("Croatia")){
			return getContext().getResources().getDrawable(R.drawable.ic_croatia_72);
		}
		if(country.equals("Mexico")){
			return getContext().getResources().getDrawable(R.drawable.ic_mexico_72);
		}
		if(country.equals("Brazil")){
			return getContext().getResources().getDrawable(R.drawable.ic_brazil_72);
		}
		if(country.equals("Chile")){
			return getContext().getResources().getDrawable(R.drawable.ic_chile_72);
		}
		if(country.equals("Australia")){
			return getContext().getResources().getDrawable(R.drawable.ic_australia_72);
		}
		if(country.equals("Spain")){
			return getContext().getResources().getDrawable(R.drawable.ic_spain_72);
		}
		if(country.equals("Netherlands")){
			return getContext().getResources().getDrawable(R.drawable.ic_netherlands_72);
		}
		if(country.equals("Greece")){
			return getContext().getResources().getDrawable(R.drawable.ic_greece_72);
		}
		if(country.equals("Japan")){
			return getContext().getResources().getDrawable(R.drawable.ic_japan_72);
		}
		if(country.equals("Ivory Coast")){
			return getContext().getResources().getDrawable(R.drawable.ic_ivorycoast_72);
		}
		if(country.equals("Colombia")){
			return getContext().getResources().getDrawable(R.drawable.ic_columbia_72);
		}
		if(country.equals("Uruguay")){
			return getContext().getResources().getDrawable(R.drawable.ic_uruguay_72);
		}
		if(country.equals("England")){
			return getContext().getResources().getDrawable(R.drawable.ic_england_72);
		}
		if(country.equals("Costa Rica")){
			return getContext().getResources().getDrawable(R.drawable.ic_costarica_72);
		}
		if(country.equals("Italy")){
			return getContext().getResources().getDrawable(R.drawable.ic_italy_72);
		}
		if(country.equals("Honduras")){
			return getContext().getResources().getDrawable(R.drawable.ic_honduras_72);
		}
		if(country.equals("Ecuador")){
			return getContext().getResources().getDrawable(R.drawable.ic_ecuador_72);
		}
		if(country.equals("France")){
			return getContext().getResources().getDrawable(R.drawable.ic_france_72);
		}
		if(country.equals("Switzerland")){
			return getContext().getResources().getDrawable(R.drawable.ic_switzerland_72);
		}
		if(country.equals("Nigeria")){
			return getContext().getResources().getDrawable(R.drawable.ic_nigeria_72);
		}
		if(country.equals("Iran")){
			return getContext().getResources().getDrawable(R.drawable.ic_iran_72);
		}
		if(country.contains("Bosnia")){
			return getContext().getResources().getDrawable(R.drawable.ic_bosnia_72);
		}
		if(country.equals("Argentina")){
			return getContext().getResources().getDrawable(R.drawable.ic_argentina_72);
		}
		if(country.equals("Germany")){
			return getContext().getResources().getDrawable(R.drawable.ic_germany_72);
		}
		if(country.equals("Ghana")){
			return getContext().getResources().getDrawable(R.drawable.ic_ghana_72);
		}
		if(country.equals("Portugal")){
			return getContext().getResources().getDrawable(R.drawable.ic_portugal_72);
		}
		if(country.equals("USA")){
			return getContext().getResources().getDrawable(R.drawable.ic_usa_72);
		}
		if(country.equals("Belgium")){
			return getContext().getResources().getDrawable(R.drawable.ic_belgium_72);
		}
		if(country.equals("Algeria")){
			return getContext().getResources().getDrawable(R.drawable.ic_algeria_72);
		}
		if(country.equals("Russia")){
			return getContext().getResources().getDrawable(R.drawable.ic_russia_72);
		}
		if(country.contains("Korea")){
			return getContext().getResources().getDrawable(R.drawable.ic_southkorea_72);
		}
		
		return getContext().getResources().getDrawable(R.drawable.ic_brazil_72);
	}
}
