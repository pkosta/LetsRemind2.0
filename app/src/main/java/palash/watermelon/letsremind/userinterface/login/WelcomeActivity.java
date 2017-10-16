package palash.watermelon.letsremind.userinterface.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;

import palash.watermelon.letsremind.R;
import palash.watermelon.letsremind.databinding.ActivityLoginWelcomeBinding;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    public ActivityLoginWelcomeBinding mBindingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mBindingLayout = DataBindingUtil.setContentView(this,
                R.layout.activity_login_welcome);

        mBindingLayout.btnSignUpProcess.setOnClickListener(this);

        mBindingLayout.btnSignInProcess.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.btn_sign_up_process:
                Intent signUpActivityIntent = new Intent(this, EmailSignUpActivity.class);
                startActivity(signUpActivityIntent);
                break;
            case R.id.btn_sign_in_process:
                Intent signInActivityIntent = new Intent(this, LoginActivity.class);
                startActivity(signInActivityIntent);
                break;
        }
    }
}
