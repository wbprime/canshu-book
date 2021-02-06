package im.wangbo.wbprime.canshubook.typesafe;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;
import im.wangbo.wbprime.canshubook.Config;
import im.wangbo.wbprime.canshubook.ConfigValueVisitor;

import java.util.List;
import java.util.Optional;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
final class ConfigImpl implements Config {
    private static final char SEP = '.';
    private static final CharMatcher DOT = CharMatcher.is(SEP);

    private final com.typesafe.config.Config delegate;
    private final String prefix;

    ConfigImpl(final com.typesafe.config.Config delegate) {
        this(delegate, "");
    }

    ConfigImpl(final com.typesafe.config.Config delegate, final String prefix) {
        this.delegate = delegate;
        this.prefix = prefix;
    }

    @Override
    public Optional<Config> get(final String k) {
        final String key = mapKey(prefix, k);
        if (delegate.hasPath(key)) {
            final ConfigValue cv = delegate.getValue(key);
            if (cv.valueType() != ConfigValueType.LIST && cv.valueType() != ConfigValueType.NULL) {
                return Optional.of(new ConfigImpl(delegate, key));
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Config> list(String k) {
        final String key = mapKey(prefix, k);
        if (delegate.hasPath(key)) {
            final ConfigValue cv = delegate.getValue(key);
            if (cv.valueType() == ConfigValueType.NULL) {
                return ImmutableList.of();
            } else if (cv.valueType() == ConfigValueType.LIST) {
                final ConfigList cl = (ConfigList) cv;

                if (!cl.isEmpty()) {
                    final ImmutableList.Builder<Config> builder = ImmutableList.builder();
                    for (ConfigValue it : cl) {
                        builder.add(new ConfigImpl(it.atKey("")));
                    }
                    return builder.build();
                }
            } else {
                return ImmutableList.of(new ConfigImpl(cv.atKey("")));
            }
        }

        return ImmutableList.of();
    }

    private static String mapKey(final String prefix, final String str) {
        return DOT.trimFrom(prefix + SEP + str);
    }

    @Override
    public <T> Optional<T> as(final ConfigValueVisitor<T> visitor) {
        return Typesafes.asOptional(delegate, mapKey(prefix, ""), visitor);
    }
}
