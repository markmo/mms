import java.lang.reflect.Method;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.plugin.inject.InjectPlugin;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Call;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

import controllers.routes;
import models.SecurityRole;
import play.mvc.Http;
import play.mvc.Result;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        PlayAuthenticate.setResolver(new Resolver() {

            @Override
            public Call login() {
                // Your login page
                return routes.Application.login();
            }

            @Override
            public Call afterAuth() {
                // The user will be redirected to this page after authentication
                // if no original URL was saved
                return routes.Application.index();
            }

            @Override
            public Call afterLogout() {
                return routes.Application.index();
            }

            @Override
            public Call auth(final String provider) {
                // You can provide your own authentication implementation,
                // however the default should be sufficient for most cases
                return com.feth.play.module.pa.controllers.routes.Authenticate
                        .authenticate(provider);
            }

            @Override
            public Call askMerge() {
                return routes.Account.askMerge();
            }

            @Override
            public Call askLink() {
                return routes.Account.askLink();
            }

            @Override
            public Call onException(final AuthException e) {
                if (e instanceof AccessDeniedException) {
                    return routes.Signup.oAuthDenied(((AccessDeniedException) e)
                            .getProviderKey());
                }

                // more custom problem handling here...
                return super.onException(e);
            }
        });

        Logger.warn("Getting an instance from guice: " + app.plugin(InjectPlugin.class).getInstance(ObjectMapper.class));
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                initialData();
            }
        });
    }

    /*
    @Override
    public Action onRequest(Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            public Result call(Http.Context ctx) throws Throwable {

                // my intercept code here

                return delegate.call(ctx);
            }
        };
    }*/

    private void initialData() {
//        if (SecurityRole.find.findRowCount() == 0) {
        if (SecurityRole.findRowCount() < 1) {
            for (final String roleName : Arrays.asList(controllers.Application.USER_ROLE)) {
                final SecurityRole role = new SecurityRole();
                role.roleName = roleName;
//                role.save();
                JPA.em().persist(role);
            }
        }
    }
}
