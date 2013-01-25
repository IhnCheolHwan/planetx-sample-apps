package com.skp.opx.svc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.skp.opx.svc.R;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.Define;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;
import com.skp.opx.svc.entity.EntityCyPhoto;
import com.skp.opx.svc.entity.EntityCyPhotoDetail;
import com.skp.opx.svc.utils.ImageDownloader;

/**
 * @���� : ���̿��� ����ø �󼼺��� Activity
 * @Ŭ������ : CyworldPhotoDetailActivity
 * 
 */
public class CyworldPhotoDetailActivity extends Activity implements OnClickListener{

	private EntityCyPhoto mCyphotoInfo;
	private ArrayList<EntityCyPhotoDetail> mDetailArray;

	private String mCyId;

	private TextView mPhotoTitleTv;
	private TextView mPhotoDateTv;
	private ImageView mPhotoIv;
	private TextView mExposeTv;
	private TextView mContentsTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cyworld_photo_detail);

		getIntentData();		
		initWidgets();

		initPhotoDetail(mCyId);
	}

	/** 
	 *  View �ʱ�ȭ Method
	 * */
	private void initWidgets() {

		findViewById(R.id.back_bt).setOnClickListener(this);
		findViewById(R.id.list_view_bt).setOnClickListener(this);

		mPhotoTitleTv = (TextView)findViewById(R.id.photo_title_tv);
		mPhotoDateTv = (TextView)findViewById(R.id.photo_date_tv);
		mPhotoIv = (ImageView)findViewById(R.id.photo_iv);
		mExposeTv = (TextView)findViewById(R.id.expose_tv);
		mContentsTv = (TextView)findViewById(R.id.content_tv);
	}

	private void getIntentData(){

		Intent intent = getIntent();    
		mCyId = intent.getStringExtra("CYID");

		mCyphotoInfo = (EntityCyPhoto)getIntent().getSerializableExtra("ENTITY");
	}

	
	/**
	 * @���� : Cyworld ����ø �Խù� �󼼺���
	 * @RequestURI : https://apis.skplanetx.com/cyworld/minihome/{cyId}/albums/{folderNo}/items/{itemSeq}
	 * @RequestPathParam : 
	 * {cyId} ��ȸ�� ����� ���̿��� ID�Դϴ�
	 * {folderNo} ���� ������ ��ȣ�Դϴ�
	 * {itemSeq} �Խù��� �Ϸù�ȣ�Դϴ�
	 */
	private void initPhotoDetail(String cyId) {

		//Querystring Parameters
		Map<String, Object> map = new HashMap<String, Object>();
		
		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.CYWORLD_MINIHOME_URI + cyId +"/albums/" +mCyphotoInfo.folderNo +"/items/"+ mCyphotoInfo.itemSeq , map);

		try {
			//API ȣ��
			AsyncRequester.request(CyworldPhotoDetailActivity.this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityCyPhotoDetail(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mDetailArray = (ArrayList<EntityCyPhotoDetail>)entityArray;

					ImageDownloader.download(mDetailArray.get(0).photoVmUrl, mPhotoIv);
					mPhotoTitleTv.setText(mDetailArray.get(0).title);
					mPhotoDateTv.setText(mDetailArray.get(0).writeDate);
					mContentsTv.setText(mDetailArray.get(0).content);
					if(mCyphotoInfo.itemOpen.equals("0")){
						mExposeTv.setText(getString(R.string.cy_private));
					}else if(mCyphotoInfo.itemOpen.equals("1")){
						mExposeTv.setText(getString(R.string.cy_friend_public));
					}else{
						mExposeTv.setText(getString(R.string.cy_public));
					}

				}
			}));

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
		case R.id.list_view_bt :
			finish();
			break;

		}
	} 

}
