package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : Cyworld �̴�Ȩ�� ����ø �󼼺��� 
 * @Ŭ������ : EntityCyPhotoDetail
 * 
 * @URI :https://apis.skplanetx.com/cyworld/minihome/{cyId}/albums/{folderNo}/items/{itemSeq}
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : YES
 * @QuerystringParameters : version={version}
 * @ResponseParameter : content, itemOpen, photoVmUrl, title, writeDate
 * 
 */
public class EntityCyPhotoDetail extends EntityAbstract {

	public String    content;        //������ �����Դϴ�
	public String    itemOpen;       //�Խù� ���� ����
	public String    photoVmUrl;     //�̹��� ������ ��ü URL ����Դϴ�
	public String    title;          //�Խù��� �����Դϴ�
	public String    writeDate;      //�ۼ� �����Դϴ�

}
