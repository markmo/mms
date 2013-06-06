package helpers;

import controllers.routes;

/**
 * User: markmo
 * Date: 5/06/13
 * Time: 8:40 PM
 */
public class ListHelper {

//    private String currentSortBy;
//    private String currentOrder;
//    private String currentFilter;
//
//    public ListHelper(String currentSortBy, String currentOrder, String currentFilter) {
//        this.currentSortBy = currentSortBy;
//        this.currentOrder = currentOrder;
//        this.currentFilter = currentFilter;
//    }

    public static String link(String currentSortBy, String currentOrder, String currentFilter,
                       int newPageIndex, String newSortBy) {
        String sortBy = currentSortBy;
        String order = currentOrder;
        if (newSortBy != null) {
            sortBy = newSortBy;
            if (currentSortBy.equals(newSortBy)) {
                order = "asc".equals(currentOrder) ? "desc" : "asc";
            } else {
                order = "asc";
            }
        }
        // Generate the link
        return routes.Organizations.list(newPageIndex, sortBy, order, currentFilter).toString();
    }

    public static String header(String currentSortBy, String currentOrder, String currentFilter,
                         String key, String title) {
        return
                "<th class=\"" + key.replace(".", "_") + " header " +
                ("asc".equals(currentOrder) ? "headerSortDown" : "headerSortUp") +
                "\"><a href=\"" + link(currentSortBy, currentOrder,currentFilter, 0, key) + "\">" + title +
                "</a></th>";
    }
}
