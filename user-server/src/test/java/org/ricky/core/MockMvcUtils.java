package org.ricky.core;

import org.mockito.internal.util.MockUtil;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/31
 * @className MockMvcUtils
 * @desc
 */
public class MockMvcUtils {

    private volatile static MockMvcUtils mockMvcUtils;
    private static MockMvc mvc; // process request controller

    private MockMvcUtils() {
    }

    public static MockMvcUtils builder(WebApplicationContext wac) {
        if (mockMvcUtils == null) {
            synchronized (MockUtil.class) {
                if (mockMvcUtils == null) {
                    mockMvcUtils = new MockMvcUtils();
                    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
                }
            }
        }
        return mockMvcUtils;
    }

    public static MvcResult getPerformResult(String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();
    }

    public static MvcResult parsePerformResult(Object controller, String url, Runnable runnable) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        runnable.run();
        return mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();
    }

}
