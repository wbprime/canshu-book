package im.wangbo.wbprime.canshubook.typesafe;

import spock.lang.Shared;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class SampleData {
    String absentKey = "absent_key";
    String emptyStringKey = "estring_key";
    String stringKey = "string_key";
    String stringVal
    String intKey = "int_key";
    int intVal
    String longKey = "long_key";
    long longVal
    String floatKey = "float_key";
    double floatVal
    String boolKey = "bool_key";
    boolean boolVal
    String mapKey = "map_key";
    String mapSubKey1 = "map_sub_key1";
    String mapSubKey2 = "map_sub_key2";
    String listKey = "list_key";

    static SampleData randomize() {
        SampleData e = new SampleData()

        e.stringVal = "Z" + UUID.randomUUID().toString()

        Random r = new Random()
        e.intVal = r.nextInt()
        e.longVal = r.nextBoolean() ? (10L + r.nextInt(Integer.MAX_VALUE)) : (-10L - r.nextInt(Integer.MAX_VALUE))
        e.floatVal = r.nextDouble()
        e.boolVal = r.nextBoolean()

        return e;
    }

    public String toJson() {
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
        return json.toString();
    }
}
