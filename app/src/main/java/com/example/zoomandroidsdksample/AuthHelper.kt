package com.example.zoomandroidsdksample

import android.content.Context
import android.util.Log
import com.example.zoomandroidsdksample.APIConstants.Companion.APP_KEY
import com.example.zoomandroidsdksample.APIConstants.Companion.APP_SECRET
import com.example.zoomandroidsdksample.APIConstants.Companion.WEB_DOMAIN
import us.zoom.sdk.ZoomSDK
import us.zoom.sdk.ZoomSDKInitParams
import us.zoom.sdk.ZoomSDKInitializeListener
import us.zoom.sdk.ZoomSDKRawDataMemoryMode

class AuthHelper private constructor() : APIConstants, ZoomSDKInitializeListener {
    private val mZoomSDK: ZoomSDK = ZoomSDK.getInstance()
    private var mInitAuthSDKCallback: AuthCallback? = null


    fun initSDK(context: Context?, callback: AuthCallback?) {
        if (!mZoomSDK.isInitialized) {
            mInitAuthSDKCallback = callback
            val initParams = ZoomSDKInitParams()
            initParams.appKey = APP_KEY
            initParams.appSecret = APP_SECRET
            initParams.enableLog = true
            initParams.logSize = 50
            initParams.domain = WEB_DOMAIN
            initParams.videoRawDataMemoryMode =
                ZoomSDKRawDataMemoryMode.ZoomSDKRawDataMemoryModeStack
            mZoomSDK.initialize(context, this, initParams)
        }
    }

    override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
        Log.i(
            TAG,
            "onZoomSDKInitializeResult, errorCode=$errorCode, internalErrorCode=$internalErrorCode"
        )
        if (mInitAuthSDKCallback != null) {
            mInitAuthSDKCallback!!.onZoomSDKInitializeResult(errorCode, internalErrorCode)
        }
    }

    override fun onZoomAuthIdentityExpired() {
        Log.e(TAG, "onZoomAuthIdentityExpired in init")
    }

    companion object {
        private const val TAG = "AuthHelper"
        private var mInitAuthSDKHelper: AuthHelper? = null
        @get:Synchronized
        val instance: AuthHelper?
            get() {
                mInitAuthSDKHelper = AuthHelper()
                return mInitAuthSDKHelper
            }
    }

}
