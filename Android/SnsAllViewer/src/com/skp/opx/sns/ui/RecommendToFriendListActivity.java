package com.skp.opx.sns.ui;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;

import com.skp.opx.sns.R;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.RequestGenerator;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;
import com.skp.opx.sns.entity.EntityFriendsList;
import com.skp.opx.sns.entity.EntityRecommendToFriend;
import com.skp.opx.sns.ui.adapater.FriendsListAdapter;
import com.skp.opx.sns.ui.adapater.RecommendFriendsAdapter;
import com.skp.opx.sns.util.PreferenceUtil;

/**
 * @���� : ���� ��õ Activity
 * @Ŭ������ : RecommendToFriendListActivity 
 *
 */
public class RecommendToFriendListActivity extends ListActivity implements OnItemClickListener, OnClickListener {

	private ListView								mRecommendListView;
	private RecommendFriendsAdapter					mRecommendListAdapter;
	private FriendsListAdapter						mFriendsListAdapter;

	private RadioButton								mRbFriendList, mRbRecommend;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend_friend_list);
		initView();

	}

	/** 
	 *  Widget �ʱ�ȭ Method
	 * */
	private void initView() {

		mRecommendListView		= (ListView) findViewById(android.R.id.list);
		mRecommendListView.setOnItemClickListener(this);

		mRbFriendList	= (RadioButton) findViewById(R.id.friends_list_rb);
		mRbRecommend	= (RadioButton) findViewById(R.id.recommend_list_rb);

		mRbFriendList.setOnClickListener(this);
		mRbRecommend.setOnClickListener(this);

		initFriendsList();
	}

	/**
	 * @���� : �Ҽ� ������Ʈ �Ҽ� �׷��� ���� ��õ ���� ��ȸ
	 * @RequestURI : https://apis.skplanetx.com/social/graph/users
	 */
	private void initRecommendList() {

		mRecommendListAdapter = new RecommendFriendsAdapter(this);
		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, RequestGenerator.makeURI_RecommendFriends(PreferenceUtil.getAppUserID(RecommendToFriendListActivity.this, false)), null);

		try {
			//API ȣ��
			AsyncRequester.request(RecommendToFriendListActivity.this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityRecommendToFriend(), new OnEntityParseComplete() {


				@Override
				public void onParsingComplete(Object entityArray) {
					ArrayList<EntityRecommendToFriend> mArray = (ArrayList<EntityRecommendToFriend>)entityArray;
					mRecommendListAdapter.setRecommendList(mArray);
					setListAdapter(mRecommendListAdapter);
				}
			}));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @���� : �Ҽ� ������Ʈ �Ҽ� �׷��� ���� ���ΰ��� ���� ��� ��ȸ �� �˻�
	 * @RequestURI : https://apis.skplanetx.com/social/graph/users
	 */
	private void initFriendsList() {

		mFriendsListAdapter = new FriendsListAdapter(this);
		//Bundle ����
		RequestBundle bundle = AsyncRequester.make_GET_DELTE_bundle(this, RequestGenerator.makeURI_FriendsList(PreferenceUtil.getAppUserID(RecommendToFriendListActivity.this, false)), null);

		try {
			//API ȣ��
			AsyncRequester.request(RecommendToFriendListActivity.this, bundle, HttpMethod.GET, new EntityParserHandler(new EntityFriendsList(), new OnEntityParseComplete() {

				@Override
				public void onParsingComplete(Object entityArray) {
					ArrayList<EntityFriendsList> mArray = (ArrayList<EntityFriendsList>)entityArray;
					mFriendsListAdapter.setFriendsList(mArray);
					setListAdapter(mFriendsListAdapter);
				}
			}));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.friends_list_rb :
			initFriendsList();
			mFriendsListAdapter.notifyDataSetChanged();
			break;
		case R.id.recommend_list_rb :
			initRecommendList();
			mRecommendListAdapter.notifyDataSetChanged();
			break;
		}
	}

}
