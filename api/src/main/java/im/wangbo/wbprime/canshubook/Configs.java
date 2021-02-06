package im.wangbo.wbprime.canshubook;

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
}
