package uk.gov.ofwat.external.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String COMPANY_ADMIN = "ROLE_COMPANY_ADMIN";

    public static final String COMPANY_USER = "ROLE_COMPANY_USER";

    public static final String OFWAT_ADMIN = "ROLE_OFWAT_ADMIN";

    // TODO We will need a pre-auth user role to use two(step) auth mechanism. TBA.
    public static final String PRE_AUTH_USER = "PRE_AUTH_ROLE_USER";

    private AuthoritiesConstants() {
    }
}
