package com.avit.itdap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ChannelControllerTest {
	@Autowired
	WebApplicationContext wac;
	MockMvc mvc;
	ObjectMapper mapper;

	@Before
	public void init()
	{
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mapper = new ObjectMapper();
	}
	@Test
	public void testSearchChannel() throws Exception
	{
		String url = "/channels?page=1&size=5";
		String result = mvc
				.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.size").value("5"))
				//.andExpect(jsonPath("$.totalPages").value("1"))
				//.andExpect(jsonPath("$.totalElements").value("1"))
				.andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);
	}
	@Test
	public void testSearchChannelByName() throws Exception
	{
		String url = "/channels?name=CCTV2";
		String result = mvc
				.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_UTF8))
				//.andExpect(jsonPath("$.content.length").value(1))
				.andExpect(jsonPath("$.content[0].channelName").value("CCTV2"))
				.andExpect(jsonPath("$.size").value("10"))
				//.andExpect(jsonPath("$.totalPages").value("1"))
				//.andExpect(jsonPath("$.totalElements").value("1"))
				.andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);
	}
	@Test
	public void testAddChannel() throws Exception
	{
		String content = "{\"channelName\":\"CCTV8\",\"channelCode\":\"8\",\"serviceId\":8,\"channelType\":1}";
		String url = "/channels";
		//String result = 
				mvc.perform(post(url).content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.channelName").value("CCTV8"))
				.andExpect(jsonPath("$.id").exists());
				
				//.andReturn().getResponse().getContentAsString();
		//System.out.println("mvc:"+result);
	}
	
	@Test
	public void testAddChannelError() throws Exception
	{
		String content = "{\"channelName\":\"CCTV8\",\"channelCode\":\"8\",\"serviceId\":8}";
		String url = "/channels";
		//String result = 
				mvc.perform(post(url).content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.id").doesNotExist());
				
				//.andReturn().getResponse().getContentAsString();
		//System.out.println("mvc:"+result);
	}
}
