package com.zd.lbsx.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.zd.lbsx.utils.NetWorkUtils;

@SuppressLint("ValidFragment")
public class XFgRoute extends XFgBase implements OnItemSelectedListener,
		OnClickListener {
	private Spinner spin_address;
	private Button bt_search_route;
	private TextView bt_error;
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
		bt_error = (TextView) v.findViewById(R.id.network_error);
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
		// "中国地质大学(北京)9号楼", "中国地质大学(北京)综合楼" });

		list.add("中国地质大学(北京)9号楼");
		list.add("中国地质大学-东门");
		list.add("中国地质大学海洋学院");
		list.add("中国地质大学综合楼");
		list.add("中国地质大学19号楼");
		list.add("中国地质大学18号楼");
		list.add("中国地质大学学生食堂");
		list.add("中国地质大学教3楼");
		list.add("中国地质大学教2楼");
		list.add("中国地质大学运动场");
		list.add("中国地质大学图书馆");

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
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		MapController mMapController = mapView.getController();
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		GeoPoint point = new GeoPoint((int) (39.997161 * 1E6),
				(int) (116.354123 * 1E6));
		// 设置地图中心点
		mMapController.setCenter(point);
		// 设置地图zoom级别
		mMapController.setZoom(20);
		mkSearch = new MKSearch();
		mkSearch.init(bMapManager,
				new XMKSearchListener(getActivity(), mapView));
		if (!NetWorkUtils.isNetworkAvailable(getActivity())) {
			Toast.makeText(getActivity(), "网络不给力，请稍后尝试~", Toast.LENGTH_LONG)
					.show();
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
		mapView.getController().setZoom(20);
		// 获取点中项的字符串
		final String keyString = arg0.getAdapter().getItem(position).toString();
		// 判断非空
		if (keyString.equals("")) {
			Toast.makeText(getActivity(), "请重新选择项目", 1000).show();
		} else {
			Log.i("onItemSelected------->", "onItemSelected");
			new Thread(new Runnable() {

				@Override
				public void run() {
					int k=0;
					k = mkSearch.poiSearchInCity("北京", keyString);
					Log.i("mksearch", "搜索");
					while (k != 0) {
						Log.i("mksearch", "搜索");
						k = mkSearch.poiSearchInCity("北京", keyString);
					}
				}
			}).start();

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
			startActivityForResult(intent, 100);
			break;
		default:
			break;
		}
	}

	class SearchRouteTask extends AsyncTask<String, Integer, String> {
		Geocoder geocoder;
		GeoPoint sgeoPoint = null;
		GeoPoint egeoPoint = null;
		GeoPoint centerPoint = null;
		MKPlanNode startMkPlanNode, endMkPlanNode;

		@Override
		protected void onPreExecute() {
			Log.i("Task is prepare to run", "Task is prepare to run");
			geocoder = new Geocoder(getActivity());
			startMkPlanNode = new MKPlanNode();
			endMkPlanNode = new MKPlanNode();
		}

		@Override
		protected String doInBackground(String... arg0) {
			Log.i("Task is doinbackground", "Task is doinbackground");
			try {
				JSONObject sret = getLocationInfo(startString);
				JSONObject eret = getLocationInfo(endString);
				JSONObject location;
				location = sret.getJSONObject("result").getJSONObject(
						"location");
				double slon = location.getDouble("lng");
				double slat = location.getDouble("lat");
				sgeoPoint = new GeoPoint((int) (slat * 1E6), (int) (slon * 1E6));
				location = eret.getJSONObject("result").getJSONObject(
						"location");
				double elon = location.getDouble("lng");
				double elat = location.getDouble("lat");
				GeoPoint point = new GeoPoint((int) (39.997161 * 1E6),
						(int) (116.354123 * 1E6));
				egeoPoint = new GeoPoint((int) (elat * 1E6), (int) (elon * 1E6));
				startMkPlanNode.pt = sgeoPoint;
				endMkPlanNode.pt = egeoPoint;
				centerPoint = new GeoPoint(
						(point.getLatitudeE6() + egeoPoint
								.getLatitudeE6()) / 2,
						(point.getLongitudeE6() + egeoPoint
								.getLongitudeE6()) / 2);
				int k=0;
				k=mkSearch.walkingSearch(null, startMkPlanNode, null,
						endMkPlanNode);
				while(k!=0){
					k=mkSearch.walkingSearch(null, startMkPlanNode, null,
							endMkPlanNode);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "result";
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("Task is finished", "Task is finished~");
			mapView.getController().setCenter(centerPoint);
			mapView.getController().setZoom(15);
		}

	}

	private SearchRouteTask searchRouteTask = null;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 200) {
			startString = data.getStringExtra("start");
			endString = data.getStringExtra("end");
			new SearchRouteTask().execute("");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public JSONObject getLocationInfo(String string) {

		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geocoder/v2/?address=" + string
						+ "&output=json&ak=KlzWmZKrOkO3P8yxKTwYN9rh");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private void showError() {
		bt_error.setVisibility(View.VISIBLE);
		bt_search_route.setVisibility(View.GONE);
		spin_address.setVisibility(View.GONE);
		mapView.setVisibility(View.GONE);
	}

	private void showMap() {
		bt_error.setVisibility(View.GONE);
		bt_search_route.setVisibility(View.VISIBLE);
		spin_address.setVisibility(View.VISIBLE);
		mapView.setVisibility(View.VISIBLE);
	}
}
