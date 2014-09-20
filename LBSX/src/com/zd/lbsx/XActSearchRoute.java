package com.zd.lbsx;

import java.util.ArrayList;

import com.zd.lbsx.adpter.AdpSpinner;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

public class XActSearchRoute extends XActBase implements OnItemSelectedListener{

	private Spinner startSpinner;
	private Spinner endSpinner;
	private Button btn_search;

	private ArrayList<String> list = new ArrayList<String>();

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.search) {
			Intent intent=new Intent(this,XActMain.class);
			intent.putExtra("start", startSpinner.getSelectedItem().toString());
			intent.putExtra("end", endSpinner.getSelectedItem().toString());
			setResult(200, intent);
			finish();
		}
	}

	@Override
	protected int setContentLayout() {
		return R.layout.act_search_route;
	}

	@Override
	protected void initView() {
		startSpinner = (Spinner) findViewById(R.id.startSpinner);
		endSpinner = (Spinner) findViewById(R.id.destSpnnner);
		btn_search = (Button) findViewById(R.id.search);
	}

	@Override
	protected void initListener() {
		startSpinner.setOnItemSelectedListener(this);
		endSpinner.setOnItemSelectedListener(this);
		btn_search.setOnClickListener(this);
	}

	@Override
	protected void initData() {

		list.add("�й����ʴ�ѧ(����)9��¥");
		list.add("�й����ʴ�ѧ-����");
		list.add("�й����ʴ�ѧ����ѧԺ");
		list.add("�й����ʴ�ѧ�ۺ�¥");
		list.add("�й����ʴ�ѧ19��¥");
		list.add("�й����ʴ�ѧ18��¥");
		list.add("�й����ʴ�ѧѧ��ʳ��");
		list.add("�й����ʴ�ѧ�̹�ʳ��");
		list.add("�й����ʴ�ѧ���չ㳡");
		list.add("�й����ʴ�ѧ��3¥");
		list.add("�й����ʴ�ѧ��2¥");
		list.add("�й����ʴ�ѧ�˶���");
		list.add("�й����ʴ�ѧͼ���");
		AdpSpinner adpSpinner = new AdpSpinner(this, list);
		startSpinner.setAdapter(adpSpinner);
		endSpinner.setAdapter(adpSpinner);
		
	}

	@Override
	public void onItemSelected(AdapterView<?> spinner, View view, int position,
			long id) {
		switch (spinner.getId()) {
		case R.id.startSpinner:		
			break;
		case R.id.destSpnnner:
			break;
		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> spinner) {
	}

}
