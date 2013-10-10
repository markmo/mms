package models.common;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.*;
import org.codehaus.jackson.JsonNode;
import play.libs.Json;

/**
 * User: markmo
 * Date: 5/06/13
 * Time: 8:12 PM
 */
public class Page<T> {

    private static final int DEFAULT_RANGE_SIZE = 5;

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

    public int getLastPageIndex() {
        return (int)totalRowCount / pageSize;
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

    public Integer[] nearestIndexes() {
        return nearestIndexes(DEFAULT_RANGE_SIZE);
    }

    public Integer[] nearestIndexes(int rangeSize) {
        int behindSize = rangeSize / 2;
        int aheadSize = rangeSize - behindSize;
        int totalPageCount = (int)Math.ceil(new Double(totalRowCount) / pageSize);
        int size = Math.min(rangeSize, totalPageCount);
        Integer[] indexes = new Integer[size];
        Range<Integer> range;
        if (pageIndex == 1) {
            range = Range.closed(1, size);
        } else {
            int ahead = Math.min(pageIndex + aheadSize, totalPageCount) - pageIndex;
            int behind = Math.min(rangeSize - ahead, pageIndex - 1);
            if (behind < behindSize)
                ahead = Math.min(pageIndex + rangeSize - behind, totalPageCount) - pageIndex;
            range = Range.closed(pageIndex - behind, pageIndex + ahead);
        }
        return ContiguousSet.create(range, DiscreteDomain.integers()).toArray(indexes);
    }

    public boolean isCurrentIndex(int idx) {
        return pageIndex == idx;
    }

    public String getDisplayXtoYofZ() {
        int start = (pageIndex - 1) * pageSize + 1;
        int end = start + Math.min(pageSize, list.size()) - 1;
        return start + " to " + end + " of " + totalRowCount;
    }

    public String toJSON(ObjectMapper mapper) throws IOException {
        String listJson = mapper.writeValueAsString(list);
        return String.format("[{\"total_entries\":%d},%s]",
                totalRowCount, listJson);
    }

    public String toJSON() {
        JsonNode listJson = Json.toJson(list);
        return String.format("[{\"total_entries\":%d},%s]",
                totalRowCount, listJson);
    }
}
