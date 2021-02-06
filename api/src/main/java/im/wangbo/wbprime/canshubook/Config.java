package im.wangbo.wbprime.canshubook;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public interface Config {
    Optional<Config> get(final String key);

    List<Config> list(final String key);

    <T> Optional<T> as(final Class<T> clz, final ConfigValueVisitor<T> visitor);

    default OptionalInt getAsIntNumber(final String str) {
        return as(OptionalInt.class, Configs.forOptionalInt()).orElse(OptionalInt.empty());
    }

    default OptionalLong getAsLongNumber(final String str) {
        return as(OptionalLong.class, Configs.forOptionalLong()).orElse(OptionalLong.empty());
    }

    default OptionalDouble getAsDoubleNumber(final String str) {
        return as(OptionalDouble.class, Configs.forOptionalDouble()).orElse(OptionalDouble.empty());
    }

    default Optional<String> getAsString(final String str) {
        return as(String.class, Configs.forString());
    }

    default Optional<Integer> getAsInteger(final String str) {
        return as(Integer.class, Configs.forInteger());
    }

    default Optional<Long> getAsLong(final String str) {
        return as(Long.class, Configs.forLong());
    }

    default Optional<Double> getAsDouble(final String str) {
        return as(Double.class, Configs.forDouble());
    }

    default Optional<BigDecimal> getAsBigDecimal(final String str) {
        return as(BigDecimal.class, Configs.forBigDecimal());
    }
}
