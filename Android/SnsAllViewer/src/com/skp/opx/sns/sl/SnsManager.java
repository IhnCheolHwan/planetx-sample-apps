package com.skp.opx.sns.sl;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skp.opx.core.client.Define.Facebook_Key;
import com.skp.opx.core.client.Define.Twitter_Key;
import com.skp.opx.sns.sl.facebook.DialogError;
import com.skp.opx.sns.sl.facebook.Facebook;
import com.skp.opx.sns.sl.facebook.Facebook.DialogListener;
import com.skp.opx.sns.sl.facebook.FacebookError;
import com.skp.opx.sns.sl.googleplus.GetNameTask;

/**
 * @���� : SNS �α��� manager Ŭ����
 * @Ŭ������ : SnsManager
 */
public final class SnsManager {

	private String mFacebookAccessToken = null;
	private String mTwitterAccessToken = null;
	private String mTwitterAccessTokenSecret = null;
	private String mGooglePlusAccessToken = null;

	private static SnsManager mSnsManager = null;

	private SnsManager() {}

	public static SnsManager getInstance() {

		if(mSnsManager == null) {
			mSnsManager = new SnsManager();
		}

		return mSnsManager;
	}
	
	/** 
	 * ����� ���̽��� AccessToken�� ��ȯ�Ѵ�.
	 * */
	public String getFacebookAccessToken() 							 						{ return mFacebookAccessToken; }
	
	/** 
	 * ����� Ʈ���� AccessToken�� ��ȯ�Ѵ�.
	 * */
	public String getTwitterAccessToken() 								 						{ return mTwitterAccessToken; }
	
	/** 
	 * ����� Ʈ���� Secret AccessToken����ū�� ��ȯ�Ѵ�.
	 * */
	public String getTwitterAccessTokenSecret() 								 				{ return mTwitterAccessTokenSecret; }
	
	/** 
	 * ����� �����÷��� AccessToken�� ��ȯ�Ѵ�.
	 * */
	public String getGooglePlusAccessToken() 							 						{ return mGooglePlusAccessToken; }
	
	/** 
	 * ���̽��� AccessToken�� �����Ѵ�.
	 * */
	public void setFacebookAccessToken(String strAccessToken)  						{ mFacebookAccessToken = strAccessToken; }
	
	/** 
	 * Ʈ���� AccessToken�� �����Ѵ�.
	 * */
	public void setTwitterAccessToken(String strAccessToken, String strSecretAccessToken) 	{ mTwitterAccessToken = strAccessToken; mTwitterAccessTokenSecret = strSecretAccessToken;}
	
	/** 
	 * �����÷��� AccessToken�� �����Ѵ�.
	 * */
	public void setGooglePlusAccessToken(String strAccessToken) 						{ mGooglePlusAccessToken = strAccessToken; }
	
	/** 
	 * ���̽��� ��밡������ ����
	 * */
	public boolean isEnableFacebook() 															{ return mFacebookAccessToken != null && mFacebookAccessToken.length() > 0; }
	
	/** 
	 * Ʈ���� ��밡������ ����
	 * */
	public boolean isEnableTwitter() 																{ return mTwitterAccessToken != null && mTwitterAccessToken.length() > 0 && mTwitterAccessTokenSecret != null && mTwitterAccessTokenSecret.length() > 0; }
	
	/** 
	 * �����÷��� ��밡������ ����
	 * */
	public boolean isEnableGooglePlus()															{ return mGooglePlusAccessToken != null && mGooglePlusAccessToken.length() > 0; }
	
	/** 
	 * ���̽��� AccessToken ������ ���� ������ �����Ѵ�.
	 * */
	public static void startFacebookAccess(Activity activity, final OnReceiveAccessToken tokenInterface) throws Exception {

		Facebook facebook = new Facebook(Facebook_Key.FACEBOOK_APP_ID);
		facebook.authorize_def(activity, new String[] {"email,read_stream,read_friendlists,publish_stream,user_photos,friends_photos,user_videos,read_mailbox,user_actions.video,manage_pages,offline_access,friends_videos,friends_birthday,user_birthday"}, new DialogListener() {

			@Override
			public void onComplete(Bundle values) {

				tokenInterface.onReceiveFacebookAccessToken(values.getString("access_token"));
			}

			@Override
			public void onFacebookError(FacebookError e) {

				tokenInterface.onReceiveFacebookAccessToken(null);
			}

			@Override
			public void onError(DialogError e) {	

				tokenInterface.onReceiveFacebookAccessToken(null);
			}

			@Override
			public void onCancel() {

				tokenInterface.onReceiveFacebookAccessToken(null);
			}
		} );
	}

	/** 
	 * Ʈ���� AccessToken ������ ���� ������ �����Ѵ�.
	 * */
	public static void startTwitterAccess(Context context, String strTwitterConsumerKey, String strTwitterConsumerSecret, final OnReceiveAccessToken tokenInterface) throws Exception {

		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getOAuthAuthorizedInstance(strTwitterConsumerKey, strTwitterConsumerSecret);
		RequestToken requestToken = twitter.getOAuthRequestToken(Twitter_Key.TWITTER_CALLBACK_URL);
		startTwitterWebView(context, twitter, requestToken, tokenInterface);
	}

	/** 
	 * �����÷��� AccessToken ������ ���� ������ �����Ѵ�.
	 * */
	public static void startGooglePlusAccess(Activity activity, final OnReceiveAccessToken tokenInterface)  throws Exception {

		new GetNameTask(activity, 0, tokenInterface).execute();
	}	

	/** 
	 * Ʈ���� AccessToken ������ ���Ͽ� ���並 ȣ���ϸ�, Preference ���尪�� ������ Preference�� ����� ���� ����Ѵ�.
	 * */
	private static void startTwitterWebView(final Context context, final Twitter twitter, final RequestToken requestToken, final OnReceiveAccessToken tokenInterface) throws Exception {

		if(getTwitterAccessToken(context) != null && getTwitterTokenSecret(context) != null) {
			tokenInterface.onReceiveTwitterAccessToken(getTwitterAccessToken(context), getTwitterTokenSecret(context));
			return;
		}

		WebView webView = new WebView(context);

		final Dialog dialog = new Dialog(context);
		dialog.setTitle("TWITTER");
		dialog.setContentView(webView, new LayoutParams(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels));
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				
				tokenInterface.onReceiveTwitterAccessToken(null, null);
			}
		});
		dialog.show();

		webView.loadUrl(requestToken.getAuthorizationURL());
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				if (url != null && url.equals("http://mobile.twitter.com/")) {
					dialog.dismiss();
					tokenInterface.onReceiveTwitterAccessToken(null, null);
				} else if (url != null && url.startsWith("https://developers.skplanetx.com")) {
					String[] urlParameters = url.split("\\?")[1].split("&");
					String oauthToken = "";
					String oauthVerifier = "";

					if (urlParameters[0].startsWith("oauth_token")) {
						oauthToken = urlParameters[0].split("=")[1];
					} else if (urlParameters[1].startsWith("oauth_token")) {
						oauthToken = urlParameters[1].split("=")[1];
					}

					if (urlParameters[0].startsWith("oauth_verifier")) {
						oauthVerifier = urlParameters[0].split("=")[1];
					} else if (urlParameters[1].startsWith("oauth_verifier")) {
						oauthVerifier = urlParameters[1].split("=")[1];
					}

					try {
						AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
						setTwitterAccessToken(context, accessToken.getToken(), accessToken.getTokenSecret());
						tokenInterface.onReceiveTwitterAccessToken(accessToken.getToken(), accessToken.getTokenSecret());
					} catch (TwitterException e) {
						tokenInterface.onReceiveTwitterAccessToken(null, null);
					} finally {
						dialog.dismiss();
					}
				}
			}
		});
	}

	/** 
	 * ����� ���̽��� AccessToken�� �����Ѵ�. 
	 * */
	public void clearFacebookAccessToken(Context context) {

		setFacebookAccessToken(null);
	}

	/** 
	 * ����� Ʈ���� AccessToken�� �����Ѵ�. 
	 * */
	public void clearTwitterAccessToken(Context context) {

		setTwitterAccessToken(null, null);
	}

	/** 
	 * ����� �����÷��� AccessToken�� �����Ѵ�. 
	 * */
	public void clearGooglePlusAccessToken(Context context) {

		setGooglePlusAccessToken(null);
	}

	/** 
	 * Ʈ���� AccessToken�� Preference�� �����Ѵ�. 
	 * */
	private static void setTwitterAccessToken(Context context, String strOauthToken, String strOauthTokenSecret) {

		Editor editor = context.getSharedPreferences("TWITTER", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE).edit();
		editor.putString("TOKEN", strOauthToken);
		editor.putString("SECRET", strOauthTokenSecret);
		editor.commit();
	}

	/** 
	 * ����� Ʈ���� AccessToken�� ��ȯ�Ѵ�. 
	 * */
	private static String getTwitterAccessToken(Context context) {

		SharedPreferences pre = context.getSharedPreferences("TWITTER", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		return pre.getString("TOKEN", null);
	}

	/** 
	 * ����� Ʈ���� Secret AccessToken�� ��ȯ�Ѵ�. 
	 * */
	private static String getTwitterTokenSecret(Context context) {

		SharedPreferences pre = context.getSharedPreferences("TWITTER", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		return pre.getString("SECRET", null);
	}
	
	/** 
	 * ���̽��� �ڵ� �α��� ���θ� �����Ѵ�.
	 * */
	public static void setAutoLoginFacebook(Context context, boolean isAuto) {

		Editor editor = context.getSharedPreferences("AUTO", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE).edit();
		editor.putBoolean("Facebook", isAuto);
		editor.commit();
	}

	/** 
	 * ���̽��� �ڵ� �α��� ����
	 * */
	public static boolean isAutoLoginFacebook(Context context) {

		SharedPreferences pre = context.getSharedPreferences("AUTO", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		return pre.getBoolean("Facebook", false);
	}
	
	/** 
	 * Ʈ���� �ڵ� �α��� ���θ� �����Ѵ�.
	 * */
	public static void setAutoLoginTwitter(Context context, boolean isAuto) {

		Editor editor = context.getSharedPreferences("AUTO", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE).edit();
		editor.putBoolean("Twitter", isAuto);
		editor.commit();
	}

	/** 
	 * Ʈ���� �ڵ� �α��� ����
	 * */
	public static boolean isAutoLoginTwitter(Context context) {

		SharedPreferences pre = context.getSharedPreferences("AUTO", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		return pre.getBoolean("Twitter", false);
	}
	
	/** 
	 * �����÷��� �ڵ� �α��� ���θ� �����Ѵ�.
	 * */
	public static void setAutoLoginGooglePlus(Context context, boolean isAuto) {

		Editor editor = context.getSharedPreferences("AUTO", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE).edit();
		editor.putBoolean("GooglePlus", isAuto);
		editor.commit();
	}

	/** 
	 * �����÷��� �ڵ� �α��� ����
	 * */
	public static boolean isAutoLoginGooglePlus(Context context) {

		SharedPreferences pre = context.getSharedPreferences("AUTO", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		return pre.getBoolean("GooglePlus", false);
	}
}
