package com.example.justscanitt;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Manages the app's language settings using SharedPreferences and updates the locale configuration.
 */
public class LanguageManger {
    private Context ct; // Context used to access resources and preferences
    private SharedPreferences sharedPreferences;

    // Constructor to initialize context and SharedPreferences
    public LanguageManger(Context ct) {
        this.ct = ct;
        sharedPreferences = ct.getSharedPreferences("LANG", Context.MODE_PRIVATE); // "LANG" is the key used to store language preference
    }

    /**
     * Updates the app's locale (language setting) to the given code.
     * Also saves the selected language code in SharedPreferences.
     *
     * @param code Language code like "en" for English, "ru" for Russian, etc.
     */
    public void updateResource(String code) {
        Locale locale = new Locale(code); // Create a new Locale with the given code
        Locale.setDefault(locale); // Set it as the default locale

        Resources resources = ct.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale; // Apply the locale to configuration
        resources.updateConfiguration(configuration, resources.getDisplayMetrics()); // Update configuration

        setLang(code); // Save the selected language code in preferences
    }

    /**
     * Retrieves the currently saved language code from SharedPreferences.
     * Defaults to English ("en") if not set.
     *
     * @return the language code
     */
    public String getLang() {
        return sharedPreferences.getString("lang", "en");
    }

    /**
     * Saves the selected language code in SharedPreferences.
     *
     * @param code the language code to save
     */
    public void setLang(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", code); // Store language code under key "lang"
        editor.commit(); // Save changes
    }
}
