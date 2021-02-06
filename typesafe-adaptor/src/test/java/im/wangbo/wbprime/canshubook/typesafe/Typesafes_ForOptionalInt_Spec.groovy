package im.wangbo.wbprime.canshubook.typesafe


import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import im.wangbo.wbprime.canshubook.Configs
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
class Typesafes_ForOptionalInt_Spec extends Specification {
    SampleData sampleData

    Config config;

    void setup() {
        sampleData = SampleData.randomize()

        config = ConfigFactory.parseReader(new StringReader(sampleData.toJson()))
    }

    def "test get empty string as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getEmptyStringKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get string as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getStringKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    @Unroll
    def "test get int as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getIntKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        opt.isPresent()

        def v = opt.getAsInt()
        v == sampleData.getIntVal()
        where:
        i << (1..100)
    }

    @Unroll
    def "test get long as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getLongKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        opt.isPresent()

        def v = opt.getAsInt()
        v == sampleData.getLongVal()

        where:
        i << (1..100)
    }

    @Unroll
    def "test get float as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getFloatKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        if ((int) sampleData.getFloatVal() == sampleData.getFloatVal()) {
            opt.isPresent()

            opt.getAsInt() == sampleData.getFloatVal()
        } else {
            !opt.isPresent()
        }

        where:
        i << (1..100)
    }

    @Unroll
    def "test get bool as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getBoolKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get list as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getListKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get map as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getMapKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get absent value as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getAbsentKey(), Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }
}
