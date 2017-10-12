package palash.watermelon.letsremind.usecase;

/*
 * Created by Palash on 28/09/17.
 */

import android.text.TextUtils;

public class EmailValidator {

    public static boolean isValidEmail(CharSequence aEmail) {

        return !TextUtils.isEmpty(aEmail)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(aEmail).matches();

    }

}
