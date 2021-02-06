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

    <T> Optional<T> as(final ConfigValueVisitor<T> visitor);

    default OptionalInt getAsIntNumber() {
        return as(Configs.forOptionalInt()).orElse(OptionalInt.empty());
    }

    default OptionalLong getAsLongNumber() {
        return as(Configs.forOptionalLong()).orElse(OptionalLong.empty());
    }

    default OptionalDouble getAsDoubleNumber() {
        return as(Configs.forOptionalDouble()).orElse(OptionalDouble.empty());
    }

    default Optional<String> getAsString() {
        return as(Configs.forString());
    }

    default Optional<Integer> getAsInteger() {
        return as(Configs.forInteger());
    }

    default Optional<Long> getAsLong() {
        return as(Configs.forLong());
    }

    default Optional<Double> getAsDouble() {
        return as(Configs.forDouble());
    }

    default Optional<BigDecimal> getAsBigDecimal() {
        return as(Configs.forBigDecimal());
    }
}
