package com.skp.opx.sns.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;

/**
 * @���� : ���� ���α׷��� ���̾� �α� ���� Ŭ����
 * @Ŭ������ : PopupDialogUtil 
 *
 */
public class PopupDialogUtil {
	private static ProgressDialog mProgressDialog = null;
	
	/** 
	 *  ���α׷��� ���̾�α� show Method 
	 * */
	public static void showProgressDialog(Context context, String title, String message) {
		
		if(mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
		
		mProgressDialog = ProgressDialog.show(context, title, message, true);
		mProgressDialog.setIcon(android.R.drawable.ic_dialog_alert);
		mProgressDialog.show();
		
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				
				if(mProgressDialog != null && mProgressDialog.isShowing()) {
					dismissProgressDialog();
//					Ÿ�Ӿƿ� ����ó��
				} 
			}
		}, 5000);
	}
	
	/** 
	 *  ���α׷��� ���̾�α� show Method 
	 * */
	public static void showProgressDialog(Context context, String message) {
		
		if(mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	    mProgressDialog = new ProgressDialog(context);
	    mProgressDialog.setCancelable(false);
	    mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}

	/** 
	 *  ���̾�α� �ݱ� Method
	 * */
	public static void dismissProgressDialog() {
		
		if( mProgressDialog != null ) {
			
			mProgressDialog.dismiss();
		}
	}
		
	/** 
	 * ��,�ƴϿ� ���̾�α� ���� Method 
	 * */
	public static void showYesNoDialog(final Context context, CharSequence title,
			CharSequence message, 
			String yesButtonTitle, 
			String noButtonTitle,
	        boolean indeterminate, 
	        boolean cancelable, OnCancelListener cancelListener,
	        DialogInterface.OnClickListener positiveClickListener,
	        DialogInterface.OnClickListener negativeClickListener)
	{
		 AlertDialog.Builder builder = new Builder(context);
		 builder.setTitle(title);
		 builder.setIcon(android.R.drawable.ic_dialog_alert);
		 builder.setMessage(message);
		 builder.setCancelable(cancelable);
		 builder.setPositiveButton(yesButtonTitle, positiveClickListener);
		 builder.setNegativeButton(noButtonTitle, negativeClickListener);
		 builder.show();
	}
	
	/** 
	 * ��,�ƴϿ� ���̾�α� ���� Method 
	 * */
	public static void showYesNoDialog(final Context context, int title,
			int message, 
			int yesButtonTitle, 
			int noButtonTitle,
	        boolean indeterminate, 
	        boolean cancelable, OnCancelListener cancelListener,
	        DialogInterface.OnClickListener positiveClickListener,
	        DialogInterface.OnClickListener negativeClickListener)
	{
		 AlertDialog.Builder builder = new Builder(context);
		 builder.setTitle(context.getString(title));
		 builder.setIcon(android.R.drawable.ic_dialog_alert);
		 builder.setMessage(context.getString(message));
		 builder.setCancelable(cancelable);
		 builder.setPositiveButton(context.getString(yesButtonTitle), positiveClickListener);
		 builder.setNegativeButton(context.getString(noButtonTitle), negativeClickListener);
		 builder.show();
	}
	
	/** 
	 * Ȯ�� ���̾�α� ���� Method 
	 * */
	public static void showConfirmDialog(final Context context, CharSequence title,
			CharSequence message, boolean indeterminate,
			boolean cancelable, OnCancelListener cancelListener,
			DialogInterface.OnClickListener positiveClickListener) 
	{
		AlertDialog.Builder builder = new Builder(context);
		 builder.setTitle(title);
		 builder.setIcon(android.R.drawable.ic_dialog_alert);
		 builder.setMessage(message);
		 builder.setCancelable(cancelable);
		 builder.setPositiveButton(context.getString(android.R.string.yes), positiveClickListener);
		 builder.show();
	}
}