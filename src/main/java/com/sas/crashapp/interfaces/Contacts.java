package com.sas.crashapp.interfaces;

import com.sas.crashapp.beans.EContactsBean;

public interface Contacts {
	public Object createContacts(EContactsBean contactsBean);
	public Object getContacts(long user_id);
	public Object deleteContacts(long user_id);
}
