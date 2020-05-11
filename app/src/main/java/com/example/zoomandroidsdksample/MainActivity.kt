package com.example.zoomandroidsdksample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import us.zoom.sdk.*

class MainActivity : AppCompatActivity(), APIConstants, AuthCallback, ZoomSDKInitializeListener, MeetingServiceListener, ZoomSDKAuthenticationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zoomSDK = ZoomSDK.getInstance()

        if (savedInstanceState == null) {
            zoomSDK.initialize(this, APIConstants.APP_KEY, APIConstants.APP_SECRET, APIConstants.WEB_DOMAIN, this)
        }
    }

    override fun onZoomSDKInitializeResult(p0: Int, p1: Int) {
        
    }

    override fun onZoomSDKLoginResult(p0: Long) {
        
    }

    override fun onZoomIdentityExpired() {
        
    }

    override fun onZoomSDKLogoutResult(p0: Long) {
        
    }

    override fun onZoomAuthIdentityExpired() {
        
    }

    override fun onMeetingStatusChanged(p0: MeetingStatus?, p1: Int, p2: Int) {
        
    }

    fun onClickJoin(view: View?) {
        val meetingNo: String = etMeetingId.text.toString().trim { it <= ' ' }
        val name: String = etName.text.toString().trim { it <= ' ' }
        if (meetingNo.isEmpty() || etName.length() == 0) {
            Toast.makeText(this, resources.getString(R.string.enter_meeting_id_and_name), Toast.LENGTH_LONG).show()
            return
        }

        val zoomSDK = ZoomSDK.getInstance()
        if (!zoomSDK.isInitialized) {
            Toast.makeText(this, resources.getString(R.string.sdk_success_message), Toast.LENGTH_SHORT).show()
            AuthHelper.instance?.initSDK(this, this)
            return
        }

        if (ZoomSDK.getInstance().meetingSettingsHelper.isCustomizedMeetingUIEnabled) {
            ZoomSDK.getInstance().smsService.enableZoomAuthRealNameMeetingUIShown(false)
        } else {
            ZoomSDK.getInstance().smsService.enableZoomAuthRealNameMeetingUIShown(true)
        }

        val params = JoinMeetingParams()
        params.meetingNo = meetingNo
        params.displayName = name
        ZoomSDK.getInstance().meetingService.joinMeetingWithParams(this, params)
    }

}
