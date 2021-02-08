package im.wangbo.wbprime.canshubook

import org.omg.PortableInterceptor.INACTIVE
import spock.lang.Shared
import spock.lang.Specification

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class Visitors_ForLong_Spec extends Specification {
    @Shared
    Visitor<Long> visitor = Visitors.forLong()

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
        v     | has   | r
        true  | false | 0
        false | false | 0
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
        1                       | true | 1
        0                       | true | 0
        -1                      | true | -1
        Integer.MAX_VALUE       | true | Integer.MAX_VALUE
        Integer.MIN_VALUE       | true | Integer.MIN_VALUE
        1L + Integer.MAX_VALUE  | true | 1L + Integer.MAX_VALUE
        -1L + Integer.MIN_VALUE | true | -1L + Integer.MIN_VALUE
        Long.MAX_VALUE          | true | Long.MAX_VALUE
        Long.MIN_VALUE          | true | Long.MIN_VALUE
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
        v                              | has   | r
        1.0                            | true  | 1
        0                              | true  | 0
        -1.0                           | true  | -1
        0.1                            | false | 0
        -0.1                           | false | 0
        Double.MAX_VALUE               | false | 0
        Double.MIN_VALUE               | false | 0
        Double.POSITIVE_INFINITY       | false | 0
        Double.NEGATIVE_INFINITY       | false | 0
        Double.NaN                     | false | 0
        Double.MIN_NORMAL              | false | 0
        Double.valueOf(Long.MAX_VALUE) | true  | Long.MAX_VALUE
        Double.valueOf(Long.MIN_VALUE) | true  | Long.MIN_VALUE
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
        v                                        | has   | r
        ""                                       | false | 0
        "Y"                                      | false | 0
        "y"                                      | false | 0
        "N"                                      | false | 0
        "n"                                      | false | 0
        "YES"                                    | false | 0
        "yES"                                    | false | 0
        "YeS"                                    | false | 0
        "yeS"                                    | false | 0
        "YEs"                                    | false | 0
        "yEs"                                    | false | 0
        "Yes"                                    | false | 0
        "yes"                                    | false | 0
        "NO"                                     | false | 0
        "nO"                                     | false | 0
        "No"                                     | false | 0
        "no"                                     | false | 0
        "TRUE"                                   | false | 0
        "tRUE"                                   | false | 0
        "true"                                   | false | 0
        "True"                                   | false | 0
        "FALSE"                                  | false | 0
        "fALSE"                                  | false | 0
        "false"                                  | false | 0
        "False"                                  | false | 0
        String.valueOf(1)                        | true  | 1
        String.valueOf(0)                        | true  | 0
        String.valueOf(-1)                       | true  | -1
        String.valueOf(Integer.MAX_VALUE)        | true  | Integer.MAX_VALUE
        String.valueOf(Integer.MIN_VALUE)        | true  | Integer.MIN_VALUE
        String.valueOf(1L + Integer.MAX_VALUE)   | true  | 1L + Integer.MAX_VALUE
        String.valueOf(-1L + Integer.MIN_VALUE)  | true  | -1L + Integer.MIN_VALUE
        String.valueOf(Long.MAX_VALUE)           | true  | Long.MAX_VALUE
        String.valueOf(Long.MIN_VALUE)           | true  | Long.MIN_VALUE
        "1.0"                                    | true  | 1
        "0.0"                                    | true  | 0
        "-1.0"                                   | true  | -1
        "0.1"                                    | false | 0
        "-0.1"                                   | false | 0
        String.valueOf(Double.MAX_VALUE)         | false | 0
        String.valueOf(Double.MIN_VALUE)         | false | 0
        String.valueOf(Double.POSITIVE_INFINITY) | false | 0
        String.valueOf(Double.NEGATIVE_INFINITY) | false | 0
        String.valueOf(Double.NaN)               | false | 0
        String.valueOf(Double.MIN_NORMAL)        | false | 0
    }
}
