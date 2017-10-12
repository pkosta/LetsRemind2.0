package palash.watermelon.letsremind.dependencyinjection.subcomponent;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import palash.watermelon.letsremind.userinterface.login.ProfileActivity;

/*
 * Created by Palash on 27/09/17.
 */

@Subcomponent()
public interface ProfileActivitySubcomponent extends AndroidInjector<ProfileActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<ProfileActivity> {}

}
