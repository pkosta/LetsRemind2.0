package palash.watermelon.letsremind.login;

/*
 * Created by Palash on 24/09/17.
 */

public interface IDataStorageResult {

    void onDataStorageSuccess(String filePath);

    void onDataStorageFailed(String message);

}
