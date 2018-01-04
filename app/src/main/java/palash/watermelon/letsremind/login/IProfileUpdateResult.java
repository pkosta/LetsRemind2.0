package palash.watermelon.letsremind.login;

/*
 * Created by Palash on 24/09/17.
 */

import palash.watermelon.letsremind.datamodel.AppUser;

public interface IProfileUpdateResult {

    void onProfileUpdateSuccess();

    void onProfileUpdateFailed(String message);

}
