package im.wangbo.wbprime.canshubook;

import java.math.BigDecimal;
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
public final class Visitors {
    private Visitors() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    private static final Visitor<?> DO_NOTHING = new Visitor<Object>() {
    };

    @SuppressWarnings("unchecked")
    static <T> Visitor<T> doNothing() {
        return (Visitor<T>) Visitors.DO_NOTHING;
    }

    public static Visitor<Boolean> forBoolean() {
        return ForBoolean.V;
    }

    private static class ForBoolean implements Visitor<Boolean> {
        static final ForBoolean V = new ForBoolean();

        @Override
        public Optional<Boolean> visitBoolean(boolean v) {
            return Optional.of(v);
        }

        @Override
        public Optional<Boolean> visitString(final String c) {
            switch (c) {
                case "1":
                case "Y":
                case "y":
                case "YES":
                case "yes":
                case "TRUE":
                case "true":
                    return visitBoolean(true);
                case "0":
                case "N":
                case "n":
                case "NO":
                case "no":
                case "FALSE":
                case "false":
                    return visitBoolean(false);
                default:
                    return Optional.empty();
            }
        }

        @Override
        public Optional<Boolean> visitIntegerNumber(final long v) {
            if (1L == v) {
                return visitBoolean(true);
            } else if (0L == v) {
                return visitBoolean(false);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Boolean> visitFloatingNumber(final double c) {
            if (1.0 == c) {
                return visitBoolean(true);
            } else if (0.0 == c) {
                return visitBoolean(false);
            } else {
                return Optional.empty();
            }
        }
    }

    public static Visitor<OptionalInt> forOptionalInt() {
        return ForOptionalInt.V;
    }

    private static class ForOptionalInt implements Visitor<OptionalInt> {
        static final ForOptionalInt V = new ForOptionalInt();

        @Override
        public Optional<OptionalInt> visitString(final String c) {
            try {
                return visitFloatingNumber(Double.parseDouble(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<OptionalInt> visitIntegerNumber(final long c) {
            final int v = (int) c;
            if (v == c) {
                return visitInt(v);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<OptionalInt> visitFloatingNumber(final double c) {
            final int v = (int) c;
            if (v == c) {
                return visitInt(v);
            } else {
                return Optional.empty();
            }
        }

        private Optional<OptionalInt> visitInt(final int v) {
            return Optional.of(OptionalInt.of(v));
        }
    }

    public static Visitor<Integer> forInteger() {
        return ForInteger.V;
    }

    private static class ForInteger implements Visitor<Integer> {
        static final ForInteger V = new ForInteger();

        @Override
        public Optional<Integer> visitString(final String c) {
            try {
                return visitFloatingNumber(Double.parseDouble(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Integer> visitIntegerNumber(final long c) {
            final int v = (int) c;
            if (v == c) {
                return visitInt(v);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Integer> visitFloatingNumber(final double c) {
            final int v = (int) c;
            if (v == c) {
                return visitInt(v);
            } else {
                return Optional.empty();
            }
        }

        private Optional<Integer> visitInt(final int v) {
            return Optional.of(v);
        }
    }

    public static Visitor<OptionalLong> forOptionalLong() {
        return ForOptionalLong.V;
    }

    private static class ForOptionalLong implements Visitor<OptionalLong> {
        static final ForOptionalLong V = new ForOptionalLong();

        @Override
        public Optional<OptionalLong> visitString(final String c) {
            try {
                return visitFloatingNumber(Double.parseDouble(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<OptionalLong> visitIntegerNumber(final long v) {
            return Optional.of(OptionalLong.of(v));
        }

        @Override
        public Optional<OptionalLong> visitFloatingNumber(final double c) {
            final long v = (long) c;
            if (v == c) {
                return visitIntegerNumber(v);
            } else {
                return Optional.empty();
            }
        }
    }

    public static Visitor<Long> forLong() {
        return ForLong.V;
    }

    private static class ForLong implements Visitor<Long> {
        static final ForLong V = new ForLong();

        @Override
        public Optional<Long> visitString(final String c) {
            try {
                return visitFloatingNumber(Double.parseDouble(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Long> visitIntegerNumber(final long v) {
            return Optional.of(v);
        }

        @Override
        public Optional<Long> visitFloatingNumber(final double c) {
            final long v = (long) c;
            if (v == c) {
                return visitIntegerNumber(v);
            } else {
                return Optional.empty();
            }
        }
    }

    public static Visitor<OptionalDouble> forOptionalDouble() {
        return ForOptionalDouble.V;
    }

    private static class ForOptionalDouble implements Visitor<OptionalDouble> {
        static final ForOptionalDouble V = new ForOptionalDouble();

        @Override
        public Optional<OptionalDouble> visitString(final String c) {
            try {
                return visitFloatingNumber(Double.parseDouble(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<OptionalDouble> visitIntegerNumber(final long v) {
            return visitFloatingNumber(v);
        }

        @Override
        public Optional<OptionalDouble> visitFloatingNumber(final double c) {
            return Optional.of(OptionalDouble.of(c));
        }
    }

    public static Visitor<Double> forDouble() {
        return ForDouble.V;
    }

    private static class ForDouble implements Visitor<Double> {
        static final ForDouble V = new ForDouble();

        @Override
        public Optional<Double> visitString(final String c) {
            try {
                return visitFloatingNumber(Double.parseDouble(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Double> visitIntegerNumber(final long v) {
            return visitFloatingNumber(v);
        }

        @Override
        public Optional<Double> visitFloatingNumber(final double c) {
            return Optional.of(c);
        }
    }

    public static Visitor<BigDecimal> forBigDecimal() {
        return ForBigDecimal.V;
    }

    private static class ForBigDecimal implements Visitor<BigDecimal> {
        static final ForBigDecimal V = new ForBigDecimal();

        @Override
        public Optional<BigDecimal> visitString(final String c) {
            try {
                return visitBigDecimal(new BigDecimal(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<BigDecimal> visitIntegerNumber(final long v) {
            return visitBigDecimal(new BigDecimal(v));
        }

        @Override
        public Optional<BigDecimal> visitFloatingNumber(final double c) {
            try {
                return visitBigDecimal(new BigDecimal(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        private Optional<BigDecimal> visitBigDecimal(final BigDecimal v) {
            return Optional.of(v);
        }
    }

    public static Visitor<String> forString() {
        return ForString.V;
    }

    private static class ForString implements Visitor<String> {
        static final ForString V = new ForString();

        @Override
        public Optional<String> visitString(final String c) {
            return Optional.of(c);
        }

        @Override
        public Optional<String> visitIntegerNumber(final long v) {
            return visitString(String.valueOf(v));
        }

        @Override
        public Optional<String> visitFloatingNumber(final double v) {
            return visitString(String.valueOf(v));
        }

        @Override
        public Optional<String> visitBoolean(boolean c) {
            return Optional.of(c ? "true" : "false");
        }
    }
}
