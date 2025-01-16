package org.ricky.common.domain;

import lombok.Getter;
import org.ricky.common.utils.ValidationUtils;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.ricky.common.utils.ValidationUtils.requireNonNull;
import static org.springframework.util.ObjectUtils.nullSafeHashCode;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className Pair
 * @desc
 */
@Getter
public final class Pair<S, T> {

    private final S first;
    private final T second;

    private Pair(S first, T second) {
        requireNonNull(first, "First must not be null");
        requireNonNull(second, "Second must not be null");
        this.first = first;
        this.second = second;
    }

    public static <S, T> Pair<S, T> of(S first, T second) {
        return new Pair<>(first, second);
    }

    public static <S, T> Collector<Pair<S, T>, ?, Map<S, T>> toMap() {
        return Collectors.toMap(Pair::getFirst, Pair::getSecond);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return ValidationUtils.equals(first, pair.first) && ValidationUtils.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        int result = nullSafeHashCode(this.first);
        result = 31 * result + nullSafeHashCode(this.second);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s->%s", this.first, this.second);
    }

}
