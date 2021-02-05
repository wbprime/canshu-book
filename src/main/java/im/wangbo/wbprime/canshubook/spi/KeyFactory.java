package im.wangbo.wbprime.canshubook.spi;

import im.wangbo.wbprime.canshubook.Config;

import java.util.Optional;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public abstract class KeyFactory {
    public abstract Config.Key root();

    public abstract Config.Key create(final String str);
}
