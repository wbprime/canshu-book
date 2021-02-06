package im.wangbo.wbprime.canshubook.typesafe


import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import im.wangbo.wbprime.canshubook.Configs
import spock.lang.Specification
import spock.lang.Unroll

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
class Typesafes_ForOptionalLong_Spec extends Specification {
    SampleData sampleData

    Config config;

    void setup() {
        sampleData = SampleData.randomize()

        config = ConfigFactory.parseReader(new StringReader(sampleData.toString()))
    }

    def "test get empty string as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getEmptyStringKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get string as long repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getStringKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    @Unroll
    def "test get int as long repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getIntKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        opt.isPresent()

        def v = opt.getAsLong()
        v == sampleData.getIntVal()
        where:
        i << (1..100)
    }

    @Unroll
    def "test get long as long repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getLongKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        opt.isPresent()

        def v = opt.getAsLong()
        v == sampleData.getLongVal()

        where:
        i << (1..100)
    }

    @Unroll
    def "test get float as long repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getFloatKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        if ((long) sampleData.getFloatVal() == sampleData.getFloatVal()) {
            opt.isPresent()

            opt.getAsLong() == sampleData.getFloatVal()
        } else {
            !opt.isPresent()
        }

        where:
        i << (1..100)
    }

    @Unroll
    def "test get bool as long repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getBoolKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get list as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getListKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get map as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getMapKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get absent value as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getAbsentKey(), Configs.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }
}
