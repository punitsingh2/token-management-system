package com.tms.resource.type;

public enum ResourceTypeEnum {

	NORMAL(1), PREMIUM(2);

	private int value;

	private ResourceTypeEnum(final int pValue) {
		this.value = pValue;
	}

	public static String valueOf(int resourceType) {
		for (ResourceTypeEnum e : ResourceTypeEnum.values()) {
			if (resourceType == e.value)
				return e.name();
		}
		return null;
	}
	
	static public boolean isMember(String aName) {
		ResourceTypeEnum[] allType = ResourceTypeEnum.values();
	       for (ResourceTypeEnum type : allType)
	           if (type.name().equals(aName))
	               return true;
	       return false;
	   }

}
