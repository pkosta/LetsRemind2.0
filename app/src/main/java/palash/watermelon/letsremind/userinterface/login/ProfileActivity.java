package palash.watermelon.letsremind.userinterface.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.getphotolibrary.addphoto.AddPhotoBottomSheetButtons;
import com.example.getphotolibrary.addphoto.GetPhotoBottomDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import palash.watermelon.letsremind.R;
import palash.watermelon.letsremind.databinding.ActivityProfileBinding;
import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.datamodel.User;
import palash.watermelon.letsremind.login.IDataStorageResult;
import palash.watermelon.letsremind.login.IProfileUpdateResult;
import palash.watermelon.letsremind.login.LoginManager;
import palash.watermelon.letsremind.userinterface.HomeActivity;
import palash.watermelon.letsremind.utility.SharedPreferenceKeys;
import palash.watermelon.letsremind.utility.SharedPreferenceManager;

public class ProfileActivity extends AppCompatActivity implements
        IProfileUpdateResult,
        GetPhotoBottomDialogFragment.OnBottomSheetButtonClickListener,
        GetPhotoBottomDialogFragment.OnImageReadyListener, IDataStorageResult {

    private ActivityProfileBinding mBindingLayout;

    @Inject
    public LoginManager mLoginManager;

    @Inject
    public SharedPreferenceManager mSharedPreferenceManager;

    // need of the library
    private boolean mShowRemoveButton = false;

    private String mPhotoLocalPath;

    private AppUser mAppUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBindingLayout = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            final String emailId = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            mAppUser = new AppUser(emailId, "", "");

            mBindingLayout.btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProgressBar(true);

                    // setting up the user with basic information
                    String userName = mBindingLayout.tieFirstName.getText().toString()
                            + mBindingLayout.tieLastName.getText().toString();
                    String profileUrl = mPhotoLocalPath;    // stored the local path until get the server one.

                    mAppUser = new AppUser(emailId, userName, profileUrl);

                    final User user = new User(mAppUser.getUserName(),
                            mAppUser.getUserId(), mAppUser.getFileUri());

                    // Get the data from an ImageView as bytes
                    mBindingLayout.profileImage.setDrawingCacheEnabled(true);
                    mBindingLayout.profileImage.buildDrawingCache();
                    Bitmap bitmap = mBindingLayout.profileImage.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    mLoginManager.uploadImageIntoFirebaseStorage(user, data);

                }
            });

           /* mSharedPreferenceManager.putPreferenceStringValueSync("temp", "temp");
            String val = mSharedPreferenceManager.getPreferenceStringValueWithKey("temp");*/

            mBindingLayout.tvShowLoggedInUser.setText(getString(R.string.tv_logged_in_user,
                    mAppUser.getUserId()));

            mBindingLayout.tvDifferentSignInOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent welcomeActivityIntent = new Intent(ProfileActivity.this,
                            WelcomeActivity.class);
                    startActivity(welcomeActivityIntent);
                }
            });

            mBindingLayout.profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetPhotoBottomDialogFragment getPhotoBottomDialogFragment
                            = GetPhotoBottomDialogFragment.newInstance(
                            ProfileActivity.this,
                            ProfileActivity.this,
                            mBindingLayout.profileImage.getWidth(),
                            mBindingLayout.profileImage.getHeight(),
                            "temp_image",
                            mShowRemoveButton);
                    getPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                            "add_photo_dialog_fragment");
                }
            });
        }
    }

    private void setFileUriString(Bitmap inImage) {

        File tempFile = null;
        try {
            tempFile = createImageFile();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] bitmapData = bytes.toByteArray();

            //write the bytes in file
            FileOutputStream fos = null;

            fos = new FileOutputStream(tempFile);

            fos.write(bitmapData);
            fos.flush();
            fos.close();
            mPhotoLocalPath = tempFile.getAbsolutePath();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        //String imageFileName = "JPEG_" + mImageName + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                "upload_image",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginManager.registerToProfileUpdate(this);
        mLoginManager.registerToDataStorage(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginManager.unregisterToProfileUpdate();
        mLoginManager.unregisterToDataStorage();
    }

    @Override
    public void onProfileUpdateSuccess() {
        showProgressBar(false);

        mSharedPreferenceManager.putPreferenceBooleanValueSync(
                SharedPreferenceKeys.IS_USER_LOGGED_IN, true);

        Intent welcomeActivityIntent = new Intent(
                ProfileActivity.this, HomeActivity.class);
        welcomeActivityIntent
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(welcomeActivityIntent);
    }

    @Override
    public void onProfileUpdateFailed(String message) {
        showProgressBar(false);
        Intent welcomeActivityIntent = new Intent(
                ProfileActivity.this, HomeActivity.class);
        welcomeActivityIntent
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(welcomeActivityIntent);
    }

    // this method is needed to disable the views and enable the only progress bar
    private void showProgressBar(boolean flag) {
        mBindingLayout.indeterminateBar.setVisibility(flag ? View.VISIBLE : View.GONE);
        disableViews(flag);
    }

    private void disableViews(boolean flag) {
        mBindingLayout.profileImage.setEnabled(!flag);
        mBindingLayout.tvShowLoggedInUser.setEnabled(!flag);
        mBindingLayout.tilFirstName.setEnabled(!flag);
        mBindingLayout.tilLastName.setEnabled(!flag);
        mBindingLayout.btnSignUp.setEnabled(!flag);
        mBindingLayout.tvDifferentSignInOption.setEnabled(!flag);
    }

    @Override
    public void onBottomSheetButtonClick(@AddPhotoBottomSheetButtons String button) {
        switch (button) {
            case AddPhotoBottomSheetButtons.USE_CAMERA:
                Log.d("BUGS", "Use Camera Button Clicked");
                break;
            case AddPhotoBottomSheetButtons.FROM_GALLERY:
                Log.d("BUGS", "From Gallery Button Clicked");
                break;
            case AddPhotoBottomSheetButtons.REMOVE_PHOTO:
                Log.d("BUGS", "Remove Photo Button Clicked");
                mShowRemoveButton = false;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageReadyWithBitmap(Bitmap finalBitmap) {
        setFileUriString(finalBitmap);
        mBindingLayout.profileImage.setImageBitmap(finalBitmap);
        mShowRemoveButton = true;
    }

    @Override
    public void onDataStorageSuccess(String filePath) {
        mAppUser.setFileUri(filePath);
        final User user = new User(mAppUser.getUserName(),
                mAppUser.getUserId(), mAppUser.getFileUri());
        mLoginManager.setProfile(user);
    }

    @Override
    public void onDataStorageFailed(String message) {

    }
}
