package im.wangbo.wbprime.canshubook.typesafe


import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import im.wangbo.wbprime.canshubook.Visitors
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

        config = ConfigFactory.parseReader(new StringReader(sampleData.toJson()))
    }

    def "test get empty string as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getEmptyStringKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get string as long repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getStringKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    @Unroll
    def "test get int as long repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getIntKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
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
        def opt = Typesafes.asOptional(config, sampleData.getLongKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
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
        def opt = Typesafes.asOptional(config, sampleData.getFloatKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
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
        def opt = Typesafes.asOptional(config, sampleData.getBoolKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get list as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getListKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get map as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getMapKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }

    def "test get absent value as long"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getAbsentKey(), Visitors.forOptionalLong()).orElse(OptionalLong.empty())
        then:
        !opt.isPresent()
    }
}
