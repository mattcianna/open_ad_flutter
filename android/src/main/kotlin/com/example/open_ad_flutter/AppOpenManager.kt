package com.example.open_ad_flutter

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenManager @JvmOverloads constructor(
    @NonNull application: Application,
    override var adUnitId: String = TEST_AD_UNIT_ID,
    override var adRequest: AdRequest = AdRequest.Builder().build(),
    override var orientation: Int = AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT
) : BaseManager(application),
    LifecycleObserver {

    private val TAG = "AppOpenManager"

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        showAdIfAvailable()
    }

    // Let's fetch the Ad
    fun fetchAd(showImmediately: Boolean = false) {
        if (isAdAvailable()) return
        if (OpenAdFlutterPlugin.isPaused) return
        loadAd(showImmediately)
        Log.d(TAG,"A pre-cached Ad was not available, loading one.")
    }

    // Show the Ad if the conditions are met.
    private fun showAdIfAvailable() {
        if (OpenAdFlutterPlugin.isPaused) return
        if (!isShowingAd &&
            isAdAvailable()
        ) appOpenAd?.show(
            currentActivity,
            getFullScreenContentCallback()
        )
        else {
            fetchAd(false)
        }
    }


    private fun loadAd(showImmediately: Boolean = false) {
        // this is good for informing the user :)
        if (adUnitId == TEST_AD_UNIT_ID)
            Log.d(
                TAG,
                "Current adUnitId is a Test Ad Unit Id, make sure to replace with yours in Production "
            )

        loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAppOpenAdLoaded(loadedAd: AppOpenAd) {
                this@AppOpenManager.appOpenAd = loadedAd
                this@AppOpenManager.loadTime = getCurrentTime()
                Log.d(TAG, "Ad Loaded")
                if (showImmediately) showAdIfAvailable()
            }

            override fun onAppOpenAdFailedToLoad(loadError: LoadAdError) {
                Log.e(TAG, "Ad Failed To Load, Reason: ${loadError.responseInfo}")
            }
        }
        AppOpenAd.load(
            getApplication(),
            adUnitId, adRequest,
            orientation,
            loadCallback
        )
    }

    // Handling the visibility of App Open Ad
    private fun getFullScreenContentCallback(): FullScreenContentCallback {
        return object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isShowingAd = false
                fetchAd(false)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.e(TAG, "Ad Failed To Show Full-Screen Content: ${adError?.message}")
            }

            override fun onAdShowedFullScreenContent() {
                isShowingAd = true
            }
        }
    }
}