package  com.developer.news

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.graphics.Typeface
import com.developer.news.di.AppComponent
import com.developer.news.di.AppModule
import com.developer.news.di.DaggerAppComponent
import com.developer.news.util.AppSignatureHelper
import com.developer.news.util.MainConstants.Companion.LANGUAGE_ARABIC
import com.zeugmasolutions.localehelper.BuildConfig
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasBroadcastReceiverInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import java.lang.reflect.Field
import java.util.*
import javax.inject.Inject


@Suppress("DEPRECATION")
open class ThisApp : LocaleAwareApplication(), Thread.UncaughtExceptionHandler, HasActivityInjector,
    HasServiceInjector,
    HasBroadcastReceiverInjector {

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var receiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var exec: AppExecutors

    override fun activityInjector() = activityInjector
    override fun broadcastReceiverInjector() = receiverInjector
    override fun serviceInjector() = serviceInjector

    private val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var instance: ThisApp
            private set
        val ThisAppContext: Context?
            get() = instance
    }

    // var currentShownActivity: BaseHomeActivity? = null
    var isAppInBackground = false
    var notificationId = 1

    val language: String
        get() {
            val res = resources
            val conf = res.configuration
            val language: String
            language =
                if (conf?.locale?.displayLanguage?.toLowerCase() == Locale(LANGUAGE_ARABIC).displayLanguage.toLowerCase()) {
                    "ar"
                } else {
                    "en"
                }

            return language
        }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        setDefaultFont("helvetica.ttf", "helvetica.ttf")
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // This code requires one time to get Hash keys do comment and share key
        val appSignature = AppSignatureHelper(this)
        Timber.tag("AppSignature").v(appSignature.appSignatures.toString())

    }


    private fun setDefaultFont(fontName: String, fontNameBold: String) {
        val bold = Typeface.createFromAsset(assets, fontNameBold)
        val italic = Typeface.createFromAsset(assets, fontName)
        val boldItalic = Typeface.createFromAsset(assets, fontNameBold)
        val regular = Typeface.createFromAsset(assets, fontName)

        val DEFAULT: Field
        try {
            DEFAULT = Typeface::class.java.getDeclaredField("DEFAULT")
            DEFAULT.isAccessible = true
            DEFAULT.set(null, regular)
        } catch (e: Exception) {
            Timber.e(e)
        }

        val DEFAULT_BOLD: Field
        try {
            DEFAULT_BOLD = Typeface::class.java.getDeclaredField("DEFAULT_BOLD")
            DEFAULT_BOLD.isAccessible = true
            DEFAULT_BOLD.set(null, bold)
        } catch (e: java.lang.Exception) {
            Timber.e(e)
        }

        val sDefaults: Field
        try {
            sDefaults = Typeface::class.java.getDeclaredField("sDefaults")
            sDefaults.isAccessible = true
            sDefaults.set(null, arrayOf(regular, bold, italic, boldItalic))
        } catch (e: java.lang.Exception) {
            Timber.e(e)
        }

        val monoField: Field
        try {
            monoField = Typeface::class.java.getDeclaredField("MONOSPACE")
            monoField.isAccessible = true
            monoField.set(null, regular)
        } catch (e: NoSuchFieldException) {
            Timber.e(e)
        }

        val SERIF: Field
        try {
            SERIF = Typeface::class.java.getDeclaredField("SERIF")
            SERIF.isAccessible = true
            SERIF.set(null, regular)
        } catch (e: Exception) {
            Timber.e(e)
        }

        val SANS_SERIF: Field
        try {
            SANS_SERIF = Typeface::class.java.getDeclaredField("SANS_SERIF")
            SANS_SERIF.isAccessible = true
            SANS_SERIF.set(null, regular)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }


    override fun uncaughtException(thread: Thread, throwable: Throwable) {

    }


}
