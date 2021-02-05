package im.wangbo.wbprime.canshubook.spi.impl

import com.google.common.collect.ImmutableList
import im.wangbo.wbprime.canshubook.Config
import spock.lang.Specification

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
class StdKeyFactorySpec extends Specification {
    def "test root() for case insensitive key"() {
        given:
        StdKeyFactory.KeySpec settings = StdKeyFactory.CaseInsensitiveKeySpec.of(".")
        StdKeyFactory factory = new StdKeyFactory(settings)

        when:
        var key = factory.root();

        then:
        key.depth() == 0
        key.segment() == ""
        key.segments() == ImmutableList.of()
        key.parent().is(key)
    }

    def "test create() for case insensitive key"() {
        given:
        StdKeyFactory.KeySpec settings = StdKeyFactory.CaseInsensitiveKeySpec.of(sep)
        StdKeyFactory factory = new StdKeyFactory(settings)

        when:
        var key = factory.create(str)

        then:
        with(key) {
            key.segment() == cur
            key.segments().size() == depth
            key.depth() == depth
            key.parent().segment() == pcur
            key.parent().segments().size() == Math.max(depth - 1, 0)
            key.parent().depth() == Math.max(depth - 1, 0)
        }

        where:
        sep  | str                           | cur       | depth | pcur
        "."  | ""                            | ""        | 0     | ""
        "."  | "."                           | ""        | 0     | ""
        "."  | " "                           | " "       | 1     | ""
        "."  | " ."                          | " "       | 1     | ""
        "."  | " .."                         | " "       | 1     | ""
        "."  | ". ."                         | " "       | 1     | ""
        "."  | ".. "                         | " "       | 1     | ""
        "."  | "im"                          | "im"      | 1     | ""
        "."  | ".im"                         | "im"      | 1     | ""
        "."  | "..im"                        | "im"      | 1     | ""
        "."  | "im."                         | "im"      | 1     | ""
        "."  | "im.."                        | "im"      | 1     | ""
        "."  | "..im.."                      | "im"      | 1     | ""
        "."  | "Im"                          | "im"      | 1     | ""
        "."  | "iM"                          | "im"      | 1     | ""
        "."  | "IM"                          | "im"      | 1     | ""
        "."  | "im.wangbo"                   | "wangbo"  | 2     | "im"
        "."  | "im.wangbo.wbprime"           | "wbprime" | 3     | "wangbo"
        "."  | ".im..wangbo.wbprime.."       | "wbprime" | 3     | "wangbo"
        // lower case
        "Z"  | ""                            | ""        | 0     | ""
        "Z"  | "Z"                           | ""        | 0     | ""
        "Z"  | " "                           | " "       | 1     | ""
        "Z"  | " Z"                          | " "       | 1     | ""
        "Z"  | " ZZ"                         | " "       | 1     | ""
        "Z"  | "Z Z"                         | " "       | 1     | ""
        "Z"  | "ZZ "                         | " "       | 1     | ""
        "Z"  | "im"                          | "im"      | 1     | ""
        "Z"  | "Zim"                         | "im"      | 1     | ""
        "Z"  | "ZZim"                        | "im"      | 1     | ""
        "Z"  | "imZ"                         | "im"      | 1     | ""
        "Z"  | "imZZ"                        | "im"      | 1     | ""
        "Z"  | "ZZimZZ"                      | "im"      | 1     | ""
        "Z"  | "Im"                          | "im"      | 1     | ""
        "Z"  | "iM"                          | "im"      | 1     | ""
        "Z"  | "IM"                          | "im"      | 1     | ""
        "Z"  | "imZwangbo"                   | "wangbo"  | 2     | "im"
        "Z"  | "imZwangboZwbprime"           | "wbprime" | 3     | "wangbo"
        "Z"  | "ZimZZwangboZwbprimeZZ"       | "wbprime" | 3     | "wangbo"
        "Z"  | ""                            | ""        | 0     | ""
        "Z"  | "z"                           | ""        | 0     | ""
        "Z"  | " "                           | " "       | 1     | ""
        "Z"  | " z"                          | " "       | 1     | ""
        "Z"  | " zz"                         | " "       | 1     | ""
        "Z"  | "z z"                         | " "       | 1     | ""
        "Z"  | "zz "                         | " "       | 1     | ""
        "Z"  | "im"                          | "im"      | 1     | ""
        "Z"  | "zim"                         | "im"      | 1     | ""
        "Z"  | "zzim"                        | "im"      | 1     | ""
        "Z"  | "imz"                         | "im"      | 1     | ""
        "Z"  | "imzz"                        | "im"      | 1     | ""
        "Z"  | "zzimzz"                      | "im"      | 1     | ""
        "Z"  | "Im"                          | "im"      | 1     | ""
        "Z"  | "iM"                          | "im"      | 1     | ""
        "Z"  | "IM"                          | "im"      | 1     | ""
        "Z"  | "imzwangbo"                   | "wangbo"  | 2     | "im"
        "Z"  | "imzwangbozwbprime"           | "wbprime" | 3     | "wangbo"
        "Z"  | "zimzzwangbozwbprimezz"       | "wbprime" | 3     | "wangbo"
        // lower case
        "z"  | ""                            | ""        | 0     | ""
        "z"  | "Z"                           | ""        | 0     | ""
        "z"  | " "                           | " "       | 1     | ""
        "z"  | " Z"                          | " "       | 1     | ""
        "z"  | " ZZ"                         | " "       | 1     | ""
        "z"  | "Z Z"                         | " "       | 1     | ""
        "z"  | "ZZ "                         | " "       | 1     | ""
        "z"  | "im"                          | "im"      | 1     | ""
        "z"  | "Zim"                         | "im"      | 1     | ""
        "z"  | "ZZim"                        | "im"      | 1     | ""
        "z"  | "imZ"                         | "im"      | 1     | ""
        "z"  | "imZZ"                        | "im"      | 1     | ""
        "z"  | "ZZimZZ"                      | "im"      | 1     | ""
        "z"  | "Im"                          | "im"      | 1     | ""
        "z"  | "iM"                          | "im"      | 1     | ""
        "z"  | "IM"                          | "im"      | 1     | ""
        "z"  | "imZwangbo"                   | "wangbo"  | 2     | "im"
        "z"  | "imZwangboZwbprime"           | "wbprime" | 3     | "wangbo"
        "z"  | "ZimZZwangboZwbprimeZZ"       | "wbprime" | 3     | "wangbo"
        "z"  | ""                            | ""        | 0     | ""
        "z"  | "z"                           | ""        | 0     | ""
        "z"  | " "                           | " "       | 1     | ""
        "z"  | " z"                          | " "       | 1     | ""
        "z"  | " zz"                         | " "       | 1     | ""
        "z"  | "z z"                         | " "       | 1     | ""
        "z"  | "zz "                         | " "       | 1     | ""
        "z"  | "im"                          | "im"      | 1     | ""
        "z"  | "zim"                         | "im"      | 1     | ""
        "z"  | "zzim"                        | "im"      | 1     | ""
        "z"  | "imz"                         | "im"      | 1     | ""
        "z"  | "imzz"                        | "im"      | 1     | ""
        "z"  | "zzimzz"                      | "im"      | 1     | ""
        "z"  | "Im"                          | "im"      | 1     | ""
        "z"  | "iM"                          | "im"      | 1     | ""
        "z"  | "IM"                          | "im"      | 1     | ""
        "z"  | "imzwangbo"                   | "wangbo"  | 2     | "im"
        "z"  | "imzwangbozwbprime"           | "wbprime" | 3     | "wangbo"
        "z"  | "zimzzwangbozwbprimezz"       | "wbprime" | 3     | "wangbo"
        // mixed case
        "zY" | ""                            | ""        | 0     | ""
        "zY" | "Zy"                          | ""        | 0     | ""
        "zY" | " "                           | " "       | 1     | ""
        "zY" | " Zy"                         | " "       | 1     | ""
        "zY" | " ZyZy"                       | " "       | 1     | ""
        "zY" | "Zy Zy"                       | " "       | 1     | ""
        "zY" | "ZyZy "                       | " "       | 1     | ""
        "zY" | "im"                          | "im"      | 1     | ""
        "zY" | "Zyim"                        | "im"      | 1     | ""
        "zY" | "ZyZyim"                      | "im"      | 1     | ""
        "zY" | "imZy"                        | "im"      | 1     | ""
        "zY" | "imZyZy"                      | "im"      | 1     | ""
        "zY" | "ZyZyimZyZy"                  | "im"      | 1     | ""
        "zY" | "Im"                          | "im"      | 1     | ""
        "zY" | "iM"                          | "im"      | 1     | ""
        "zY" | "IM"                          | "im"      | 1     | ""
        "zY" | "imZywangbo"                  | "wangbo"  | 2     | "im"
        "zY" | "imZywangboZywbprime"         | "wbprime" | 3     | "wangbo"
        "zY" | "ZyimZyZywangboZywbprimeZyZy" | "wbprime" | 3     | "wangbo"
        "yZ" | ""                            | ""        | 0     | ""
        "yZ" | "yZ"                          | ""        | 0     | ""
        "yZ" | " "                           | " "       | 1     | ""
        "yZ" | " yZ"                         | " "       | 1     | ""
        "yZ" | " yZyZ"                       | " "       | 1     | ""
        "yZ" | "yZ yZ"                       | " "       | 1     | ""
        "yZ" | "yZyZ "                       | " "       | 1     | ""
        "yZ" | "im"                          | "im"      | 1     | ""
        "yZ" | "yZim"                        | "im"      | 1     | ""
        "yZ" | "yZyZim"                      | "im"      | 1     | ""
        "yZ" | "imyZ"                        | "im"      | 1     | ""
        "yZ" | "imyZyZ"                      | "im"      | 1     | ""
        "yZ" | "yZyZimyZyZ"                  | "im"      | 1     | ""
        "yZ" | "Im"                          | "im"      | 1     | ""
        "yZ" | "iM"                          | "im"      | 1     | ""
        "yZ" | "IM"                          | "im"      | 1     | ""
        "yZ" | "imyZwangbo"                  | "wangbo"  | 2     | "im"
        "yZ" | "imyZwangboyZwbprime"         | "wbprime" | 3     | "wangbo"
        "yZ" | "yZimyZyZwangboyZwbprimeyZyZ" | "wbprime" | 3     | "wangbo"
    }

    def "test create() for case sensitive key"() {
        given:
        StdKeyFactory.KeySpec settings = StdKeyFactory.CaseSensitiveKeySpec.of(sep)
        StdKeyFactory factory = new StdKeyFactory(settings)

        when:
        var key = factory.create(str)

        then:
        with(key) {
            key.segment() == cur
            key.segments().size() == depth
            key.depth() == depth
            key.parent().segment() == pcur
            key.parent().segments().size() == Math.max(depth - 1, 0)
            key.parent().depth() == Math.max(depth - 1, 0)
        }

        where:
        sep  | str                           | cur                           | depth | pcur
        "."  | ""                            | ""                            | 0     | ""
        "."  | "."                           | ""                            | 0     | ""
        "."  | " "                           | " "                           | 1     | ""
        "."  | " ."                          | " "                           | 1     | ""
        "."  | " .."                         | " "                           | 1     | ""
        "."  | ". ."                         | " "                           | 1     | ""
        "."  | ".. "                         | " "                           | 1     | ""
        "."  | "im"                          | "im"                          | 1     | ""
        "."  | ".im"                         | "im"                          | 1     | ""
        "."  | "..im"                        | "im"                          | 1     | ""
        "."  | "im."                         | "im"                          | 1     | ""
        "."  | "im.."                        | "im"                          | 1     | ""
        "."  | "..im.."                      | "im"                          | 1     | ""
        "."  | "Im"                          | "Im"                          | 1     | ""
        "."  | "iM"                          | "iM"                          | 1     | ""
        "."  | "IM"                          | "IM"                          | 1     | ""
        "."  | "im.wangbo"                   | "wangbo"                      | 2     | "im"
        "."  | "im.wangbo.wbprime"           | "wbprime"                     | 3     | "wangbo"
        "."  | ".im..wangbo.wbprime.."       | "wbprime"                     | 3     | "wangbo"
        // lower case
        "Z"  | ""                            | ""                            | 0     | ""
        "Z"  | "Z"                           | ""                            | 0     | ""
        "Z"  | " "                           | " "                           | 1     | ""
        "Z"  | " Z"                          | " "                           | 1     | ""
        "Z"  | " ZZ"                         | " "                           | 1     | ""
        "Z"  | "Z Z"                         | " "                           | 1     | ""
        "Z"  | "ZZ "                         | " "                           | 1     | ""
        "Z"  | "im"                          | "im"                          | 1     | ""
        "Z"  | "Zim"                         | "im"                          | 1     | ""
        "Z"  | "ZZim"                        | "im"                          | 1     | ""
        "Z"  | "imZ"                         | "im"                          | 1     | ""
        "Z"  | "imZZ"                        | "im"                          | 1     | ""
        "Z"  | "ZZimZZ"                      | "im"                          | 1     | ""
        "Z"  | "Im"                          | "Im"                          | 1     | ""
        "Z"  | "iM"                          | "iM"                          | 1     | ""
        "Z"  | "IM"                          | "IM"                          | 1     | ""
        "Z"  | "imZwangbo"                   | "wangbo"                      | 2     | "im"
        "Z"  | "imZwangboZwbprime"           | "wbprime"                     | 3     | "wangbo"
        "Z"  | "ZimZZwangboZwbprimeZZ"       | "wbprime"                     | 3     | "wangbo"
        "Z"  | ""                            | ""                            | 0     | ""
        "Z"  | "z"                           | "z"                           | 1     | ""
        "Z"  | " "                           | " "                           | 1     | ""
        "Z"  | " z"                          | " z"                          | 1     | ""
        "Z"  | " zz"                         | " zz"                         | 1     | ""
        "Z"  | "z z"                         | "z z"                         | 1     | ""
        "Z"  | "zz "                         | "zz "                         | 1     | ""
        "Z"  | "im"                          | "im"                          | 1     | ""
        "Z"  | "zim"                         | "zim"                         | 1     | ""
        "Z"  | "zzim"                        | "zzim"                        | 1     | ""
        "Z"  | "imz"                         | "imz"                         | 1     | ""
        "Z"  | "imzz"                        | "imzz"                        | 1     | ""
        "Z"  | "zzimzz"                      | "zzimzz"                      | 1     | ""
        "Z"  | "Im"                          | "Im"                          | 1     | ""
        "Z"  | "iM"                          | "iM"                          | 1     | ""
        "Z"  | "IM"                          | "IM"                          | 1     | ""
        "Z"  | "imzwangbo"                   | "imzwangbo"                   | 1     | ""
        "Z"  | "imzwangbozwbprime"           | "imzwangbozwbprime"           | 1     | ""
        "Z"  | "zimzzwangbozwbprimezz"       | "zimzzwangbozwbprimezz"       | 1     | ""
        // lower case
        "z"  | ""                            | ""                            | 0     | ""
        "z"  | "Z"                           | "Z"                           | 1     | ""
        "z"  | " "                           | " "                           | 1     | ""
        "z"  | " Z"                          | " Z"                          | 1     | ""
        "z"  | " ZZ"                         | " ZZ"                         | 1     | ""
        "z"  | "Z Z"                         | "Z Z"                         | 1     | ""
        "z"  | "ZZ "                         | "ZZ "                         | 1     | ""
        "z"  | "im"                          | "im"                          | 1     | ""
        "z"  | "Zim"                         | "Zim"                         | 1     | ""
        "z"  | "ZZim"                        | "ZZim"                        | 1     | ""
        "z"  | "imZ"                         | "imZ"                         | 1     | ""
        "z"  | "imZZ"                        | "imZZ"                        | 1     | ""
        "z"  | "ZZimZZ"                      | "ZZimZZ"                      | 1     | ""
        "z"  | "Im"                          | "Im"                          | 1     | ""
        "z"  | "iM"                          | "iM"                          | 1     | ""
        "z"  | "IM"                          | "IM"                          | 1     | ""
        "z"  | "imZwangbo"                   | "imZwangbo"                   | 1     | ""
        "z"  | "imZwangboZwbprime"           | "imZwangboZwbprime"           | 1     | ""
        "z"  | "ZimZZwangboZwbprimeZZ"       | "ZimZZwangboZwbprimeZZ"       | 1     | ""
        "z"  | ""                            | ""                            | 0     | ""
        "z"  | "z"                           | ""                            | 0     | ""
        "z"  | " "                           | " "                           | 1     | ""
        "z"  | " z"                          | " "                           | 1     | ""
        "z"  | " zz"                         | " "                           | 1     | ""
        "z"  | "z z"                         | " "                           | 1     | ""
        "z"  | "zz "                         | " "                           | 1     | ""
        "z"  | "im"                          | "im"                          | 1     | ""
        "z"  | "zim"                         | "im"                          | 1     | ""
        "z"  | "zzim"                        | "im"                          | 1     | ""
        "z"  | "imz"                         | "im"                          | 1     | ""
        "z"  | "imzz"                        | "im"                          | 1     | ""
        "z"  | "zzimzz"                      | "im"                          | 1     | ""
        "z"  | "Im"                          | "Im"                          | 1     | ""
        "z"  | "iM"                          | "iM"                          | 1     | ""
        "z"  | "IM"                          | "IM"                          | 1     | ""
        "z"  | "imzwangbo"                   | "wangbo"                      | 2     | "im"
        "z"  | "imzwangbozwbprime"           | "wbprime"                     | 3     | "wangbo"
        "z"  | "zimzzwangbozwbprimezz"       | "wbprime"                     | 3     | "wangbo"
        // lower case
        "zY" | ""                            | ""                            | 0     | ""
        "zY" | "zY"                          | ""                            | 0     | ""
        "zY" | " "                           | " "                           | 1     | ""
        "zY" | " zY"                         | " "                           | 1     | ""
        "zY" | " zYzY"                       | " "                           | 1     | ""
        "zY" | "zY zY"                       | " "                           | 1     | ""
        "zY" | "zYzY "                       | " "                           | 1     | ""
        "zY" | "im"                          | "im"                          | 1     | ""
        "zY" | "zYim"                        | "im"                          | 1     | ""
        "zY" | "zYzYim"                      | "im"                          | 1     | ""
        "zY" | "imzY"                        | "im"                          | 1     | ""
        "zY" | "imzYzY"                      | "im"                          | 1     | ""
        "zY" | "zYzYimzYzY"                  | "im"                          | 1     | ""
        "zY" | "Im"                          | "Im"                          | 1     | ""
        "zY" | "iM"                          | "iM"                          | 1     | ""
        "zY" | "IM"                          | "IM"                          | 1     | ""
        "zY" | "imzYwangbo"                  | "wangbo"                      | 2     | "im"
        "zY" | "imzYwangbozYwbprime"         | "wbprime"                     | 3     | "wangbo"
        "zY" | "zYimzYzYwangbozYwbprimezYzY" | "wbprime"                     | 3     | "wangbo"
        "zY" | ""                            | ""                            | 0     | ""
        "zY" | "Zy"                          | "Zy"                          | 1     | ""
        "zY" | " "                           | " "                           | 1     | ""
        "zY" | " Zy"                         | " Zy"                         | 1     | ""
        "zY" | " ZyZy"                       | " ZyZy"                       | 1     | ""
        "zY" | "Zy Zy"                       | "Zy Zy"                       | 1     | ""
        "zY" | "ZyZy "                       | "ZyZy "                       | 1     | ""
        "zY" | "im"                          | "im"                          | 1     | ""
        "zY" | "Zyim"                        | "Zyim"                        | 1     | ""
        "zY" | "ZyZyim"                      | "ZyZyim"                      | 1     | ""
        "zY" | "imZy"                        | "imZy"                        | 1     | ""
        "zY" | "imZyZy"                      | "imZyZy"                      | 1     | ""
        "zY" | "ZyZyimZyZy"                  | "ZyZyimZyZy"                  | 1     | ""
        "zY" | "Im"                          | "Im"                          | 1     | ""
        "zY" | "iM"                          | "iM"                          | 1     | ""
        "zY" | "IM"                          | "IM"                          | 1     | ""
        "zY" | "imZywangbo"                  | "imZywangbo"                  | 1     | ""
        "zY" | "imZywangboZywbprime"         | "imZywangboZywbprime"         | 1     | ""
        "zY" | "ZyimZyZywangboZywbprimeZyZy" | "ZyimZyZywangboZywbprimeZyZy" | 1     | ""
        "Yz" | ""                            | ""                            | 0     | ""
        "Yz" | "Yz"                          | ""                            | 0     | ""
        "Yz" | " "                           | " "                           | 1     | ""
        "Yz" | " Yz"                         | " "                           | 1     | ""
        "Yz" | " YzYz"                       | " "                           | 1     | ""
        "Yz" | "Yz Yz"                       | " "                           | 1     | ""
        "Yz" | "YzYz "                       | " "                           | 1     | ""
        "Yz" | "im"                          | "im"                          | 1     | ""
        "Yz" | "Yzim"                        | "im"                          | 1     | ""
        "Yz" | "YzYzim"                      | "im"                          | 1     | ""
        "Yz" | "imYz"                        | "im"                          | 1     | ""
        "Yz" | "imYzYz"                      | "im"                          | 1     | ""
        "Yz" | "YzYzimYzYz"                  | "im"                          | 1     | ""
        "Yz" | "Im"                          | "Im"                          | 1     | ""
        "Yz" | "iM"                          | "iM"                          | 1     | ""
        "Yz" | "IM"                          | "IM"                          | 1     | ""
        "Yz" | "imYzwangbo"                  | "wangbo"                      | 2     | "im"
        "Yz" | "imYzwangboYzwbprime"         | "wbprime"                     | 3     | "wangbo"
        "Yz" | "YzimYzYzwangboYzwbprimeYzYz" | "wbprime"                     | 3     | "wangbo"
        "Yz" | ""                            | ""                            | 0     | ""
        "Yz" | "yZ"                          | "yZ"                          | 1     | ""
        "Yz" | " "                           | " "                           | 1     | ""
        "Yz" | " yZ"                         | " yZ"                         | 1     | ""
        "Yz" | " yZyZ"                       | " yZyZ"                       | 1     | ""
        "Yz" | "yZ yZ"                       | "yZ yZ"                       | 1     | ""
        "Yz" | "yZyZ "                       | "yZyZ "                       | 1     | ""
        "Yz" | "im"                          | "im"                          | 1     | ""
        "Yz" | "yZim"                        | "yZim"                        | 1     | ""
        "Yz" | "yZyZim"                      | "yZyZim"                      | 1     | ""
        "Yz" | "imyZ"                        | "imyZ"                        | 1     | ""
        "Yz" | "imyZyZ"                      | "imyZyZ"                      | 1     | ""
        "Yz" | "yZyZimyZyZ"                  | "yZyZimyZyZ"                  | 1     | ""
        "Yz" | "Im"                          | "Im"                          | 1     | ""
        "Yz" | "iM"                          | "iM"                          | 1     | ""
        "Yz" | "IM"                          | "IM"                          | 1     | ""
        "Yz" | "imyZwangbo"                  | "imyZwangbo"                  | 1     | ""
        "Yz" | "imyZwangboyZwbprime"         | "imyZwangboyZwbprime"         | 1     | ""
        "Yz" | "yZimyZyZwangboyZwbprimeyZyZ" | "yZimyZyZwangboyZwbprimeyZyZ" | 1     | ""
    }

    def "test resolve() of Key for root"() {
        given:
        StdKeyFactory.KeySpec settings = StdKeyFactory.CaseSensitiveKeySpec.of(sep)
        StdKeyFactory factory = new StdKeyFactory(settings)
        Config.Key parent = factory.root();

        when:
        var key = parent.resolve(str)

        then:
        key.segment() == cur
        key.depth() == depth

        where:
        sep  | str        | cur   | depth
        "."  | ""         | ""    | 0
        "."  | " "        | " "   | 1
        "."  | "a"        | "a"   | 1
        "."  | "a.b"      | "b"   | 2
        "/"  | "a//b/"    | "b"   | 2
        "/"  | "/a//b/"   | "b"   | 2
        "__" | "A.B"      | "A.B" | 1
        "__" | "A__B__"   | "B"   | 2
        "__" | ".A__/B__" | "/B"  | 2
    }

    def "test resolve() of Key for non-root"() {
        given:
        StdKeyFactory.KeySpec settings = StdKeyFactory.CaseSensitiveKeySpec.of(sep)
        StdKeyFactory factory = new StdKeyFactory(settings)
        Config.Key parent = factory.create(pstr);

        when:
        var key = parent.resolve(str)

        then:
        key.segment() == cur
        key.depth() == depth

        where:
        sep  | pstr        | str        | cur   | depth
        "."  | ""          | "A.B"      | "B"   | 2
        "/"  | ""          | "A_/B__"   | "B__" | 2
        "__" | ""          | ".A__/B__" | "/B"  | 2
        "."  | "root..sub" | ""         | "sub" | 2
        "."  | "root..sub" | " "        | " "   | 3
        "."  | "root//sub" | "a"        | "a"   | 2
        "."  | "root__sub" | "a.b"      | "b"   | 3
        "/"  | "root..sub" | "a//b/"    | "b"   | 3
        "/"  | "root//sub" | "/a//b/"   | "b"   | 4
        "/"  | "root__sub" | "/a//b/"   | "b"   | 3
        "__" | "root..sub" | "A.B"      | "A.B" | 2
        "__" | "root//sub" | "A__B__"   | "B"   | 3
        "__" | "root..sub" | ".A__/B__" | "/B"  | 3
    }
}
