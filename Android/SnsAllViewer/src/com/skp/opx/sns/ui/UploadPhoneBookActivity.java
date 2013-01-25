package com.skp.opx.sns.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.opx.sns.R;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.core.client.RequestGenerator;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityAbstract;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;
import com.skp.opx.sns.contact.AddressBookDAO;
import com.skp.opx.sns.contact.EntityContact;
import com.skp.opx.sns.entity.EntityUploadPhoneBookPayload;
import com.skp.opx.sns.ui.adapater.FriendContactAdapter;
import com.skp.opx.sns.util.IntentUtil;
import com.skp.opx.sns.util.PreferenceUtil;

/**
 * @���� : ���� ��� Activity
 * @Ŭ������ : UploadPhoneBookActivity 
 *
 */
public class UploadPhoneBookActivity extends Activity implements OnClickListener, OnItemClickListener {

	private Button mBtnShowMenu, mBtnUpload;
	private CheckBox mChkContact;
	private TextView mTvContactCount;
	private ListView mContactListView;
	private FriendContactAdapter mContactAdapter;
	private List<EntityContact> mEntityContacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_created_list);

		initView();
	}

	/** 
	 *  Widget �ʱ�ȭ Method
	 * */
	private void initView() {
		mBtnUpload		= (Button) findViewById(R.id.add_bt);
		mBtnUpload.setOnClickListener(this);

		mBtnShowMenu 	= (Button) findViewById(R.id.show_menu_bt);
		mBtnShowMenu.setOnClickListener(this);

		mChkContact		= (CheckBox) findViewById(R.id.select_contact_chk);
		mChkContact.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//if checked, select all checkbox in rows
				mContactAdapter.setAllChecked(isChecked);
				mContactAdapter.notifyDataSetChanged();
			}
		});

		mTvContactCount	= (TextView) findViewById(R.id.all_contacts_tv);
		mTvContactCount.setText(getString(R.string.all_contacts, "0"));

		initListView();
	}

	/** 
	 *  ����Ʈ �� �ʱ�ȭ Method
	 * */
	private void initListView() {

		mEntityContacts = AddressBookDAO.getContactList(getApplicationContext());

		if (mEntityContacts.size() > 0) {
			((TextView) findViewById(android.R.id.empty)).setVisibility(View.GONE);
		}

		mContactListView	= (ListView) findViewById(android.R.id.list);
		mContactListView.setOnItemClickListener(this);
		mContactAdapter		= new FriendContactAdapter(UploadPhoneBookActivity.this, mEntityContacts);
		mContactAdapter.notifyDataSetChanged();
		mContactListView.setAdapter(mContactAdapter);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.add_bt :
			// All Upload

			List<EntityContact> entityCheckedList = mContactAdapter.getEntityChecked();
			ArrayList<EntityUploadPhoneBookPayload> arrayList = new ArrayList<EntityUploadPhoneBookPayload>();
			for(EntityContact entity : entityCheckedList){
				if(entity.isChecked==true){
					arrayList.add(new EntityUploadPhoneBookPayload(entity.phoneNumber, entity.name));
				}
			}

			if(arrayList.size()==0)
			{
				Toast.makeText(UploadPhoneBookActivity.this, R.string.cb_check_msg, Toast.LENGTH_SHORT).show();
			}
			else{
				try {		
					/**
					 * @���� : �Ҽ� ������Ʈ �Ҽ� �׷��� ���� ���ϰ��� ���� ���
					 * @RequestURI : https://apis.skplanetx.com/social/graph/users/{appUserId}/phoneBook
					 * @RequestPathParam : 
					 * {appUserId} ������� ID�Դϴ�
					 */
					String mStrPayload = RequestGenerator.makePayload_UploadPhoneBook(arrayList);
					//Bundle ����
					RequestBundle bundle = AsyncRequester.make_PUT_POST_bundle(this, RequestGenerator.makeURI_UploadPhoneBook(PreferenceUtil.getAppUserID(this, false)), null, mStrPayload, null);
					//API ȣ��
					AsyncRequester.request(UploadPhoneBookActivity.this, bundle, HttpMethod.PUT, new EntityParserHandler(new EntityAbstract(), new OnEntityParseComplete() {

						@Override
						public void onParsingComplete(Object entityArray) {
							// TODO Auto-generated method stub
							Toast.makeText(UploadPhoneBookActivity.this, R.string.complete_msg, Toast.LENGTH_SHORT).show();
						}
					}));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case R.id.show_menu_bt :
			IntentUtil.changeActivityWithAnim(UploadPhoneBookActivity.this, new Intent(UploadPhoneBookActivity.this, MainPreferenceActivity.class), 1);
			break;
		}

	}
}
