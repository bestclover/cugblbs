package com.zd.lbsx.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zd.lbsx.R;
import com.zd.lbsx.XActInfoItemDetails;

public class XFgInfo extends XFgBase implements  OnItemClickListener{
	private ListView listView_Info;
	private ArrayList<HashMap<String, Object>> listItems_Info;
	private SimpleAdapter listItemAdapter;
	private int[]images;
	private String[] titles , briefs,times;
	
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
		listView_Info = (ListView) v.findViewById(R.id.ListView_Info);
	}
	@Override
	protected void initData() {
		listItems_Info =new ArrayList<HashMap<String, Object>>();
		images = new int[] { R.drawable.cat, R.drawable.dog,// �൱�ڻ�ȡ����
				R.drawable.friend, R.drawable.love, R.drawable.lovely,R.drawable.cat, R.drawable.dog,
				R.drawable.friend, R.drawable.love, R.drawable.lovely };
		titles = new String[] { "cat", "dog", "friend", "love",
				"lovely" ,"cat", "dog", "friend", "love",
				"lovely"};
		briefs = new String[] { "è����ɰ�", "�����Ĺ�", "�����Ǻ�����", "���Ǻ��а�",
				"������ɰ�", "è����ɰ�", "�����Ĺ�", "�����Ǻ�����", "���Ǻ��а�",
				"������ɰ�" };
		times = new String[] { "2014-07-29", "2014-08-01",
				"2014-08-01", "2014-08-01", "2014-08-01","2014-07-29", "2014-08-01",
				"2014-08-01", "2014-08-01", "2014-08-01" };
		for (int i = 0; i < 10; i++) {// �Ӷ������ó�ֵ
			HashMap<String, Object> listItem_Info = new HashMap<String, Object>();
			listItem_Info.put("ItemImage", images[i]);
			listItem_Info.put("ItemTitle", titles[i]);
			listItem_Info.put("ItemBrief", briefs[i]);
			listItem_Info.put("ItemTime", times[i]);
			listItems_Info.add(listItem_Info);
		}
		
		 listItemAdapter = new SimpleAdapter(getActivity(),// this�ǵ�ǰActivity�Ķ���
					listItems_Info,// ����Դ Ϊ������ݺ��ArrayList���͵Ķ���
					R.layout.fg_info_item,// ����Ĳ���.xml�ļ���
					new String[] { "ItemImage", "ItemTitle", "ItemBrief",
							"ItemTime" },
					// ���String�����е�Ԫ�ؾ���list�����е���
					new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemBrief,
							R.id.ItemTime });
	    // ��Ӳ���ʾ
	    listView_Info.setAdapter(listItemAdapter);
	
	}
	@Override
	protected void initListener() {
		listView_Info.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		/*Toast toast  = Toast.makeText(getActivity(), "�����",Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		//����ͼƬ��ͼ����
		ImageView  imageView = new ImageView(getActivity());
		//����ͼƬ
		imageView.setImageResource (images[position]);
		
		//imageView.setImageResource ((Integer)listItems_Info.get(position).get("ImageItem"));
		//���toast����
		LinearLayout toastView = (LinearLayout)toast.getView();
		//���ô˲���Ϊ�����
		toastView.setOrientation(LinearLayout.HORIZONTAL);
		//��imageView ���뵽�˲����еĵ�һ��λ��
		toastView.addView(imageView,0);
		toast.show();*/
		Intent intent = new Intent();
		intent.putExtra("position", position);
		intent.setClass(getActivity(),XActInfoItemDetails.class);
		startActivity(intent);
	}	
}
