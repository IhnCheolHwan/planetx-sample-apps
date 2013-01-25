package com.skp.opx.mss.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.skp.opx.mss.ui.R;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.Define;
import com.skp.opx.mss.entity.EntityMelonNewSongChart;
import com.skp.opx.mss.ui.adapter.Adpater_MelonChartNewSong;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;

/**
 * @���� : �ֽŰ� ��Ʈ ��ȸ Activity
 * @Ŭ������ : ChartNewSongsActivity
 * 
 */
public class ChartNewSongsActivity extends ListActivity implements OnClickListener, OnItemClickListener{
	private ImageButton 			mImgBtnSearch;
	private TextView				mTvSubTitle;
	private ListView				mContentListview;
	
	private ArrayList<EntityMelonNewSongChart> mArray;
	
	Adpater_MelonChartNewSong 		mChartNewSongAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chart_list);
		
		initWidgets();
		initNewSongsList();

	}

	/** 
	 *  View �ʱ�ȭ Method
	 * */
	private void initWidgets() {
		
		mImgBtnSearch		= (ImageButton)findViewById(R.id.search_bt);
		mImgBtnSearch.setOnClickListener(this);

		mTvSubTitle			= (TextView)findViewById(R.id.sub_title_tv);
		mTvSubTitle.setText(getString(R.string.recent_music));
	
		mContentListview	= (ListView) findViewById(android.R.id.list);
		mContentListview.setOnItemClickListener(this);
		
		mChartNewSongAdapter = new Adpater_MelonChartNewSong(this);
	}

	/**
	 * @���� : Melon �ֽŰ�
	 * @RequestURI : http://apis.skplanetx.com/melon/newreleases/songs
	 */
	private void initNewSongsList() {
		
		//Querystring Parameter
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 0);   //��ȸ�� ����� �������� �����մϴ�
		map.put("count", 10); //�������� ��µǴ� �� ���� �����մϴ�
		
		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.MELON_NEW_SONGS_URI, map);

		try {
			//API ȣ��
			AsyncRequester.request(this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityMelonNewSongChart(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mArray = (ArrayList<EntityMelonNewSongChart>)entityArray;
					mChartNewSongAdapter.setMelonChartList(mArray);
					setListAdapter(mChartNewSongAdapter);
				}
			}, null, "menuId"));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v)
	{
		if(v.getId()==R.id.search_bt)
		{
			startActivity(new Intent(this, SearchListActivity.class));
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		Intent intent = new Intent(this, ChartNewSongsDetailActivity.class);
		intent.putExtra("ENTITY", mArray.get(position));
		startActivity(intent);
	}

}












