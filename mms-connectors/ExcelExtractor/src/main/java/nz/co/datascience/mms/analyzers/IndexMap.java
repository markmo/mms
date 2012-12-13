package nz.co.datascience.mms.analyzers;

import java.util.*;

/**
 * User: markmo
 * Date: 16/10/12
 * Time: 9:46 PM
 */
public class IndexMap<K, V> {

    private Map<K, List<V>> indexes = new HashMap<K, List<V>>();

    public void add(K type, V value) {
        List<V> index = indexes.get(type);
        if (index == null) {
            index = new ArrayList<V>();
            this.indexes.put(type, index);
        }
        index.add(value);
    }

    public List<V> getIndex(K type) {
        List<V> index = indexes.get(type);
        return (index == null) ? Collections.<V>emptyList() : index;
    }

    public int size(K... types) {
        int len = 0;
        for (K type : types) {
            List<V> index = indexes.get(type);
            if (index != null) {
                len += index.size();
            }
        }
        return len;
    }

    public boolean isEmpty(K... types) {
        return (size(types) == 0);
    }
}
