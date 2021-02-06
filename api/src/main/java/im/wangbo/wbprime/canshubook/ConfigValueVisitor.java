package im.wangbo.wbprime.canshubook;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public interface ConfigValueVisitor<T> {
    static <T> ConfigValueVisitor<T> doNothing() {
        return Configs.doNothingVisitor();
    }

    default Optional<T> visitNull() {
        return Optional.empty();
    }

    default Optional<T> visitBoolean(final boolean v) {
        return Optional.empty();
    }

    default Optional<T> visitString(final String v) {
        return Optional.empty();
    }

    default Optional<T> visitIntegerNumber(final long v) {
        return Optional.empty();
    }

    default Optional<T> visitFloatingNumber(final BigDecimal v) {
        return Optional.empty();
    }
}
