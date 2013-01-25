package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : Melon ��Ƽ��Ʈ �˻�  Entity
 * @Ŭ������ : EntityMelonSearchArtist
 * 
 * @URI : http://apis.skplanetx.com/melon/artists
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 * @QuerystringParameters : version={version}&page={page}&count={count}&searchKeyword={searchKeyword}
 * @ResponseParameter : menuId, artistId, artistName, genreNames
 * 
 */
public class EntityMelonSearchArtist extends EntityAbstract  {

	public int menuId;         //�޴�ID�Դϴ� (��, �ٹ�, ��Ƽ��Ʈ�� ���� �� �������� �̵��ϱ� ���� �뵵�� ��� �˴ϴ� )
	public int artistId;       //��Ƽ��Ʈ�� ID�Դϴ�
	public String artistName;  //��Ƽ��Ʈ�� �̸��Դϴ�
	public String genreNames;   //�帣 �̸��Դϴ�

}
