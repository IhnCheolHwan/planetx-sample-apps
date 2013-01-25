package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : NateOn ģ�� ���� Entity
 * @Ŭ������ : EntityNateOnBuddiesInfo
 * 
 * @URI :  https://apis.skplanetx.com/nateon/buddies
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : YES
 * @QuerystringParameters : version={version}&order={order}&page={page}&count={count} 
 * @ResponseParameter : nateId, name, nickname, onlineStatus, groupId, groupName
 * 
 */
public class EntityNateOnBuddiesInfo extends EntityAbstract {

	public String nateId;   //ģ���� ID�Դϴ�
	public String name;      //ģ���� �̸��Դϴ�
	public String nickname;  //ģ���� ��ȭ���Դϴ�
	public String onlineStatus; //ģ���� ���� �����Դϴ�
	public String groupId;   //�׷��� ID�Դϴ�
	public String groupName; //�׷��� �̸��Դϴ�

}
