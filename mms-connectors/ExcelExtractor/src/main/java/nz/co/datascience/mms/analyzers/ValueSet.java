package nz.co.datascience.mms.analyzers;

import com.google.common.collect.Sets;

import java.util.*;

/**
 * User: markmo
 * Date: 17/10/12
 * Time: 9:37 PM
 */
public class ValueSet {

    private List<Object> values;
    private List<String> cachedStringValues;
    private TreeSet<String> cachedTreeSet;
    private HashSet<?> cachedHashSet;

    public ValueSet(List<Object> values) {
        this.values = values;
    }

    public List<String> getStringValues() {
        if (cachedStringValues == null) {
            this.cachedStringValues = new ArrayList<String>();
            for (Object value : values) {
                this.cachedStringValues.add(value.toString());
            }
        }
        return cachedStringValues;
    }

    public boolean test(int sampleSize, Predicate<Object> fn) {
        int size = values.size();
        int n = Math.min(sampleSize, size);
        Random random = new Random();
        while (n-- > 0) {
            if (!fn.apply(values.get(random.nextInt(size)))) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsSet(Set<?> members, boolean caseInsensitive) {
        if (caseInsensitive) {
            if (cachedTreeSet == null) {
                this.cachedTreeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
                this.cachedTreeSet.addAll(getStringValues());
            }
            return Sets.difference(this.cachedTreeSet, members).isEmpty();
        }
        if (cachedHashSet == null) {
            this.cachedHashSet = new HashSet<Object>(values);
        }
        return Sets.difference(this.cachedHashSet, members).isEmpty();
    }
}
