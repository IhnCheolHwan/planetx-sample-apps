package com.skp.opx.sns.contact;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.util.Log;

/**
 * @���� : ���� �ּҷϿ� ���� ��ƿ��Ƽ Ŭ����
 * @Ŭ������ : AddressBookDAO
 * 
 */
public class AddressBookDAO {

    public static final String   SORT_ORDER                    = Phone.DISPLAY_NAME + "," + Phone.TYPE;
	public static final String[] DATA_PROJECTION = new String[] { Data.CONTACT_ID, Data.DISPLAY_NAME, Data.DATA1 };

	/** 
	 *  ���ÿ� ����� ��� �ּҷ��� ��ȸ�Ͽ� List���·� ��ȯ�Ѵ�.
	 * */
	public static List<EntityContact> getContactList (Context context) {

		ArrayList<EntityContact> contactList = new ArrayList<EntityContact>();
		StringBuilder where = new StringBuilder();

		where.append(Data.MIMETYPE); where.append("="); where.append("'"); where.append(Phone.CONTENT_ITEM_TYPE); where.append("'");
		where.append(" AND ");
		where.append("("); 
		where.append(Data.DATA2); where.append("='2'");
		where.append(")");

		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(Data.CONTENT_URI, DATA_PROJECTION, where.toString(), null, SORT_ORDER);

			if (cursor != null) {
				while (cursor.moveToNext()) {
					EntityContact entityContact = new EntityContact();
					entityContact.contactID = cursor.getLong(0);
					entityContact.name = cursor.getString(1);
					entityContact.phoneNumber = cursor.getString(2).replace("-", "");
					contactList.add(entityContact);
				}
			}
		} 	
		catch (Exception e) {
			Log.e(AddressBookDAO.class.getSimpleName(), e.getMessage());
		} finally {
			if (null != cursor) {
				cursor.close();
				cursor = null;
			}
		}
		return contactList;
	}
}