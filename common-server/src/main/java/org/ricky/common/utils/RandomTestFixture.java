package org.ricky.common.utils;

import com.apifan.common.random.source.AreaSource;
import com.apifan.common.random.source.OtherSource;
import com.apifan.common.random.source.PersonInfoSource;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ricky.common.utils.UUIDGenerator.newShortUuid;

public class RandomTestFixture {

    public static String rMobile() {
        return String.valueOf(nextLong(13000000000L, 19000000000L));
    }

    public static String rEmail() {
        return (PersonInfoSource.getInstance().randomEnglishName().split(" ")[0] + "@" + randomAlphabetic(rInt(3, 8)) + ".com").toLowerCase();
    }

    public static String rMobileOrEmail() {
        return rBool() ? rMobile() : rEmail();
    }

    public static String rPassword() {
        return randomAlphanumeric(10);
    }

    public static String rVerificationCode() {
        return randomNumeric(6);
    }

    public static String rNickname() {
        return rRawNickname() + randomAlphanumeric(10);
    }

    public static String rRawNickname() {
        return PersonInfoSource.getInstance().randomChineseName();
    }

    public static String rCompanyName() {
        return OtherSource.getInstance().randomCompanyName(AreaSource.getInstance().randomCity(""));
    }

    // // 排除掉省市县不全的
    // private static final Set<String> excludedProvinces = Set.of("台湾省", "香港", "澳门", "海南省", "新疆维吾尔自治区", "湖北省", "河南省", "广东省", "甘肃省");
    //
    // public static Address rAddress() {
    //     String provinceName;
    //     String cityName = null;
    //     String districtName = null;
    //
    //     List<Administrative> provinces = AdministrativeProvider.CHINA.getChild().stream()
    //             .filter(administrative -> !excludedProvinces.contains(administrative.getName()))
    //             .collect(toList());
    //
    //     Administrative province = provinces.get(nextInt(0, provinces.size()));
    //     provinceName = province.getName();
    //     List<Administrative> cities = province.getChild();
    //     if (isNotEmpty(cities)) {
    //         Administrative city = cities.get(nextInt(0, cities.size()));
    //         cityName = city.getName();
    //         List<Administrative> districts = city.getChild();
    //         if (isNotEmpty(districts)) {
    //             Administrative district = districts.get(nextInt(0, districts.size()));
    //             districtName = district.getName();
    //         }
    //     }
    //     return Address.builder()
    //             .province(provinceName)
    //             .city(cityName)
    //             .district(districtName)
    //             .address(rAddressDetail())
    //             .build();
    // }
    //
    // public static Geolocation rGeolocation() {
    //     return Geolocation.builder()
    //             .address(rAddress())
    //             .point(Geopoint.builder()
    //                     .longitude(nextFloat(74, 135))
    //                     .latitude(nextFloat(18, 53))
    //                     .build())
    //             .build();
    // }

    public static String rCustomId() {
        return newShortUuid();
    }

    public static boolean rBool() {
        return nextBoolean();
    }

    public static <T extends Enum<?>> T rEnumOf(Class<T> clazz) {
        int x = nextInt(0, clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static String rSentence(int maxLength) {
        if (maxLength < 5) {
            return RandomStringUtils.random(maxLength);
        }

        String sentence = OtherSource.getInstance().randomChinese(nextInt(1, 5000));
        if (sentence.length() > maxLength) {
            return sentence.substring(0, maxLength - 1).trim();
        }

        String trimed = sentence.trim();
        if (isBlank(trimed)) {
            return RandomStringUtils.random(maxLength);
        }

        return trimed;
    }

    public static int rInt(int minInclusive, int maxInclusive) {
        return nextInt(minInclusive, maxInclusive + 1);
    }

    public static String rUrl() {
        return "https://www." + randomAlphanumeric(10) + ".com";
    }

    public static String rColor() {
        return OtherSource.getInstance().randomHexColor();
    }

    public static String rDate() {
        // 最近5年
        LocalDate start = LocalDate.of(Year.now().getValue() - 5, Month.JANUARY, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDate.now());
        LocalDate randomDate = start.plusDays(new Random().nextInt((int) days + 1));
        return randomDate.toString();
    }

    public static String rTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static Instant rInstant() {
        // 最近5年
        return Instant.now().minusSeconds(rInt(0, 5 * 365 * 24 * 3600));
    }
}
