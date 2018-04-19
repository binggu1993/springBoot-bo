package com.avit.itdap.util;



public enum ResultCodeEnum {


	SUCCESS(200, "OK"),
	NOT_EXIST(404, "频道不存在！"),
	FORMAT_ERROR(205, "节目单格式错误！");
	
	
	private Integer key;
	private String value;
	
	private ResultCodeEnum(Integer key, String value){
		this.key = key;
		this.value = value;
	}
	public static ResultCodeEnum getDeviceType(Integer key){
		for(ResultCodeEnum type : ResultCodeEnum.values()){
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
		for(ResultCodeEnum type : ResultCodeEnum.values()){
			if(type.key.equals(key)){
				return type.value;
			}
		}
		return null;
	}
	
	
	public static Integer getKey(String value)
	{
		for(ResultCodeEnum type:ResultCodeEnum.values())
		{
			if(type.getValue().equals(value))
			{
				return type.getKey();
			}
		}
		return null;
	}

	
}
