package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : Melon �� �˻�  Entity
 * @Ŭ������ : EntityMelonSearchSong
 * 
 * @URI : http://apis.skplanetx.com/melon/songs
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 * @QuerystringParameters : version={version}&page={page}&count={count}&searchKeyword={searchKeyword}
 * @ResponseParameter : menuId, songId, songName, artistId, artistName, albumId, albumName
 * 
 */
public class EntityMelonSearchSong extends EntityAbstract  {

	public int menuId;         //�޴�ID�Դϴ� (��, �ٹ�, ��Ƽ��Ʈ�� ���� �� �������� �̵��ϱ� ���� �뵵�� ��� �˴ϴ� )
	public int songId;         //�� ID�Դϴ�
	public String songName;    //�� �̸��Դϴ�
	public int artistId;       //��Ƽ��Ʈ�� ID�Դϴ�
	public String artistName;  //��Ƽ��Ʈ�� �̸��Դϴ�
	public int albumId;        //�ٹ� ID�Դϴ�
	public String albumName;   //�ٹ��� �̸��Դϴ�

}
