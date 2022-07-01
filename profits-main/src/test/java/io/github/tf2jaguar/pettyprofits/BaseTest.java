package io.github.tf2jaguar.pettyprofits;

import com.alibaba.fastjson.JSON;
import io.github.tf2jaguar.micro.core.input.InputMessage;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhangguodong
 * @since 2022.06.30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
public class BaseTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions performPost(String mapping, Object... request) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(mapping)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        if (request.length > 0) {
            requestBuilder
                    .content(JSON.toJSONString(InputMessage.params(request[0])));
        }
        ResultActions actions = mockMvc.perform(requestBuilder).andExpect(status().isOk());
        actions.andReturn()
                .getResponse()
                .setCharacterEncoding("utf8");
        return actions;
    }
}
