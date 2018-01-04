package palash.watermelon.letsremind.app;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


import palash.watermelon.letsremind.dependencyinjection.component.DaggerDAppComponent;
import palash.watermelon.letsremind.repository.localdatasource.LetsRemindDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

/*
 * Created by Palash on 26/09/17.
 */

public class LRApplication extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        DaggerDAppComponent.builder().application(this).build().inject(this);
        Room.databaseBuilder(getApplicationContext(),
                LetsRemindDatabase.class, "letsreminddb").build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
