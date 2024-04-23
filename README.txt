Title: Appointment Handler
Purpose: A java based desktop application that handles the appointment system of a global company. The application uses
         SQL to store data and is connected to a live database 24/7. The application's can facilitate CRUD operations and
         can be interacted with for more customizations.
Author: Hamza Yousaf
Contact information: hyousa2@wgu.edu
Student application version: 1.8
Date: 01/31/2024

IDE: IntelliJ IDEA 2023.2.4 (Community Edition)
Java SE: 17.0.9
JavaFX-SDK: 17.0.1

Directions:

Login: Use the following credentials
username: admin
password: admin

Upon logging in the user will see the following sections on the main schedule screen.


Views:
    Users will be able to filter the appointments using radio buttons according to the pre-defined timeframe,
    user's can filter appointment according to Month, Week or all.
        ALL - Displays the default for all appointments.
        MONTH - Filters the appointments to display appointments in current month.
        WEEK - Filters the appointments to display appointments in current week.

Appointments Functions (ADD, EDIT/UPDATE, DELETE):
    Users will be able to interact with the appointment data using these functionalities.
    To add an appointment:
        1. Click "ADD" under appointment functions.
        2. Fill out necessary information
        3. Click "ADD"
        To add an appointment in the database users will be able to click on "ADD" button in the main screen. All the
        text-field are required to be filled by the User except the Appointment ID which is auto-generated. Time selected
        must be between 8:00AM - 10:00PM (ET). A reset button is provided to quickly clear the information to start again.
        when done users will click "Add" to navigate safely back to the main screen.
    To Edit/Update Appointment
        1. Select an appointment from data
        2. Click "EDIT" under appointment functions.
        3. Edit/Update information
        4. Click "save"
        To Update and appointment in the database users will be able to click on "Edit" after selecting an appointment
        from the list to update. Once clicked the program will open the edit screen and users will be able to edit
        all necessary information except AppointmentID. A reset button is provided to quickly clear out the information.
        Once done click on "Close" button to return safely to main menu.
    To Delete/Remove Appointment
         1. Select an appointment from data
         2. Click "Delete" under appointment functions.
         3. Confirm to Delete.
         To delete an appointment from the database users will be able to click on "delete" after selecting an appointment
         form the list. Once selected and clicked on delete the program will confirm from the user for deletion.

All Customers (ADD, EDIT/UPDATE, DELETE):
    Users will be able to interact with the customer data using these functionalities.
    To add a customer:
        1. Click "Customers" button to toggle Customer View
        2. Click "Add"
        3. Fill out required information
        4. Click "Add"
        Ao add a customerText-fields are provided with necessary information to add a customer. All the text-fields must be
        completed in order for the customer to save. After filling all the fields click on add to save the customer.
        A reset button is provided to quickly clear all the fields if user chooses to start again. Close button will navigate
        back to main view.
    To Edit/Update a customer:
        1. Click "Customers" button to toggle Customer View
        2. Select a Customer to update by selecting it and clicking "Edit"
        3. Edit/Update information
        4. Click "Save" to update and return.
        To update an existing customer, first a customer needs to be selected from the customer view list and then click on
        'Edit' will toggle the update customer screen. The scene will be similar to Add Customer and user will be able to edit
        any information except the CustomerID. When done click save to safely exit.
    To Delete a customer:
        1. Click "Customers" button to toggle Customer View
        2. Select a Customer to update by selecting it and clicking "Remove"
        3. If there exist an appointment for the customer, the program will inform the user.
        4. Click "OK" to confirm to delete.
        To Delete/Remove a customer, first a customer needs to be selected from the customer view list and then click on
        'Remove' to delete the customer. If the customer has an existing appointment the user will not be able to delete
        the customer until appointment is deleted.

Reports:
    Different Report filters with types are created for the users to generate reports according to need.
    Users can generate the schedule reports using the following directions
        Report by Month:
           1. Select month from the drop-down menu
           2. Select Type of appointment
           3. Click Report button next to selected month.
        Report by Contact:
           1. Select Contact Name from the drop-down menu
           2. Click Report button next to selected contact
        Report by Location:
           1. Select Location from drop-down menu.
           2. Click Report button next to selected location.

A description of the additional report of your choice you ran in part A3f:
Users can generate a report based on the location of the appointment, once selected the program will display appointments
tied to a existing location.

MySQL Database Driver: mysql-connector-java-8.0.25