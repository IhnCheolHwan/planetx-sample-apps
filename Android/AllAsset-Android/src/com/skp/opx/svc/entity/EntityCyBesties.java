package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : Cyworld �̴�Ȩ�� �������̸�Ϻ���
 * @Ŭ������ : EntityCyBesties
 *
 * @URI : https://apis.skplanetx.com/cyworld/cys/besties
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : YES
 * @QuerystringParameters : version={version}
 * @ResponseParameter : cyId, cyName, relationName, groupNo
 * 
 */
public class EntityCyBesties extends EntityAbstract {

	public String cyId;             //���� ���� cyId���� �Դϴ�
	public String cyName;        //���� ������ �̸��Դϴ�
	public String relationName;  //������ ���̸��Դϴ� 
	public int groupNo;          //���� Group ��ȣ(1~4)�Դϴ�

}
