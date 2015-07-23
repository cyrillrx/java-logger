package com.cyrillrx.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * @author Cyril Leroux
 *         Created on 19/12/2014.
 */
public class PrefUtils {

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static SharedPreferences.Editor edit(final Context context) {
        return getPreferences(context).edit();
    }

    /**
     * Retrieves a string value from the shared preferences.
     *
     * @param context The context.
     * @param key     The name of the preference to retrieve.
     * @return The preference value if it exists, or an empty String.
     * @throws ClassCastException if there is a preference with this name that is not a String.
     */
    public static String getString(Context context, String key) {
        return getPreferences(context).getString(key, "");
    }

    /**
     * Retrieves a boolean value from the shared preferences.
     *
     * @param context The context.
     * @param key     The name of the preference to retrieve.
     * @return The preference value if it exists, or defaultValue.
     * @throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    private static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPreferences(context).getBoolean(key, defaultValue);
    }

    /**
     * Retrieves a long value from the shared preferences.
     *
     * @param context The context.
     * @param key     The name of the preference to retrieve.
     * @return The preference value if it exists, or defaultValue.
     * @throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    public static long getLong(Context context, String key) {
        return getPreferences(context).getLong(key, 0l);
    }

    /**
     * Serializes and saves the given object to the shared preferences.
     *
     * @param context The context.
     */
    public static void saveObject(Context context, String key, Object object) {
        PrefUtils.edit(context)
                .putString(key, new Gson().toJson(object))
                .apply();
    }

    /**
     * Gets the serialized object from the shared preferences and de-serializes it.
     *
     * @param context The context.
     * @param key     The name of the preference to retrieve.
     * @param clazz   The type of the object.
     * @return The de-serializes object or null.
     */
    public static <T> T loadObject(Context context, String key, Class<T> clazz) {

        final String serializedData = getString(context, key);
        if (serializedData.isEmpty()) { return null; }

        try {
            return new Gson().fromJson(serializedData, clazz);
        } catch (Exception e) {
            // If a problem occurred while parsing, better clear the stored field.
            PrefUtils.edit(context)
                    .remove(key)
                    .apply();
            return null;
        }
    }
}