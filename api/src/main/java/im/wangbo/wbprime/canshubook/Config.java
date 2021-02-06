package im.wangbo.wbprime.canshubook;

import java.util.Optional;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public interface Config extends Configs.Value {
    @Override
    default Configs.ValueType type() {
        return Configs.ValueType.MAP;
    }

    @Override
    default Optional<Configs.BooleanValue> asBoolean() {
        return Optional.empty();
    }

    @Override
    default Optional<Configs.StringValue> asString() {
        return Optional.empty();
    }

    @Override
    default Optional<Configs.NumberValue> asNumber() {
        return Optional.empty();
    }

    @Override
    default Optional<Configs.ListValue> asList() {
        return Optional.empty();
    }

    @Override
    default Optional<Configs.MapValue> asMap() {
        return Optional.empty();
    }
}
