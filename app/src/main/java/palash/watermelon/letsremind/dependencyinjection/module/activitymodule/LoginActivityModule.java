package palash.watermelon.letsremind.dependencyinjection.module.activitymodule;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import palash.watermelon.letsremind.dependencyinjection.subcomponent.LoginActivitySubcomponent;
import palash.watermelon.letsremind.userinterface.login.LoginActivity;

/*
 * Created by Palash on 27/09/17.
 */

@Module(subcomponents = LoginActivitySubcomponent.class)
public abstract class LoginActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(LoginActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindYourActivityInjectorFactory(LoginActivitySubcomponent.Builder builder);
}
