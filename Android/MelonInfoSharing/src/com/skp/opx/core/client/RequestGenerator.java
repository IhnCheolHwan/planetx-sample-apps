package com.skp.opx.core.client;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.internal.org.json.JSONArray;

/**
 * @���� : ������ǰ��� SDK ������ URI �Ǵ� Payload�� �ۼ��ϴ� Ŭ����
 * @Ŭ������ : RequestGenerator
 * 
 */
public class RequestGenerator {

	public static final String makePayload_AddPost(String strContent, String strSocialName, String strLinkId, String strSocialAccessToken, String strSocialAccessTokenSecret, String category) throws JSONException {

		JSONObject rootObject = new JSONObject();
		JSONObject posting = new JSONObject();
		JSONObject postInfos = new JSONObject();
		JSONArray postInfo = new JSONArray();

		rootObject.put("posting", posting);
		posting.put("content", strContent);

		posting.put("postInfos", postInfos);
		postInfos.put("postInfo", postInfo);

		JSONObject entity = new JSONObject();
		entity.put("socialName", strSocialName);
		entity.put("linkId", strLinkId);
		entity.put("socialAccessToken", strSocialAccessToken);
		entity.put("socialAccessTokenSecret", strSocialAccessTokenSecret);
		
		entity.put("category", category);
		postInfo.put(entity);
		
		return rootObject.toString();
	}
}
