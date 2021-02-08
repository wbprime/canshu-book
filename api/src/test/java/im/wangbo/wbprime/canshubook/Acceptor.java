package im.wangbo.wbprime.canshubook;

import com.google.common.collect.ImmutableList;

import java.util.Optional;
import java.util.Random;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public interface Acceptor {
    default <T> Optional<T> accept(final Visitor<T> visitor) {
        return Optional.empty();
    }

    public static NullAcceptor ofNull() {
        return new NullAcceptor();
    }

    public static BooleanAcceptor ofBoolean(final boolean v) {
        return v ? BooleanAcceptor.TRUE : BooleanAcceptor.FALSE;
    }

    public static LongAcceptor ofLong(final long v) {
        return new LongAcceptor(v);
    }

    public static DoubleAcceptor ofDouble(final double v) {
        return new DoubleAcceptor(v);
    }

    public static StringAcceptor ofString(final String v) {
        return new StringAcceptor(v);
    }

    public static class NullAcceptor implements Acceptor {
        private NullAcceptor() {
        }

        @Override
        public <T> Optional<T> accept(Visitor<T> visitor) {
            return visitor.visitNull();
        }
    }

    public static class BooleanAcceptor implements Acceptor {
        public static final BooleanAcceptor TRUE = new BooleanAcceptor(true);
        public static final BooleanAcceptor FALSE = new BooleanAcceptor(false);

        public final boolean value;

        private BooleanAcceptor(final boolean y) {
            this.value = y;
        }

        @Override
        public <T> Optional<T> accept(Visitor<T> visitor) {
            return visitor.visitBoolean(value);
        }
    }

    public static class LongAcceptor implements Acceptor {
        public final long value;

        private LongAcceptor(long value) {
            this.value = value;
        }

        @Override
        public <T> Optional<T> accept(Visitor<T> visitor) {
            return visitor.visitIntegerNumber(value);
        }
    }

    public static class DoubleAcceptor implements Acceptor {
        public final double value;

        private DoubleAcceptor(double value) {
            this.value = value;
        }

        @Override
        public <T> Optional<T> accept(Visitor<T> visitor) {
            return visitor.visitFloatingNumber(value);
        }
    }

    public static class StringAcceptor implements Acceptor {
        public final String value;

        private StringAcceptor(String value) {
            this.value = value;
        }

        @Override
        public <T> Optional<T> accept(Visitor<T> visitor) {
            return visitor.visitString(value);
        }
    }
}
