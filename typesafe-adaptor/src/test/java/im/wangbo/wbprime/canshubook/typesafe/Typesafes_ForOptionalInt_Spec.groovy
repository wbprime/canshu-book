package im.wangbo.wbprime.canshubook.typesafe


import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import im.wangbo.wbprime.canshubook.Configs
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
class Typesafes_ForOptionalInt_Spec extends Specification {
    @Shared
    String absentKey = "absent_key";
    @Shared
    String emptyStringKey = "estring_key";
    @Shared
    String stringKey = "string_key";
    String stringVal
    @Shared
    String intKey = "int_key";
    int intVal
    @Shared
    String longKey = "long_key";
    long longVal
    @Shared
    String floatKey = "float_key";
    double floatVal
    @Shared
    String boolKey = "bool_key";
    boolean boolVal
    @Shared
    String mapKey = "map_key";
    @Shared
    String mapSubKey1 = "map_sub_key1";
    @Shared
    String mapSubKey2 = "map_sub_key2";
    @Shared
    String listKey = "list_key";

    Config config;

    void setup() {
        stringVal = "Z" + UUID.randomUUID().toString()

        Random r = new Random()
        intVal = r.nextInt()
        longVal = r.nextBoolean() ? (10L + r.nextInt(Integer.MAX_VALUE)) : (-10L - r.nextInt(Integer.MAX_VALUE))
        floatVal = r.nextDouble()
        boolVal = r.nextBoolean()

        StringBuilder json = new StringBuilder("{");
        json.append(String.format("\"%s\":\"%s\",", stringKey, stringVal))
        json.append(String.format("\"%s\":%d,", intKey, intVal))
        json.append(String.format("\"%s\":%d,", longKey, longVal))
        json.append(String.format("\"%s\":%s,", boolKey, boolVal))
        json.append(String.format("\"%s\":%s,", floatKey, floatVal))
        json.append(String.format("\"%s\":{\"%s\": \"%s\", \"%s\": \"%s\"},", mapKey, mapSubKey1, stringVal, mapSubKey2, stringVal));
        json.append(String.format("\"%s\":[{\"%s\": \"%s\"},{\"%s\": \"%s\"}],", listKey, mapSubKey1, stringVal, mapSubKey2, stringVal));
        json.append(String.format("\"%s\":\"\"", emptyStringKey));
        json.append("}");

        config = ConfigFactory.parseReader(new StringReader(json.toString()))
    }

    def "test get empty string as int"() {
        when:
        def opt = Typesafes.asOptional(config, emptyStringKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get string as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, stringKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    @Unroll
    def "test get int as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, intKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        opt.isPresent()

        def v = opt.getAsInt()
        v == intVal
        where:
        i << (1..100)
    }

    @Unroll
    def "test get long as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, longKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        opt.isPresent()

        def v = opt.getAsInt()
        v == longVal

        where:
        i << (1..100)
    }

    @Unroll
    def "test get float as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, floatKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        if ((int) floatVal == floatVal) {
            opt.isPresent()

            opt.getAsInt() == floatVal
        } else {
            !opt.isPresent()
        }

        where:
        i << (1..100)
    }

    @Unroll
    def "test get bool as int repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, boolKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get list as int"() {
        when:
        def opt = Typesafes.asOptional(config, listKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get map as int"() {
        when:
        def opt = Typesafes.asOptional(config, mapKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }

    def "test get absent value as int"() {
        when:
        def opt = Typesafes.asOptional(config, absentKey, Configs.forOptionalInt()).orElse(OptionalInt.empty())
        then:
        !opt.isPresent()
    }
}
