package io.junq.examples.usercenter.util;

public final class UserCenterMapping {
	
    public static final String BASE = "/api";
	
    public static final String USERS = BASE + "/users";
    public static final String PRIVILEGES = BASE + "/privileges";
    public static final String ROLES = BASE + "/roles";
    
    public static final String AUTHENTICATION = BASE + "/authentication";

    public static final class Singular {

        public static final String USER = "user";
        public static final String PRIVILEGE = "privilege";
        public static final String ROLE = "role";

    }
    
    public static final class Plural {
        public static final String USERS = "users";
        public static final String PRIVILEGES = "privileges";
        public static final String ROLES = "roles";
    }
    
    public static final class Hateoas {
        private static final String HATEOAS = BASE + "/hateoas/";

        public static final String USER = HATEOAS + Plural.USERS;
        public static final String PRIVILEGE = HATEOAS + Plural.PRIVILEGES;
        public static final String ROLES = HATEOAS + Plural.ROLES;
    }
}
