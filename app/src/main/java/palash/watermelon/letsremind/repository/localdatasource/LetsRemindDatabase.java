package palash.watermelon.letsremind.repository.localdatasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import palash.watermelon.letsremind.repository.model.WorkListRepoModel;

/**
 * Created by 849501 on 10/30/2017.
 */

@Database(entities =
        {WorkListRepoModel.class},
        version = 1)
public abstract class LetsRemindDatabase extends RoomDatabase {

    public abstract WorkListDao workListDao();

}
