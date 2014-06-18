package com.psapp.worldcupapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {

	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.football_256_white);
		
		String url = getIntent().getStringExtra("url");
 
		WebViewClient yourWebClient = new WebViewClient()
	       {
	           @Override
	           public boolean shouldOverrideUrlLoading(WebView  view, String  url)
	           {
	            return false;
	           }
	       };
		
		
		
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true); 
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebViewClient(yourWebClient);
		
		webView.loadUrl(url);
		
		
 
	}
}
