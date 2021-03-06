package palash.watermelon.letsremind.dependencyinjection.subcomponent;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import palash.watermelon.letsremind.userinterface.HomeActivity;

/*
 * Created by Palash on 27/09/17.
 */

@Subcomponent()
public interface HomeActivitySubcomponent extends AndroidInjector<HomeActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<HomeActivity> {}

}
