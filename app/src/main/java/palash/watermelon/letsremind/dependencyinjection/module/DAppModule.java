package palash.watermelon.letsremind.dependencyinjection.module;

/*
 * Created by Palash on 26/09/17.
 */

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import palash.watermelon.letsremind.utility.SharedPreferenceManager;

@Module
public class DAppModule {

    @Singleton
    @Provides
    public SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Singleton
    @Provides
    public SharedPreferenceManager provideSharedPreferenceManager
            (SharedPreferences sharedPreferences) {
        return new SharedPreferenceManager(sharedPreferences);
    }

}
