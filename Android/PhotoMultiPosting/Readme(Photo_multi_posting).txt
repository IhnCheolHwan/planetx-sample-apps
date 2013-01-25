i. ���� - "Photo Multi Posting"
	
	1. ��Ī : ��Ƽ���������� sample application
	
	2. ���� : T cloud �� �ܸ� �������� �̹����� ��Ƽ�� SNS�� ������ �� �ִ� ����

	3. API Ȱ�� : T cloud, Social Component API



ii.Sample Project ���� ����
	
	1. /OpenPlatform_MPP�� AndroidManifest.xml ���� : Planet X�� ���̺귯���� ����� �� �� �����ؾ� �� 

	   permission�� Activity�� ����Ǿ� �ֽ��ϴ�.

	2. /OpenPlatform_MPP/libs : ���̺귯�� ����

		- SKPOP_SDK.jar : Planet X server ����

		- SKPOP_SDK_OAuth.jar : OneID ����

		- twitter4j-core-2.1.3-SNAPSHOT.jar : Twitter ����

	3. /OpenPlatform_MPP/src/com/skp/opx/core/client : ������Ʈ ���࿡ �ʿ��� OAuth Key, SNS key, �� API�� 

	   URI �� ������Ʈ ������ ����

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

	   3) ���� ����� �� [Select Service] ���� "SK Planet One ID", "T cloud", "�Ҽ� ������Ʈ"�� �� �����ؾ� �մϴ�.
              
	      �̴� ���� ����ϴ� API�� ���� ���񽺺��� ��� ������ ���ؾ� �ϱ� �����Դϴ�.                   
	 
	4. ���� �׽�Ʈ�� ���ؼ��� �� �ҼȺ�(Facebook, Twitter) ����� ������ ����Ǿ�� �մϴ�. 
 


iv. ȯ�� ����

	1. Android SDK API level 10 �̻� ��ġ

	2. �ܸ� �� emulator�� �۽��� ���� Gingerbread 2.3 �̻� (������ ���ķ����� ���׹����� ���Ͽ� ���̽�ũ��������ġ ���ķ����͸� �����մϴ�.)

	3. �ٿ���� ���� ������Ʈ�� ������ Ǭ �� OpenPlatform_MPP ������ eclipse���� import.

	4. OpenPlatform_MPP/src/com/skp/opx/core/client/Define.java�� OAuth class�� ������ ���Ϳ��� �� ��� �� �߱޹��� Client ID, 

	   Client secret, App key�� �Է��ϰ� �����ϼž� �մϴ�.

	5. �� SNS �� �۵�� �� ����
	 
	   1) �� SNS ����Ʈ �� �۵�� ��, ��Ű������ �ߺ��� �����ϱ� ���Ͽ� ��Ű������ �����մϴ�.

	        : eclipse ���� > Package Explore > Android Tools > Rename Application Package > ��Ű���� ����

	   2) �� SNS(Facebook, Twitter)���� �����ڼ��� ����Ʈ�� ���� ����ϰ� ����Ű�� �߱� �޽��ϴ�.

	      �� �۾��� Social Component�� ����ϱ� ���ؼ��� ���� �ϼž� �ϸ�, ����Ű�� �־�� �� SNS�� ������ �� �ֽ��ϴ�.

	      �ڼ��� ������ "https://developers.skplanetx.com/apidoc/kor/social" �Ұ� �������� �����Ͻñ� �ٶ��ϴ�.
	
	              - Facebook : Facebook AppKey �߱޹ޱ� �׸� 

		- Twitter : twitter AppKey �߱޹ޱ� �׸�  
	  
	   3) �� SNS ����Ʈ���� �߱޹��� key �� ID ������ ������ ���� (https://developers.skplanetx.com)�� ����մϴ�.

		: [ �� ����(my-center) > �� ����(app-station) > �Ҽ� ���ι��̴� ����(social-provider) ] ���� �ش� ���� Ŭ���ؼ� �� SNS���� 

		  �߱޹��� key �� ID �� �Է��ϼ���.

	   4) �߱޹��� key �� ID ������ �� �ҽ��� �����ؾ� �մϴ�.

		: OpenPlatform_MPP/src/com/skp/opx/core/client/Define.java�� Twitter Key class �� Facebook Key class�� �Է��ϼ���.

 		  /** Twitter callback uri, consumer key, consume secret key */
 		  public static final class Twitter_Key{
 		  	public static final String TWITTER_LINK_ID    	    = "< your key here >";
  			public static final String TWITTER_CALLBACK_URL     = "< your key here >";
  			public static final String TWITTER_CONSUMER_KEY     = "< your key here >";
  			public static final String TWITTER_CONSUMER_SECRET  = "< your key here >";
 		  }
 
 		  /** Facebook App ID & Auth code */
 		  public static final class Facebook_Key {
  		  	public static final String FACEBOOK_APP_ID   = "< your key here >";
 		  }
		  


vi. Sample Project�� ���� ����ó

	�ñ��Ͻ� ������ �Ʒ� Planet X ������ ������ [ �����ϱ� ] �޴��� ���� ������ �ֽʽÿ�
   
	(https://developers.skplanetx.com/community/contact-us/enrollment)