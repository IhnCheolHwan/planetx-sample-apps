package com.skp.opx.core.client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.Constants.HttpMethod;
import com.skp.opx.sdk.AsyncRequester;
import com.skp.opx.sdk.EntityAbstract;
import com.skp.opx.sdk.EntityParserHandler;
import com.skp.opx.sdk.OnEntityParseComplete;
import com.skp.opx.sns.entity.EntityUploadPhoneBookPayload;
import com.skp.opx.sns.util.PreferenceUtil;

/**
 * @���� : SNS SDK ������ URI �Ǵ� Payload�� �ۼ��ϴ� Ŭ����
 * @Ŭ������ : RequestGenerator
 * 
 */
public class RequestGenerator {

	/**
	 * @���� :  ��� ��� URI
	 * @RequestURI : https://apis.skplanetx.com/social/providers/{socialName}/users/{linkId}/feeds/{feedId}/comments?version={version}
	 */
	public static final String makeURI_AddComment(String strFeedId) {

		return String.format("https://apis.skplanetx.com/social/providers/facebook/users/me/feeds/%s/comments?version=%s",strFeedId, Define.VERSION);
	}

	/**
	 * @���� :  ��� ����� ���� Payload ����
	 */
	public static final String makePayload_AddComment(String strContent, String strSocialAccessToken) throws JSONException {

		JSONObject rootObject = new JSONObject();
		JSONObject comment = new JSONObject();

		comment.put("content", strContent); //������(��)
		comment.put("socialAccessToken", strSocialAccessToken); //Social�� AccessToken

		rootObject.put("comment", comment);

		return rootObject.toString();
	}

	/**
	 * @���� :  ����� ���� ����� ���� Payload ����
	 */
	public static final String makePayload_AddUserInfo(String strUserID, String strName, String strPhone) {

		JSONObject rootObject = new JSONObject();

		try {
			JSONObject profiles = new JSONObject(); 
			JSONObject users = new JSONObject();
			JSONArray user = new JSONArray();

			JSONObject userObject = new JSONObject(); 
			userObject.put("appUserId", strUserID); //����� ID
			userObject.put("name", strName); //����� �̸� 
			userObject.put("nationDialing", "82"); //���� �ڵ�(�ѱ�)
			userObject.put("phoneNumber", strPhone); //��ȭ��ȣ

			rootObject.put("profiles", profiles);
			profiles.put("users", users);
			users.put("user", user);
			user.put(userObject);
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}	

		return rootObject.toString();
	}

	/**
	 * @���� : �Ҽ� ������Ʈ �Ҽ� Ŀ��Ʈ ����� ���� ���
	 * @RequestURI : https://apis.skplanetx.com/social/graph/users
	 */
	public static final void getAppUserId(String strName, String strPhone, Context context, OnEntityParseComplete completeListener) {
		
		//Unique userID ���� - SKP + ����ð� ����
		String strUserID = "SKP" + System.currentTimeMillis();
		// Request Payload
		//����� ���� ����� ���� Payload ������ ���� �Լ� ȣ��
		String strPayload  = makePayload_AddUserInfo(strUserID, strName, strPhone );
		//Bundle ����
		//����� ���� ����� ���� bundle��ü ���� 
		RequestBundle bundle = AsyncRequester.make_PUT_POST_bundle(context, "https://apis.skplanetx.com/social/graph/users", null, strPayload, null);

		try {
			//API ȣ��
			//����� ���� ��� ��û
			AsyncRequester.request(context, bundle, HttpMethod.PUT, new EntityParserHandler(new EntityAbstract(), completeListener));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		//��ϵ� ����� ���� Preference ����
		PreferenceUtil.setAppUserID(context, strUserID, true);
	}

	/**
	 * @���� : ���� ��� URI 
	 * @RequestURI : https://apis.skplanetx.com/social/graph/users/{appUserId}/phoneBook?version={version}
	 */
	public static final String makeURI_UploadPhoneBook(String strUserID) {
		return String.format("https://apis.skplanetx.com/social/graph/users/%s/phoneBook?version=%s", strUserID, Define.VERSION);
	}

	/**
	 * @���� :  ���� ����� ���� Payload ����, 1���� ����ó�� ����Ҷ� ����Ѵ�.
	 */
	public static final String makePayload_UploadPhoneBook(EntityUploadPhoneBookPayload entity) throws JSONException {

		ArrayList<EntityUploadPhoneBookPayload> arrayList = new ArrayList<EntityUploadPhoneBookPayload>();
		arrayList.add(entity);

		return makePayload_UploadPhoneBook(arrayList);
	}

	/**
	 * @���� :  ���� ����� ���� Payload ����, �������� ����ó�� ����Ҷ� ����Ѵ�.
	 */
	public static final String makePayload_UploadPhoneBook(ArrayList<EntityUploadPhoneBookPayload> arrayList) throws JSONException {

		JSONObject rootObject = new JSONObject();
		JSONObject phoneBook= new JSONObject();
		JSONObject phones = new JSONObject();
		JSONArray phone = new JSONArray();

		for(EntityUploadPhoneBookPayload entity : arrayList) {
			JSONObject phoneEntity = new JSONObject();
			phoneEntity.put("nationDialing", entity.nationDialing); //���� �ڵ�(82 : �ѱ�)
			phoneEntity.put("phoneNumber", entity.phoneNumber); //��ȭ��ȣ
			phoneEntity.put("name", entity.name); //�̸�
			phone.put(phoneEntity);
		}

		phones.put("phone", phone);	
		phoneBook.put("autoNodeYn", "Y");
		phoneBook.put("deleteYn", "N");
		phoneBook.put("phones", phones);
		rootObject.put("phoneBook", phoneBook);

		return rootObject.toString();
	}

	/**
	 * @���� : ��� ��ȸ URI 
	 * @RequestURI : https://apis.skplanetx.com/social/providers/{socialName}/users/{linkId}/feeds/{feedId}/comments?version={version}&socialAccessToken={socialAccessToken}&socialAccessTokenSecret={socialAccessTokenSecret}&category={category}&index={index}&count={count}
	 */
	public static final String makeURI_CommentInfo(String strSocialName, String strLinkId, String strFeedId, String strSocialAccessToken) {
		
		return String.format("https://apis.skplanetx.com/social/providers/%s/users/%s/feeds/%s/comments?version=%s&socialAccessToken=%s", strSocialName, strLinkId, strFeedId, Define.VERSION, strSocialAccessToken);
	}
	
	/**
	 * @���� : ��õ ���� ��ȸ URI 
	 * @RequestURI : https://apis.skplanetx.com/social/graph/users/{appUserId}/nodes/recommend?version={version}&type={type}&count={count}
	 */
	public static final String makeURI_RecommendFriends(String strUserID){
		return String.format("https://apis.skplanetx.com/social/graph/users/%s/nodes/recommend?count=10&type=0&version=%s",strUserID, Define.VERSION);
	}

	/**
	 * @���� : ���� ��� ��ȸ �� �˻� URI 
	 * @RequestURI : https://apis.skplanetx.com/social/graph/users/{appUserId}/nodes?version={version}&type={type}&index={index}&count={count}&searchKeyword={searchKeyword}
	 */
	public static final String makeURI_FriendsList(String strUserID){
		return String.format("https://apis.skplanetx.com/social/graph/users/%s/nodes?version=%s&type=0&count=20", strUserID, Define.VERSION);
	}
	
	/**
	 * @���� : ���� ���� URI 
	 * @RequestURI : https://apis.skplanetx.com/social/graph/users/{appUserId}/nodes?version={version}
	 */
	public static final String makeURI_AddGraph(String strUserID) {

		return String.format("https://apis.skplanetx.com/social/graph/users/%s/nodes?version=%s", strUserID, Define.VERSION);
	}

	/**
	 * @���� : ���� ������ ���� Payload ����
	 */
	public static final String makePayload_AddGraph(String strTargetUserID) throws JSONException {

		JSONObject rootObject = new JSONObject();
		JSONObject graph = new JSONObject();

		rootObject.put("graph", graph);
		graph.put("targetUserID", strTargetUserID); //����� ������ userID 
		graph.put("type", "0"); //����� ������ ��� type (0 : ����, 1 : �Ҽ�)

		return rootObject.toString();
	}
	
}
