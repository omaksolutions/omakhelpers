package com.omak.outils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class HelperFunctions {

    private static final String PREFS_NAME = "preferenceName";

    /*Realm Configuration */
    public static Realm getRealm(String whichRealm, Context applicationContext) {
        Realm.init(applicationContext);

        switch (whichRealm) {
            case "messages":
                RealmConfiguration config = new RealmConfiguration.Builder()
                        .name("messages.realm")
                        .deleteRealmIfMigrationNeeded()
                        //.schemaVersion(1)
                        //.migration(new MyRealMigration())
                        .build();

                Realm.setDefaultConfiguration(config);
                break;
        }
        return Realm.getDefaultInstance();
    }

    /*Getting Sim Selection */
    public static JSONObject getoldPreferences(String key, String variable, Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String variableString = preferences.getString(variable, "");
        HelperFunctions.theLogger("getPreferences", "Current object is - " + variableString);
        if (variableString.isEmpty()) return null;

        try {
            JSONObject variableObj = new JSONObject(variableString);

            if (!key.isEmpty()) {
                String keyString = variableObj.getString(key);
                //HelperFunctions.theLogger("Test myData", "" + data);
                JSONObject keyObj = new JSONObject(keyString);
                return keyObj;
            }

            return variableObj;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return null;
        }
    }

    /* Not Use This Method */
    public static JSONObject getPrefObject(String variable, Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String variableString = preferences.getString(variable, "");
        //if (variableString.isEmpty()) return null;
        try {
            JSONObject variableObj = new JSONObject(variableString);
            return variableObj;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return null;
        }
    }
  /*
        Get individual key from an object
        Returns:
            Null when object is null OR object is not a JSONObject
            String - whey key is available
            emptyString - when key does not exist
     */

    /*
        Get individual key from an object
        Returns:
            Null when object is null OR object is not a JSONObject
            String - whey key is available
            emptyString - when key does not exist
     */

    public static String getUserFullName(Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String variableString = preferences.getString("currentUser", "");

        // Return if the preferences does not have requested variable stored
        if (variableString.isEmpty()) return null;

        try {
            JSONObject variableObj = new JSONObject(variableString);
            String first_name = variableObj.has("first_name") ? variableObj.getString("first_name") : "";
            String last_name = variableObj.has("last_name") ? variableObj.getString("last_name") : "";
            return first_name + " " + last_name;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
        }

        return null;
    }

    /*Getting Key From This Helper Class*/
    public static String getPrefString(String key, String variable, Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String variableString = preferences.getString(variable, "");

        // Return if the preferences does not have requested variable stored
        if (variableString.isEmpty()) return null;

        try {
            JSONObject variableObj = new JSONObject(variableString);
            if (!key.isEmpty()) {
                String keyString = variableObj.has(key) ? variableObj.getString(key) : "";
                return keyString;
            }
        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return null;
        }
        return null;
    }

    public static String getUser_id(Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String variableString = preferences.getString("currentUser", "");

        // Return if the preferences does not have requested variable stored
        if (variableString.isEmpty()) return null;

        try {
            JSONObject variableObj = new JSONObject(variableString);
            String User_id = variableObj.has("id") ? variableObj.getString("id") : "";
            //String last_name = variableObj.has("last_name") ? variableObj.getString("last_name") : "";
            return User_id;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
        }

        return null;
    }


    /*Not USed Delete this */
    public static Boolean getPrefBoolean(String key, String variable, Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String variableString = preferences.getString(variable, "");

        // Return if the preferences does not have requested variable stored
        if (variableString.isEmpty()) return false;

        try {
            JSONObject variableObj = new JSONObject(variableString);
            if (!key.isEmpty()) {
                Boolean keyString = variableObj.has(key) && variableObj.getBoolean(key);
                return keyString;
            }
        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return false;
        }
        return false;
    }


    /*
        Get a saved JSONObject from the preferences
     */
    public static String getoldPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "defaultValue");
    }


    public static JSONObject getPreferences(String key, String variable, Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String variableString = preferences.getString(variable, "");
        HelperFunctions.theLogger("getPreferences", "Current object is - " + variableString);
        if (variableString.isEmpty()) return null;

        try {
            JSONObject variableObj = new JSONObject(variableString);

            if (!key.isEmpty()) {
                String keyString = variableObj.getString(key);
                //HelperFunctions.theLogger("Test myData", "" + data);
                JSONObject keyObj = new JSONObject(keyString);
                return keyObj;
            }

            return variableObj;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return null;
        }
    }

    public static void setPreferences(String key, Boolean data, String variable, Context context) {

        String Tag = "setPreferences";
        HelperFunctions.theLogger(Tag, "Will set data for " + key + " - " + data + " in " + variable);

        JSONObject keyObj = getPreferences("", variable, context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String value;
        HelperFunctions.theLogger(Tag, "Current value of " + variable + " is " + keyObj);

        if (keyObj == null) {
            HelperFunctions.theLogger(Tag, "I am null, setting up new JSONObject to store: " + key + " - " + data + " in " + variable);
            keyObj = new JSONObject();
        }

        try {
            keyObj.put(key, data);
        } catch (final JSONException e) {
            e.printStackTrace();
            HelperFunctions.theLogger("JSONException", "Could not put the data in key: " + e.getMessage());
        }

        HelperFunctions.theLogger(Tag, keyObj.toString());
        editor.putString(variable, keyObj.toString());
        editor.apply();
    }

    public static String get_pref_array(String array_name, String key, Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String arrayData = preferences.getString(array_name, "");
        HelperFunctions.theLogger("ArrayData1", "" + arrayData);

        try {
            //HelperFunctions.theLogger("FromPreference", login_data);
            JSONObject jsonObj = new JSONObject(arrayData);
            HelperFunctions.theLogger("ArrayData", "" + jsonObj);

            String keyValue = jsonObj.getString(key);
            HelperFunctions.theLogger("Test myData", "" + keyValue);

            return keyValue;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return "test-no-value";
        }
    }

    public static JSONObject get_pref_data(String key, Context context) {

        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String login_data = preferences.getString("login_data", "");

        try {
            //HelperFunctions.theLogger("FromPreference", login_data);
            JSONObject jsonObj = new JSONObject(login_data);
            //HelperFunctions.theLogger("jsonObb of login_data", "" + jsonObj);

            String data = jsonObj.getString(key);
            //HelperFunctions.theLogger("Test myData", "" + data);

            JSONObject current_user = new JSONObject(data);

            return current_user;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return null;
        }
    }

    public static void set_pref_data(String key, String val, Context context) {

        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String login_data = preferences.getString("login_data", "");
        theLogger("PRE GSON", login_data);

        try {
            //HelperFunctions.theLogger("FromPreference", login_data);
            JSONObject jsonObj = new JSONObject(login_data);
            //HelperFunctions.theLogger("jsonObb of login_data", "" + jsonObj);
            String current_user_string = jsonObj.getString("data");
            JSONObject current_user = new JSONObject(current_user_string);
            theLogger("Current User: ", "" + current_user);

            try {
                current_user.put(key, val);
                theLogger("After Put", "" + current_user);
                jsonObj.put("data", current_user);
            } catch (JSONException e) {
                HelperFunctions.theLogger("JSONEXCEPT", "Json parsing error: " + e.getMessage());
            }

            SharedPreferences.Editor editor = preferences.edit();
            theLogger("POST GSON for obejct", "" + jsonObj);
            editor.putString("login_data", "" + jsonObj);
            editor.apply();

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONEXCEPT", "Json parsing error: " + e.getMessage());
        }
    }

    /* public static JSONObject get_message(String key, Context context) {

        SharedPreferences preferences = android.preference.PreferenceManager2.getDefaultSharedPreferences(context);
        String MessageData = preferences.getString("Message", "");

        try {
            //HelperFunctions.theLogger("FromPreference", login_data);
            JSONObject jsonObj = new JSONObject(MessageData);
            //HelperFunctions.theLogger("jsonObb of login_data", "" + jsonObj);

            String data = jsonObj.getString(key);
            //HelperFunctions.theLogger("Test myData", "" + data);

            JSONObject current_user = new JSONObject(data);

            return current_user;

        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return null;
        }
    }
*/

    /*Method For Print Log */
    public static void theLogger(String tag, String message) {
        Log.e(tag, message);
    }

    public static String get_string(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (final JSONException e) {
            HelperFunctions.theLogger("JSONException", "Json parsing error: " + e.getMessage());
            return null;
        }
    }

    /* GEt Date Method*/
    public static Date convertString2Date(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String dateString = formatter.format(date);

        return dateString;
    }

    public static String getFormattedDateFromString(String dateString) {
        Date date = convertString2Date(dateString);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public static String getFormattedDateFromString(String dateString, String format) {
        Date date = convertString2Date(dateString);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static void log(String Title, String message) {
        Log.e(Title, message);
    }

    public static void tost(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /*Get Device Name Here*/
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            HelperFunctions.theLogger("My Device Name ", "IS" + model);
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    public static JSONObject addApiPair(String key, String value) {
        JSONObject keyValuePair = new JSONObject();
        try {
            keyValuePair.put("key", key);
            keyValuePair.put("value", value);
        } catch (JSONException e) {

        }
        return keyValuePair;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    // hide keyboard after it use
    public void hidekeyboard(Context context) {
     /*   InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
 */
    }

    public String getDate() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public String dateDifference(String dateStart, String dateStop) {

        //dateStart = "01/14/2012 09:29:58";
        //dateStop = "01/15/2012 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            return Long.toString(diffSeconds);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String setupTimeZoneValue() {
        TimeZone tz = TimeZone.getDefault();
        return getCurrentTimezoneOffset() + "/" + tz.getRawOffset();
    }

    public String getCurrentTimezoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        String offset = String.format(Locale.getDefault(), "%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = "GMT" + (offsetInMillis >= 0 ? "+" : "-") + offset;

        return offset + "/" + offsetInMillis;
    }

    public String convertUnix2Date(Long time) {
        return DateFormat.format("dd-MM-yyyy hh:mm:ss", time).toString();
    }

    /////////////set data function by shared prefrence //////

   /* private static final String PREFS_NAME = "preferenceName";
    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        ./
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "defaultValue");
    }
*/


}
