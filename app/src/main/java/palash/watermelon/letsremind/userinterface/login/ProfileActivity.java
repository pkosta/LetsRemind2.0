package palash.watermelon.letsremind.userinterface.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import palash.watermelon.letsremind.R;
import palash.watermelon.letsremind.app.LRApplication;
import palash.watermelon.letsremind.databinding.ActivityProfileBinding;
import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.login.ILoginManager;
import palash.watermelon.letsremind.login.IProfileUpdateResult;
import palash.watermelon.letsremind.login.LoginManager;
import palash.watermelon.letsremind.userinterface.BaseActivity;
import palash.watermelon.letsremind.userinterface.WelcomeActivity;
import palash.watermelon.letsremind.utility.SharedPreferenceManager;

public class ProfileActivity extends AppCompatActivity implements IProfileUpdateResult {

    private ActivityProfileBinding mBindingLayout;

    @Inject
    protected LoginManager mLoginManager;

    @Inject
    protected SharedPreferenceManager mSharedPreferenceManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBindingLayout = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        mBindingLayout.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUser loggedInUser = mLoginManager.getLoggedInUser();
                if (loggedInUser != null) {
                    loggedInUser.setUserName(mBindingLayout.tieFirstName.getText().toString()
                            + mBindingLayout.tieLastName.getText().toString());
                    mLoginManager.setProfile(loggedInUser);

                }
            }
        });

        mSharedPreferenceManager.putPreferenceStringValueSync("temp", "temp");
        String val = mSharedPreferenceManager.getPreferenceStringValueWithKey("temp");
        Toast.makeText(this, val, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginManager.registerToProfileUpdate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginManager.unregisterToProfileUpdate();
    }

    @Override
    public void onProfileUpdateSuccess() {
        Intent welcomeActivityIntent = new Intent(
                ProfileActivity.this, WelcomeActivity.class);
        startActivity(welcomeActivityIntent);
    }

    @Override
    public void onProfileUpdateFalied(String message) {
        Intent welcomeActivityIntent = new Intent(
                ProfileActivity.this, WelcomeActivity.class);
        startActivity(welcomeActivityIntent);
    }
}
