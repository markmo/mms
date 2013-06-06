package models;

import java.util.List;

/**
 * User: markmo
 * Date: 5/06/13
 * Time: 8:12 PM
 */
public class Page<T> {

    private final int pageSize;
    private final long totalRowCount;
    private final int pageIndex;
    private final List<T> list;

    public Page(List<T> list, long totalRowCount, int pageIndex, int pageSize) {
        this.list = list;
        this.totalRowCount = totalRowCount;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public long getTotalRowCount() {
        return totalRowCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public List<T> getList() {
        return list;
    }

    public boolean hasPrev() {
        return pageIndex > 1;
    }

    public boolean hasNext() {
        return (totalRowCount / pageSize) >= pageIndex;
    }

    public boolean isEmpty() {
        return (list == null || list.isEmpty());
    }

    public String getDisplayXtoYofZ() {
        int start = (pageIndex - 1) * pageSize + 1;
        int end = start + Math.min(pageSize, list.size()) - 1;
        return start + " to " + end + " of " + totalRowCount;
    }
}
