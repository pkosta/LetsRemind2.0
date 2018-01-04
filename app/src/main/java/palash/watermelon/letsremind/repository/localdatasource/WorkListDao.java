package palash.watermelon.letsremind.repository.localdatasource;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import palash.watermelon.letsremind.repository.model.WorkListRepoModel;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/*
 * Created by 849501 on 10/30/2017.
 */

@Dao
public interface WorkListDao {

    @WorkerThread
    @Insert(onConflict = REPLACE)
    void save(WorkListRepoModel workListRepoModel);

    @Query("SELECT * FROM worklist")
    LiveData<WorkListRepoModel> load();


}
