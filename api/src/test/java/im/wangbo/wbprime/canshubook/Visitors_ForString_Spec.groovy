package im.wangbo.wbprime.canshubook


import spock.lang.Shared
import spock.lang.Specification

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class Visitors_ForString_Spec extends Specification {
    @Shared
    Visitor<String> visitor = Visitors.forString()

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
        true  | true | "true"
        false | true | "false"
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
        v                       | has  | r
        1                       | true | "1"
        0                       | true | "0"
        -1                      | true | "-1"
        Integer.MAX_VALUE       | true | String.valueOf(Integer.MAX_VALUE)
        Integer.MIN_VALUE       | true | String.valueOf(Integer.MIN_VALUE)
        1L + Integer.MAX_VALUE  | true | String.valueOf(1L + Integer.MAX_VALUE)
        -1L + Integer.MIN_VALUE | true | String.valueOf(-1L + Integer.MIN_VALUE)
        Long.MAX_VALUE          | true | String.valueOf(Long.MAX_VALUE)
        Long.MIN_VALUE          | true | String.valueOf(Long.MIN_VALUE)
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
        v                        | has  | r
        1.0                      | true | "1.0"
        0.0                      | true | "0.0"
        -1.0                     | true | "-1.0"
        0.1                      | true | "0.1"
        -0.1                     | true | "-0.1"
        Double.MAX_VALUE         | true | String.valueOf(Double.MAX_VALUE)
        Double.MIN_VALUE         | true | String.valueOf(Double.MIN_VALUE)
        Double.POSITIVE_INFINITY | true | String.valueOf(Double.POSITIVE_INFINITY)
        Double.NEGATIVE_INFINITY | true | String.valueOf(Double.NEGATIVE_INFINITY)
        Double.NaN               | true | String.valueOf(Double.NaN)
        Double.MIN_NORMAL        | true | String.valueOf(Double.MIN_NORMAL)
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
        v       | has  | r
        ""      | true | ""
        "Y"     | true | "Y"
        "y"     | true | "y"
        "N"     | true | "N"
        "n"     | true | "n"
        "YES"   | true | "YES"
        "yES"   | true | "yES"
        "YeS"   | true | "YeS"
        "yeS"   | true | "yeS"
        "YEs"   | true | "YEs"
        "yEs"   | true | "yEs"
        "Yes"   | true | "Yes"
        "yes"   | true | "yes"
        "NO"    | true | "NO"
        "nO"    | true | "nO"
        "No"    | true | "No"
        "no"    | true | "no"
        "TRUE"  | true | "TRUE"
        "tRUE"  | true | "tRUE"
        "true"  | true | "true"
        "True"  | true | "True"
        "FALSE" | true | "FALSE"
        "fALSE" | true | "fALSE"
        "false" | true | "false"
        "False" | true | "False"
    }
}
