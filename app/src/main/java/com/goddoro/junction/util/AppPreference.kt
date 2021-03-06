package com.goddoro.junction.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject


/**
 * created By DORO 5/18/21
 */

class AppPreference @Inject constructor(context: Application) {

    private val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    enum class KEY(val key: String) {
        // FCM token
        KEY_FCM_TOKEN("KEY_FCM_TOKEN"),


        KEY_IS_LOGIN("KEY_IS_LOGIN"),

        KEY_USER("KEY_USER"),

        // Device UUID
        KEY_DEVICE_UUID("KEY_DEVICE_UUID"),

        KEY_FEED_ORDER_BY("KEY_FEED_ORDER_BY")
    }

    @SuppressLint("ApplySharedPref")
    private operator fun set(key: KEY, value: Any?) {
        value?.let { value ->
            when (value) {
                is String -> preference.edit().putString(key.key, value).commit()
                is Int -> preference.edit().putInt(key.key, value).commit()
                is Boolean -> preference.edit().putBoolean(key.key, value).commit()
                is Float -> preference.edit().putFloat(key.key, value).commit()
                is Long -> preference.edit().putLong(key.key, value).commit()
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        } ?: kotlin.run {
            preference.edit().remove(key.key).commit()
        }
    }

    private inline operator fun <reified T : Any> get(key: KEY, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> preference.getString(key.key, defaultValue as? String) as T?
            Int::class -> preference.getInt(key.key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> preference.getBoolean(key.key, defaultValue as? Boolean ?: false) as T?
            Float::class -> preference.getFloat(key.key, defaultValue as? Float ?: -1f) as T?
            Long::class -> preference.getLong(key.key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }


    var curUser: String?
        get() = get(KEY.KEY_USER)
        @SuppressLint("ApplySharedPref")
        set(value) {
            if (value != null) {
                set(KEY.KEY_USER, value)
            } else {
                preference.edit().remove(KEY.KEY_USER.key).commit()
            }
        }

    var curDeviceUUID: String
        get() = get(KEY.KEY_DEVICE_UUID) ?: ""
        set(value) = set(KEY.KEY_DEVICE_UUID, value)


    var curFCMToken: String
        get() = get(KEY.KEY_FCM_TOKEN) ?: ""
        set(value) = set(KEY.KEY_FCM_TOKEN, value)

    var isLogin : Boolean
        get() = get(KEY.KEY_IS_LOGIN) ?: false
        set(value) = set(KEY.KEY_IS_LOGIN,value)


}