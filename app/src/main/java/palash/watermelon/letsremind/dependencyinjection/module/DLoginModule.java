package palash.watermelon.letsremind.dependencyinjection.module;

/*
 * Created by Palash on 26/09/17.
 */

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import palash.watermelon.letsremind.login.LoginManager;

@Module
public class DLoginModule {

    @Singleton
    @Provides
    LoginManager getLoginManager() {
        return new LoginManager();
    }

}
