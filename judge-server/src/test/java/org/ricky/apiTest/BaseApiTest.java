package org.ricky.apiTest;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.ricky.apiTest.utils.SetupApi;
import org.ricky.common.domain.event.DomainEventDao;
import org.ricky.common.exception.ErrorCodeEnum;
import org.ricky.common.exception.ErrorResponse;
import org.ricky.common.exception.MyError;
import org.ricky.common.password.MyPasswordEncoder;
import org.ricky.common.security.jwt.JwtService;
import org.ricky.common.utils.MyObjectMapper;
import org.ricky.core.judge.domain.JudgeRepository;
import org.ricky.core.user.domain.UserRepository;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.function.Supplier;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.ricky.common.constants.CommonConstants.AUTHORIZATION;
import static org.ricky.common.constants.CommonConstants.AUTH_COOKIE_NAME;
import static org.ricky.common.utils.ValidationUtils.isNotBlank;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className BaseApiTest
 * @desc 接口测试基类
 */
@ActiveProfiles("ci")
@Execution(CONCURRENT)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BaseApiTest {

    @Autowired
    protected MyObjectMapper objectMapper;

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Autowired
    protected SetupApi setupApi;

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected MyPasswordEncoder passwordEncoder;

    @Autowired
    protected DomainEventDao domainEventDao;

    @Autowired
    protected VerificationCodeRepository verificationCodeRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JudgeRepository judgeRepository;

    // add repository here...

    @LocalServerPort
    protected int port;

    public static RequestSpecification given() {
        return RestAssured.given()
                .config(RestAssuredConfig.config()
                        .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((type, s) -> new MyObjectMapper()))
                        .encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
                        .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()));
    }

    public static RequestSpecification given(String jwt) {
        if (isNotBlank(jwt)) {
            return given().cookie(AUTH_COOKIE_NAME, jwt);
        }

        return given();
    }

    public static RequestSpecification givenBearer(String jwt) {
        if (isNotBlank(jwt)) {
            return given().header(AUTHORIZATION, String.format("Bearer %s", jwt));
        }

        return given();
    }

    public static RequestSpecification givenBasic(String username, String password) {
        return given().auth().preemptive().basic(username, password);
    }

    @BeforeEach
    public void setUp() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        RestAssured.port = port;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    @AfterEach
    public void cleanUp() {

    }

    public static void assertError(Supplier<Response> apiCall, ErrorCodeEnum expectedCode) {
        MyError error = apiCall.get().then().statusCode(expectedCode.getStatus()).extract().as(ErrorResponse.class).getError();
        Assertions.assertEquals(expectedCode, error.getCode());
    }

}
