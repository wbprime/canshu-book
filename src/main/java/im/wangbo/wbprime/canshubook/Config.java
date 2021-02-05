package im.wangbo.wbprime.canshubook;

import com.google.common.collect.ImmutableList;

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
public interface Config {
    interface Key {
        Key resolve(final String key);

        Key parent();

        CharSequence segment();

        ImmutableList<CharSequence> segments();

        default int depth() {
            return segments().size();
        }
    }

    interface Value {
        ValueType type();

        Optional<BooleanValue> asBoolean();

        Optional<StringValue> asString();

        Optional<NumberValue> asNumber();

        Optional<ListValue> asList();

        Optional<MapValue> asMap();

        <T> Optional<T> as(final Class<? extends T> clz);
    }

    enum ValueType {
        MAP, LIST, NUMBER, BOOLEAN, STRING
    }

    interface NumberValue extends Value {
        BigDecimal get();

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

    interface BooleanValue extends Value {
        boolean get();
    }

    interface StringValue extends Value {
        String get();
    }

    interface ListValue extends Value, List<Value> {

    }

    interface MapValue extends Value, Map<Key, Value> {
    }

}
