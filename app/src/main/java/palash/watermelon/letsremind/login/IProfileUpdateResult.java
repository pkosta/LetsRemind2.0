package palash.watermelon.letsremind.login;

/*
 * Created by Palash on 24/09/17.
 */

public interface IProfileUpdateResult {

    void onProfileUpdateSuccess();

    void onProfileUpdateFalied(String message);

}
