package im.wangbo.wbprime.canshubook.spi;

import im.wangbo.wbprime.canshubook.Configs;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public abstract class KeyFactory {
    public abstract Configs.Key root();

    public abstract Configs.Key create(final String str);
}
