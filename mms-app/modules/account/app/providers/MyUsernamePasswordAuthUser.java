package providers.account;

import providers.account.MyUsernamePasswordAuthProvider.MySignup;

import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.NameIdentity;

public class MyUsernamePasswordAuthUser extends UsernamePasswordAuthUser
        implements NameIdentity {

    private static final long serialVersionUID = 1L;

    private final String name;

    private final String organizationCode;

    public MyUsernamePasswordAuthUser(final MySignup signup) {
        super(signup.password, signup.email);
        this.name = signup.name;
        this.organizationCode = signup.organizationCode;
    }

    /**
     * Used for password reset only - do not use this to signup a user!
     *
     * @param password
     */
    public MyUsernamePasswordAuthUser(final String password) {
        super(password, null);
        name = null;
        organizationCode = null;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }
}
