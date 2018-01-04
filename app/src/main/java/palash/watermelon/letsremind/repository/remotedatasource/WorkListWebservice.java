package palash.watermelon.letsremind.repository.remotedatasource;

/*
 * Created by 849501 on 10/30/2017.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Singleton;

@Singleton
public class WorkListWebservice {

    private DatabaseReference mFirebaseDatabaseReference;


    public WorkListWebservice(
            DatabaseReference databaseReference) {

        this.mFirebaseDatabaseReference = databaseReference;
        this.mFirebaseDatabaseReference = this.mFirebaseDatabaseReference.child(WorkListKeyContract.KEY_WORK_LIST_ROOK);

    }


    public DatabaseReference getAllWorkListItems() {
        return this.mFirebaseDatabaseReference;
    }

}
