package com.zd.lbsx.listener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
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
import com.baidu.platform.comapi.basestruct.GeoPoint;
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
	public void onGetAddrResult(MKAddrInfo res, int error) {
		if (error != 0) {
            String str = String.format("����ţ�%d", error);
            Toast.makeText(mainActivity, str,
                    Toast.LENGTH_LONG).show();
            return;
        }
        // ��ͼ�ƶ����õ�
		MyMapView.getController().animateTo(res.geoPt);
        if (res.type == MKAddrInfo.MK_GEOCODE) {
            // ������룺ͨ����ַ���������
            String strInfo = String.format("γ�ȣ�%f ���ȣ�%f",
                    res.geoPt.getLatitudeE6() / 1e6,
                    res.geoPt.getLongitudeE6() / 1e6);
            Toast.makeText(mainActivity, strInfo,
                    Toast.LENGTH_LONG).show();
        }
        if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
            // ��������룺ͨ������������ϸ��ַ���ܱ�poi
            String strInfo = res.strAddr;
            Toast.makeText(mainActivity, strInfo,
                    Toast.LENGTH_LONG).show();

        }
        // ����ItemizedOverlayͼ��������ע�����
        ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(
                null, MyMapView);
        // ����Item
        OverlayItem item = new OverlayItem(res.geoPt, "", null);
        // �õ���Ҫ���ڵ�ͼ�ϵ���Դ
        Drawable marker = mainActivity.getResources().getDrawable(
                R.drawable.ic_launcher);
        // Ϊmaker����λ�úͱ߽�
        marker.setBounds(0, 0, marker.getIntrinsicWidth(),
                marker.getIntrinsicHeight());
        // ��item����marker
        item.setMarker(marker);
        // ��ͼ�������item
        itemOverlay.addItem(item);

        // �����ͼ����ͼ��
        MyMapView.getOverlays().clear();
        // ���һ����עItemizedOverlayͼ��
        MyMapView.getOverlays().add(itemOverlay);
        // ִ��ˢ��ʹ��Ч
        MyMapView.refresh();

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
	public void onGetTransitRouteResult(MKTransitRouteResult result, int arg1) {
	}

	@Override
	public void onGetWalkingRouteResult(MKWalkingRouteResult result, int arg1) {
		if (result == null) {
			return;
		}
		System.out.println("����·����ʾ�Ļص�����`");
		MyMapView.getOverlays().clear();
		RouteOverlay routeOverlay = new RouteOverlay(mainActivity, MyMapView);
		routeOverlay.setData(result.getPlan(0).getRoute(0));
		MyMapView.getOverlays().add(routeOverlay);
		MyMapView.refresh();
		GeoPoint point = new GeoPoint((int) (39.997161 * 1E6),
				(int) (116.354123 * 1E6));
		MKRoute route = result.getPlan(0).getRoute(0);
		TextView routeTextView = (TextView) mainActivity
				.findViewById(R.id.route_info);
		routeTextView.setText("");
		routeTextView.setVisibility(View.VISIBLE);
		for (int i = 0; i < route.getNumSteps(); i++) {
			MKStep step = route.getStep(i);
			routeTextView.append(i + 1 + "��" + step.getContent().toString()
					+ "\n");

		}
	}

}
