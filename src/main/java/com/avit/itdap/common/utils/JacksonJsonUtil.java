package com.avit.itdap.common.utils;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonJsonUtil
{
	private static final Logger logger = LoggerFactory.getLogger(JacksonJsonUtil.class);
    /**
     * json操作对象
     */
    private static final ObjectMapper OM = new ObjectMapper();
    
    static
    {
        // 允许单引号、允许不带引号的字段名称
    	// OM.enableSimple();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 空值处理为空串
        OM.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
        {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
            {
                jgen.writeString("");
            }
        });
        //不包含空值属性
        OM.setSerializationInclusion(Include.NON_NULL);

        //设置时区
        OM.setTimeZone(TimeZone.getDefault());
    }
    
    /**
     * 把对象json格式，包括list数组--默认类名作为根节点
     * 转化格式为：
     * 对象 -- {"XXXX":{"name":"安迪","age":12,"p":false,"tes":"aaa"}}
     * 数组 -- {"XXXX":[{"name":"安迪","age":12,"p":false,"tes":"aaa"},
     *                 {"name":"安迪","age":12,"p":false,"tes":"aaa"},
     *                 {"name":"安迪","age":12,"p":false,"tes":"aaa"}]}
     * @param 0 对象或者list数组
     * @throws Exception 
     */
    public static String transObjToJsonClassName(Object obj)
    {
        String json = "";
        try
        {
            OM.configure(SerializationFeature.WRAP_ROOT_VALUE,true);
            json = OM.writeValueAsString(obj);
        }
        catch (Exception e)
        {
        	logger.error("",e);
        }
        return json;
    }
    
    /**
     * 把对象json格式，包括list数组
     * 转化格式为：
     * 对象 -- {"name":"安迪","age":12,"p":false,"tes":"aaa"}
     * 数组 -- [{"name":"安迪","age":12,"p":false,"tes":"aaa"},{"name":"安迪","age":12,"p":false,"tes":"aaa"},{"name":"安迪","age":12,"p":false,"tes":"aaa"}]
     * @param 0 对象或者list数组
     * @throws Exception 
     */
    public static String transObjToJson(Object obj)
    {
        String json = "";
        try
        {
            OM.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
            json = OM.writeValueAsString(obj);
        }
        catch (Exception e)
        {
        	logger.error("",e);
        }
        return json;
    }
    
    /**
     * 转化Json为指定对象
     * @param json json字符串
     * @param clasz 对象
     * @return 指定对象
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws Exception 
     */
    public static <T> T transJsonToObj(String json,Class<T> clazz)
    {
    	if(json == null){
    		return null;
    	}
    	
        T jsonObj = null;
        try
        {
            jsonObj = OM.readValue(json, clazz);
        }
        catch (Exception e)
        {
        	logger.error("",e);
        }
        return jsonObj;
    }
    
    /**
     * 转化Json为指定对象
     * @param json json字符串
     * @param clasz 对象数组
     * @return 指定对象
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws Exception 
     */
    public static <T> T[] transJsonToArray(String json,Class<T[]> clazz)
    {
        T[] jsonObj = null;
        try
        {
            jsonObj = OM.readValue(json, clazz);
        }
        catch (Exception e)
        {
        	logger.error("",e);
        }
        return jsonObj;
    }
    
    /**
     * 为easyUI表格界面拼接json字符串，对空值list进行处理，格式如下：
     * {"total":28,"rows":[
     *   {"productid":"AV-CB-01","attr1":"Adult Male","itemid":"EST-18"}
     * ]}
     * list为null和size为0空值格式如下：{"total":0,"rows":[]}（ps:total为传入值，不会自动转化）
     * @param total 
     * @param data
     * @return
     * @throws Exception 
     */
    public static <T> String transJsonForEasyUITable(int total,List<T> data)
    {
        return transObjToJson(new EasyUITable<T>(total, data));
    }
    
    private static class EasyUITable<T>
    {
        @JsonProperty("total")
        private Integer total;
        
        @JsonProperty("rows")
        private List<T> rows;
        
        public EasyUITable(Integer total, List<T> rows)
        {
            super();
            this.total = total;
            this.rows = rows;
        }
    }
}
