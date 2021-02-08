package im.wangbo.wbprime.canshubook.typesafe


import com.typesafe.config.ConfigFactory
import im.wangbo.wbprime.canshubook.Visitor
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.ThreadLocalRandom

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class Typesafes_SimpleKey_FromProperties_Spec extends Specification {
    @Shared
    String key = "k123"

    Properties properties

    def setup() {
        properties = new Properties()
    }

    def "test absent value"() {
        given:
        def visitor = Mock(Visitor)

        def config = ConfigFactory.parseProperties(properties)

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        0 * _
    }

    def "test boolean value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextBoolean()

        properties.setProperty(key, String.valueOf(val))
        def config = ConfigFactory.parseProperties(properties)

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitString(String.valueOf(val))
        0 * _
    }

    def "test integer value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextInt()

        properties.setProperty(key, String.valueOf(val))
        def config = ConfigFactory.parseProperties(properties)

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitString(String.valueOf(val))
        0 * _

        where:
        repeated << (1..10)
    }

    def "test float value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextLong(Integer.MAX_VALUE) / 1000.0

        properties.setProperty(key, String.valueOf(val))
        def config = ConfigFactory.parseProperties(properties)

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitString(String.valueOf(val))
        0 * _

        where:
        repeated << (1..10)
    }

    def "test string value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextBoolean() ? "" : UUID.randomUUID().toString()

        properties.setProperty(key, val)
        def config = ConfigFactory.parseProperties(properties)

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitString(val)
        0 * _

        where:
        repeated << (1..10)
    }
}
