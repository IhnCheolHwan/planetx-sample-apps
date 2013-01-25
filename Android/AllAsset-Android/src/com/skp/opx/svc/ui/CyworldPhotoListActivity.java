package com.skp.opx.svc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.skp.opx.svc.R;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.Define;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;
import com.skp.opx.svc.entity.EntityCyBesties;
import com.skp.opx.svc.entity.EntityCyPhoto;
import com.skp.opx.svc.entity.EntityCyPhotoFolder;
import com.skp.opx.svc.ui.adapter.Adapter_CyworldPhoto;

/**
 * @���� : ���̿��� ���� ����ø��Ϻ��� Activity 
 * @Ŭ������ : CyworldPhotoListActivity
 * 
 */
public class CyworldPhotoListActivity extends ListActivity implements OnClickListener {

	private Adapter_CyworldPhoto mPhotoAdapter;
	private ArrayList<EntityCyPhoto> mPhotoArray;
	private EntityCyBesties mBestiesInfo;
	private ArrayList<EntityCyPhotoFolder> mFolderArray;

	private Spinner mFolderSp;  //����ø ��� spinner
	private ArrayAdapter<String> mSpinnerAdapter = null;
	private ArrayList< String > mSpinnerItems = new ArrayList< String >();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cyworld_photo_list);

		mBestiesInfo = (EntityCyBesties)getIntent().getSerializableExtra("ENTITY");

		initWidgets();
		initCyPhotoFolderList();
	}

	/** 
	 *  View �ʱ�ȭ Method
	 * */
	private void initWidgets() {

		findViewById(R.id.back_bt).setOnClickListener(this);
		TextView homeNameTv = (TextView)findViewById(R.id.home_name_tv);
		homeNameTv.setText(getString(R.string.mini_home_name, mBestiesInfo.cyName));

		mFolderSp = (Spinner)findViewById(R.id.folder_search_sp);
		mPhotoAdapter = new Adapter_CyworldPhoto(this);
	}

	/**
	 * @���� : Cyworld ����ø ���� ��� ����
	 * @RequestURI : https://apis.skplanetx.com/cyworld/minihome/{cyId}/albums
	 * @RequestPathParam :
	 * {cyId} ��ȸ�� ����� ���̿��� ID�Դϴ�
	 */
	private void initCyPhotoFolderList() {

		//Querystring Parameters
		Map<String, Object> map = new HashMap<String, Object>();

		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.CYWORLD_MINIHOME_URI + mBestiesInfo.cyId  + "/albums", map);

		try {
			//API ȣ��
			AsyncRequester.request(CyworldPhotoListActivity.this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityCyPhotoFolder(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mFolderArray = (ArrayList<EntityCyPhotoFolder>)entityArray;

					ArrayList<String> folderNameList = new ArrayList<String>();
					folderNameList.add(getString(R.string.view_all));

					for(int i = 0; i < mFolderArray.size() ; i ++){
						folderNameList.add(mFolderArray.get(i).folderName);
					}

					mSpinnerItems.addAll(folderNameList);
					mSpinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mSpinnerItems);
					mSpinnerAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);       
					mFolderSp.setAdapter(mSpinnerAdapter);
					mFolderSp.setOnItemSelectedListener(mSpinnerSelectedListener);

				}
			}));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @���� : Cyworld ����ø �Խù� ��Ϻ���
	 * @RequestURI : https://apis.skplanetx.com/cyworld/minihome/{cyId}/albums/{folderNo}/items
	 */
	private void initCyPhotoAlbumList(int folderNo) {

		//Querystring Parameters
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1);   //��ȸ�� ����� �������� �����մϴ�.
		map.put("count", 20); //�������� ��µǴ� �Խù� ���� �����մϴ�.

		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, 
				Define.CYWORLD_MINIHOME_URI + mBestiesInfo.cyId + "/albums/" + folderNo + "/items" , map);

		try {
			AsyncRequester.request(CyworldPhotoListActivity.this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityCyPhoto(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mPhotoArray = (ArrayList<EntityCyPhoto>)entityArray;

					mPhotoAdapter.setCyworldPhotoList(mPhotoArray);
					setListAdapter(mPhotoAdapter);
				}
			}));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Spinner Selected Listener 
	 * */
	private OnItemSelectedListener mSpinnerSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			if(position == 0){
				initCyPhotoAlbumList(0);
			}else{
				initCyPhotoAlbumList(mFolderArray.get(position - 1).folderNo);
			}
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}

	};

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.back_bt :
			finish();
			break;
		}
	} 

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent_photo = new Intent(CyworldPhotoListActivity.this, CyworldPhotoDetailActivity.class );
		intent_photo.putExtra("ENTITY", mPhotoArray.get(position));
		intent_photo.putExtra("CYID", mBestiesInfo.cyId);
		startActivity( intent_photo );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		boolean result = super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_cyworld, menu);
		return result;
	} 


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.photo_album:
			return true;
		case R.id.guest_book:
			Intent intent_visit = new Intent(CyworldPhotoListActivity.this, CyworldVisitbookListActivity.class );
			intent_visit.putExtra("ENTITY", mBestiesInfo);
			intent_visit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity( intent_visit );

			return true;
		}
		return super.onOptionsItemSelected(item);
	} 

}
