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
class Typesafes_ForString_Spec extends Specification {
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

    def "test get empty string as string"() {
        when:
        def opt = Typesafes.asOptional(config, emptyStringKey, Configs.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v.isEmpty()
    }

    @Unroll
    def "test get string as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, stringKey, Configs.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == stringVal
        where:
        i << (1..100)
    }

    @Unroll
    def "test get int as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, intKey, Configs.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(intVal)
        where:
        i << (1..100)
    }

    @Unroll
    def "test get long as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, longKey, Configs.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(longVal)

        where:
        i << (1..100)
    }

    @Unroll
    def "test get float as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, floatKey, Configs.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(floatVal)

        where:
        i << (1..100)
    }

    @Unroll
    def "test get bool as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, boolKey, Configs.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(boolVal)

        where:
        i << (1..10)
    }

    def "test get list as string"() {
        when:
        def opt = Typesafes.asOptional(config, listKey, Configs.forString())
        then:
        !opt.isPresent()
    }

    def "test get map as string"() {
        when:
        def opt = Typesafes.asOptional(config, mapKey, Configs.forString())
        then:
        !opt.isPresent()
    }

    def "test get absent value as string"() {
        when:
        def opt = Typesafes.asOptional(config, absentKey, Configs.forString())
        then:
        !opt.isPresent()
    }
}
