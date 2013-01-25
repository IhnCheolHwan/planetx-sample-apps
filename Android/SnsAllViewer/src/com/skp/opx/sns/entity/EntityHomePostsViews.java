package com.skp.opx.sns.entity;

import com.skp.opx.sdk.EntityAbstract; 

/**
 * @���� : Ȩ �Խñ� ��ȸ Entity
 * @Ŭ������ : EntityHomePostsViews
 * @URI : https://apis.skplanetx.com/social/providers/{socialName}/users/{linkId}/feeds/home?version={version}&socialAccessToken={socialAccessToken}&socialAccessTokenSecret={socialAccessTokenSecret}&index={index}&count={count}
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 * @QuerystringParameters : version, socialAccessToken, socialAccessTokenSecret, category, index, count
 */
public class EntityHomePostsViews extends EntityAbstract  {

	public String feedId; 		//�Խñ��� feed ID
    public String name;			//�Խ��� �̸�
    public String image;			//�Խ��� ����
    public String publishTime;	//�Խ��� �ð�
    public String title;			//����
    public String content;		//������(��)
    public String link;				//LinkID 
    public String picture;		//�Խ� �̹���
}
