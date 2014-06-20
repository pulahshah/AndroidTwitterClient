package com.psapp.worldcupapp.adapters;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.Utilities;
import com.psapp.worldcupapp.models.Standing;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class StandingsAdapter extends ArrayAdapter<Standing> {
	public StandingsAdapter(Context context, List<Standing> standings) {
		super(context, 0, standings);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// View view = convertView;
		view = null;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (position == 0 || position == 4 || position == 8
					|| position == 12 || position == 16 || position == 20
					|| position == 24 || position == 28) {
				view = inflater.inflate(R.layout.item_standing_header, null);
			} else {
				view = inflater.inflate(R.layout.item_standing, null);
			}
		}

		final Standing standing = getItem(position);

		if (position == 0 || position == 4 || position == 8 || position == 12
				|| position == 16 || position == 20 || position == 24
				|| position == 28) {
			TextView tvGroupName = (TextView) view
					.findViewById(R.id.tvGroupName);
			tvGroupName.setText(standing.getGroup());
		}

		ImageView ivFlag = (ImageView) view.findViewById(R.id.ivSmallFlag);
		ivFlag.setBackground(Utilities.getFlag(getContext(),
				standing.getTeam(), 72));

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

		// String formatterName = "<b>" + tweet.getUser().getName() + "</b>"
		// + " <small><font color='#777777'>"
		// + "@" + tweet.getUser().getScreenName() + "</font></small>";
		//
		// nameView.setText(Html.fromHtml(formatterName));
		//
		// TextView bodyView = (TextView)view.findViewById(R.id.tvBody);
		// bodyView.setText(Html.fromHtml(tweet.getBody()));

		return view;
	}

}
