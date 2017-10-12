package palash.watermelon.letsremind.login;

import palash.watermelon.letsremind.datamodel.AppUser;

/**
 * Created by Palash on 23/09/17.
 */

public interface ILoginResult {

    void onLoginSuccess(AppUser user);

    void onLoginFailure(String errorMessage);
}
