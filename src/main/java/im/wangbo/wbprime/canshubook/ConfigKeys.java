package im.wangbo.wbprime.canshubook;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
final class ConfigKeys {
    private ConfigKeys() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    static StdKeyFactory of(final String sep, final boolean caseSensitive) {
        return new StdKeyFactory(
                caseSensitive ? StdKeyFactory.CaseSensitiveKeySpec.of(sep) : StdKeyFactory.CaseInsensitiveKeySpec.of(sep)
        );
    }
}
