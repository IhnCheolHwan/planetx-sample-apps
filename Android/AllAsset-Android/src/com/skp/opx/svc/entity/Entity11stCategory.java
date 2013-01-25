package com.skp.opx.svc.entity;

import com.skp.opx.sdk.EntityAbstract;

/**
 * @���� : 11���� Root ī�װ�Entity
 * @Ŭ������ : Entity11stCategory
 * 
 * @URI : http://apis.skplanetx.com/11st/common/categories
 * @Protocol/HTTP Method : REST / Get Method 
 * @OAuth : NO
 * @QuerystringParameters : version={1} 
 * @ResponseParameter : CategoryCode,CategoryName
 *                                    
 */
public class Entity11stCategory extends EntityAbstract{

	public String CategoryName;   //ī�װ� �̸� �����Դϴ�
	public int CategoryCode; 

}
