package nz.co.datascience.mms.analyzers;

import com.eaio.stringsearch.BNDMWildcards;
import com.eaio.stringsearch.StringSearch;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: markmo
 * Date: 17/10/12
 * Time: 8:12 PM
 */
public class PatternSet {

    private SortedSet<String> patterns = new TreeSet<String>();
    private StringSearch searcher;

    public PatternSet(List<Object> values) {
        for (Object value : values) {
            this.patterns.add(getMask(value.toString()));
        }
        searcher = new BNDMWildcards();
    }

    // Add case insensitive searches and the * wildcard - use regex?
    public boolean equalsMask(String... masks) {
        for (String mask : masks) {
            for (String p : patterns) {
                if (searcher.searchString(p, mask) == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean matches(String regex, boolean caseInsensitive) {
        Pattern pattern;
        if (caseInsensitive) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(regex);
        }
        for (String p : patterns) {
            Matcher matcher = pattern.matcher(p);
            if (!matcher.find()) {
                return false;
            }
        }
        return true;
    }

    public boolean containsMask(String... masks) {
        for (String mask : masks) {
            for (String p : patterns) {
                if (searcher.searchString(p, mask) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getMask(String value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            Character ch = value.charAt(i);
            if (Character.isLetter(ch)) {
                if (Character.isLowerCase(ch)) {
                    sb.append('a');
                } else {
                    sb.append('A');
                }
            } else if (Character.isDigit(ch)) {
                sb.append('9');
            } else if (Character.isSpaceChar(ch)) {
                sb.append(' ');
            } else {
                sb.append('.');
            }
        }
        return sb.toString();
    }
}
