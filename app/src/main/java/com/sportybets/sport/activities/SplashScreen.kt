package com.sportybets.sport.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.sportybets.sport.R
import com.sportybets.sport.utils.AppPreferences
import com.sportybets.sport.utils.CheckConnection
import com.sportybets.sport.utils.KEY_INTENT
import com.sportybets.sport.utils.LoadScreenSettings
import com.sportybets.sport.utils.RemoteConfig


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private val checkConnection = CheckConnection()
    private val remoteConfig = RemoteConfig()
    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppPreferences.getPreferences(this)
        remoteConfig.getConfig({
            url = AppPreferences.getUrl()
            if (checkConnection.check(this)) {
                if (url.isEmpty()) {
                    remoteConfig.getLink { link ->
                        remoteConfig.getRegion { region ->
                            if (isEmulator() || !isSimAvailable() || link == "" || !isRegionValid(region)){
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra(KEY_INTENT, LoadScreenSettings.PLUG)
                                startActivity(intent)
                                finish()
                            }
                            else{
                                AppPreferences.setUrl(link)
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra(KEY_INTENT, LoadScreenSettings.WEB)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(KEY_INTENT, LoadScreenSettings.WEB)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(KEY_INTENT, LoadScreenSettings.ERROR)
                startActivity(intent)
                finish()
            }
        }, {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(KEY_INTENT, LoadScreenSettings.PLUG)
            startActivity(intent)
            finish()
        })
    }

    private fun isRegionValid(region : String) : Boolean{
        var result = false
        val userRegion = getRegion()
        val regionsArray = region.split(",")
        regionsArray.forEach {
            if(it == userRegion){
                result = true
                return@forEach
            }
        }
        return result
    }

    private fun getRegion() : String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0).country
        } else {
            resources.configuration.locale.country
        }
    }

    private fun isSimAvailable(): Boolean {
        var isAvailable = false
        val telMgr = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val firstSim = telMgr.getSimState(0)
            val secondSim = telMgr.getSimState(1)

            if (firstSim == TelephonyManager.SIM_STATE_READY) {
                isAvailable = true
            } else {
                if (secondSim == TelephonyManager.SIM_STATE_READY) {
                    isAvailable = true
                }
            }
        } else {
            if (telMgr.simState == TelephonyManager.SIM_STATE_READY) {
                isAvailable = true
            }
        }
        return isAvailable
    }

//    private fun isVpnActive() : Boolean{
//        val connectivityManager = getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork: Network? = connectivityManager.activeNetwork
//        val caps: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(activeNetwork)
//        return caps?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ?: false
//    }

    private fun isEmulator(): Boolean {
        return ((Build.MANUFACTURER == "Google" && Build.BRAND == "google" &&
                ((Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                        && Build.FINGERPRINT.endsWith(":user/release-keys")
                        && Build.PRODUCT.startsWith("sdk_gphone_")
                        && Build.MODEL.startsWith("sdk_gphone_"))
                        || (Build.FINGERPRINT.startsWith("google/sdk_gphone64_")
                        && (Build.FINGERPRINT.endsWith(":userdebug/dev-keys") || Build.FINGERPRINT.endsWith(
                    ":user/release-keys"
                ))
                        && Build.PRODUCT.startsWith("sdk_gphone64_")
                        && Build.MODEL.startsWith("sdk_gphone64_"))))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(
            Build.MANUFACTURER,
            ignoreCase = true
        )
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HOST.startsWith("Build")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.PRODUCT == "google_sdk")
    }
}