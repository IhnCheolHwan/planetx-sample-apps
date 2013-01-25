package com.skp.opx.svc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.skp.opx.svc.R;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.Define;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;
import com.skp.opx.svc.entity.Entity11stCategory;
import com.skp.opx.svc.entity.Entity11stCategorySub;
import com.skp.opx.svc.ui.adapter.Adapter_11stSettingSubCategory;

/**
 * @���� : 11����  sub category ���� Activity
 * @Ŭ������ : MenuSetting11StSubCategotyListActivity
 * 
 */
public class MenuSetting11StSubCategotyListActivity extends ListActivity implements OnClickListener{
	
	private Adapter_11stSettingSubCategory mCategoryAdapter;
	private ArrayList<Entity11stCategorySub> mCategoryArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu_setting_eleven_st_sub_category);

		Entity11stCategory categoryEntity = (Entity11stCategory)getIntent().getSerializableExtra("ENTITY");

		initWidgets();
		initCategoryList(categoryEntity.CategoryCode);
	}

	/** 
	 *  View �ʱ�ȭ Method
	 * */
	private void initWidgets() {

		findViewById(R.id.back_bt).setOnClickListener(this);
		
		mCategoryAdapter = new Adapter_11stSettingSubCategory(this);
	}
	
	/**
	 * @���� : 11����  ī�װ� ��ȸ
	 * @RequestURI : http://apis.skplanetx.com/11st/common/categories/{categoryCode}
	 * @RequestPathParam :
	 * {categoryCode} ī�װ� �ڵ� �����Դϴ�
	 */
	private void initCategoryList(int categoryCode) {

		//Querystring Parameters
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("option", "Children");  //�ΰ� ������ �����մϴ�

		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.ELEVEN_ST_CATEGORY_URI + "/" + String.valueOf(categoryCode) , map);

		try {
			//API ȣ��
			AsyncRequester.request(MenuSetting11StSubCategotyListActivity.this, bundle, HttpMethod.GET, new EntityParserHandler(new Entity11stCategorySub(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mCategoryArray = (ArrayList<Entity11stCategorySub>)entityArray;
					mCategoryAdapter.set11stSubCategoryList(mCategoryArray);
					setListAdapter(mCategoryAdapter);
				}
			},"RootCategory"));
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
		
		
	}

}
