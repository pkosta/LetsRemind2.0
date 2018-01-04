package palash.watermelon.letsremind.login;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.datamodel.User;

/*
 * Created by Palash on 23/09/17.
 */

public class LoginManager implements ILoginManager {

    private ILoginResult mLoginResult;

    private IProfileUpdateResult mProfileUpdateResult;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    private IProfileFetchResult mProfileFetchResult;

    private IDataStorageResult mDataStorageResult;

    public LoginManager() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://letsremind20.firebaseio.com/");
    }

    @Override
    public void registerToProfileFetchListener(IProfileFetchResult result) {
        mProfileFetchResult = result;
    }

    @Override
    public void unregisterToProfileFetchResult() {
        mProfileFetchResult = null;
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
    public void registerToDataStorage(IDataStorageResult result) {
        mDataStorageResult = result;
    }

    @Override
    public void unregisterToDataStorage() {
        mDataStorageResult = null;
    }

    @Override
    public void signOut() {
        mAuth.signOut();
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
                                        null, null);
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
                            if (firebaseUser != null && firebaseUser.getEmail() != null) {
                                AppUser appUser = new AppUser(firebaseUser.getEmail(),
                                        firebaseUser.getDisplayName(), null);
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
    public void setProfile(final User user) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getUsername())
                .setPhotoUri(Uri.parse(user.getPhotoUrl()))
                .build();
        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            mDatabase.child("users").child(userId).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (mProfileUpdateResult != null) {
                                        mProfileUpdateResult.onProfileUpdateSuccess();
                                    }
                                }
                            });
                        } else {
                            if (mProfileUpdateResult != null) {
                                mProfileUpdateResult.onProfileUpdateFailed("Failed");
                            }
                        }
                    }
                });
    }



    public void uploadImageIntoFirebaseStorage(final User user, byte[] data) {
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child("mountains.jpg");


        UploadTask uploadTask = mountainsRef.putBytes(data);

        // Upload file and metadata to the path 'images/mountains.jpg'

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0
                        * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d("BUGS", "Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("BUGS", "Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d("BUGS", "Upload is failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                Log.d("BUGS", "Upload is good");
                final Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                Log.d("BUGS", "Download URL: "+downloadUrl.toString());
                user.setPhotoUrl(downloadUrl.toString());
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mDatabase.child("users").child(userId).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Log.d("BUGS", "DatabaseReference: "+ databaseReference.toString());
                        if (mDataStorageResult != null) {
                            mDataStorageResult.onDataStorageSuccess(downloadUrl.toString());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void forgotPassword(String userId) {

    }

    @Override
    public @Nullable
    AppUser getLoggedInUser() {


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userReference = mDatabase.child("users").child(userId);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (mProfileFetchResult != null) {
                    mProfileFetchResult.onProfileFetchSuccessful(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            String fileUrl = "";
            if (user.getPhotoUrl() != null) {
                fileUrl = user.getPhotoUrl().toString();
            }


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();     //TODO: add emailVerified field

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();

            if (email != null) {
                return new AppUser(email, name, fileUrl);
            }

        }*/

        return null;
    }

    @Override
    public boolean isUserVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // currently we will implement the email verification
        return user != null && user.isEmailVerified();
    }

    @Override
    public boolean isProfileAdded() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // currently we will implement the email verification
        return user != null && !TextUtils.isEmpty(user.getDisplayName());
    }

    @Override
    public boolean isUserLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // currently we will implement the email verification
        return user != null;
    }
}
