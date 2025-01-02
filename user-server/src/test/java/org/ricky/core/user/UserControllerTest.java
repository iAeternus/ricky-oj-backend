package org.ricky.core.user;

import org.junit.jupiter.api.Test;
import org.ricky.core.user.alter.UserAlterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className UserControllerTest
 * @desc
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAlterService userAlterService;

}