package com.skp.opx.mss.util;

import android.os.Environment;

public class SystemUtil {

	/**
	 * sd ī�� ����Ʈ ����(�̵�� ��� ���� �� Ȯ�� ���� )
	 * @return
	 */
	public static boolean isMediaMounted(){
		
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			return true;
		}else{
			return false;
		}			
	}
}
