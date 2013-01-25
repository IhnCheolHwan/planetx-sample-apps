package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : Tmap ��� �� Entity
 * @Ŭ������ : EntityTmapPathDetail
 * 
 * @URI :  https://apis.skplanetx.com/tmap/routes
 * @Protocol/HTTP Method : REST / Post Method  
 * @OAuth : NO
 * @QuerystringParameters : version={version}
 * @ResponseParameter : 
 * 
 */
public class EntityTmapPathDetail extends EntityAbstract {

	public String 	mStartLocation; // �����
	public String   mDestination; // ������
	public String 	mName;
	public String   mTurnType;
	public String 	mTime; //���� �ҿ�ð�
	public String   mTotalTime; //�� �ҿ� �ð�
	public String	mDistance;
	public String   mTotalDistance;
	public String 	mSpeed;	
	public String   mDestinatioLon; //������ ��ǥ
	public String   mDestinationLat;
	public String   mDescription;

}
