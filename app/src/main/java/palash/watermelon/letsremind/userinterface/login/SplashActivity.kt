package palash.watermelon.letsremind.userinterface.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import palash.watermelon.letsremind.login.LoginManager
import palash.watermelon.letsremind.userinterface.HomeActivity
import javax.inject.Inject

public class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mLoginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        /*val homeActivityIntent: Intent = Intent(this, WelcomeActivity::class.java);
        startActivity(homeActivityIntent)*/

        if (mLoginManager.isProfileAdded) {

            // if the profile is added then we need to navigate to home screen
            // profile added means isUserVerified and isUserLoggedIns
            val homeActivityIntent: Intent = Intent(this, HomeActivity::class.java);
            startActivity(homeActivityIntent)

        } else if (mLoginManager.isUserVerified) {

            // if the user is verified through the email but profile will be added afterwards
            val profileActivityIntent: Intent = Intent(this, ProfileActivity::class.java);
            startActivity(profileActivityIntent)

        } else if (mLoginManager.isUserLoggedIn) {

            // if the user is logged in
            // TODO: for not redirect to the profile activity.
            val profileActivityIntent: Intent = Intent(this, ProfileActivity::class.java);
            startActivity(profileActivityIntent)

        } else {

            // no options has been made,,, redirect it to the welcome Activity
            val welcomeActivityIntent: Intent = Intent(this, WelcomeActivity::class.java);
            startActivity(welcomeActivityIntent)

        }

        finish()        // clear the activity from the back stack

    }
}
