package palash.watermelon.letsremind.login;

/*
 * Created by Palash on 23/09/17.
 */

import org.jetbrains.annotations.Nullable;

import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.datamodel.User;

public interface ILoginManager {

    void signUp(String userId, String password);

    void signIn(String userId, String password);

    void setProfile(User user);

    void forgotPassword(String userId);

    @Nullable AppUser getLoggedInUser();

    boolean isUserVerified();

    void registerToLoginManager(ILoginResult result);

    void unregisterToLoginManager();

    void registerToProfileUpdate(IProfileUpdateResult result);

    void unregisterToProfileUpdate();

    void signOut();

    boolean isProfileAdded();

    boolean isUserLoggedIn();

    void registerToProfileFetchListener(IProfileFetchResult result);

    void unregisterToProfileFetchResult();

    void registerToDataStorage(IDataStorageResult result);

    void unregisterToDataStorage();
}
