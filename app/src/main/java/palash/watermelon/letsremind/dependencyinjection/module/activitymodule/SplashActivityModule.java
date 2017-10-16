package palash.watermelon.letsremind.dependencyinjection.module.activitymodule;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import palash.watermelon.letsremind.dependencyinjection.subcomponent.SplashActivitySubcomponent;
import palash.watermelon.letsremind.userinterface.login.SplashActivity;

/**
 * Created by 849501 on 10/14/2017.
 */

@Module(subcomponents = SplashActivitySubcomponent.class)
public abstract class SplashActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
            bindYourActivityInjectorFactory(SplashActivitySubcomponent.Builder builder);

}
