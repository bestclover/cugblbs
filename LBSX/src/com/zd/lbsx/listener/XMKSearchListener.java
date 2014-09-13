package com.zd.lbsx.listener;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKStep;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.zd.lbsx.R;

public class XMKSearchListener implements MKSearchListener {

	MapView MyMapView = null;
	Activity mainActivity = null;
	public MKPlanNode defaultStart = null;

	public XMKSearchListener(Activity activity, MapView mapView) {
		this.MyMapView = mapView;
		this.mainActivity = activity;

	}

	@Override
	public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiDetailSearchResult(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

 
	@Override
	public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		// �����ж��Ƿ����������
		if (arg2 != 0 || arg0 == null) {
			Toast.makeText(mainActivity.getApplicationContext(), "û���ҵ������",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// ��������Ƶ���ͼ��
		if (arg0.getCurrentNumPois() > 0) {
			PoiOverlay poiOverlay = new PoiOverlay(mainActivity, MyMapView);
			poiOverlay.setData(arg0.getAllPoi());
			MyMapView.getOverlays().clear();
			MyMapView.getOverlays().add(poiOverlay);
			MyMapView.refresh();
			// ��arg1Ϊ2��������·����4��������·��ʱ�� poi����Ϊ��
			for (MKPoiInfo info : arg0.getAllPoi()) {
				if (info.pt != null) {
					MyMapView.getController().animateTo(info.pt);
					defaultStart = new MKPlanNode();
					defaultStart.pt = info.pt;
					break;
				}
			}
		}

	}

	@Override
	public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onGetWalkingRouteResult(MKWalkingRouteResult result, int arg1) {
		if (result == null) {
			return;
		}
		System.out.println("����·����ʾ�Ļص�����`");
		RouteOverlay routeOverlay = new RouteOverlay(mainActivity, MyMapView);
		routeOverlay.setData(result.getPlan(0).getRoute(0));
		MyMapView.getOverlays().clear();
		MyMapView.getOverlays().add(routeOverlay);
		MyMapView.refresh();
		MKRoute route = result.getPlan(0).getRoute(0);
		TextView routeTextView = (TextView) mainActivity
				.findViewById(R.id.route_info);
		routeTextView.setVisibility(View.VISIBLE);
		for (int i = 0; i < route.getNumSteps(); i++) {
			MKStep step = route.getStep(i);
			routeTextView.append(i + 1 + "��" + step.getContent().toString()
					+ "\n");

		}
	}

}
