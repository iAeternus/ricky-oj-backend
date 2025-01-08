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
    String AUTH_COOKIE_NAME = "ROJToken";
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";
    String NO_USER_ID = "NO_USER_ID";

    String EVENT_COLLECTION = "event";
    String SHEDLOCK_COLLECTION = "shedlock";
    String USER_COLLECTION = "user";
    String VERIFICATION_COLLECTION = "verification";
    String TAG_COLLECTION = "tag";
    String TEAM_COLLECTION = "team";
    String PROBLEM_COLLECTION = "problem";
    String JUDGE_COLLECTION = "judge";
    String JUDGER_COLLECTION = "judger";

    String CASE_ID_PREFIX = "CAS";
    String CASE_GROUP_ID_PREFIX = "CSG";
    String USER_ID_PREFIX = "USR";
    String ACCEPTED_PROBLEM_ID_PREDIX = "ACP";
    String VERIFICATION_ID_PREFIX = "VRC";
    String TAG_ID_PREFIX = "TAG";
    String TEAM_ID_PREFIX = "TEM";
    String PROBLEM_ID_PREFIX = "PRO";
    String SUBMIT_ID_PREFIX = "SMT";
    String JUDGER_ID_PREFIX = "JDG";
    String JUDGE_CASE_ID_PREFIX = "JGC";

    String INCORRECT_ID_MESSAGE = " ID format is incorrect.";

    String USER_CACHE = "USER";
    String PROBLEMS_CACHE = "PROBLEMS";
    String PROBLEM_CACHE = "PROBLEM";
    String TAGS_CACHE = "TAGS";
    String JUDGE_CACHE = "JUDGE";

    String CASE_GROUPS_DELETED_EVENT = "CASE_GROUPS_DELETED_EVENT";
    String CASES_DELETED_EVENT = "CASES_DELETED_EVENT";
    String USER_CREATED_EVENT = "USER_CREATED_EVENT";
    String TAG_DELETED_EVENT = "TAG_DELETED_EVENT";
    String PROBLEM_DELETED_EVENT = "PROBLEM_DELETED_EVENT";
    String SUBMITTED_EVENT = "SUBMITTED_EVENT";

    int MAX_URL_LENGTH = 1024;
    int MAX_CASES_SIZE = 64;
    int MAX_ANSWER_COUNT = 64;
    int MAX_GENERIC_NAME_LENGTH = 50;
    int MAX_GENERIC_TEXT_LENGTH = 1024;
    int MAX_CODE_LENGTH = 65535;

    String REDIS_DOMAIN_EVENT_CONSUMER_GROUP = "domain.event.group";
    String REDIS_WEBHOOK_CONSUMER_GROUP = "webhook.group";
    String REDIS_NOTIFICATION_CONSUMER_GROUP = "notification.group";

}
