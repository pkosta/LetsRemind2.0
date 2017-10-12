package palash.watermelon.letsremind.dependencyinjection.component;

/*
 * Created by Palash on 26/09/17.
 */

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import palash.watermelon.letsremind.app.LRApplication;
import palash.watermelon.letsremind.dependencyinjection.module.DAppModule;
import palash.watermelon.letsremind.dependencyinjection.module.DLoginModule;
import palash.watermelon.letsremind.dependencyinjection.module.activitymodule.LoginActivityModule;
import palash.watermelon.letsremind.dependencyinjection.module.activitymodule.ProfileActivityModule;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                DAppModule.class,
                DLoginModule.class,
                ProfileActivityModule.class,
                LoginActivityModule.class
        }
)
public interface DAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        DAppComponent build();
    }

    void inject(LRApplication application);

}
