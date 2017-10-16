package palash.watermelon.letsremind.userinterface.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.android.AndroidInjection
import palash.watermelon.letsremind.R
import palash.watermelon.letsremind.login.LoginManager
import palash.watermelon.letsremind.userinterface.WelcomeActivity
import palash.watermelon.letsremind.utility.SharedPreferenceKeys
import palash.watermelon.letsremind.utility.SharedPreferenceManager
import javax.inject.Inject

public class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mSharedPrefManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (mSharedPrefManager.getPreferenceBooleanValueWithKey(
                SharedPreferenceKeys.IS_USER_LOGGED_IN)) {

            val homeActivityIntent: Intent = Intent(this, WelcomeActivity::class.java);
            startActivity(homeActivityIntent)

        } else {

            val welcomeActivityIntent: Intent = Intent(this, palash.watermelon.letsremind.userinterface.login.WelcomeActivity::class.java);
            startActivity(welcomeActivityIntent)

        }

        finish()

    }
}
