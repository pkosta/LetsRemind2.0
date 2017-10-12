package palash.watermelon.letsremind.dependencyinjection.subcomponent;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import palash.watermelon.letsremind.userinterface.login.LoginActivity;

/*
 * Created by Palash on 27/09/17.
 */

@Subcomponent()
public interface LoginActivitySubcomponent extends AndroidInjector<LoginActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<LoginActivity> {}

}
