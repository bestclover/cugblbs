package com.zd.lbsx.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zd.lbsx.R;

public class XFgMy extends XFgBase implements OnClickListener {
	private WebView contentWebView = null;

	@Override
	protected int setFragmentView() {
		return R.layout.fg_my;
	}

	@SuppressLint("JavascriptInterface")
	@Override
	protected void initView(View v) {
		contentWebView = (WebView) v.findViewById(R.id.webview);
		// ����javascript
		contentWebView.getSettings().setJavaScriptEnabled(true);
		contentWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				Log.i("page progress-------------------->", newProgress + "");
				super.onProgressChanged(view, newProgress);
			}
		});
		contentWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.i("page start-------------------->", "page start");
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.i("page finished-------------------->", "page finished");
				super.onPageFinished(view, url);
			}
		});

		ConnectionDetector cd = new ConnectionDetector(this.getActivity()
				.getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent == false) {
			contentWebView.loadUrl("file:///android_asset/InternetError.html");
		} else {
			contentWebView.loadUrl("http://192.168.95.1:8080/XgMy");
		}
		contentWebView.addJavascriptInterface(this, "post");
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		/**
		 * ������Ӧ����WebView�鿴��ҳʱ�������ؼ���ʱ�������ʷ�˻أ���������˴���������WebView�����˳�
		 */
		contentWebView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Log.i("s1", "XFgInfo_on_back");
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK
							&& contentWebView.canGoBack()) { // ��ʾ�����ؼ� ʱ�Ĳ���
						contentWebView.loadUrl("http://192.168.95.1:8080/XgMy");
						return true; // �Ѵ���
					}
				}
				return false;
			}
		});
	}

	@Override
	protected void initData() {
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {

	}

	// class MyWebViewClient extends WebViewClient {
	// @Override
	// public boolean shouldOverrideUrlLoading(WebView view, String url){
	// // ��д�˷������������ҳ��������ӻ����ڵ�ǰ��webview����ת��������������Ǳ�
	// view.loadUrl(url);
	// return true;
	// }
	// }
	// �ж������Ƿ�������
	public class ConnectionDetector {

		private Context _context;

		public ConnectionDetector(Context context) {
			this._context = context;
		}

		public boolean isConnectingToInternet() {
			ConnectivityManager connectivity = (ConnectivityManager) _context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
					for (int i = 0; i < info.length; i++)
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
			}
			return false;
		}
	}

}
