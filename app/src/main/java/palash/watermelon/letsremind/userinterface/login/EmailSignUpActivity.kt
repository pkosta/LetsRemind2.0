package palash.watermelon.letsremind.userinterface.login

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import palash.watermelon.letsremind.R
import palash.watermelon.letsremind.databinding.ActivityRegistrationBinding
import palash.watermelon.letsremind.datamodel.AppUser
import palash.watermelon.letsremind.login.ILoginResult
import palash.watermelon.letsremind.login.LoginManager
import palash.watermelon.letsremind.usecase.EmailValidator
import palash.watermelon.letsremind.usecase.PasswordValidator
import javax.inject.Inject

/*
*
*/
class EmailSignUpActivity : AppCompatActivity(), ILoginResult, View.OnClickListener {

    private var mBindingLayout: ActivityRegistrationBinding? = null;

    var mLoginManager: LoginManager? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindingLayout = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        mBindingLayout!!.btnSignUp.setOnClickListener(this)
        mLoginManager = LoginManager()

    }

    override fun onResume() {
        super.onResume()
        mLoginManager!!.registerToLoginManager(this)
    }

    override fun onPause() {
        super.onPause()
        mLoginManager!!.unregisterToLoginManager()
    }

    /*
    * View On Click Listener
    */
    override fun onClick(p0: View?) {
        val id: Int = p0!!.id;
        when(id) {
            R.id.btn_sign_up -> {
                signUp()
            }
        }
    }

    private fun signUp() {

        // check for validity of email and password
        val email : String? = mBindingLayout?.tieUserName?.text.toString()
        val password : String? = mBindingLayout?.tiePassword?.text.toString()
        val reEnterPassword : String? = mBindingLayout?.tieReenterPassword?.text.toString()

        if (email != null && password != null && reEnterPassword != null) {
            if (EmailValidator.isValidEmail(email)) {

                // email is ok
                if (PasswordValidator.isPasswordValid(password)) {

                    // password is ok
                    if (PasswordValidator.isPasswordsMatches(password, reEnterPassword)) {

                        // ALL IS OK
                        showProgressBar(true)
                        mLoginManager!!.signUp(email, password)

                    } else {
                        // password is not matching.
                        showInvalidDialogs(getString(R.string.error_title_password_not_match),
                                getString(R.string.error_message_password_not_match))
                    }

                } else {
                    // password is not valid
                    showInvalidDialogs(getString(R.string.error_title_password_not_valid),
                            getString(R.string.error_message_password_not_valid))
                }

            } else {
                // email is not valid
                showInvalidDialogs(getString(R.string.error_title_email_not_valid),
                        getString(R.string.error_message_email_not_valid));
            }
        } else{
            //TODO :- unexpected error...close the application
        }
    }


    /*
    * Login Manager Result Implementation
    */
    override fun onLoginSuccess(user: AppUser?) {
        showProgressBar(false)
        Toast.makeText(this, "Authentication Success", Toast.LENGTH_LONG).show()
        val profileActivityIntent: Intent = Intent(this, ProfileActivity::class.java)
        startActivity(profileActivityIntent)
    }

    override fun onLoginFailure(errorMessage: String?) {
        showProgressBar(false)
        // TODOs
        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
    }


    fun showInvalidDialogs(titleText : String, messageText : String) {
        alert {
            message = messageText
            yesButton {  }
        }.show()
    }

    fun showProgressBar(flag : Boolean) {
        if (flag) {
            mBindingLayout?.indeterminateBar?.visibility = View.VISIBLE;
            enableLayoutViews(false)
            mBindingLayout?.tieReenterPassword?.isEnabled = false
        } else {
            mBindingLayout?.indeterminateBar?.visibility = View.GONE;
            enableLayoutViews(true)
        }
    }

    fun enableLayoutViews(flag : Boolean) {
        mBindingLayout?.btnSignUp?.isEnabled = flag;
        mBindingLayout?.tieUserName?.isEnabled = flag
        mBindingLayout?.tiePassword?.isEnabled = flag
        mBindingLayout?.tieReenterPassword?.isEnabled = flag
    }
}
