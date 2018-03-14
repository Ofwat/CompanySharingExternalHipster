package uk.gov.ofwat.external.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String OAUTH_FAIL_MESSAGE = "Token Failed";
    public static final String UPLOAD_SUCCESS_MESSAGE ="Success";
    public static final String UPLOAD_FAIL_MESSAGE ="Failure";


    private Constants() {
    }
}
