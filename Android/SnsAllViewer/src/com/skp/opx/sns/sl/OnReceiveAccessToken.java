package com.skp.opx.sns.sl;

/**
 * @���� : SNS�� AccessToken ���� ������ ȣ�� �������̽�
 * @�������̽��� : OnReceiveAccessToken
 */
public interface OnReceiveAccessToken {
	
	/** 
	 * ���̽��� AccessToken ���Ž� ȣ�� �������̽�
	 * */
	public void onReceiveFacebookAccessToken(String strAccessToken);
	
	/** 
	 * Ʈ���� AccessToken ���Ž� ȣ�� �������̽�
	 * */
	public void onReceiveTwitterAccessToken(String strAccessToken, String strAccessVerifier);
	
	/** 
	 * �����÷��� AccessToken ���Ž� ȣ�� �������̽�
	 * */
	public void onReceiveGogglePlusAccessToken(String strAccessToken);
}
