package palash.watermelon.letsremind.login;

/*
 * Created by Palash on 24/09/17.
 */

import palash.watermelon.letsremind.datamodel.User;

public interface IProfileFetchResult {

    void onProfileFetchSuccessful(User aUser);

}
