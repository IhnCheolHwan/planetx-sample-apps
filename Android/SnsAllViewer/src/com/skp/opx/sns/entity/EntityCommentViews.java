package com.skp.opx.sns.entity;

import com.skp.opx.sdk.EntityAbstract; 


/**
 * @���� : ��� ��ȸ Entity
 * @Ŭ������ : EntityCommentViews
 * @URI : https://apis.skplanetx.com/social/providers/{socialName}/users/{linkId}/feeds/{feedId}/comments?version={version}&socialAccessToken={socialAccessToken}&socialAccessTokenSecret={socialAccessTokenSecret}&category={category}&index={index}&count={count}
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 */
public class EntityCommentViews extends EntityAbstract {
	
    public String name; 			//�̸�
    public String content; 		//������(��)
    public String publishTime; //������ �ð�
}
