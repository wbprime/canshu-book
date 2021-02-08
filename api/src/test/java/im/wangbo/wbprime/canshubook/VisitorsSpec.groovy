package im.wangbo.wbprime.canshubook

import spock.lang.Specification

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class VisitorsSpec extends Specification {
    def "test construction"() {
        when:
        new Visitors()
        then:
        thrown(UnsupportedOperationException)
    }

    def "test doNothing in Visitors"() {
        given:
        def visitor = Visitors.doNothing()
        expect:
        visitor instanceof Visitor
    }

    def "test doNothing in Visitor"() {
        given:
        def visitor = Visitor.doNothing()
        expect:
        visitor instanceof Visitor
    }

    def "test default visitor method"() {
        given:
        def visitor = Visitor.doNothing()

        expect:
        !visitor.visitNull()
        !visitor.visitBoolean(true).isPresent()
        !visitor.visitString("true").isPresent()
        !visitor.visitIntegerNumber(1).isPresent()
        !visitor.visitFloatingNumber(1.0).isPresent()
    }
}
