package com.zd.lbsx;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import com.zd.lbsx.adpter.AdpSpinner;

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

		list.add("�й����ʴ�ѧ(����)");
		list.add("�廪��ѧ");
		list.add("������ѧ");
		list.add("�������պ����ѧ");
		list.add("�����Ƽ���ѧ");
		list.add("�������Դ�ѧ");
		list.add("����������ѧ");
		list.add("�����ʵ��ѧ");
		list.add("�������ϴ�ѧ");
		list.add("������ӰѧԺ");
		list.add("��������ѧ");
		list.add("����������ѧ");
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
