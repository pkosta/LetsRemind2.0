package palash.watermelon.letsremind.usecase;

/*
 * Created by Palash on 28/09/17.
 */

import android.text.TextUtils;

public class PasswordValidator {

    public static boolean isPasswordValid(CharSequence aPassword) {
        return !TextUtils.isEmpty(aPassword) && aPassword.length() > 6;
    }

    public static boolean isPasswordsMatches (CharSequence password, CharSequence reEnterPassword) {
        return !TextUtils.isEmpty(password) && !TextUtils.isEmpty(reEnterPassword)
                && TextUtils.equals(password, reEnterPassword);
    }

}
