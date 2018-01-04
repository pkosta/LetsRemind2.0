package palash.watermelon.letsremind.repository;

/*
 * Created by 849501 on 10/30/2017.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import palash.watermelon.letsremind.repository.localdatasource.LetsRemindDatabase;
import palash.watermelon.letsremind.repository.localdatasource.WorkListDao;
import palash.watermelon.letsremind.repository.model.WorkListRepoModel;
import palash.watermelon.letsremind.repository.remotedatasource.WorkListWebservice;

import static com.facebook.FacebookSdk.getApplicationContext;

public class WorkListRepository {

    // Local DataSource
    private WorkListDao mWorkListDao;

    // Remote DataSource
    private WorkListWebservice mWorkListWebservice;

    public WorkListRepository() {
        LetsRemindDatabase db = Room.databaseBuilder(getApplicationContext(),
                LetsRemindDatabase.class, "letsreminddb").build();
        this.mWorkListDao = db.workListDao();
        Log.d("BUGS", "WorkListRepository Initiated And WorkListDoa is: "
                + this.mWorkListDao.toString());
        this.mWorkListWebservice = new WorkListWebservice(
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://letsremind20.firebaseio.com/"));
    }

    public LiveData<WorkListRepoModel> getWorkList() {

        Log.d("BUGS", "WorkListRepository getWorkList");

        mWorkListWebservice.getAllWorkListItems().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("BUGS", "WorkListRepository getWorkList onDataChange DataSnapshot: " +dataSnapshot.toString());
                saveDataToLocalDb(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("BUGS", "WorkListRepository getWorkList onCancelled DatabaseError: "+ databaseError.toString());

            }
        });

        Log.d("BUGS", "WorkListRepository getWorkList local db: "+mWorkListDao.load().toString());

        return mWorkListDao.load();

    }

    @WorkerThread
    private void saveDataToLocalDb(final DataSnapshot dataSnapshot) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
            executor.execute(new Runnable() {
                public void run() {
                    // Do some long running operation on a worker thread
                    WorkListRepoModel repoModel = snapshot.getValue(WorkListRepoModel.class);
                    if (repoModel != null) {
                        repoModel.setTaskId(dataSnapshot.getKey());
                        mWorkListDao.save(repoModel);
                    }
                }
            });
        }
    }

}
