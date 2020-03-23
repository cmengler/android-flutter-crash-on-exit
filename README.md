# android_crash_on_exit

## Steps to reproduce

- Open app, allow page to render.
- Press back button to exit app.

## Result

- App crashes.

## Expected

- App closes gracefully.

---

This crash appears to occur for both platform views (e.g `WebView`) and `video_player` (Textures?).

```log
2020-03-23 14:04:43.231 32174-32174/com.example.android_crash_on_exit E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.example.android_crash_on_exit, PID: 32174
    java.lang.RuntimeException: Cannot execute operation because FlutterJNI is not attached to native.
        at io.flutter.embedding.engine.FlutterJNI.ensureAttachedToNative(FlutterJNI.java:227)
        at io.flutter.embedding.engine.FlutterJNI.markTextureFrameAvailable(FlutterJNI.java:554)
        at io.flutter.embedding.engine.renderer.FlutterRenderer.markTextureFrameAvailable(FlutterRenderer.java:274)
        at io.flutter.embedding.engine.renderer.FlutterRenderer.access$300(FlutterRenderer.java:38)
        at io.flutter.embedding.engine.renderer.FlutterRenderer$SurfaceTextureRegistryEntry$1.onFrameAvailable(FlutterRenderer.java:145)
        at android.graphics.SurfaceTexture$1.handleMessage(SurfaceTexture.java:211)
        at android.os.Handler.dispatchMessage(Handler.java:107)
        at android.os.Looper.loop(Looper.java:237)
        at android.app.ActivityThread.main(ActivityThread.java:7777)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:493)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1047)
```

---

The platform view crash can be solved by adding `flutterEngine.getPlatformViewsController().onFlutterViewDestroyed()` on `onDestroy` of `MainActivity`.
