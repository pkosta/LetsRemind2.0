package palash.watermelon.letsremind.dependencyinjection.module.activitymodule;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import palash.watermelon.letsremind.dependencyinjection.subcomponent.HomeActivitySubcomponent;
import palash.watermelon.letsremind.userinterface.HomeActivity;


/*
 * Created by Palash on 27/09/17.
 */

@Module(subcomponents = HomeActivitySubcomponent.class)
public abstract class HomeActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(HomeActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindYourActivityInjectorFactory(HomeActivitySubcomponent.Builder builder);
}
