package im.wangbo.wbprime.canshubook.typesafe


import spock.lang.Specification

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class TypesafesSpec extends Specification {
    def "test constructor"() {
        when:
        new Typesafes()
        then:
        thrown(UnsupportedOperationException)
    }
}
