package im.wangbo.wbprime.canshubook;

import java.util.Optional;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public interface Visitor<T> {
    static <T> Visitor<T> doNothing() {
        return Visitors.doNothingVisitor();
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

    default Optional<T> visitFloatingNumber(final double v) {
        return Optional.empty();
    }
}
