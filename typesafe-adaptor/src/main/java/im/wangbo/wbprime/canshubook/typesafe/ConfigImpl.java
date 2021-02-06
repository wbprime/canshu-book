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

    ConfigImpl(final com.typesafe.config.Config delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<Config> get(final String k) {
        if (delegate.hasPath(k)) {
            final ConfigValue cv = delegate.getValue(k);
            if (cv.valueType() != ConfigValueType.LIST && cv.valueType() != ConfigValueType.NULL) {
                return Optional.of(new ConfigImpl(delegate.atPath(k)));
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Config> list(String k) {
        if (delegate.hasPath(k)) {
            final ConfigValue cv = delegate.getValue(k);
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

    @Override
    public <T> Optional<T> as(final ConfigValueVisitor<T> visitor) {
        return Typesafes.asOptional(delegate, "", visitor);
    }
}
