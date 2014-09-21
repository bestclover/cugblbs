package com.zd.lbsx.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zd.lbsx.R;
//import com.zd.lbsx.XActMain.BackWeview;

//public class XFgInfo extends XFgBase implements OnItemClickListener, BackWeview {
public class XFgInfo extends XFgBase implements OnItemClickListener{
	private WebView WebView_Info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int setFragmentView() {
		return R.layout.fg_info;
	}

	@Override
	protected void initView(View v) {
		WebView_Info = (WebView) v.findViewById(R.id.WebView_Info);
	}

	@Override
	protected void initData() {
		WebView_Info.setWebChromeClient(new WebChromeClient());
		WebView_Info.setWebViewClient(new WebViewClient() {
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

		// WebSettings webSet = WebView_Info.getSettings();
		// webSet.setJavaScriptEnabled(true);
		// WebView_Info.addJavascriptInterface(new DemoJavaScriptInterface(),
		// "demo");
		WebView_Info.loadUrl("http://192.168.1.169:8080/XFgInfo/Shownewslist");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	 @Override
	 protected void initListener() {
	 // TODO Auto-generated method stub
	 /**
	 * ������Ӧ����WebView�鿴��ҳʱ�������ؼ���ʱ�������ʷ�˻أ���������˴���������WebView�����˳�
	 */
	 WebView_Info.setOnKeyListener(new OnKeyListener() {
	 @Override
	 public boolean onKey(View v, int keyCode, KeyEvent event) {
	 Log.i("s1", "XFgInfo_on_back");
	 if (event.getAction() == KeyEvent.ACTION_DOWN) {
	 if (keyCode == KeyEvent.KEYCODE_BACK
	 && WebView_Info.canGoBack()) { // ��ʾ�����ؼ� ʱ�Ĳ���
	 WebView_Info.goBack(); // ����
	 return true; // �Ѵ���
	 }
	 }
	 return false;
	 }
	 });
	 }

//	@Override
//	public boolean backwebview() {
//		Log.i("back", "back");
//		if (WebView_Info.canGoBack()) {
//			WebView_Info.goBack();
//			return true;
//		} else {
//			return false;
//		}
//	}


}
