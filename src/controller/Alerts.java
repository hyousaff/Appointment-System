package controller;

/**
 * @author Hamza Yousaf
 *
 * Interface for the Errors lambda expression, implemented in the AddAppointment controller.
 * Simplifies the creation of alert windows, reducing repetitive code lines.
 */
public interface Alerts {
    void makeAlert(String d, String m);
}