package org.ricky.core.verification.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.verification.domain.VerificationCode;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.ricky.core.verification.domain.VerificationCodeTypeEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.ricky.common.utils.ValidationUtils.requireNonBlank;
import static org.ricky.common.utils.ValidationUtils.requireNonNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className MongoVerificationCodeRepository
 * @desc
 */
@Repository
@RequiredArgsConstructor
public class MongoVerificationCodeRepository extends MongoBaseRepository<VerificationCode> implements VerificationCodeRepository {

    @Override
    public Optional<VerificationCode> findValidOptional(String mobileOrEmail, String code, VerificationCodeTypeEnum type) {
        requireNonBlank(mobileOrEmail, "Mobile or email must not be blank.");
        requireNonBlank(code, "Code must not be blank.");
        requireNonNull(type, "Type must not be null.");

        Criteria criteria = where("code").is(code)
                .and("mobileOrEmail").is(mobileOrEmail)
                .and("type").is(type.name())
                .and("usedCount").lt(3)
                .and("createdAt").gte(Instant.now().minus(10, MINUTES));

        Query query = query(criteria).with(Sort.by("createdAt").descending());
        return Optional.ofNullable(mongoTemplate.findOne(query, VerificationCode.class));
    }

    @Override
    public void save(VerificationCode verificationCode) {
        super.save(verificationCode);
    }

    @Override
    public boolean existsWithinOneMinutes(String mobileOrEmail, VerificationCodeTypeEnum type) {
        requireNonBlank(mobileOrEmail, "Mobile or email must not be blank.");
        requireNonNull(type, "Type must not be null.");

        Query query = query(where("mobileOrEmail").is(mobileOrEmail)
                .and("type").is(type.name())
                .and("createdAt").gte(Instant.now().minus(1, MINUTES)));
        return mongoTemplate.exists(query, VerificationCode.class);
    }

    @Override
    public long totalCodeCountOfTodayFor(String mobileOrEmail) {
        requireNonBlank(mobileOrEmail, "Mobile or email must not be blank.");

        Instant beginOfToday = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Query query = query(where("mobileOrEmail").is(mobileOrEmail)
                .and("createdAt").gte(beginOfToday));
        return mongoTemplate.count(query, VerificationCode.class);
    }

    @Override
    public VerificationCode byId(String id) {
        return super.byId(id);
    }
}
