package im.wangbo.wbprime.canshubook;

import com.google.auto.value.AutoValue;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import im.wangbo.wbprime.canshubook.spi.KeyFactory;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

/**
 * A {@link KeyFactory} to parse {@link im.wangbo.wbprime.canshubook.Configs.Key} from string with
 * customized separator and case sensitivity strategy.
 * <p>
 * Given separator ".":
 * <ol>
 * <li> "im.wangbo.wbprime" => ["im", "wangbo", "wbprime"] </li>
 * <li> ".wangbo.wbprime" => ["wangbo", "wbprime"] </li>
 * <li> ".wangbo." => ["wangbo"] </li>
 * <li> ".wangbo.." => ["wangbo"] </li>
 * </ol>
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public final class StdKeyFactory extends KeyFactory {
    private final KeySpec spec;
    private final AbstractKey root;

    public StdKeyFactory(final KeySpec spec) {
        this.spec = spec;
        this.root = new RootKey(this);
    }

    @Override
    public Configs.Key root() {
        return root;
    }

    @Override
    public Configs.Key create(final String s) {
        return resolve(root, s);
    }

    @Override
    public String toString() {
        return "StdKeyFactory{" +
                "spec=" + spec +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StdKeyFactory that = (StdKeyFactory) o;
        return spec.equals(that.spec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spec);
    }

    abstract static class AbstractKey implements Configs.Key {
        private final StdKeyFactory factory;

        AbstractKey(final StdKeyFactory factory) {
            this.factory = factory;
        }

        final StdKeyFactory factory() {
            return factory;
        }

        abstract void collectSegments(final ImmutableList.Builder<CharSequence> builder);

        @Override
        public final Configs.Key resolve(final String key) {
            return factory.resolve(this, key);
        }

        @Override
        public final String toString() {
            final StringBuilder sb = new StringBuilder("Key {|>");
            Joiner.on("->").appendTo(sb, segments());
            return sb.append("}").toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !AbstractKey.class.isAssignableFrom(o.getClass())) return false;
            AbstractKey that = (AbstractKey) o;
            return Objects.equals(factory.spec, that.factory.spec) && Objects.equals(segments(), that.segments());
        }

        @Override
        public int hashCode() {
            return Objects.hash(segments());
        }
    }

    static class RootKey extends AbstractKey implements Configs.Key {
        RootKey(final StdKeyFactory factory) {
            super(factory);
        }

        @Override
        public Configs.Key parent() {
            return this;
        }

        @Override
        public CharSequence segment() {
            return "";
        }

        @Override
        void collectSegments(final ImmutableList.Builder<CharSequence> builder) {
            // Do nothing
        }

        @Override
        public ImmutableList<CharSequence> segments() {
            return ImmutableList.of();
        }

        @Override
        public int depth() {
            return 0;
        }
    }

    static class KeyImpl extends AbstractKey implements Configs.Key {
        private final AbstractKey ancestor;

        private final String keyStr;
        private final OpenClose range; // [inclusive, exclusive)

        private transient volatile ImmutableList<CharSequence> cachedSegments;

        public KeyImpl(final StdKeyFactory factory, AbstractKey ancestor, String keyStr, OpenClose range) {
            super(factory);
            this.ancestor = ancestor;
            this.keyStr = keyStr;
            this.range = range;
        }

        @Override
        public Configs.Key parent() {
            return factory().resolve(ancestor, keyStr, range.left());
        }

        @Override
        void collectSegments(final ImmutableList.Builder<CharSequence> builder) {
            ancestor.collectSegments(builder);

            factory().each(keyStr, range.left(), (str, r) -> builder.add(str.subSequence(r.left(), r.right())));

            builder.add(keyStr.subSequence(range.left(), range.right()));
        }

        @Override
        public final ImmutableList<CharSequence> segments() {
            ImmutableList<CharSequence> list = cachedSegments;
            if (null == list) {
                synchronized (this) {
                    list = cachedSegments;
                    if (null == list) {
                        final ImmutableList.Builder<CharSequence> builder = ImmutableList.builder();
                        collectSegments(builder);
                        cachedSegments = list = builder.build();
                    }
                }
            }
            return list;
        }

        @Override
        public String segment() {
            return keyStr.substring(range.left(), range.right());
        }

        @Override
        public int depth() {
            return segments().size();
        }
    }

    private Configs.Key resolve(final AbstractKey ancestor, final String key) {
        final String str = spec.caseMapper().apply(key);
        return resolve(ancestor, str, str.length());
    }

    private Configs.Key resolve(final AbstractKey ancestor, final String key, final int right) {
        final OpenClose found = findPrevious(key, spec.separator(), right);
        if (found.left() != -1) {
            return new KeyImpl(this, ancestor, key, found);
        } else {
            return ancestor;
        }
    }

    private void each(final String str, final int right, final BiConsumer<String, OpenClose> consumer) {
        OpenClose found = OpenClose.of(right, str.length());
        do {
            found = findPrevious(str, spec.separator(), found.left());
            if (found.left() == -1) break;

            consumer.accept(str, found);
        } while (true);
    }

    /**
     * @param start exclusively
     */
    private static OpenClose findPrevious(final String str, final String sep, final int start) {
        int pbeg = start;
        int pend;
        do {
            pend = pbeg;
            pbeg = str.lastIndexOf(sep, pbeg - 1);
        } while (-1 != pbeg && pend - pbeg <= sep.length());

        if (pbeg != -1) {
            return OpenClose.of(pbeg + sep.length(), pend);
        } else {
            if (pend != 0) {
                return OpenClose.of(0, pend);
            } else {
                return OpenClose.of(pbeg, pend);
            }
        }
    }

    @AutoValue
    static abstract class OpenClose {
        abstract int left();

        abstract int right();

        public static OpenClose of(int l, int r) {
            return new AutoValue_StdKeyFactory_OpenClose(l, r);
        }
    }

    static abstract class KeySpec {
        abstract String separator();

        abstract boolean caseSensitive();

        abstract UnaryOperator<String> caseMapper();
    }

    @AutoValue
    static abstract class CaseSensitiveKeySpec extends KeySpec {
        @Override
        abstract String separator();

        final boolean caseSensitive() {
            return true;
        }

        final UnaryOperator<String> caseMapper() {
            return UnaryOperator.identity();
        }

        public static CaseSensitiveKeySpec of(String separator) {
            return new AutoValue_StdKeyFactory_CaseSensitiveKeySpec(separator);
        }
    }

    @AutoValue
    static abstract class CaseInsensitiveKeySpec extends KeySpec {
        @Override
        abstract String separator();

        final boolean caseSensitive() {
            return false;
        }

        final UnaryOperator<String> caseMapper() {
            return String::toLowerCase;
        }

        public static CaseInsensitiveKeySpec of(String separator) {
            Preconditions.checkArgument(!separator.isEmpty());
            return new AutoValue_StdKeyFactory_CaseInsensitiveKeySpec(separator.toLowerCase());
        }
    }
}
