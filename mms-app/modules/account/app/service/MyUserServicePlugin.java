package service.account;

import play.Application;

import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.service.UserServicePlugin;
import play.db.jpa.JPA;
import play.libs.F;

import models.account.User;

public class MyUserServicePlugin extends UserServicePlugin {

    public MyUserServicePlugin(final Application app) {
        super(app);
    }

    @Override
    public Object save(final AuthUser authUser) {
        try {
            return JPA.withTransaction(new F.Function0<Object>() {
                @Override
                public Object apply() {
                    final boolean isLinked = User.existsByAuthUserIdentity(authUser);
                    if (!isLinked) {
                        return User.create(authUser).getId();
                    } else {
                        // we have this user already, so return null
                        return null;
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getLocalIdentity(final AuthUserIdentity identity) {
        // For production: Caching might be a good idea here...
        // ...and don't forget to sync the cache when users get deactivated/deleted
        final User u = User.findByAuthUserIdentity(identity);
        if (u != null) {
            return u.getId();
        } else {
            return null;
        }
    }

    @Override
    public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {
        try {
            return JPA.withTransaction(new F.Function0<AuthUser>() {
                @Override
                public AuthUser apply() {
                    if (!oldUser.equals(newUser)) {
                        User.merge(oldUser, newUser);
                    }
                    return oldUser;
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AuthUser link(final AuthUser oldUser, final AuthUser newUser) {
        try {
            return JPA.withTransaction(new F.Function0<AuthUser>() {
                @Override
                public AuthUser apply() {
                    User.addLinkedAccount(oldUser, newUser);
                    return newUser;
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AuthUser update(final AuthUser knownUser) {
        // User logged in again, bump last login date
        User.setLastLoginDate(knownUser);
        return knownUser;
    }
}
