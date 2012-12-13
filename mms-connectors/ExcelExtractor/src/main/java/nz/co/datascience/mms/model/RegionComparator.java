package nz.co.datascience.mms.model;

import java.util.Comparator;

/**
 * User: markmo
 * Date: 7/10/12
 * Time: 11:28 AM
 */
public class RegionComparator implements Comparator<Region> {

    public int compare(Region region1, Region region2) {
        if (region1 == null || region2 == null) return 0;
        if (region1.getTopRowNum() == region2.getTopRowNum()) {
            if (region1.getLeftColumnIndex() == region2.getLeftColumnIndex()) {
                return 0;
            } else {
                return (region1.getLeftColumnIndex() < region2.getLeftColumnIndex()) ? -1 : 1;
            }
        } else {
            return (region1.getTopRowNum() < region2.getTopRowNum()) ? -1 : 1;
        }
    }
}
