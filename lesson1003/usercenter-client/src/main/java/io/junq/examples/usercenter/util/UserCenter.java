package io.junq.examples.usercenter.util;

public class UserCenter {
	
	/**
	 * 权限：权限有`ROLES`前缀，Spring约定。（有需要可以覆盖）
	 */
	public static final String ADMIN_USERNAME = "admin";
	public static final String ADMIN_PASS = "adminpass";
	public static final String ADMIN_EMAIL = "admin@junq.io";
	
    public static final String USER_USERNAME = "user";
    public static final String USER_PASS = "userpass";
    public static final String USER_EMAIL = "user@junq.io";

	public static final String NAME = ADMIN_USERNAME;
	public static final String PASS = ADMIN_PASS;
	public static final String EMAIL = ADMIN_EMAIL;

	// 权限

	public static final class Privileges {

		// 用户
		public static final String CAN_USER_READ = "ROLE_USER_READ";
		public static final String CAN_USER_WRITE = "ROLE_USER_WRITE";

		// 角色
		public static final String CAN_ROLE_READ = "ROLE_ROLE_READ";
		public static final String CAN_ROLE_WRITE = "ROLE_ROLE_WRITE";

		// 权限
		public static final String CAN_PRIVILEGE_READ = "ROLE_PRIVILEGE_READ";
		public static final String CAN_PRIVILEGE_WRITE = "ROLE_PRIVILEGE_WRITE";

	}

	public static final class Roles {

		/** 管理员 */
		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		/** 终端用户 */
		public static final String ROLE_ENDUSER = "ROLE_ENDUSER";

	}

	private UserCenter() {
        throw new AssertionError();
    }

}
