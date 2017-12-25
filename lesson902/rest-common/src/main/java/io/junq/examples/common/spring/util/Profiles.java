package io.junq.examples.common.spring.util;

public final class Profiles {
	
	public static final String PRODUCTION = "production";
	public static final String DEV = "dev";
	
    // common
    public static final String TEST = "test";
    public static final String CLIENT = "client";


	private Profiles() {
		throw new AssertionError();
	}
	
}
