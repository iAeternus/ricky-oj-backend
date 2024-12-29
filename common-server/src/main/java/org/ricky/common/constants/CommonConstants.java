package org.ricky.common.constants;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className CommonConstants
 * @desc
 */
public interface CommonConstants {

    String CHINA_TIME_ZONE = "Asia/Shanghai";
    String AUTH_COOKIE_NAME = "roj_token";
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";

    String EVENT_COLLECTION = "event";
    String SHEDLOCK_COLLECTION = "shedlock";
    String PROBLEM_COLLECTION = "problem";
    String TAG_GROUP_COLLECTION = "tag_group";
    String USER_COLLECTION = "user";

    String GROUP_ID_PREFIX = "GRP";
    String PROBLEM_ID_PREFIX = "PRO";
    String CASE_ID_PREFIX = "CAS";
    String CASE_GROUP_ID_PREFIX = "CSG";
    String TAG_GROUP_ID_PREFIX = "TGG";
    String TAG_ID_PREFIX = "TAG";
    String USER_ID_PREFIX = "USR";
    String ACCEPTED_PROBLEM_ID_PREDIX = "ACP";
    String SUBMIT_ID_PREFIX = "SMT";

    String INCORRECT_ID_MESSAGE = " ID format is incorrect.";

    String USER_CACHE = "USER";

    int MAX_URL_LENGTH = 1024;
    int MAX_CASES_SIZE = 64;

    String REDIS_DOMAIN_EVENT_CONSUMER_GROUP = "domain.event.group";
    String REDIS_WEBHOOK_CONSUMER_GROUP = "webhook.group";
    String REDIS_NOTIFICATION_CONSUMER_GROUP = "notification.group";

}
