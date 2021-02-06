package im.wangbo.wbprime.canshubook;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public interface Config {
    default OptionalInt getAsIntNumber(final String key) {
        return OptionalInt.empty();
    }

    default OptionalLong getAsLongNumber(final String key) {
        return OptionalLong.empty();
    }

    default OptionalDouble getAsDoubleNumber(final String key) {
        return OptionalDouble.empty();
    }

    Optional<Config> get(final String key);

    <T> Optional<T> as(final Class<T> clz, final ConfigValueVisitor<T> visitor);

    default Optional<String> getAsString(final String key) {
        return as(String.class, Configs.doNothingVisitor());
    }

    default Optional<Integer> getAsInteger(final String key) {
        return as(Integer.class, Configs.doNothingVisitor());
    }

    default Optional<Long> getAsLong(final String key) {
        return as(Long.class, Configs.doNothingVisitor());
    }

    default Optional<Float> getAsFloat(final String key) {
        return as(Float.class, Configs.doNothingVisitor());
    }

    default Optional<Double> getAsDouble(final String key) {
        return as(Double.class, Configs.doNothingVisitor());
    }

    default Optional<BigDecimal> getAsBigDecimal(final String key) {
        return as(BigDecimal.class, Configs.doNothingVisitor());
    }

    default <T> Optional<List<T>> getAsList(final String key) {
        return Optional.empty();
    }

    default <T> Optional<Set<T>> getAsSet(final String key) {
        return Optional.empty();
    }

    default <K, V> Optional<Map<K, V>> getAsMap(final String key) {
        return Optional.empty();
    }
}
