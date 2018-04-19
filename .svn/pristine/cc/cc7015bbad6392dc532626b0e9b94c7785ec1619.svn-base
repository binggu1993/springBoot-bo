package com.avit.itdap.task;



public enum ChannelEnum {


	CCTV1(60, "CCTV1"),
	CCTV2(82, "CCTV2"),
	CCTV3(32, "CCTV3"),
	CCTV4(13, "CCTV4"),
	CCTV5(39, "CCTV5"),
	CCTV6(17, "CCTV6"),
	CCTV7(90, "CCTV7"),
	CCTV8(80, "CCTV8"),
	BJWS(61, "北京卫视"),
	ZJWS(57, "浙江卫视"),
	SZWS(20, "深圳卫视"),
	HBWS(50, "湖北卫视"),
	HNWS(70, "湖南卫视"),
	LNWS(68, "辽宁卫视"),
	JXWS(53, "江西卫视"),
	AHWS(33, "安徽卫视");
	
	
	private Integer key;
	private String value;
	
	private ChannelEnum(Integer key, String value){
		this.key = key;
		this.value = value;
	}
	public static ChannelEnum getDeviceType(Integer key){
		for(ChannelEnum type : ChannelEnum.values()){
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
		for(ChannelEnum type : ChannelEnum.values()){
			if(type.key.equals(key)){
				return type.value;
			}
		}
		return null;
	}
	
	
	public static Integer getKey(String value)
	{
		for(ChannelEnum type:ChannelEnum.values())
		{
			if(type.getValue().equals(value))
			{
				return type.getKey();
			}
		}
		return null;
	}

	
}
