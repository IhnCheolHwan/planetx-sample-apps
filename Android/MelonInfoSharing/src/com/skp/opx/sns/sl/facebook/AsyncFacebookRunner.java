package com.skp.opx.sns.sl.facebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Context;
import android.os.Bundle;

/**
 * @���� : ���̽��� �񵿱� �ۼ��� Ŭ����
 * @Ŭ������ : AsyncFacebookRunner
 */
public class AsyncFacebookRunner {

    Facebook fb;

	/** 
	 * AsyncFacebookRunner ������
	 * */
    public AsyncFacebookRunner(Facebook fb) {
    	
        this.fb = fb;
    }

	/** 
	 * �α׾ƿ� �����Լ�
	 * */
    public void logout(final Context context, final RequestListener listener, final Object state) {
    	
        new Thread() {
            @Override public void run() {
                try {
                    String response = fb.logout(context);
                    if (response.length() == 0 || response.equals("false")){
                        listener.onFacebookError(new FacebookError(
                                "auth.expireSession failed"), state);
                        return;
                    }
                    listener.onComplete(response, state);
                } catch (FileNotFoundException e) {
                    listener.onFileNotFoundException(e, state);
                } catch (MalformedURLException e) {
                    listener.onMalformedURLException(e, state);
                } catch (IOException e) {
                    listener.onIOException(e, state);
                }
            }
        }.start();
    }

	/** 
	 * �α׾ƿ� �����Լ�
	 * */
    public void logout(final Context context, final RequestListener listener) {
    	
        logout(context, listener, /* state */ null);
    }

	/** 
	 * ���� �۽� �Լ�
	 * */
    public void request(Bundle parameters, RequestListener listener, final Object state) {
    	
        request(null, parameters, "GET", listener, state);
    }

	/** 
	 * ���� �۽� �Լ�
	 * */
    public void request(Bundle parameters, RequestListener listener) {
    	
    	request(null, parameters, "GET", listener, /* state */ null);
    }

	/** 
	 * ���� �۽� �Լ�
	 * */
    public void request(String graphPath, RequestListener listener, final Object state) {
    	
        request(graphPath, new Bundle(), "GET", listener, state);
    }

	/** 
	 * ���� �۽� �Լ�
	 * */
    public void request(String graphPath, RequestListener listener) {
    	
        request(graphPath, new Bundle(), "GET", listener, /* state */ null);
    }

	/** 
	 * ���� �۽� �Լ�
	 * */
    public void request(String graphPath, Bundle parameters, RequestListener listener, final Object state) {
    	
        request(graphPath, parameters, "GET", listener, state);
    }

	/** 
	 * ���� �۽� �Լ�
	 * */
    public void request(String graphPath, Bundle parameters, RequestListener listener) {
    	
        request(graphPath, parameters, "GET", listener, /* state */ null);
    }

	/** 
	 * ���� �۽� �Լ�
	 * */
    public void request(final String graphPath, final Bundle parameters, final String httpMethod, final RequestListener listener, final Object state) {
    	
        new Thread() {
            @Override public void run() {
                try {
                    String resp = fb.request(graphPath, parameters, httpMethod);
                    listener.onComplete(resp, state);
                } catch (FileNotFoundException e) {
                    listener.onFileNotFoundException(e, state);
                } catch (MalformedURLException e) {
                    listener.onMalformedURLException(e, state);
                } catch (IOException e) {
                    listener.onIOException(e, state);
                }
            }
        }.start();
    }

	/** 
	 * ���� �۽� �� ��� ������
	 * */
    public static interface RequestListener {

    	/** 
    	 * �ۼ��� ������ ����� ��ȯ �������̽�
    	 * */
        public void onComplete(String response, Object state);

    	/** 
    	 * onIOException ����ó���� ���� �������̽�
    	 * */
        public void onIOException(IOException e, Object state);

    	/** 
    	 * onFileNotFoundException ����ó���� ���� �������̽�
    	 * */
        public void onFileNotFoundException(FileNotFoundException e, Object state);
        
    	/** 
    	 * onMalformedURLException ����ó���� ���� �������̽�
    	 * */
        public void onMalformedURLException(MalformedURLException e, Object state);

    	/** 
    	 * onFacebookError ����ó���� ���� �������̽�
    	 * */
        public void onFacebookError(FacebookError e, Object state);

    }
}
