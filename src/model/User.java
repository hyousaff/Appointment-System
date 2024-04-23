package model;

/**
 * @author Hamza Yousaf
 *
 * Represents the model for a User. Includes a constructor for creating User instances and methods
 * to access and modify their attributes. This class is essential for managing user information within the application.
 */
public class User
{
    int userID;
    String userName, password;

    /**
     * Default constructor for creating a User object.
     */
    public User()
    {

    }

    /**
     * Retrieves the user ID of this User object.
     * @return User ID as an integer. */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID for this User object.
     * @param userID The new user ID as an integer. */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Retrieves the username of this User object.
     * @return Username as a String. */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username for this User object.
     * @param userName The new username as a String. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password of this User object. Note: Storing passwords as plain text is generally not secure.
     * @return Password as a String. */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for this User object. Caution is advised when handling passwords.
     * @param password The new password as a String. */
    public void setPassword(String password) {
        this.password = password;
    }
}