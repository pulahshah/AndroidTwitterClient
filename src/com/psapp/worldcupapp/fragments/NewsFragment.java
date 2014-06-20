package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.NetworkChecker;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.WebViewActivity;
import com.psapp.worldcupapp.adapters.NewsAdapter;
import com.psapp.worldcupapp.models.News;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class NewsFragment extends Fragment {
	NewsAdapter newsAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<News> news;
	private String title;
	private int page;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_news, container, false);
		setHasOptionsMenu(true);
		return view;
	}

	public static NewsFragment newInstance(int page, String title) {
		NewsFragment nf = new NewsFragment();
		Bundle b = new Bundle();
		b.putInt("someInt", page);
		b.putString("someTitle", title);
		nf.setArguments(b);
		return nf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 3);
		title = getArguments().getString("someTitle");

		Log.d("DEBUG", "news --- onCreate");
	}
	
	
	private Crouton crouton;
	public boolean checkConnection(){
		ConnectivityManager cm =
		        (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
	}
	
	
	public void showCrouton(){
		crouton = Crouton.makeText(getActivity(), "No network connection", Style.ALERT)
			    .setConfiguration(new Configuration.Builder().setDuration(Configuration.DURATION_LONG).build());
		crouton.show();
	}
	
	public void hideCrouton(){
		if(crouton != null){
			crouton.hide();
		}
	}

	public void onResume() {
		super.onResume();
		Log.d("DEBUG", "news --- onResume");

		if(NetworkChecker.checkConnection(getActivity())){
			getNews();
			NetworkChecker.hideCrouton();
		}
		else{
			NetworkChecker.showCrouton(getActivity());
		}
		
	}

	public NewsAdapter getAdapter() {
		return newsAdapter;
	}

	public void getNews() {
		
		String url = URL + "/news.json";
		client.get(url, new AsyncHttpResponseHandler() {
			public void onSuccess(String json) {
				news = new ArrayList<News>();
				try {
					JSONArray newsArray = new JSONArray(json);

					for (int i = 0; i < newsArray.length(); i++) {
						news = News.fromJson(newsArray);
					}

					newsAdapter = new NewsAdapter(getActivity(), news);
					final ListView lvNews = (ListView) getActivity()
							.findViewById(R.id.lvNews);
					lvNews.setAdapter(newsAdapter);

					lvNews.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							Intent intent = new Intent(getActivity(),
									WebViewActivity.class);

							News n = (News) lvNews.getItemAtPosition(position);

							String url = n.getLink();
							intent.putExtra("url", url);
							startActivity(intent);
						}
					});

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

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.refresh:
			if(NetworkChecker.checkConnection(getActivity())){
				getNews();
				NetworkChecker.hideCrouton();
			}
			else{
				NetworkChecker.showCrouton(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
