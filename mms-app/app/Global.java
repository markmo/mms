import java.util.Arrays;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import controllers.routes;
import module.Dependencies;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import play.mvc.*;

import models.account.SecurityRole;
//import service.FileRepoService;

public class Global extends GlobalSettings {

    private Injector injector = Guice.createInjector(new Dependencies());

    @Override
    public void onStart(Application app) {
        Logger.info("Application has started");
        PlayAuthenticate.setResolver(new Resolver() {

            @Override
            public Call login() {
                // Your login page
                return controllers.account.routes.Application.login();
            }

            @Override
            public Call afterAuth() {
                // The user will be redirected to this page after authentication
                // if no original URL was saved
                return controllers.account.routes.Application.index();
            }

            @Override
            public Call afterLogout() {
                return controllers.account.routes.Application.login();
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
                return controllers.account.routes.Account.askMerge();
            }

            @Override
            public Call askLink() {
                return controllers.account.routes.Account.askLink();
            }

            @Override
            public Call onException(final AuthException e) {
                if (e instanceof AccessDeniedException) {
                    return controllers.account.routes.Signup.oAuthDenied(((AccessDeniedException) e)
                            .getProviderKey());
                }

                // more custom problem handling here...
                return super.onException(e);
            }
        });

        //Logger.warn("Getting an instance from guice: " + app.plugin(InjectPlugin.class).getInstance(ObjectMapper.class));
//        JPA.withTransaction(new F.Callback0() {
//            @Override
//            public void invoke() {
//                initialData();
//            }
//        });

        //IndexService.cleanIndex();
    }

//    @Override
//    public void onStop(Application app) {
//        Logger.info("Application shutdown...");
//        FileRepoService repo = injector.getInstance(FileRepoService.class);
//        if (repo != null) {
//            repo.shutdown();
//        }
//    }

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

//    private void initialData() {
//        if (SecurityRole.findRowCount() < 1) {
//            for (final String roleName : Arrays.asList(controllers.Application.USER_ROLE)) {
//                final SecurityRole role = new SecurityRole();
//                role.roleName = roleName;
//                JPA.em().persist(role);
//            }
//        }
//    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) {
        return injector.getInstance(clazz);
    }
}
