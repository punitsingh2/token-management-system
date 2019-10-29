package com.tms.resource.type;

public enum ServiceTypeEnum {

	DEPOSIT(0), INQUIRY(1), ACCOUNT_OPEN(2),PREMIUMDEPOSIT(3), PREMIUMINQUIRY(4), PREMIUMACCOUNT_OPEN(5);

	private int value;

	private ServiceTypeEnum(final int pValue) {
		this.value = pValue;
	}

	public static String valueOf(final int serviceValue) {
		for (ServiceTypeEnum e : ServiceTypeEnum.values()) {
			if (serviceValue == e.value)
				return e.name();
		}
		return null;
	}
	
	public static int typeOf(final String serviceType) {
		for (ServiceTypeEnum e : ServiceTypeEnum.values()) {
			if (serviceType.equals(e.name()))
				return e.ordinal();
		}
		return 0;
	}

}
