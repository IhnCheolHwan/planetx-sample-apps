package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : 11���� ��ǰ ������ Entity (������ ��ǰ �ڵ忡 �ش��ϴ� ��ǰ ������ �����ɴϴ�.)
 * @Ŭ������ : Entity11stProducts
 * 
 * @URI : http://apis.skplanetx.com/11st/common/products/{productCode}
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 * @QuerystringParameters : version={version}&option={option}
 * @ResponseParameter : ProductName, ProductImage, Price, LowestPrice, BasicImage, Point, Installment
 * 
 */
public class Entity11stProducts extends EntityAbstract {

	public int ProductCode;    //��ǰ �ڵ��Դϴ�
	public String ProductName;    //��ǰ �̸��Դϴ�
	public String Price;          //��ǰ �����Դϴ�
	public String LowestPrice;    //11�������� ������ �� �ִ� ���� �����Դϴ�
	public String BasicImage;     //��ǰ �⺻ �̹����� URL �ּ��Դϴ�
	public int Point;          //��ǰ ���� �� �����Ǵ� ����Ʈ �����Դϴ�
	public String Installment;    //��ǰ ������ �Һ� �����Դϴ�

}
