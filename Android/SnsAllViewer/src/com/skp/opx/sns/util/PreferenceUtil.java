package com.skp.opx.sns.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @���� : ���� Preference ���� Util
 * @Ŭ������ : PreferenceUtil 
 *
 */
public class PreferenceUtil {
	public static final String PREF_LOGIN = "login";
	public static final String PREF_LOGIN_STATE = "login_def";
	public static final String KEY_AUTO_LOGIN = "auto_login";
	public static final String KEY_LOGIN_STATE = "login_state";

	/** 
	 *  AppUserId ���� Method
	 * */
	public static String getAppUserID(Context context, boolean isTempID) {

		SharedPreferences preference;
		
		if(isTempID) {
			preference = context.getSharedPreferences("APP_USER_ID_TEMP", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		} else {
			preference = context.getSharedPreferences("APP_USER_ID", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		}
		
		
		return preference.getString("EXTRA_APP_USER_ID", null);
	}
   
	/** 
	 *  AppUserId ���� Method
	 * */
	public static void setAppUserID(Context context, String strAppUserID, boolean isTempID) {
		
		SharedPreferences preference;
		
		if(isTempID) {
			preference = context.getSharedPreferences("APP_USER_ID_TEMP", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		} else {
			 preference = context.getSharedPreferences("APP_USER_ID", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		}
		
		Editor editor = preference.edit();
		editor.putString("EXTRA_APP_USER_ID", strAppUserID);
		editor.commit();
	}
	
	/** 
	 *  �ڵ� �α��� üũ ���� ���� Method
	 * */
	public static void setAutoLogin(Context context, boolean isAutoLogin) {

		Editor editor = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).edit();
		editor.putBoolean(KEY_AUTO_LOGIN, isAutoLogin);
		editor.commit();
	}

	/** 
	 *  �ڵ� �α��� üũ ���� ���� Method
	 * */
	public static boolean getAutoLogin(Context context) {
		return context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).getBoolean(KEY_AUTO_LOGIN, false);
	}

	/** 
	 *  ����Ʈ�� �α��� ���� ���� Method
	 * */
	public static void setNateOnLoginState(Context context, boolean isLogin) {
		Editor editor = context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).edit();
		editor.putBoolean(KEY_LOGIN_STATE, isLogin);
		editor.commit();
	}

	/** 
	 *  ����Ʈ�� �α��� ���� ���� Method
	 * */
	public static boolean getNateOnLoginState(Context context) {
		return context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).getBoolean(KEY_LOGIN_STATE, false);
	}

	/** 
	 *  ���̿��� �α��� ���� ���� Method
	 * */
	public static void setCyworldLoginState(Context context, boolean isLogin) {
		Editor editor = context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).edit();
		editor.putBoolean(KEY_LOGIN_STATE, isLogin);
		editor.commit();
	}
	
	/** 
	 *  ���̿��� �α��� ���� ���� Method
	 * */
	public static boolean getCyworldLoginState(Context context) {
		return context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).getBoolean(KEY_LOGIN_STATE, false);
	}

	/** 
	 *  ���̽��� �α��� ���� ���� Method
	 * */
	public static void setFacebookLoginState(Context context, boolean isLogin) {
		Editor editor = context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).edit();
		editor.putBoolean(KEY_LOGIN_STATE, isLogin);
		editor.commit();
	}

	/** 
	 *  ���̽��� �α��� ���� ���� Method
	 * */
	public static boolean getFacebookLoginState(Context context) {
		return context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).getBoolean(KEY_LOGIN_STATE, false);
	}

	/** 
	 * Ʈ���� �α��� ���� ���� Method
	 * */
	public static void setTwitterLoginState(Context context, boolean isLogin) {
		Editor editor = context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).edit();
		editor.putBoolean(KEY_LOGIN_STATE, isLogin);
		editor.commit();
	}

	/** 
	 *  Ʈ���� �α��� ���� ���� Method
	 * */
	public static boolean getTwitterLoginState(Context context) {
		return context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).getBoolean(KEY_LOGIN_STATE, false);
	}

	/** 
	 *  ���� �÷��� �α��� ���� ���� Method
	 * */
	public static void setGoogleLoginState(Context context, boolean isLogin) {
		Editor editor = context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).edit();
		editor.putBoolean(KEY_LOGIN_STATE, isLogin);
		editor.commit();
	}
	
	/** 
	 *  ���̿��� �α��� ���� ���� Method
	 * */
	public static boolean getGoogleLoginState(Context context) {
		return context.getSharedPreferences(PREF_LOGIN_STATE, Context.MODE_PRIVATE).getBoolean(KEY_LOGIN_STATE, false);
	}
}
