package im.wangbo.wbprime.canshubook


import spock.lang.Shared
import spock.lang.Specification

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class Visitors_ForBoolean_Spec extends Specification {
    @Shared
    Visitor<Boolean> visitor = Visitors.forBoolean()

    def "test from null"() {
        given:
        Acceptor acceptor = Acceptor.ofNull()
        when:
        def opt = acceptor.accept(visitor)
        then:
        !opt.isPresent()
    }

    def "test from boolean"() {
        given:
        Acceptor acceptor = Acceptor.ofBoolean(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == r
        } else {
            assert !opt.isPresent()
        }

        where:
        v     | has  | r
        true  | true | true
        false | true | false
    }

    def "test from long"() {
        given:
        Acceptor acceptor = Acceptor.ofLong(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == r
        } else {
            assert !opt.isPresent()
        }

        where:
        v                 | has   | r
        1                 | true  | true
        0                 | true  | false
        -1                | false | false
        Integer.MAX_VALUE | false | false
        Integer.MIN_VALUE | false | false
        Long.MAX_VALUE    | false | false
        Long.MIN_VALUE    | false | false
    }

    def "test from double"() {
        given:
        Acceptor acceptor = Acceptor.ofDouble(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == r
        } else {
            assert !opt.isPresent()
        }

        where:
        v                        | has   | r
        1.0                      | true  | true
        0                        | true | false
        -1.0                     | false | false
        0.1                      | false | false
        -0.1                     | false | false
        Double.MAX_VALUE         | false | false
        Double.MIN_VALUE         | false | false
        Double.POSITIVE_INFINITY | false | false
        Double.NEGATIVE_INFINITY | false | false
        Double.NaN               | false | false
        Double.MIN_NORMAL        | false | false
    }

    def "test from string"() {
        given:
        Acceptor acceptor = Acceptor.ofString(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == r
        } else {
            assert !opt.isPresent()
        }

        where:
        v       | has   | r
        ""      | false | true
        "Y"     | true  | true
        "y"     | true  | true
        "N"     | true  | false
        "n"     | true  | false
        "YES"   | true  | true
        "yES"   | false | true
        "YeS"   | false | true
        "yeS"   | false | true
        "YEs"   | false | true
        "yEs"   | false | true
        "Yes"   | false | true
        "yes"   | true  | true
        "NO"    | true  | false
        "nO"    | false | true
        "No"    | false | true
        "no"    | true  | false
        "TRUE"  | true  | true
        "tRUE"  | false | true
        "true"  | true  | true
        "True"  | false | true
        "FALSE" | true  | false
        "fALSE" | false | false
        "false" | true  | false
        "False" | false | false
    }
}
