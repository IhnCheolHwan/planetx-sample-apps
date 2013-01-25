package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : NateOn ģ�� ��� Entity
 * @Ŭ������ : EntityNateOnBuddiesId
 * 
 * @URI :  https://apis.skplanetx.com/nateon/buddies
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : YES
 * @QuerystringParameters : version={version}&order={order}&page={page}&count={count} 
 * @ResponseParameter : nateId
 * 
 */
public class EntityNateOnBuddiesId extends EntityAbstract {

	public String nateId;  //ģ���� ID�Դϴ�

}
