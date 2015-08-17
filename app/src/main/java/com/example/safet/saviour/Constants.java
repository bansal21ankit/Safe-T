package com.example.safet.saviour;

public final class Constants {
    public static final String LOCATION_ACTION = "location_action";
    public static final String INCIDENT_STATUS_ACTIVE = "Active";
    public static final String INCIDENT_STATUS_EXIRED = "Expired";

    public class RequestTables {
        public static final String TABLE_USERS = "Users";
        public static final String TABLE_INCIDENT = "Incidents";
        public static final String TABLE_REQUEST = "PendingRequests";
    }

    public class RequestKeys {
        public static final String COL_SAVIOURS = "saviours";
        public static final String COL_SAVIOURS_OF = "saviourOf";
        public static final String COL_OBJECT_ID = "objectId";
        public static final String COL_PASSWORD = "password";
        public static final String COL_PHONE = "phone";
        public static final String COL_NAME = "name";

        public static final String COL_REQUESTED_BY = "requestedBy";
        public static final String COL_REQUESTED_TO = "requestedTo";

        public static final String COL_START_LOCATION = "startLocation";
        public static final String COL_START_TIME = "createdAt";
        public static final String COL_LAST_LOCATION = "lastLocation";
        public static final String COL_INCIDENT_STATUS = "status";
    }

    public class UserErrorMessage {
        public static final String SOMETHING_WRONG = "Sorry! Something went wrong";

    }

    public class UserInfoMessage {
        public static final String LOGIN_AUTHENTICATION_FAILED = "Inccorrct Password";
        public static final String EMPTY_USER_NAME = "User Name filed must not be Empty!";
        /**
         * IF PASSWORD MATCH FAILED
         */
        public static final String PASSWORD_MATCH_FAILED = "Password and its confirmation are not equal," +
                " please try again.";
        public static final String VALUES_NOT_UPDATED = "Updation request failed. Please try again later!";
        /**
         * IF USERNAME IS INVALID
         */
        public static final String INVALID_USERNAME_MESSAGE = "Invalid Username";
        /**
         * IF TEXT FIELDS ARE EMPTY
         */
        public static final String EMPTY_FIELDS = "Fields must not be empty.";
        /**
         * PLEASE WAIT MESSAGE
         */
        public static final String PLEASE_WAIT = "Please Wait...";
        /**
         * MESSAGE TO BE PROMPT ON LOGOUT
         */
        public static final String EXIT_MESSAGE = "Do you want to exit ?";
        /**
         * TITLE OF DIALOG
         */
        public static final String CONFIRM = "Confirm";
        public static final String INFO = "Info";
        public static final String USER_ALREADY_EXIST = "User Name Already Exist";

        /**
         * Message to display if password field is empty
         */
        public static final String EMPTY_PASSWORD_FIELDS = "Password Field Must not be Empty!";
        /**
         * Message to display if email field is empty
         */
        public static final String EMPTY_EMAIL_FIELD = "Emal Field Must not be Empty!";

        /**
         * Message to display if Confirm password field is empty
         */
        public static final String EMPTY_CONFIRM_PASSWORD_FIELDS = "Confirm Password Field Must not be Empty!";
        public static final String OK = "OK";
        public static final String CANCEL = "CANCEL";
        public static final String SAVE = "Save";
        public static final String EDIT = "Edit";
        public static final String DETAILS_UPDATED = "Your Details have been successfully updated!";
    }
}