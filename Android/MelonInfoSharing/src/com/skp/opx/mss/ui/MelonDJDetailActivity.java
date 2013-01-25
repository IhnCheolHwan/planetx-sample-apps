package com.skp.opx.mss.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.Define;
import com.skp.opx.mss.entity.EntityMelonDJCategory;
import com.skp.opx.mss.entity.EntityMelonDJDetail;
import com.skp.opx.mss.ui.adapter.Adapter_MelonDJDetail;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @���� : MelOn DJ ������ Activity
 * @Ŭ������ : MelonDJDetailActivity
 * 
 */
public class MelonDJDetailActivity extends ListActivity implements OnClickListener{

	private EntityMelonDJCategory mCateogoryInfo;
	private Adapter_MelonDJDetail mDJDetailAdapter;
	ArrayList<EntityMelonDJDetail> mDJDetailArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dj_detail_list);

		mCateogoryInfo = (EntityMelonDJCategory)getIntent().getSerializableExtra("CATEGORYENTITY");

		initWidgets();
		initDJDetailList();
	}

	/** 
	 *  View �ʱ�ȭ Method
	 * */
	private void initWidgets() {

		findViewById(R.id.back_bt).setOnClickListener(this);
		TextView subTitle = (TextView)findViewById(R.id.sub_title_tv);
		subTitle.setText(mCateogoryInfo.offeringTitle);
		
		mDJDetailAdapter = new Adapter_MelonDJDetail(this);
	}

	/**
	 * @���� : Melon DJ ������
	 * @RequestURI : http://apis.skplanetx.com/melon/melondj/categories/{categoryId}/offerings/{offeringId}
	 * @RequestPathParam : 
	 * {categoryId} ī�װ� ID�Դϴ�
	 * {offeringId} ��� DJ���� ���Ƿ� �ο��� ���� ���� ID�Դϴ�
	 */
	private void initDJDetailList() {

		//Querystring Parameters
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1); //��ȸ�� ����� �������� �����մϴ�
		map.put("count", 50);  //�������� ��µǴ� �� ���� �����մϴ�
		
		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.MELON_DJ_CATEGORY_URI + mCateogoryInfo.categoryId + "/offerings/"  + mCateogoryInfo.offeringId , map);

		try {
			//API ȣ��
			AsyncRequester.request(MelonDJDetailActivity.this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityMelonDJDetail(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mDJDetailArray = (ArrayList<EntityMelonDJDetail>)entityArray;
					mDJDetailAdapter.setMelonDJDetailList(mDJDetailArray);
					setListAdapter(mDJDetailAdapter);
				}
			}, null, "menuId"));
		} catch (CloneNotSupportedException e) {
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// ���������� �̵� - �ش� position�� arr ���� �Ѱ���
		Intent intent = new Intent(this, MelonDJRecommendDetailActivity.class);
		intent.putExtra("ENTITY", mDJDetailArray.get(position));
		Log.d("TEST", "menuId : " + mDJDetailArray.get(position).menuId);
		startActivity(intent);
	}

}













