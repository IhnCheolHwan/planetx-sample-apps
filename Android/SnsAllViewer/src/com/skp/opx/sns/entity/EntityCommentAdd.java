package com.skp.opx.sns.entity;

import com.skp.opx.sdk.EntityAbstract; 

/**
 * @���� : ��� ��� Entity
 * @Ŭ������ : EntityCommentAdd
 * @URI : https://apis.skplanetx.com/social/providers/{socialName}/users/{linkId}/feeds/{feedId}/comments?version={version}
 * @Protocol/HTTP Method : REST / Post Method 
 * @OAuth : NO
 * @QuerystringParameters : version={1}
 */
public class EntityCommentAdd extends EntityAbstract {
	
    public String content; 							//������ (��)
    public String socialAccessToken; 			//Social AccessToken
    public String socialAccessTokenSecret; 	//Social Secret AccessToken
}
