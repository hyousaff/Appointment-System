package model;

/**
 * @author Hamza Yousaf
 *
 * Represents the model for a Contact. This class includes constructors for creating Contact instances and methods
 * to manipulate their attributes, particularly useful for populating contact combo boxes.
 */
public class Contact
{
    int contactId;
    String contactName;

    /**
     * Constructor to create a Contact object with a name.
     * @param contactName The name of the contact as a String.
     */
    public Contact(String contactName)
    {
        this.contactName = contactName;
    }

    /**
     * Retrieves the contact ID of this Contact object.
     * @return The integer ID of the contact.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID for this Contact object.
     * @param contactId The integer ID to assign to this contact.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Retrieves the name of this Contact object.
     * @return The name of the contact as a String.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the name for this Contact object.
     * @param contactName The new name to assign to this contact.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Overrides the default toString method to return the contact's name.
     * @return The name of the contact.
     */
    @Override
    public String toString(){
        return contactName;
    }
}