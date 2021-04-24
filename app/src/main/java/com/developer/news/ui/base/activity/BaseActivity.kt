package com.developer.news.ui.base.activity;

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.developer.news.R
import com.developer.news.util.MainConstants.Companion.INTENT_INFO_1
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal
import spencerstudios.com.bungeelib.Bungee
import java.io.Serializable
import javax.inject.Inject


abstract class BaseActivity : LocaleAwareCompatActivity(),
    HasSupportFragmentInjector {


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private var waitingAlertDialog: Dialog? = null
    private var errorAlertDialog: Dialog? = null

    // No Internet Dialog
//    private var noInternetDialog: NoInternetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        noInterentConnection()

    }

    fun runOnUi(runnable: () -> Unit, delay: Long) {
        val h = Handler(Looper.getMainLooper())
        h.postDelayed(runnable, delay)
    }


    fun noInterentConnection() {
        //--------------------------------

        // No Internet Dialog: Signal
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {

                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = getString(R.string.no_interent) // Optional
                noInternetConnectionMessage =
                    getString(R.string.check_your_interent_connection_and_try_again) // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = getString(R.string.plz_turn_on) // Optional
                wifiOnButtonText = getString(R.string.wifi) // Optional
                mobileDataOnButtonText = getString(R.string.mobile_data) // Optional

                onAirplaneModeTitle = getString(R.string.no_interent) // Optional
                onAirplaneModeMessage = getString(R.string.you_turened_on_airplan_mode) // Optional
                pleaseTurnOffText = getString(R.string.plz_turn_off) // Optional
                airplaneModeOffButtonText = getString(R.string.airplane_mode) // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }.build()


    }


    fun openActivityWithSlidingUpAndInfoInt(activityClass: Class<*>, info: Serializable) {
        val intent = Intent(this, activityClass)
        intent.putExtra(INTENT_INFO_1, info)
        startActivity(intent)
        Bungee.swipeRight(this)


    }


    open fun showErrors(
        baseActivity: Activity,
        errorMessages: String?,
        click: () -> Unit
    ) {
        val context: Context = baseActivity
        val dialog = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.alert))
            .setMessage(errorMessages)
            .setPositiveButton(
                context.getString(R.string.ok)
            ) { dialogInterface: DialogInterface, i: Int ->
                run {
                    dialogInterface.dismiss();
                }
            }
            .create()
        dialog.show()
    }

    open fun showErrors(
        baseActivity: Activity,
        errorMessages: String?
    ) {
        val context: Context = baseActivity
        val dialog = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.alert))
            .setMessage(errorMessages)
            .setPositiveButton(
                context.getString(R.string.ok)
            ) { dialogInterface: DialogInterface, i: Int ->
                run {
                    dialogInterface.dismiss();
                }
            }
            .create()
        dialog.show()
    }


    fun showShortToast(@StringRes stringRes: Int) {
        runOnUiThread {
            Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
        }
    }

    fun showShortToast(string: String) {
        runOnUiThread {
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            0,
            R.anim.close_activity_on_back
        )
    }


}