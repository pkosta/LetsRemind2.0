package palash.watermelon.letsremind.userinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import palash.watermelon.letsremind.R;
import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.login.LoginManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tvSample = (TextView) findViewById(R.id.sample);

        AppUser appUser = new LoginManager().getLoggedInUser();

        tvSample.setText(appUser.getUserName());

    }
}
