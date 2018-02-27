package uk.gov.ofwat.external.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";


    public static final  String GRANT_TYPE = "client_credentials";
    public static final String CLIENT_ID = "db07ea61-47d8-48b1-a0ac-1145da3ee907@42a92f0e-996a-41b2-8512-3ed237ab8313";
    public static final String CLIENT_SECRET = "hEZLL7UFHaFohKDy0UtBM1/S9YbftVV0h0mfG6AYT5I=";
    public static final String RESOURCE ="00000003-0000-0ff1-ce00-000000000000/ofwat.sharepoint.com@42a92f0e-996a-41b2-8512-3ed237ab8313";
    public static final String URL ="https://accounts.accesscontrol.windows.net/42a92f0e-996a-41b2-8512-3ed237ab8313/tokens/OAuth/2";
    public static final String OAUTH_FAIL_MESSAGE = "Token Failed";
    public static final String UPLOAD_FOLDER_URL="https://ofwat.sharepoint.com/sites/rms/pr-test/Data%20Capture%20System/";
    public static final String UPLOAD_SUCCESS_MESSAGE ="Success";
    public static final String UPLOAD_FAIL_MESSAGE ="Failure";

    private Constants() {
    }
}
