package im.wangbo.wbprime.canshubook.typesafe;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import im.wangbo.wbprime.canshubook.Visitor;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public final class Typesafes {
    private Typesafes() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static <T> Optional<T> asOptional(final Config c, final String key, final Visitor<T> visitor) {
        if (c.hasPathOrNull(key)) {
            final ConfigValue v = c.getValue(key);
            switch (v.valueType()) {
                case NUMBER: {
                    final BigDecimal num = new BigDecimal(c.getString(key));
                    try {
                        return visitor.visitIntegerNumber(num.longValueExact());
                    } catch (ArithmeticException ex) {
                        return visitor.visitFloatingNumber(num.doubleValue());
                    }
                }
                case BOOLEAN:
                    return visitor.visitBoolean(c.getBoolean(key));
                case STRING:
                    return visitor.visitString(c.getString(key));
                case NULL:
                    return visitor.visitNull();
                case OBJECT: // falling through
                case LIST:   // falling through
                default:
                    return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
