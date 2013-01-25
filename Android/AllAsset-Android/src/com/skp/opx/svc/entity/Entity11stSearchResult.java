
package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : 11���� ��ǰ �˻���� Entity
 * @Ŭ������ : Entity11stSearchResult
 * 
 * @URI : http://apis.skplanetx.com/11st/common/products?
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 * @QuerystringParameters : version={version}&page={page}&count={count}&searchKeyword={searchKeyword}&={sortCode}&option={option}
 * @ResponseParameter : ProductCode, ProductName, ProductPrice, ProductImage, SellerNick, Seller, ReviewCount, BuySatisfy, Delivery
 * 
 */
public class Entity11stSearchResult extends EntityAbstract {
	
	public int ProductCode;     //��ǰ �ڵ��Դϴ�
	public String ProductName;  //��ǰ �̸��Դϴ�
	public int ProductPrice;    //��ǰ �����Դϴ�
	public String ProductImage; //��ǰ�� �̹��� URL �ּ��Դϴ�
	public String ProductImage300; //��ǰ�� �̹��� URL �ּ��Դϴ�
	public String SellerNick;   //�Ǹ����� �г����Դϴ�
	public String Seller;       //�Ǹ��� ID�Դϴ�
	public int SalePrice;       //��ǰ ���ݿ��� ������ ����� �����Դϴ�.(���� ������)
	public String Delivery;     //��� �����Դϴ�
	public int ReviewCount;     //��ǰ �� ���Դϴ�;
	public int BuySatisfy;      //���� �������Դϴ�
	
}
