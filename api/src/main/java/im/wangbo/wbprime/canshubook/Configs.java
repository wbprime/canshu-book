package im.wangbo.wbprime.canshubook;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public final class Configs {
    private Configs() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    private static final ConfigValueVisitor<?> DO_NOTHING = new ConfigValueVisitor<Object>() {
    };

    @SuppressWarnings("unchecked")
    static <T> ConfigValueVisitor<T> doNothingVisitor() {
        return (ConfigValueVisitor<T>) Configs.DO_NOTHING;
    }


    public static ConfigValueVisitor<Boolean> forBoolean() {
        return ForBoolean.V;
    }

    private static class ForBoolean implements ConfigValueVisitor<Boolean> {
        static final ForBoolean V = new ForBoolean();

        @Override
        public Optional<Boolean> visitString(String c) {
            if ("1".equals(c) || "Y".equalsIgnoreCase(c) || "true".equalsIgnoreCase(c) || "yes".equalsIgnoreCase(c)) {
                return Optional.of(Boolean.TRUE);
            } else if ("0".equals(c) || "N".equalsIgnoreCase(c) || "false".equalsIgnoreCase(c) || "no".equalsIgnoreCase(c)) {
                return Optional.of(Boolean.FALSE);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Boolean> visitIntegerNumber(long v) {
            if (1L == v) {
                return Optional.of(Boolean.TRUE);
            } else if (0L == v) {
                return Optional.of(Boolean.FALSE);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Boolean> visitFloatingNumber(BigDecimal c) {
            try {
                return visitIntegerNumber(c.longValueExact());
            } catch (ArithmeticException ex) {
                return Optional.empty();
            }
        }
    }

    public static ConfigValueVisitor<OptionalInt> forOptionalInt() {
        return ForOptionalInt.V;
    }

    private static class ForOptionalInt implements ConfigValueVisitor<OptionalInt> {
        static final ForOptionalInt V = new ForOptionalInt();

        @Override
        public Optional<OptionalInt> visitString(String c) {
            try {
                return Optional.of(OptionalInt.of(Integer.parseInt(c)));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<OptionalInt> visitIntegerNumber(long v) {
            final int casted = (int) v;
            if (casted == v) {
                return Optional.of(OptionalInt.of(casted));
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<OptionalInt> visitFloatingNumber(BigDecimal c) {
            try {
                return Optional.of(OptionalInt.of(c.intValueExact()));
            } catch (ArithmeticException ex) {
                return Optional.empty();
            }
        }
    }

    public static ConfigValueVisitor<Integer> forInteger() {
        return ForInteger.V;
    }

    private static class ForInteger implements ConfigValueVisitor<Integer> {
        static final ForInteger V = new ForInteger();

        @Override
        public Optional<Integer> visitString(String c) {
            try {
                return Optional.of(Integer.valueOf(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Integer> visitIntegerNumber(long v) {
            final int casted = (int) v;
            if (casted == v) {
                return Optional.of(casted);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Integer> visitFloatingNumber(BigDecimal c) {
            try {
                return Optional.of(c.intValueExact());
            } catch (ArithmeticException ex) {
                return Optional.empty();
            }
        }
    }

    public static ConfigValueVisitor<OptionalLong> forOptionalLong() {
        return ForOptionalLong.V;
    }

    private static class ForOptionalLong implements ConfigValueVisitor<OptionalLong> {
        static final ForOptionalLong V = new ForOptionalLong();

        @Override
        public Optional<OptionalLong> visitString(String c) {
            try {
                return Optional.of(OptionalLong.of(Long.parseLong(c)));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<OptionalLong> visitIntegerNumber(long v) {
            return Optional.of(OptionalLong.of(v));
        }

        @Override
        public Optional<OptionalLong> visitFloatingNumber(BigDecimal c) {
            try {
                return Optional.of(OptionalLong.of(c.longValueExact()));
            } catch (ArithmeticException ex) {
                return Optional.empty();
            }
        }
    }

    public static ConfigValueVisitor<Long> forLong() {
        return ForLong.V;
    }

    private static class ForLong implements ConfigValueVisitor<Long> {
        static final ForLong V = new ForLong();

        @Override
        public Optional<Long> visitString(String c) {
            try {
                return Optional.of(Long.valueOf(c));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Long> visitIntegerNumber(long v) {
            return Optional.of(v);
        }

        @Override
        public Optional<Long> visitFloatingNumber(BigDecimal c) {
            try {
                return Optional.of(c.longValueExact());
            } catch (ArithmeticException ex) {
                return Optional.empty();
            }
        }
    }

    public static ConfigValueVisitor<String> forString() {
        return ForString.V;
    }

    private static class ForString implements ConfigValueVisitor<String> {
        static final ForString V = new ForString();

        @Override
        public Optional<String> visitString(String c) {
            return Optional.of(c);
        }

        @Override
        public Optional<String> visitIntegerNumber(long v) {
            return Optional.of(String.valueOf(v));
        }

        @Override
        public Optional<String> visitFloatingNumber(BigDecimal v) {
            return Optional.of(v.toPlainString());
        }

        @Override
        public Optional<String> visitBoolean(boolean c) {
            return Optional.of(c ? "true" : "false");
        }
    }
}
