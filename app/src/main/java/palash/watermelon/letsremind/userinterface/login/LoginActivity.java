package palash.watermelon.letsremind.userinterface.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import palash.watermelon.letsremind.R;
import palash.watermelon.letsremind.databinding.ActivityLoginBinding;
import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.login.ILoginResult;
import palash.watermelon.letsremind.login.LoginManager;
import palash.watermelon.letsremind.usecase.EmailValidator;
import palash.watermelon.letsremind.usecase.PasswordValidator;
import palash.watermelon.letsremind.userinterface.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginResult {

    public ActivityLoginBinding mBindingLayout;

    @Inject
    public LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBindingLayout = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mBindingLayout.btnSignIn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginManager.registerToLoginManager(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginManager.unregisterToLoginManager();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_sign_in:
                doLoginIn();
                break;
        }
    }


    private void doLoginIn() {

        String emailId = mBindingLayout.tieUserName.getText().toString();

        String password = mBindingLayout.tiePassword.getText().toString();

        if (EmailValidator.isValidEmail(emailId)) {

            // EMAIL IS VALID
            if (PasswordValidator.isPasswordValid(password)) {
                // PASSWORD IS VALID
                // ALL IS OK...PROCEED
                showProgressBar(true);
                mLoginManager.signIn(emailId, password);

            } else {
                // PASSWORD IS NOT VALID
            }

        } else {
            // EMAIL IS NOT VALID

        }


    }

    @Override
    public void onLoginSuccess(AppUser user) {
        showProgressBar(false); //hide the progress bar
        Intent welcomeActivityIntent = new Intent(
                this, HomeActivity.class);
        welcomeActivityIntent
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(welcomeActivityIntent);
        finish();
    }

    @Override
    public void onLoginFailure(String errorMessage) {
        showProgressBar(false); // hide the progress bar
        //TODO: handle the login failure case.
    }

    private void showProgressBar(boolean flag) {
        if (flag) {
            mBindingLayout.indeterminateBar.setVisibility(View.VISIBLE);
            enableLayoutViews(false);
        } else {
            mBindingLayout.indeterminateBar.setVisibility(View.GONE);
            enableLayoutViews(true);
        }
    }

    private void enableLayoutViews(boolean flag) {
        mBindingLayout.tieUserName.setEnabled(flag);
        mBindingLayout.tiePassword.setEnabled(flag);
        mBindingLayout.btnSignIn.setEnabled(flag);
    }


}
