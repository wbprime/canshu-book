package im.wangbo.wbprime.canshubook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import im.wangbo.wbprime.canshubook.Configs.BooleanValue;
import im.wangbo.wbprime.canshubook.Configs.Key;
import im.wangbo.wbprime.canshubook.Configs.ListValue;
import im.wangbo.wbprime.canshubook.Configs.MapValue;
import im.wangbo.wbprime.canshubook.Configs.NumberValue;
import im.wangbo.wbprime.canshubook.Configs.StringValue;
import im.wangbo.wbprime.canshubook.Configs.Value;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.RandomAccess;
import java.util.Set;

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
final class ConfigValues {
    private ConfigValues() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    static BooleanValue of(final boolean v) {
        return v ? TheTrue.V : TheFalse.V;
    }

    static TheInt of(final int v) {
        return new TheInt(v);
    }

    static TheLong of(final long v) {
        return new TheLong(v);
    }

    static TheNumber of(final BigDecimal v) {
        return new TheNumber(v);
    }

    static TheString of(final String v) {
        return new TheString(v);
    }

    static TheList of(final Iterable<? extends Value> v) {
        return new TheList(ImmutableList.copyOf(v));
    }

    static TheMap of(final Map<Key, ? extends Value> v) {
        return new TheMap(ImmutableMap.copyOf(v));
    }

    private static class TheTrue implements BooleanValue {
        static final TheTrue V = new TheTrue();

        @Override
        public Optional<StringValue> asString() {
            return Optional.of(of("true"));
        }

        @Override
        public Optional<NumberValue> asNumber() {
            return Optional.of(of(1));
        }

        @Override
        public Optional<ListValue> asList() {
            return Optional.of(of(ImmutableList.of(this)));
        }

        @Override
        public boolean get() {
            return true;
        }
    }

    private static class TheFalse implements BooleanValue {
        static final TheFalse V = new TheFalse();

        @Override
        public Optional<StringValue> asString() {
            return Optional.of(of("false"));
        }

        @Override
        public Optional<NumberValue> asNumber() {
            return Optional.of(of(0));
        }

        @Override
        public Optional<ListValue> asList() {
            return Optional.of(of(ImmutableList.of(this)));
        }

        @Override
        public boolean get() {
            return false;
        }
    }

    private static class TheNumber implements NumberValue {
        private final BigDecimal v;

        TheNumber(final BigDecimal v) {
            this.v = v;
        }

        @Override
        public Optional<BooleanValue> asBoolean() {
            try {
                switch (v.intValueExact()) {
                    case 1:
                        return Optional.of(TheTrue.V);
                    case 0:
                        return Optional.of(TheFalse.V);
                    default:
                        return Optional.empty();
                }
            } catch (ArithmeticException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<StringValue> asString() {
            return Optional.of(of(v.toPlainString()));
        }

        @Override
        public Optional<ListValue> asList() {
            return Optional.of(of(ImmutableList.of(this)));
        }

        @Override
        public Optional<MapValue> asMap() {
            return Optional.empty();
        }

        @Override
        public BigDecimal get() {
            return v;
        }
    }

    private static class TheInt implements NumberValue {
        private final int v;

        TheInt(final int v) {
            this.v = v;
        }

        @Override
        public BigDecimal get() {
            return new BigDecimal(v);
        }

        @Override
        public Optional<BooleanValue> asBoolean() {
            switch (v) {
                case 1:
                    return Optional.of(TheTrue.V);
                case 0:
                    return Optional.of(TheFalse.V);
                default:
                    return Optional.empty();
            }
        }

        @Override
        public OptionalInt asInt() {
            return OptionalInt.of(v);
        }

        @Override
        public OptionalLong asLong() {
            return OptionalLong.of(v);
        }

        @Override
        public Optional<StringValue> asString() {
            return Optional.of(of(String.valueOf(v)));
        }

        @Override
        public Optional<ListValue> asList() {
            return Optional.of(of(ImmutableList.of(this)));
        }
    }

    private static class TheLong implements NumberValue {
        private final long v;

        TheLong(final long v) {
            this.v = v;
        }

        @Override
        public BigDecimal get() {
            return new BigDecimal(v);
        }

        @Override
        public Optional<BooleanValue> asBoolean() {
            final int vv = (int) v;
            if (vv != v) return Optional.empty();

            switch (vv) {
                case 1:
                    return Optional.of(TheTrue.V);
                case 0:
                    return Optional.of(TheFalse.V);
                default:
                    return Optional.empty();
            }
        }

        @Override
        public OptionalInt asInt() {
            final int vv = (int) v;
            if (vv != v) return OptionalInt.empty();

            return OptionalInt.of(vv);
        }

        @Override
        public OptionalLong asLong() {
            return OptionalLong.of(v);
        }

        @Override
        public Optional<StringValue> asString() {
            return Optional.of(of(String.valueOf(v)));
        }

        @Override
        public Optional<ListValue> asList() {
            return Optional.of(of(ImmutableList.of(this)));
        }
    }

    private static class TheString implements StringValue {
        private final String v;

        TheString(final String v) {
            this.v = v;
        }

        @Override
        public Optional<BooleanValue> asBoolean() {
            if ("1".equals(v) || "Y".equalsIgnoreCase(v) || "true".equalsIgnoreCase(v) || "yes".equalsIgnoreCase(v)) {
                return Optional.of(TheTrue.V);
            } else if ("0".equals(v) || "N".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v) || "no".equalsIgnoreCase(v)) {
                return Optional.of(TheFalse.V);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public Optional<NumberValue> asNumber() {
            try {
                return Optional.of(of(new BigDecimal(v)));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<ListValue> asList() {
            return Optional.of(of(ImmutableList.of(this)));
        }

        @Override
        public String get() {
            return v;
        }
    }

    private static class TheList implements ListValue, RandomAccess {
        private final ImmutableList<Value> list;

        TheList(final ImmutableList<Value> list) {
            this.list = list;
        }

        @Override
        public List<Value> get() {
            return list;
        }

        @Override
        public Optional<BooleanValue> asBoolean() {
            if (list.isEmpty()) return Optional.of(TheFalse.V);
            else if (list.size() == 1) return list.get(0).asBoolean();
            else return Optional.empty();
        }

        @Override
        public Optional<StringValue> asString() {
            if (list.isEmpty()) return Optional.of(of(""));
            else if (list.size() == 1) return list.get(0).asString();
            else return Optional.empty();
        }

        @Override
        public Optional<NumberValue> asNumber() {
            if (list.isEmpty()) return Optional.of(of(0));
            else if (list.size() == 1) return list.get(0).asNumber();
            else return Optional.empty();
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return list.contains(o);
        }

        @Override
        public Iterator<Value> iterator() {
            return list.iterator();
        }

        @Override
        public Object[] toArray() {
            return list.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return list.toArray(a);
        }

        @Override
        @Deprecated
        public boolean add(Value value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return list.containsAll(c);
        }

        @Override
        @Deprecated
        public boolean addAll(Collection<? extends Value> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean addAll(int index, Collection<? extends Value> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Value get(int index) {
            return list.get(index);
        }

        @Override
        @Deprecated
        public Value set(int index, Value element) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int index, Value element) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Value remove(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object o) {
            return list.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return list.lastIndexOf(o);
        }

        @Override
        public ListIterator<Value> listIterator() {
            return list.listIterator();
        }

        @Override
        public ListIterator<Value> listIterator(int index) {
            return list.listIterator(index);
        }

        @Override
        public TheList subList(int fromIndex, int toIndex) {
            return of(list.subList(fromIndex, toIndex));
        }
    }

    private static class TheMap implements MapValue {
        private final ImmutableMap<Key, Value> map;

        TheMap(final ImmutableMap<Key, Value> map) {
            this.map = map;
        }

        @Override
        public ImmutableMap<Key, Value> get() {
            return map;
        }

        @Override
        public Optional<BooleanValue> asBoolean() {
            return map.isEmpty() ? Optional.of(TheFalse.V) : Optional.empty();
        }

        @Override
        public Optional<StringValue> asString() {
            return map.isEmpty() ? Optional.of(of("")) : Optional.empty();
        }

        @Override
        public Optional<NumberValue> asNumber() {
            return map.isEmpty() ? Optional.of(of(0)) : Optional.empty();
        }

        @Override
        public Optional<ListValue> asList() {
            return Optional.of(of(ImmutableList.of(this)));
        }

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        @Override
        public Value get(Object key) {
            return map.get(key);
        }

        @Override
        @Deprecated
        public Value put(Key key, Value value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Value remove(Object key) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void putAll(Map<? extends Key, ? extends Value> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<Key> keySet() {
            return map.keySet();
        }

        @Override
        public Collection<Value> values() {
            return map.values();
        }

        @Override
        public Set<Entry<Key, Value>> entrySet() {
            return map.entrySet();
        }
    }
}
