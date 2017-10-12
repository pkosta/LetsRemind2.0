package palash.watermelon.letsremind.userinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import palash.watermelon.letsremind.R;
import palash.watermelon.letsremind.login.LoginManager;
import palash.watermelon.letsremind.utility.SharedPreferenceManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);
    }
}
