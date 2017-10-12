package palash.watermelon.letsremind.dependencyinjection.module.activitymodule;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import palash.watermelon.letsremind.dependencyinjection.subcomponent.ProfileActivitySubcomponent;
import palash.watermelon.letsremind.userinterface.login.ProfileActivity;

/*
 * Created by Palash on 27/09/17.
 */

@Module(subcomponents = ProfileActivitySubcomponent.class)
public abstract class ProfileActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(ProfileActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindYourActivityInjectorFactory(ProfileActivitySubcomponent.Builder builder);
}
