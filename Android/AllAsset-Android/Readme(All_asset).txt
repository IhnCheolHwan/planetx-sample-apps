i. ���� - "All Asset"
	
	1. ��Ī : SKP���񽺸�ƺ��� sample application
	
	2. ���� : SK Planet���� �����ϴ� ���񽺸� �ѹ��� ����� �� �ִ� ����

	3. API Ȱ�� : NateOn, Melon, Cyworld, T map, 11st API



ii.Sample Project ���� ����
	
	1. /OpenPlatform_SVC�� AndroidManifest.xml ���� : Planet X�� ���̺귯���� ����� �� �� �����ؾ� �� 

	   permission�� Activity�� ����Ǿ� �ֽ��ϴ�.

	2. /OpenPlatform_SVC/libs : ���̺귯�� ����

		- SKPOP_SDK.jar : Planet X server ����

		- SKPOP_SDK_OAuth.jar : OneID ����
		
		- TmapSDK.1.0.0.jar : Tmap static ���̺귯��

	3. /OpenPlatform_SVC/src/com/skp/opx/core/client : ������Ʈ ���࿡ �ʿ��� OAuth Key, �� API�� URI ��

	   ������Ʈ ������ ����

	        - Define.java : ���� �� ����� Define.java ������ �Ʒ� ���̵� ���뿡 ���� �������ؾ� �մϴ�.

		  /** Authentication */
 		  public final class OAuth {
  		  	public static final String CLIENT_ID       = "< your key here >";
 			public static final String SECRET   	= "< your key here >";
  			public static final String KEY    	= "< your key here >";
  		  }
 


iii. �غ� ����
	
	1. One ID ���� �� ��ȯ 
                  
		: �� ���ø����̼��� One ID�θ� �α����� �� �����Ƿ� One ID(https://oneid.skplanet.co.kr)�� �����ϼž� �մϴ�.

	2. ������ ���Ϳ� ȸ�� ����
                  
		: Sample Application�� �����ϱ� ���ؼ��� Planet X���� �߱��� AppKey�� �ʿ��մϴ�.
                    
		  ����, https://developers.skplanetx.com ����Ʈ�� ������ ������ �ϼž� �մϴ�. �̹� One ID ������ ������ 

		  �ִٸ� ��� ���Ǹ� �Ͻø� �˴ϴ�.
	
	3. ������ ���Ϳ� ���� �� ����ϱ�

	   1) ������ ���� ����Ʈ ��� �޴����� [ �� ���� > �� ���� ] ���������� ���� ����ϼž� �մϴ�.

	   2) �� ��� �Ϸ��� �� ����Ű(AppKey)�� �߱޹����ž� �մϴ�. 
                    
		- ����Ű�� Planet X API�� ȣ���ϱ� ���ؼ� ����ϴ� ����Ű�� ���ø����̼� ������ ���� �ſ� �߿��� ���̸�
                 
		- Client ID, Client secret, App key ������ ���� �߱޵Ǹ� �� ������ �Ʒ� iv. ȯ�漳�� 4�� �׸񿡼� ���˴ϴ�.

	   3) ���� ����� �� [Select Service] ���� "SK Planet One ID", "����Ʈ��", "���̿���", "���", "T map",

	      "11����"�� �� �����ؾ� �մϴ�.
          
	      �̴� ���� ����ϴ� API�� ���� ���񽺺��� ��� ������ ���ؾ� �ϱ� �����Դϴ�.          
 


iv. ȯ�� ����

	1. Android SDK API level 10 �̻� ��ġ

	2. �ܸ� �� emulator�� �۽��� ���� Gingerbread 2.3 �̻� (������ ���ķ����� ���׹����� ���Ͽ� ���̽�ũ��������ġ ���ķ����͸� �����մϴ�.)

	3. �ٿ���� ���� ������Ʈ�� ������ Ǭ �� OpenPlatform_SVC ������ eclipse���� import.

	4. OpenPlatform_SVC/src/com/skp/opx/core/client/Define.java�� OAuth class�� ������ ���Ϳ��� �� ��� �� �߱޹��� Client ID, 

	   Client secret, App key�� �Է��ϰ� �����ϼž� �մϴ�.

	�� emulator�� �׽�Ʈ �Ͻô� ���
	
	   T map API�� ����Ͻñ� ���ؼ��� emulator���� GPS ������ ���� ������ �ּž� �մϴ�.
	
	   : DDMS -> Location Controls -> Logitude, Latitude �� �Է� �� send
	  
	   [����] ���� Android emulator�� API Level 10(Gingerbread)�� ��� GPS ����� �������� �ʽ��ϴ�. 

	          ���� emulator ������ target API Level�� 15(ICS)�� �����Ͻñ� �ٶ��ϴ�.



vi. Sample Project�� ���� ����ó

	�ñ��Ͻ� ������ �Ʒ� Planet X ������ ������ [ �����ϱ� ] �޴��� ���� ������ �ֽʽÿ�
   
	(https://developers.skplanetx.com/community/contact-us/enrollment)