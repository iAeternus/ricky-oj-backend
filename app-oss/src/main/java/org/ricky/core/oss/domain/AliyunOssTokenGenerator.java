package org.ricky.core.oss.domain;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import lombok.RequiredArgsConstructor;
import org.ricky.common.properties.AliyunProperties;
import org.ricky.common.properties.CommonProperties;
import org.ricky.common.utils.MyObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.aliyuncs.http.MethodType.POST;
import static java.time.Instant.parse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/15
 * @className AliyunOssTokenGenerator
 * @desc 生成阿里云OSS临时访问凭证
 */
@Component
@RequiredArgsConstructor
public class AliyunOssTokenGenerator {

    private static final String ENDPOINT = "sts.aliyuncs.com";
    private final MyObjectMapper objectMapper;
    private final AliyunProperties aliyunProperties;
    private final CommonProperties commonProperties;
    private IAcsClient acsClient;

    public OssToken generateOssToken(String folderPath) {
        try {
            AssumeRoleRequest request = createRequest(folderPath);
            AssumeRoleResponse.Credentials credentials = getAcsClient().getAcsResponse(request).getCredentials();
            return OssToken.builder()
                    .accessKeyId(credentials.getAccessKeyId())
                    .accessKeySecret(credentials.getAccessKeySecret())
                    .securityToken(credentials.getSecurityToken())
                    .folder(folderPath + "/" + LocalDate.now())
                    .bucket(aliyunProperties.getOss().getOssBucket())
                    .endpoint(aliyunProperties.getOss().getOssEndpoint())
                    .secure(commonProperties.isHttpsEnabled())
                    .expiration(parse(credentials.getExpiration()))
                    .build();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to create STS token from Aliyun for folder[" + folderPath + "].", t);
        }
    }

    private IAcsClient getAcsClient() {
        if (acsClient == null) {
            IClientProfile profile = DefaultProfile.getProfile("", aliyunProperties.getAk(), aliyunProperties.getAks());
            acsClient = new DefaultAcsClient(profile);
        }

        return acsClient;
    }

    private AssumeRoleRequest createRequest(String folder) {
        List<String> resource = List.of("acs:oss:*:*:" + aliyunProperties.getOss().getOssBucket() + "/" + folder + "/*");

        Statement putStatement = Statement.builder()
                .Action("oss:PutObject")
                .Effect("Allow")
                .Resource(resource)
                .build();

        // 将deleteStatement加入到armPolicy中表示可以删除文件
        Statement deleteStatement = Statement.builder()
                .Action("oss:DeleteObject")
                .Effect("Allow")
                .Resource(resource)
                .build();

        AliyunArmPolicy armPolicy = AliyunArmPolicy.builder()
                .Version("1")
                .Statement(List.of(putStatement, deleteStatement))
                .build();

        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysEndpoint(ENDPOINT);
        request.setDurationSeconds(900L);
        request.setSysMethod(POST);
        request.setRoleArn(aliyunProperties.getRole());
        request.setRoleSessionName("sts-session");
        request.setPolicy(objectMapper.writeValueAsString(armPolicy));
        return request;
    }
}
