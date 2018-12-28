package com.bateman.rich.rmblibrary.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;

/**
 * A wrapper around SharedPreferences.  The SharedPreferences object has no easy way to persist doubles, but it is possible.
 * Also, SharedPreferences will ask you for a default when retrieving a value.  For ease, the default of the data type will be used,
 * but you can still specify a default to use.
 */
public class SharedAppData {

    private SharedPreferences m_sharedPreferences;

    /**
     * Loads the SharedApp data.  You may then ask this object for any saved data.
     * @param c Application Context (call getApplicationContext() from your activity)
     */
    public void load(Context c) {
        m_sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }
    public int getInt(String key, int defaultValue) {
        return m_sharedPreferences.getInt(key, defaultValue);
    }
    public void putInt(String key, int value) {
        m_sharedPreferences.edit().putInt(key, value).commit();
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }
    public double getDouble(String key, double defaultValue) {
        return getDouble(m_sharedPreferences, key, defaultValue);
    }
    public void putDouble(String key, double value) {
        putDouble(m_sharedPreferences.edit(), key, value).commit();
    }

    public String getString(String key) {
        return getString(key, "");
    }
    public String getString(String key, String defaultValue) {
        return m_sharedPreferences.getString(key, defaultValue);
    }
    public void putString(String key, String value) {
        m_sharedPreferences.edit().putString(key, value).commit();
    }

    public Serializable getSerializable(String key) {
        String serializedString = getString(key);
        Serializable serializedObject = ObjectSerializerHelper.stringToObject(serializedString);
        return serializedObject;
    }
    public void putSerializable(String key, Serializable object) {
        String serializedString = ObjectSerializerHelper.objectToString(object);
        putString(key, serializedString);
    }

    // From: https://stackoverflow.com/questions/16319237/cant-put-double-sharedpreferences
    // This converts to/from a double's "raw long bits" equivalent and stores it as a long.
    // The two data types have the same size.
    private SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    private double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
}
