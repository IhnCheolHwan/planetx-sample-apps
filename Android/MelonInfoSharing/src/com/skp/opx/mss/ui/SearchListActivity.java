package com.skp.opx.mss.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.skp.opx.mss.ui.R;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.Define;
import com.skp.opx.mss.entity.EntityMelonAlbumSearch;
import com.skp.opx.mss.entity.EntityMelonArtistSearch;
import com.skp.opx.mss.entity.EntityMelonSongSearch;
import com.skp.opx.mss.ui.adapter.Adapter_Flickr;
import com.skp.opx.mss.ui.dialog.AlertListDialog;
import com.skp.opx.mss.util.FlickrUtil;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;
import com.skp.opx.sdk.PopupDialogUtil;

/**
 * @���� : Melon �˻� Activity
 * @Ŭ������ : SearchListActivity
 * 
 */
public class SearchListActivity extends Activity implements OnClickListener, OnItemClickListener{

	private	GridView 			mGridview;
	private ImageButton 		mImgBtnSearch;
	private Button 				mBtnMelonInfo, mBtnYoutube, mBtnDelete;
	private EditText			mEtSearch;
	private String 				mUrlValue, mStrServiceType, mStrSearchYoutube;
	private int					mStrContentId, mStrMenuId;
	private InputMethodManager 	mInputMethodManager;

	ArrayList<EntityMelonSongSearch> 	mSongSearchArray; 
	ArrayList<EntityMelonAlbumSearch> 	mAlbumSearchArray; 
	ArrayList<EntityMelonArtistSearch> 	mArtistSearchArray; 

	@Override
	protected void onPause() {
		super.onPause();
		//�󼼺������Խ� �޸� ����
		mGridview.setAdapter(null);
	}
	@Override
	protected void onResume() {
		super.onResume();
		//�ٽ� �׸���� ���Խ� ������ ��������
		if(mEtSearch.getText().length() > 0) {
			SearchSong(mEtSearch.getText().toString());
			new youTubeTask().execute();
		}
	}
	@Override
	protected void onStart() {

		super.onStart();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_list);

		FlickrUtil.imageCacheIn(getApplicationContext());

		initWidgets();
	}

	/** 
	 *  View �ʱ�ȭ Method
	 * */
	private void initWidgets() {

		mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		mEtSearch		= (EditText) findViewById(R.id.search_et);
		mImgBtnSearch	= (ImageButton) findViewById(R.id.search_bt);
		mBtnMelonInfo	= (Button) findViewById(R.id.melon_info_bt);
		mBtnYoutube		= (Button) findViewById(R.id.youtube_bt);
		mBtnDelete		= (Button) findViewById(R.id.delete_bt);
		mGridview 		= (GridView) findViewById(R.id.gridview);

		mGridview.setOnItemClickListener(this);
		mImgBtnSearch.setOnClickListener(this);
		mBtnMelonInfo.setOnClickListener(this);
		mBtnYoutube.setOnClickListener(this);
		mBtnDelete.setOnClickListener(this);

		mBtnMelonInfo.setVisibility(View.GONE);
		mBtnYoutube.setVisibility(View.GONE);

		((Button)findViewById(R.id.home_bt)).setOnClickListener(this);
	}

	/**
	 * @���� : Melon ��˻�
	 * @RequestURI : http://apis.skplanetx.com/melon/songs
	 */
	private void SearchSong(String keyword) {

		//Querystring Parameter
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 0);   //��ȸ�� ����� �������� �����մϴ�
		map.put("count", 10); //�������� ��µǴ� �� ���� �����մϴ�
		map.put("searchKeyword", keyword); //�� �˻� Ű�����Դϴ�

		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.MELON_SEARCH_SONGS_URI, map);

		try {
			//API ȣ��
			AsyncRequester.request(this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityMelonSongSearch(), new OnEntityParseComplete() {
				@Override
				public void onParsingComplete(Object entityArray) {
					mSongSearchArray = (ArrayList<EntityMelonSongSearch>)entityArray;
					// ������� �� ������ URL�� �Ѱ��� ������ - serviceType/contentId/menuId
					mStrServiceType		= "song";
					mStrContentId		= mSongSearchArray.get(0).songId;	
					mStrMenuId			= mSongSearchArray.get(0).menuId;
					// ������ �� ������ �˻� Keyword�� �Ѱ��� ����
					mStrSearchYoutube	= mSongSearchArray.get(0).songName;
				}
			}, null, "menuId"));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @���� : Melon �ٹ��˻�
	 * @RequestURI : http://apis.skplanetx.com/melon/albums
	 */
	private void SearchAlbum(String keyword) {

		//Querystring Parameter
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 0);   //��ȸ�� ����� �������� �����մϴ�
		map.put("count", 10); //�������� ��µǴ� �� ���� �����մϴ�
		map.put("searchKeyword", keyword); //�� �˻� Ű�����Դϴ�

		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.MELON_SEARCH_ALBUMS_URI, map);

		try {
			//API ȣ��
			AsyncRequester.request(this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityMelonAlbumSearch(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mAlbumSearchArray = (ArrayList<EntityMelonAlbumSearch>)entityArray;
					mStrServiceType 	= "album";
					mStrContentId		= mAlbumSearchArray.get(0).albumId;	
					mStrMenuId			= mAlbumSearchArray.get(0).menuId;
					mStrSearchYoutube	= mAlbumSearchArray.get(0).albumName;
				}
			}, null, "menuId"));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @���� : Melon ��Ƽ��Ʈ�˻�
	 * @RequestURI : http://apis.skplanetx.com/melon/artists
	 */
	private void SearchArtist(String keyword) {

		//Querystring Parameter
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 0);   //��ȸ�� ����� �������� �����մϴ�
		map.put("count", 10); //�������� ��µǴ� �� ���� �����մϴ�
		map.put("searchKeyword", keyword); //�� �˻� Ű�����Դϴ�

		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, Define.MELON_SEARCH_ARISTS_URI, map);

		try {
			//API ȣ��
			AsyncRequester.request(this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityMelonArtistSearch(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					mArtistSearchArray = (ArrayList<EntityMelonArtistSearch>)entityArray;
					mStrServiceType 	= "artist";
					mStrContentId		= mArtistSearchArray.get(0).artistId;	
					mStrMenuId			= mArtistSearchArray.get(0).menuId;
					mStrSearchYoutube	= mArtistSearchArray.get(0).artistName;
				}
			}, null, "menuId"));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}	

	/**
	 * Get Youtube ������ AsycTask
	 * */
	private class youTubeTask extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			FlickrUtil.getYoutubeData(mEtSearch.getText().toString());				// Flickr �˻� Keyword PUT

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			((TextView)findViewById(R.id.photo_tv)).setVisibility(View.VISIBLE);
			mGridview.setAdapter(new Adapter_Flickr(getApplicationContext()));
			mBtnMelonInfo.setVisibility(View.VISIBLE);
			mBtnYoutube.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId()) {
		case R.id.search_bt :
		{
			if(mEtSearch.getText().length()<=0)
			{
				if (mEtSearch.getText().length() <= 0) {
					PopupDialogUtil.showConfirmDialog(SearchListActivity.this, R.string.app_name, R.string.search_hint, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {}
					});
				}
				break;
			}

			// List Alert ����� �˻� ���� �����ϵ��� ��
			final AlertListDialog search_category_dialog = new AlertListDialog(this, getResources().getString(R.string.search_category), getResources().getStringArray(R.array.search_category)); 
			search_category_dialog.setOnItemSelectedListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> view, View arg1,
						int position, long arg3) {
					
					if(position == 0){ 
						//��˻�
						SearchSong(mEtSearch.getText().toString());
						new youTubeTask().execute();
					}else if(position ==1){
						//��Ƽ��Ʈ�˻�
						SearchArtist(mEtSearch.getText().toString());
						new youTubeTask().execute();
					}else if(position ==2){
						//�ٹ��˻�						
						SearchAlbum(mEtSearch.getText().toString());
						new youTubeTask().execute();
					}
					search_category_dialog.dismiss();
				}
			});
			mInputMethodManager.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);
			search_category_dialog.show();
			break;
		}
		case R.id.delete_bt : 
		{
			mEtSearch.setText("");
			break;
		}
		case R.id.melon_info_bt :
		{
			mUrlValue="http://m.melon.com/cds/common/mobile/openapigate_dispatcher.htm?type="+mStrServiceType+"&cid="+mStrContentId+"&menuId="+mStrMenuId;

			Intent melonInfo_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrlValue));
			startActivity(melonInfo_intent);
			break;
		}
		case R.id.youtube_bt :
		{
			mUrlValue = "http://m.youtube.com/results?q="+mStrSearchYoutube;

			Intent melonInfo_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrlValue));
			startActivity(melonInfo_intent);
			break;
		}
		case R.id.home_bt : 
		{
			startActivity(new Intent(SearchListActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP ));
		}
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		Intent intent = new Intent(SearchListActivity.this, ShareActivity.class);
		intent.putExtra("IS_SHARE_YOUTUBE", true);
		intent.putExtra("TAG_SONGNAME", mEtSearch.getText().toString());
		intent.putExtra("TAG_YOUTUBE_THUMBNAIL_URI", (String)FlickrUtil.thumbnailUrl.get(position));
		intent.putExtra("TAG_YOUTUBE_VIDEO_URI", (String)FlickrUtil.videoUrl.get(position));
		startActivity(intent);
	}
}
