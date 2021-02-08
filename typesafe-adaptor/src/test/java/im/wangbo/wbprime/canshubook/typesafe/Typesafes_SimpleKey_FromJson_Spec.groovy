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
public class Typesafes_SimpleKey_FromJson_Spec extends Specification {
    @Shared
    String key = "k123"

    def "test absent value"() {
        given:
        def visitor = Mock(Visitor)

        StringBuilder sb = new StringBuilder("{");
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        0 * _
    }

    def "test null value"() {
        given:
        def visitor = Mock(Visitor)

        StringBuilder sb = new StringBuilder("{");
        sb.append(String.format("\"%s\":null", key))
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitNull()
        0 * _
    }

    def "test boolean value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextBoolean()

        StringBuilder sb = new StringBuilder("{");
        sb.append(String.format("\"%s\":%s", key, val))
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitBoolean(val)
        0 * _
    }

    def "test integer value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextInt()

        StringBuilder sb = new StringBuilder("{");
        sb.append(String.format("\"%s\":%d", key, val))
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitIntegerNumber(val)
        0 * _

        where:
        repeated << (1..10)
    }

    def "test float value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextLong(Integer.MAX_VALUE) / 1000.0

        StringBuilder sb = new StringBuilder("{");
        sb.append(String.format("\"%s\":%.8f", key, val))
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitFloatingNumber(_) // NOTE here use wildcard to avoid test failure due to double accuracy
        0 * _

        where:
        repeated << (1..10)
    }

    def "test string value"() {
        given:
        def visitor = Mock(Visitor)
        def val = ThreadLocalRandom.current().nextBoolean() ? "" : UUID.randomUUID().toString()

        StringBuilder sb = new StringBuilder("{");
        sb.append(String.format("\"%s\":\"%s\"", key, val))
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        1 * visitor.visitString(val)
        0 * _

        where:
        repeated << (1..10)
    }

    def "test array value"() {
        given:
        def visitor = Mock(Visitor)

        StringBuilder sb = new StringBuilder("{");
        if (ThreadLocalRandom.current().nextBoolean())
            sb.append(String.format("\"%s\":[]", key))
        else
            sb.append(String.format("\"%s\":[\"%s\"]", key, key))
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        0 * _

        where:
        repeated << (1..10)
    }

    def "test object value"() {
        given:
        def visitor = Mock(Visitor)

        StringBuilder sb = new StringBuilder("{");
        if (ThreadLocalRandom.current().nextBoolean())
            sb.append(String.format("\"%s\":{}", key))
        else
            sb.append(String.format("\"%s\":{\"%s\":%d}", key, key, ThreadLocalRandom.current().nextInt()))
        def config = ConfigFactory.parseReader(new StringReader(sb.append("}").toString()))

        when:
        Typesafes.asOptional(config, key, visitor)

        then:
        0 * _

        where:
        repeated << (1..10)
    }
}
