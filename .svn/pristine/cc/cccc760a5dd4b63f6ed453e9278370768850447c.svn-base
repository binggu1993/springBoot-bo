package com.avit.itdap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration*/
public class MenuControllerTest
{
	/*@Autowired
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
	public void testSearchServer() throws Exception
	{
		String url = "/menus?pid=-1";
		
		 * 
		 * perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
		 * andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确；
		 * andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台；
		 * andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理；
		 
		String result = mvc
				.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.size").value("5"))
				.andExpect(jsonPath("$.totalPages").value("1"))
				.andExpect(jsonPath("$.totalElements").value("1")).andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);
	}

	*/

}
