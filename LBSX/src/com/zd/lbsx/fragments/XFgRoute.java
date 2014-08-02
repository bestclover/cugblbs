package com.zd.lbsx.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.zd.application.MyApplication;
import com.zd.lbsx.R;
import com.zd.lbsx.XActSearchRoute;
import com.zd.lbsx.adpter.AdpSpinner;
import com.zd.lbsx.listener.XMKSearchListener;

@SuppressLint("ValidFragment")
public class XFgRoute extends XFgBase implements OnItemSelectedListener,
		OnClickListener {

	private Spinner spin_address;
	private Button bt_search_route;

	private static BMapManager bMapManager;
	private MapView mapView;
	private MKSearch mkSearch;

	private ArrayList<String> list = new ArrayList<String>();
	private String startString;
	private String endString;

	public XFgRoute(String startString, String endString) {
		super();
		this.startString = startString;
		this.endString = endString;
	}

	public XFgRoute() {
		super();
	}

	@Override
	protected int setFragmentView() {
		return R.layout.fg_route;
	}

	@Override
	protected void initView(View v) {
		spin_address = (Spinner) v.findViewById(R.id.spinner);
		bt_search_route = (Button) v.findViewById(R.id.bt_search_route);
	}

	@Override
	protected void initListener() {
		spin_address.setOnItemSelectedListener(this);
		bt_search_route.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		// ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(
		// getActivity(), R.layout.spinner_item, new String[] {
		// "�й����ʴ�ѧ(����)9��¥", "�й����ʴ�ѧ(����)�ۺ�¥" });

		list.add("�й����ʴ�ѧ(����)9��¥");
		list.add("�й����ʴ�ѧ(����)ͼ���");
		AdpSpinner spinAdapter = new AdpSpinner(getActivity(), list);
		spin_address.setAdapter(spinAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		bMapManager = new BMapManager(getActivity().getApplicationContext());
		bMapManager.init(MyApplication.getAK(), null);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mapView = (MapView) getActivity().findViewById(R.id.bmapsView);
		mapView.setBuiltInZoomControls(false);
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		MapController mMapController = mapView.getController();
		// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		GeoPoint point = new GeoPoint((int) (39.997161 * 1E6),
				(int) (116.354123 * 1E6));
		// ���õ�ͼ���ĵ�
		mMapController.setCenter(point);
		// ���õ�ͼzoom����
		mMapController.setZoom(20);
		mkSearch = new MKSearch();
		mkSearch.init(bMapManager,
				new XMKSearchListener(getActivity(), mapView));
		if (startString != null && endString != null) {
			new SearchRouteTask().execute();
		}
	}

	@Override
	public void onDestroy() {
		mapView.destroy();
		if (bMapManager != null) {
			bMapManager.destroy();
			bMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	public void onPause() {
		mapView.onPause();
		if (bMapManager != null) {
			bMapManager.stop();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		mapView.onResume();
		if (bMapManager != null) {
			bMapManager.start();
		}
		super.onResume();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long id) {
		// ��ȡ��������ַ���
		String keyString = arg0.getAdapter().getItem(position).toString();
		// �жϷǿ�
		if (keyString.equals("")) {
			Toast.makeText(getActivity(), "������ѡ����Ŀ", 1000).show();
		} else {
			// �ڳ�����������Ȥ��
			mkSearch.poiSearchInCity("����", keyString);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_search_route:
			Intent intent = new Intent(getActivity(), XActSearchRoute.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	class SearchRouteTask extends AsyncTask<Void, Void, Void> {
		Geocoder geocoder;
		GeoPoint sgeoPoint = null;
		GeoPoint egeoPoint = null;
		MKPlanNode startMkPlanNode, endMkPlanNode;

		@Override
		protected void onPreExecute() {
			geocoder = new Geocoder(getActivity().getApplicationContext(),
					Locale.CHINA);
			startMkPlanNode=new MKPlanNode();
			endMkPlanNode=new MKPlanNode();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Address startAddress = geocoder.getFromLocationName(
						"����,�й����ʴ�ѧ,��ѧ¥", 1).get(0);
				double slon = startAddress.getLongitude();
				double slat = startAddress.getLatitude();	
				sgeoPoint = new GeoPoint((int) slat, (int) slon);
				Address endAddress = geocoder.getFromLocationName("����,�й����ʴ�ѧ,", 1).get(0);
				double elon = endAddress.getLongitude();
				double elat = endAddress.getLatitude();
				egeoPoint = new GeoPoint((int) elat, (int) elon);
				startMkPlanNode.pt = sgeoPoint;
				endMkPlanNode.pt = egeoPoint;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mkSearch.walkingSearch(null, startMkPlanNode, null, endMkPlanNode);
			super.onPostExecute(result);
		}

	}
}
