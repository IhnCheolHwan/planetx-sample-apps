package com.skp.opx.svc.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.skp.openplatform.android.sdk.api.APIRequest;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.common.RequestListener;
import com.skp.openplatform.android.sdk.common.ResponseMessage;
import com.skp.openplatform.android.sdk.oauth.Constants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.openplatform.android.sdk.oauth.SKPOPException;
import com.skp.opx.core.client.Define;
import com.skp.opx.sdk.PopupDialogUtil;
import com.skp.opx.svc.R;
import com.skp.opx.svc.entity.EntityTmapPathDetail;
import com.skp.opx.svc.ui.adapter.Adapter_TmapPathDetail;
import com.skp.opx.svc.utils.ConvertUnitUtil;

/**
 * @���� : Tmap ��� �� Activity
 * @Ŭ������ : TmapPathDetailActivity
 * 
 */
public class TmapPathDetailActivity extends ListActivity implements OnClickListener{

	private ArrayList<EntityTmapPathDetail> mMovePathDetailArray;
	private Adapter_TmapPathDetail mMovePathDetailAdapter;

	private ArrayList<String> mNameList = new ArrayList<String>(); //point name
	private ArrayList<String> mTuryTypeList = new ArrayList<String>(); 
	private ArrayList<String> mDistanceList = new ArrayList<String>();
	private ArrayList<String> mDescriptionList = new ArrayList<String>();

	private String mLocation     = ""; //�����ġ
	private String mRsdStartXPos = ""; //����� �浵
	private String mRsdStartYPos = ""; //����� ����
	private String mRsdEndXPos   = ""; //������ �浵
	private String mRsdEndYPos   = ""; //����� ����
	
	private String mRsdTotLen     = ""; //�ѱ���
	private String mRsdTotDtm     = ""; //�ҿ�ð�	

	private TMapView mTmapview; //TMap
	private TextView mLocationTv;
	private TextView mDistanceTv;
	private TextView mTimeTv;

	private final int FINDPATH = 0;
	private final int SHOWDETAILPATH = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());       
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tmap_path_detail);
	
		getIntentData(); //������ ��ġ setting

		//���� �� progress Dialog
		PopupDialogUtil.showProgressDialog(TmapPathDetailActivity.this, getString(R.string.app_name), getString(R.string.progress_msg));
		initWidgets();
		mHandler.sendEmptyMessage(FINDPATH); //��� ��û
	}

	/** 
	 *  View �ʱ�ȭ Method
	 * */
	private void initWidgets() {

		findViewById(R.id.back_bt).setOnClickListener(this);
		mLocationTv = (TextView)findViewById(R.id.location_tv);
		mDistanceTv = (TextView)findViewById(R.id.distance_tv);
		mTimeTv = (TextView)findViewById(R.id.time_tv);
		
        RadioGroup rg = (RadioGroup)findViewById(R.id.appear_type_rg);
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch(checkedId) {
				case R.id.map_rb :
					findViewById(R.id.context_ly).setVisibility(View.GONE);
					findViewById(R.id.map_ly).setVisibility(View.VISIBLE);
					break;
					
				case R.id.context_rb :
					findViewById(R.id.context_ly).setVisibility(View.VISIBLE);
					findViewById(R.id.map_ly).setVisibility(View.GONE);
					break;
				}
			}
		});
        
        makeTMapWidget();
	}

    /**
     * TMap�� �����Ѵ�.
     */
    private void makeTMapWidget() {
    	
    	LinearLayout linear =(LinearLayout)findViewById(R.id.map_ly);
		mTmapview = new TMapView(this);
		linear.addView(mTmapview);
    }
	
    /**
     * TMap�� �ʱ�ȭ ��Ų��.
     */
    private void tmapSet() {
    
		mTmapview.setSKPMapApiKey(Define.OAuth.KEY);
		mTmapview.setLanguage(mTmapview.LANGUAGE_KOREAN);
		mTmapview.setZoomLevel(15);
		mTmapview.setMapType(mTmapview.MAPTYPE_STANDARD);
		mTmapview.setTrackingMode(true);
		mTmapview.setIconVisibility(true);
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		
		tmapSet();
	}
    
    /**
     * TMap ���Ž�� �� ǥ���Ѵ�.
     */
	private void findPath() {
		
		TMapData tmapdata = new TMapData();
		mTmapview.setLocationPoint(Double.parseDouble(mRsdStartXPos), Double.parseDouble(mRsdStartYPos));
		mTmapview.setCenterPoint(Double.parseDouble(mRsdStartXPos), Double.parseDouble(mRsdStartYPos));
		TMapPoint startpoint = new TMapPoint(Double.parseDouble(mRsdStartYPos), Double.parseDouble(mRsdStartXPos));
		TMapPoint endpoint = new TMapPoint(Double.parseDouble(mRsdEndYPos), Double.parseDouble(mRsdEndXPos));
		try {
			TMapPolyLine polyline = tmapdata.findPathData(startpoint, endpoint);
			mTmapview.addTMapPolyLine("TestID", polyline);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	/** 
	 * get Intent 
	 * */
	private void getIntentData(){

		Intent intent = getIntent();    
		mLocation = intent.getStringExtra("location");
		mRsdEndXPos = intent.getStringExtra("rsdEndXPos");
		mRsdEndYPos = intent.getStringExtra("rsdEndYPos");
		//���� ��ǥ�� �޴´�
		mRsdStartXPos = intent.getStringExtra("rsdStartXPos");
		mRsdStartYPos = intent.getStringExtra("rsdStartYPos");
		
	}

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg) {
			if(msg.what == FINDPATH){   //��� ã��
				findPath();
				getRouteByCar();

			}else if(msg.what == SHOWDETAILPATH){  //�� ��� UI binding
				long len = Long.parseLong(mRsdTotLen);
				mLocationTv.setText(mLocation);
				mDistanceTv.setText(getString(R.string.until_arrived) + ConvertUnitUtil.convertMeterToKiroMeter(String.valueOf(len)));
				mTimeTv.setText(getString(R.string.for_time) + ConvertUnitUtil.convertSecondToProperTime(mRsdTotDtm));

				EntityTmapPathDetail detailPath = null;
				mMovePathDetailArray = new ArrayList<EntityTmapPathDetail>();

				for (int i = 0; i <mNameList.size(); i++) {

					detailPath = new EntityTmapPathDetail();
					detailPath.mStartLocation = getString(R.string.current_pos);
					detailPath.mDestination = mLocation;
					detailPath.mTotalTime = ConvertUnitUtil.convertSecondToProperTime(mRsdTotDtm);
					detailPath.mName = mNameList.get(i);
					detailPath.mTurnType = mTuryTypeList.get(i);
					detailPath.mDistance = mDistanceList.get(i);
					detailPath.mTotalDistance = ConvertUnitUtil.convertMeterToKiroMeter(String.valueOf(len));
					detailPath.mTime = "";
					detailPath.mSpeed = "";
					detailPath.mDestinatioLon = mRsdEndXPos;
					detailPath.mDestinationLat = mRsdEndYPos;
					detailPath.mDescription = mDescriptionList.get(i);
					mMovePathDetailArray.add(detailPath);
				}		

				mMovePathDetailAdapter  = new Adapter_TmapPathDetail(getApplicationContext());
				mMovePathDetailAdapter.setMovePathDetailList(mMovePathDetailArray);
				setListAdapter(mMovePathDetailAdapter);

				PopupDialogUtil.dismissProgressDialog();
			}

			super.handleMessage(msg);
		}
	};

	/**
	 * @���� : T map �ڵ��� ��ξȳ�
	 * @RequestURI : https://apis.skplanetx.com/tmap/routes
	 *              
	 */
	private void getRouteByCar(){				
		
		//Querystring Parameters
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("version", "1");
		param.put("reqCoordType", "WGS84GEO");  //�ް��� �ϴ� ���� ��ǥ�� ������ �����մϴ�
		param.put("endX", mRsdEndXPos);//������ X��ǥ: �浵
		param.put("endY", mRsdEndYPos);//������ Y��ǥ: ����
		param.put("startX", mRsdStartXPos);//����� X��ǥ: �浵
		param.put("startY", mRsdStartYPos);//������ Y��ǥ: ����
		
		RequestBundle requestBundle = new RequestBundle();
		requestBundle.setUrl(Define.TMAP_ROUTES_SEARCH_URI);
		requestBundle.setParameters(param);
		requestBundle.setHttpMethod(HttpMethod.POST);
		requestBundle.setResponseType(CONTENT_TYPE.KML);
		APIRequest api = new APIRequest();
		try {
			api.request(requestBundle, new RequestListener() {

				@Override
				public void onSKPOPException(SKPOPException arg0) {
				}

				@Override
				public void onMalformedURLException(MalformedURLException arg0) {
				}

				@Override
				public void onIOException(IOException arg0) {
				}

				@Override
				public void onComplete(ResponseMessage result) {
					startKMLParsing(getStreamFromString(result.getResultMessage()));
				}

			});			

		} catch (SKPOPException e) {
			e.printStackTrace();
		}	
	}

	public static InputStream getStreamFromString(String str)
	{
		byte[] bytes = null;
		try {
			bytes = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ByteArrayInputStream(bytes);
	}
	
	/**
	 * KML Parsing...
	 * */
	private void startKMLParsing(InputStream stream){

		boolean isPoint = false;
		String tagName     = "";
		String nodeType    = "";
		String name        = "";  //���� �̸�
		String turnType    = "";  //��Ÿ��
		String distance    = "";  //���� �� �Ÿ�
		String description = "";  //���� �̵� ����
		ArrayList<String> nameList        = new ArrayList<String>();
		ArrayList<String> turnTypeList    = new ArrayList<String>();
		ArrayList<String> distanceList    = new ArrayList<String>();
		ArrayList<String> descriptionList = new ArrayList<String>();

		try {
			XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserCreator.newPullParser();
			parser.setInput(stream, "utf-8"); // Data GET START

			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					tagName = parser.getName();

				} else if (eventType == XmlPullParser.TEXT) {

					if (tagName.equals("tmap:totalDistance")) { //���������� �� �̵��Ÿ�
							mRsdTotLen = parser.getText();
					}else if(tagName.equals("tmap:totalTime")){ //���������� �� �ҿ�ð� (��)
							mRsdTotDtm = parser.getText();
					}else if(tagName.equals("tmap:nodeType")){ //��� Ÿ�� (point or line)
							nodeType += parser.getText();
							if(nodeType.equals("POINT")){
								isPoint = true;
							}else{
								isPoint = false;
							}
					}else if(tagName.equals("description")){
							description += parser.getText();			
					}else if(tagName.equals("name")){ //�̸�(point �� ��츸 ���)
							name += parser.getText();			
					}else if(tagName.equals("tmap:turnType")){//point�� ��츸 ����
							turnType+= parser.getText();
					}else if(tagName.equals("tmap:distance")){//line �ϰ�츸 ����
							distance += parser.getText();
					}

				} else if (eventType == XmlPullParser.END_TAG) {

					tagName = parser.getName();

					if (tagName.equals("Placemark") && isPoint) { //����Ʈ �� ��츸 �̸� �ʿ�

						nameList.add(name);
						name = "";

						turnTypeList.add(turnType);
						turnType = "";

						descriptionList.add(description);
						description = "";
					}else if(tagName.equals("Placemark") && isPoint){//����Ʈ �� ��츸

					}else if(tagName.equals("Placemark") && !isPoint){//���� �� ��츸
						distanceList.add(distance);
						distance = "";
					}
					if(tagName.equals("Placemark")){
						name = "";
						turnType = "";
						distance = "";
						nodeType = "";
						description = "";
					}
					tagName = ""; //�ױ� ���� �ʱ�ȭ
				}
				eventType = parser.next();
			}
			nameList.remove(nameList.size()-1); //�������� �ʿ��� ������ ����..
			turnTypeList.remove(turnTypeList.size()-1);//�������� ��Ÿ���� �ʿ� ����.

			mNameList        = (ArrayList<String>) nameList.clone();
			mTuryTypeList    = (ArrayList<String>) turnTypeList.clone();			
			mDistanceList    = (ArrayList<String>) distanceList.clone();
			mDescriptionList = (ArrayList<String>) descriptionList.clone();

			mHandler.sendEmptyMessage(SHOWDETAILPATH);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.back_bt :
			finish();
			break;
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		boolean result = super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_tmap, menu);
		return result;
	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.path_search:
			startActivity(new Intent(this, TmapPathSearchActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			return true;
		case R.id.area_search:
			startActivity(new Intent(this, TmapAreaSearchCategoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			return true;
			
		case R.id.traffic_info : 
			startActivity(new Intent(this, TmapTrafficInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			return true;
		}
		return super.onOptionsItemSelected(item);
	} 

}
