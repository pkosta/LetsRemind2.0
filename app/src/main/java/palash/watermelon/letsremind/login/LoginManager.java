package palash.watermelon.letsremind.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;

import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.utility.SharedPreferenceManager;

/*
 * Created by Palash on 23/09/17.
 */

public class LoginManager implements ILoginManager {

    private ILoginResult mLoginResult;

    private IProfileUpdateResult mProfileUpdateResult;

    private FirebaseAuth mAuth;

    public LoginManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void registerToLoginManager(ILoginResult result) {
        mLoginResult = result;
    }

    @Override
    public void unregisterToLoginManager() {
        mLoginResult = null;
    }

    @Override
    public void registerToProfileUpdate(IProfileUpdateResult result) {
        mProfileUpdateResult = result;
    }

    @Override
    public void unregisterToProfileUpdate() {
        mProfileUpdateResult = null;
    }

    @Override
    public void signUp(String userId, String password) {
        mAuth.createUserWithEmailAndPassword(userId, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null && firebaseUser.getEmail() != null) {
                                AppUser appUser = new AppUser(firebaseUser.getEmail(),
                                        null);
                                if (mLoginResult != null) {
                                    mLoginResult.onLoginSuccess(appUser);
                                }
                            } else {
                                if (mLoginResult != null) {
                                    mLoginResult.onLoginFailure(
                                            "SignUp Failed: Info Not Sufficient");
                                }
                            }
                        } else {
                            if (mLoginResult != null) {
                                mLoginResult.onLoginFailure(
                                        "SignUp Failed: Wrong Credentials");
                            }
                        }
                    }
                });
    }

    @Override
    public void signIn(String userId, String password) {
        mAuth.signInWithEmailAndPassword(userId, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null && firebaseUser.getEmail() != null &&
                                    firebaseUser.getDisplayName() != null) {
                                AppUser appUser = new AppUser(firebaseUser.getEmail(),
                                        firebaseUser.getDisplayName());
                                if (mLoginResult != null) {
                                    mLoginResult.onLoginSuccess(appUser);
                                }
                            } else {
                                if (mLoginResult != null) {
                                    mLoginResult.onLoginFailure(
                                            "Authentication Failed: Info Not Sufficient");
                                }
                            }
                        } else {
                            if (mLoginResult != null) {
                                mLoginResult.onLoginFailure(
                                        "Authentication Failed: Wrong Credentials");
                            }
                        }
                    }
                });
    }

    @Override
    public void setProfile(AppUser user) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            // User is signed in

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(user.getUserName())
                    .build();

            firebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Log.d(TAG, "User profile updated.");
                                if (mProfileUpdateResult != null) {
                                    mProfileUpdateResult.onProfileUpdateSuccess();
                                }
                            } else {
                                if (mProfileUpdateResult != null) {
                                    mProfileUpdateResult.onProfileUpdateFalied("Failed");
                                }
                            }
                        }
                    });

        } else {
            // No user is signed in
        }

    }

    @Override
    public void forgotPassword(String userId) {

    }

    @Override
    public @Nullable AppUser getLoggedInUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();

            if (email != null) {
                return new AppUser(email, name);
            }

        }

        return null;
    }

    @Override
    public boolean isUserVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // currently we will implement the email verification
        return user != null && user.isEmailVerified();
    }

}
