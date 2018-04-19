package com.avit.itdap.task;

public enum EpgName {
	
	XWLB(1,"新闻联播"),
	TQYB(2,"天气预报"),
	JDFT(3,"焦点访谈"),
	HJJC(4,"黄金剧场"),
	WYDY(5,"午夜电影");
	
	
	
	private Integer key;
	private String value;
	
	private EpgName(Integer key, String value){
		this.key = key;
		this.value = value;
	}
	public static EpgName getDeviceType(Integer key){
		for(EpgName type : EpgName.values()){
			if(type.key.equals(key)){
				return type;
			}
		}
		return null;
	}
	public Integer getKey() {
		return key;
	}
	public String getValue(){
		return value;
	}
	public static String getValue(Integer key) {
		for(EpgName type : EpgName.values()){
			if(type.key.equals(key)){
				return type.value;
			}
		}
		return null;
	}
	
	
	public static Integer getKey(String value)
	{
		for(EpgName type:EpgName.values())
		{
			if(type.getValue().equals(value))
			{
				return type.getKey();
			}
		}
		return null;
	}
}
