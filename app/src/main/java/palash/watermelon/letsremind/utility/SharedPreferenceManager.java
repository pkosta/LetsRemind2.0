package palash.watermelon.letsremind.utility;

/*
 * Created by Palash on 27/09/17.
 */

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferenceManager {

    private SharedPreferences preferences;

    public SharedPreferenceManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void putPreferenceStringValueAsync(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putPreferenceStringValueSync(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPreferenceStringValueWithKey(String key) {
        return preferences.getString(key, "");
    }

}
