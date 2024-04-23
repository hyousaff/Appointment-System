package controller;

/**
 *
 * @author Hamza Yousaf
 *
 * Interface for the TableColumns lambda expression utilized in the SchedulerDashboardController.
 * This interface simplifies the initialization of TableColumns in a TableView, reducing the amount of
 * repetitive code required. The lambda expression is employed in multiple methods, including populateTable,
 * populateTableMonth, and populateTableWeek, to streamline the setup of table columns.
 */
public interface TableColumns {
    void setColumns(String a, String t, String d, String l, String c, String t2, String st, String et, String cId, String uId);
}