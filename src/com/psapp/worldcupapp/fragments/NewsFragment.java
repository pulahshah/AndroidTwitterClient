package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.ScoreDetailActivity;
import com.psapp.worldcupapp.adapters.NewsAdapter;
import com.psapp.worldcupapp.adapters.ScoresAdapter;
import com.psapp.worldcupapp.models.News;
import com.psapp.worldcupapp.models.Fixture;

public class NewsFragment extends Fragment {
	NewsAdapter newsAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<News> news = new ArrayList<News>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_news, container, false);
		getNews();
		return view;
	}

	public static NewsFragment newInstance(String str) {
		NewsFragment nf = new NewsFragment();
		Bundle b = new Bundle();
		b.putString("msg", str);
		nf.setArguments(b);
		return nf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public NewsAdapter getAdapter() {
		return newsAdapter;
	}

	public void getNews() {
		String url = URL + "/news.json";
		client.get(url, new AsyncHttpResponseHandler() {
			public void onSuccess(String json) {
				try {
					JSONArray newsArray = new JSONArray(json);

					for (int i = 0; i < newsArray.length(); i++) {
						news = News.fromJson(newsArray);
					}

					newsAdapter = new NewsAdapter(getActivity(), news);
					ListView lvNews = (ListView) getActivity().findViewById(
							R.id.lvNews);
					lvNews.setAdapter(newsAdapter);

					// lvNews
					// .setOnItemClickListener(new OnItemClickListener() {
					// @Override
					// public void onItemClick(AdapterView<?> parent,
					// View view, int position, long id) {
					// Intent intent = new Intent(getActivity(),
					// ScoreDetailActivity.class);
					// String message = "abc";
					// intent.putExtra("EXTRA_MESSAGE", message);
					// startActivity(intent);
					// }
					// });

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}
}
