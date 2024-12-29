package org.ricky.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className UserController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "用户相关接口")
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping("/test")
    public String testUserController() {
        return "UserController";
    }

}
