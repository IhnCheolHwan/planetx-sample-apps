package com.skp.opx.mss.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : Melon �ٹ� �˻� Entity
 * @Ŭ������ : EntityMelonAlbumSearch
 * 
 */
public class EntityMelonAlbumSearch extends EntityAbstract {

	public int		menuId, albumId;
	public String 	albumName;  	 //�ٹ��� �̸��Դϴ�
	
	public EntityMelonAlbumSearch() {}
	
	public EntityMelonAlbumSearch(int menuId,int albumId, String albumName) {

		this.menuId		= menuId;
		this.albumId	= albumId;
		this.albumName 	= albumName;
	}

}
