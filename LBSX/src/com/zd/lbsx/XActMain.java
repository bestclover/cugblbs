package com.zd.lbsx;

/*
 * create by Juice Zhu 2014.7.31
 */
<<<<<<< HEAD
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
=======
>>>>>>> e9f624a1d24ba0aeb599462df0d3db005411a00d

import com.zd.lbsx.fragments.XFgBase;
import com.zd.lbsx.fragments.XFgFind;
import com.zd.lbsx.fragments.XFgInfo;
import com.zd.lbsx.fragments.XFgMy;
import com.zd.lbsx.fragments.XFgRoute;

<<<<<<< HEAD
=======
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

>>>>>>> e9f624a1d24ba0aeb599462df0d3db005411a00d
public class XActMain extends XActBase implements
		android.widget.RadioGroup.OnCheckedChangeListener {

	private RadioGroup radioGroup;
	private long mLastTimeBackPressed;

	private XFgInfo xFgInfo;

	@Override
	public void onClick(View v) {
	}

	@Override
	protected int setContentLayout() {
		return R.layout.act_main;
	}

	@Override
	protected void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
	}

	@Override
	protected void initListener() {
		radioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	protected void initData() {
		XFgRoute fg = null;
		Intent intent = getIntent();
		String startString = intent.getStringExtra("start");
		String endsString = intent.getStringExtra("end");
		if (startString != null && endsString != null) {
			fg = new XFgRoute(startString, endsString);
		} else {
			fg = new XFgRoute();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fg).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		XFgBase fg = null;
		switch (checkedId) {
		case R.id.rb_route:
			fg = new XFgRoute();
			break;
		case R.id.rb_info:
			fg = new XFgInfo();
			break;
		case R.id.rb_my:
			fg = new XFgMy();
			break;
		case R.id.rb_find:
			fg = new XFgFind();
			break;
		default:
			fg = new XFgRoute();
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fg).commit();
	}
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	 //�������·��ؼ�
	 if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==
	 KeyEvent.ACTION_DOWN) {
	 /*�Ѿ������һ��fragment
	 * getSupportFragmentManager()����getFragmentManager()
	 * ����Ҫ����add to back stack �����ĸ�*/
	 //if no more history in stack
	 Log.i("s", "main_on_back");
	 if (XActMain.this.getFragmentManager().getBackStackEntryCount() == 0) {
	 //��ʾ�˳���ҵ���߼�
	 event.startTracking();
	 return true;
	 }
	 }
	 return super.onKeyDown(keyCode, event);
	 }

}
