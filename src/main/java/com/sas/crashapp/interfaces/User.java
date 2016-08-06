package com.sas.crashapp.interfaces;

import com.sas.crashapp.beans.UserBean;

public interface User {
	public Object createUser(UserBean userBean);
	public Object updateUser(UserBean userBean);
	public Object checkLogin(UserBean userBean);
	public Object getUser(long user_id);
	public Object deleteUser(long user_id);
}
