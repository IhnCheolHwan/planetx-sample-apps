package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : Melon ���� ��Ʈ  Entity
 * @Ŭ������ : EntityMelonChart
 * 
 * @URI : [�ǽð� ��Ʈ] http://apis.skplanetx.com/melon/charts/realtime
 *        [�ְ� ��Ʈ]  http://apis.skplanetx.com/melon/charts/topgenres
 *        [�ٹ� ��Ʈ]  http://apis.skplanetx.com/melon/charts/topalbums
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 * @QuerystringParameters : version={version}&page={page}&count={count}
 * @ResponseParameter : menuId, songName, artistName, currentRank, pastRank, albumName
 * 
 */
public class EntityMelonChart extends EntityAbstract  {

	public int menuId;         //�޴�ID�Դϴ� (��, �ٹ�, ��Ƽ��Ʈ�� ���� �� �������� �̵��ϱ� ���� �뵵�� ��� �˴ϴ� )
	public int songId;         //�� ID�Դϴ�
	public String songName;    //�� �̸��Դϴ�
	public int artistId;       //��Ƽ��Ʈ�� ID�Դϴ�
	public String artistName;  //��Ƽ��Ʈ�� �̸��Դϴ�
	public int currentRank;    //���� ���� �����Դϴ�
	public int pastRank;       //���� ���� �����Դϴ�
	public int albumId;        //�ٹ� ID�Դϴ�
	public String albumName;   //�ٹ��� �̸��Դϴ�

}
