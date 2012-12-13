package nz.co.datascience.mms.analyzers;

/**
 * User: markmo
 * Date: 17/10/12
 * Time: 9:40 PM
 */
public interface Predicate<T> {

    boolean apply(T value);
}
