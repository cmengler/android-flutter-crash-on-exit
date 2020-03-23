package com.example.android_crash_on_exit

import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity: FragmentActivity() {
    companion object {
        private const val FLUTTER_FRAGMENT_TAG = "flutter_fragment"
        private const val FLUTTER_ENGINE_ID = "flutter_engine_test"
    }

    private var flutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        val flutterEngine = FlutterEngine(this)

        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
                .getInstance()
                .put(FLUTTER_ENGINE_ID, flutterEngine)

        val fragmentManager: FragmentManager = supportFragmentManager

        flutterFragment = fragmentManager.findFragmentByTag(FLUTTER_FRAGMENT_TAG) as FlutterFragment?

        if (flutterFragment == null) {
            var newFlutterFragment: FlutterFragment = FlutterFragment.withCachedEngine(FLUTTER_ENGINE_ID).destroyEngineWithFragment(true).build()
            flutterFragment = newFlutterFragment
            fragmentManager
                    .beginTransaction()
                    .add(
                            R.id.fragment_container,
                            newFlutterFragment,
                            FLUTTER_FRAGMENT_TAG
                    )
                    .commit()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        flutterFragment!!.onPostResume()
    }

    override fun onNewIntent(@NonNull intent: Intent) {
        flutterFragment!!.onNewIntent(intent)
    }

    override fun onBackPressed() {
        flutterFragment!!.onBackPressed()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String?>,
            grantResults: IntArray
    ) {
        flutterFragment!!.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        )
    }

    override fun onUserLeaveHint() {
        flutterFragment!!.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment!!.onTrimMemory(level)
    }
}
