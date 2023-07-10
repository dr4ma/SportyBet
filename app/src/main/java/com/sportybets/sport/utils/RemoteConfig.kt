package com.sportybets.sport.utils

import android.util.Log
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

private const val LINK = "link"
private const val REGION = "GEO"

class RemoteConfig {

    private lateinit var remoteConfig : FirebaseRemoteConfig

    private val defaults : HashMap<String, Any> =
        hashMapOf(
            LINK to "",
            REGION to ""
        )

    fun getConfig(onSuccess:() -> Unit, onError :() -> Unit){
        try {
            remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                    5
                } else {
                    10
                }
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.setDefaultsAsync(defaults)
            onSuccess()
        }
        catch (e : Exception){
            Log.e("TAG", e.message.toString())
            onError()
        }
    }

    fun getLink(onSuccess :(url : String) -> Unit){
        remoteConfig.fetchAndActivate().addOnSuccessListener {
           onSuccess(remoteConfig.getString(LINK))
        }
    }

    fun getRegion(onSuccess :(geo : String) -> Unit){
        remoteConfig.fetchAndActivate().addOnSuccessListener {
            onSuccess(remoteConfig.getString(REGION))
        }
    }
}