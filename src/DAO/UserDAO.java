package DAO;

import DataBase.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hamza Yousaf
 *
 * Class for UserDAO. This handles database queries related to Users. */
public abstract class UserDAO {

    /**
     * Retrieves a list of user IDs from the database. This method is used to populate combo boxes
     * with user IDs in the Add and Update Appointment views.
     *
     * @return An ObservableList containing the IDs of all users.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList<String> getUsers() throws SQLException {
        String sql = "SELECT User_ID FROM USERS";
        ObservableList<String> users = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String userId = rs.getString("User_ID");
            users.add(userId);
        }
        return users;
    }

    /**
     * Queries the database to generate a list of user IDs. This method is often used for populating
     * combo boxes in the UI with user IDs.
     *
     * @return An ObservableList of strings containing user IDs.
     * @throws SQLException If there is an issue executing the query in the database.
     */
    public static ObservableList <String> getUserIds() throws SQLException {
        String sql = "SELECT User_ID FROM USERS";
        ObservableList<String> userIds = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String userId = rs.getString("User_ID");
            userIds.add(userId);
        }
        return userIds;
    }

    /**
     * Finds the index of a specific user ID in a list of user IDs. This method is used in the UI, such as when
     * selecting a user for updating customer information, to determine the position of a user ID in a combo box.
     *
     * @param userId The user ID whose index is to be found in the list.
     * @return The index position of the specified user ID in the combo box list.
     * @throws SQLException If there is an issue retrieving user IDs from the database.
     */
    public static int getMatchingUserId(String userId) throws SQLException {
        String[] userIdList = getUserIds().toArray(new String[0]);
        int userIdIndex = 0;
        for (int i = 0; i <= userIdList.length; i++) {
            if (userId.equals(userIdList[i]))
            {
                userIdIndex = i;
                break;
            }
        }
        return userIdIndex;
    }
}