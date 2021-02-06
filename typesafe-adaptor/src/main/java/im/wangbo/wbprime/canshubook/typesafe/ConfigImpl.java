package im.wangbo.wbprime.canshubook.typesafe;

import com.google.common.base.CharMatcher;
import im.wangbo.wbprime.canshubook.Config;
import im.wangbo.wbprime.canshubook.ConfigValueVisitor;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class ConfigImpl implements Config {
    private static final char SEP = '.';
    private static final CharMatcher DOT = CharMatcher.is(SEP);

    private final com.typesafe.config.Config delegate;
    private final UnaryOperator<String> realKeyMapper;

    ConfigImpl(final com.typesafe.config.Config delegate) {
        this(delegate, UnaryOperator.identity());
    }

    ConfigImpl(final com.typesafe.config.Config delegate, final UnaryOperator<String> realKeyMapper) {
        this.delegate = delegate;
        this.realKeyMapper = realKeyMapper;
    }

    @Override
    public Optional<Config> get(final String k) {
        final String key = DOT.trimFrom(k);
        final String realKey = realKeyMapper.apply(key);
        if (delegate.hasPath(realKey)) {
            return Optional.of(new ConfigImpl(delegate, v -> realKeyMapper.apply(realKey + SEP + v)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public <T> Optional<T> as(final Class<T> clz, final ConfigValueVisitor<T> visitor) {
        return Typesafes.asOptional(delegate, realKeyMapper.apply(""), visitor);
    }
}
