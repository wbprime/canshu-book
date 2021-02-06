package im.wangbo.wbprime.canshubook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

    public interface Key {
        Key resolve(final String key);

        Key parent();

        CharSequence segment();

        ImmutableList<CharSequence> segments();

        default int depth() {
            return segments().size();
        }
    }

    public interface KeyFactory {
        Key root();

        Key create(final String str);
    }

    public enum ValueType {
        MAP, LIST, NUMBER, BOOLEAN, STRING
    }

    public interface Value {
        ValueType type();

        Optional<BooleanValue> asBoolean();

        Optional<StringValue> asString();

        Optional<NumberValue> asNumber();

        Optional<ListValue> asList();

        Optional<MapValue> asMap();

        default <T> Optional<T> as(final Class<? extends T> clz) {
            return Optional.empty();
        }
    }

    public interface NumberValue extends Value {
        BigDecimal get();

        @Override
        default ValueType type() {
            return ValueType.NUMBER;
        }

        @Override
        default Optional<BooleanValue> asBoolean() {
            return Optional.empty();
        }

        @Override
        default Optional<StringValue> asString() {
            return Optional.empty();
        }

        @Override
        default Optional<NumberValue> asNumber() {
            return Optional.of(this);
        }

        @Override
        default Optional<ListValue> asList() {
            return Optional.empty();
        }

        @Override
        default Optional<MapValue> asMap() {
            return Optional.empty();
        }

        default double getDouble() {
            return get().doubleValue();
        }

        default OptionalInt asInt() {
            try {
                return OptionalInt.of(get().intValueExact());
            } catch (ArithmeticException ex) {
                return OptionalInt.empty();
            }
        }

        default OptionalLong asLong() {
            try {
                return OptionalLong.of(get().longValueExact());
            } catch (ArithmeticException ex) {
                return OptionalLong.empty();
            }
        }
    }

    public interface BooleanValue extends Value {
        boolean get();

        @Override
        default ValueType type() {
            return ValueType.BOOLEAN;
        }

        @Override
        default Optional<BooleanValue> asBoolean() {
            return Optional.of(this);
        }

        @Override
        default Optional<StringValue> asString() {
            return Optional.empty();
        }

        @Override
        default Optional<NumberValue> asNumber() {
            return Optional.empty();
        }

        @Override
        default Optional<ListValue> asList() {
            return Optional.empty();
        }

        @Override
        default Optional<MapValue> asMap() {
            return Optional.empty();
        }
    }

    public interface StringValue extends Value {
        String get();

        @Override
        default ValueType type() {
            return ValueType.STRING;
        }

        @Override
        default Optional<BooleanValue> asBoolean() {
            return Optional.empty();
        }

        @Override
        default Optional<StringValue> asString() {
            return Optional.of(this);
        }

        @Override
        default Optional<NumberValue> asNumber() {
            return Optional.empty();
        }

        @Override
        default Optional<ListValue> asList() {
            return Optional.empty();
        }

        @Override
        default Optional<MapValue> asMap() {
            return Optional.empty();
        }
    }

    public interface ListValue extends Value, List<Value> {
        List<Value> get();

        @Override
        default ValueType type() {
            return ValueType.LIST;
        }

        @Override
        default Optional<BooleanValue> asBoolean() {
            return Optional.empty();
        }

        @Override
        default Optional<StringValue> asString() {
            return Optional.empty();
        }

        @Override
        default Optional<NumberValue> asNumber() {
            return Optional.empty();
        }

        @Override
        default Optional<ListValue> asList() {
            return Optional.of(this);
        }

        @Override
        default Optional<MapValue> asMap() {
            return Optional.empty();
        }
    }

    public interface MapValue extends Value, Map<Key, Value> {
        ImmutableMap<Key, Value> get();

        @Override
        default ValueType type() {
            return ValueType.MAP;
        }

        @Override
        default Optional<BooleanValue> asBoolean() {
            return Optional.empty();
        }

        @Override
        default Optional<StringValue> asString() {
            return Optional.empty();
        }

        @Override
        default Optional<NumberValue> asNumber() {
            return Optional.empty();
        }

        @Override
        default Optional<ListValue> asList() {
            return Optional.empty();
        }

        @Override
        default Optional<MapValue> asMap() {
            return Optional.of(this);
        }
    }

    //
    // implementations
    //

    public static BooleanValue value(final boolean v) {
        return ConfigValues.of(v);
    }

    public static NumberValue value(final int v) {
        return ConfigValues.of(v);
    }

    public static NumberValue value(final long v) {
        return ConfigValues.of(v);
    }

    public static NumberValue value(final BigDecimal v) {
        return ConfigValues.of(v);
    }

    public static StringValue value(final String v) {
        return ConfigValues.of(v);
    }

    public static ListValue value(final Iterable<? extends Configs.Value> v) {
        return ConfigValues.of(v);
    }

    public static MapValue value(final Map<Configs.Key, ? extends Configs.Value> v) {
        return ConfigValues.of(v);
    }

    public static Configs.KeyFactory forDotted() {
        return ConfigKeys.of(".", true);
    }

    public static Configs.KeyFactory forDashDashed() {
        return ConfigKeys.of("__", false);
    }
}
