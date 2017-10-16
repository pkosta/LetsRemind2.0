package palash.watermelon.letsremind.dependencyinjection.subcomponent;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import palash.watermelon.letsremind.userinterface.login.SplashActivity;

/*
 * Created by 849501 on 10/14/2017.
 */

@Subcomponent()
public interface SplashActivitySubcomponent extends AndroidInjector<SplashActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SplashActivity>{}

}
