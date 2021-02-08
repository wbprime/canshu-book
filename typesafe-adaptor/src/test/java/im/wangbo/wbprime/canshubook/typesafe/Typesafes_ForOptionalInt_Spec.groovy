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
class Typesafes_ForOptionalInt_Spec extends Specification {
    SampleData sampleData

    Config config;

    void setup() {
        sampleData = SampleData.randomize()

        config = ConfigFactory.parseReader(new StringReader(sampleData.toJson()))
    }

    def "test get empty string as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getEmptyStringKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get string as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getStringKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    @Unroll
    def "test get int as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getIntKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
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
        def opt = Typesafes.asOptional(config, sampleData.getLongKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
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
        def opt = Typesafes.asOptional(config, sampleData.getFloatKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
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
        def opt = Typesafes.asOptional(config, sampleData.getBoolKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get list as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getListKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get map as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getMapKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get absent value as int"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getAbsentKey(), Visitors.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }
}
