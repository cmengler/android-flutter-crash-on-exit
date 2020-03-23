import 'package:chewie/chewie.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'package:webview_flutter/webview_flutter.dart';

//void main() => runApp(VideoApp());
void main() => runApp(PlatformViewApp());

class PlatformViewApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Platform View Demo',
      home: Scaffold(
        body: Center(
          child: WebView(
            initialUrl: 'https://flutter.dev',
            javascriptMode: JavascriptMode.unrestricted,
          ),
        ),
      ),
    );
  }
}

class VideoApp extends StatefulWidget {
  @override
  _VideoAppState createState() => _VideoAppState();
}

class _VideoAppState extends State<VideoApp> {
  VideoPlayerController _videoPlayerController;
  ChewieController _chewieController;

@override
  void initState() {
    super.initState();
    _videoPlayerController = VideoPlayerController.asset('assets/SampleVideo_1280x720_5mb.mp4');
    _chewieController = ChewieController(
      videoPlayerController: _videoPlayerController,
      autoPlay: true,
      showControls: true,
      looping: true,
      autoInitialize: true,
    );
  }

  @override
  void dispose() {
    _videoPlayerController.dispose();
    _chewieController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Video Demo',
      home: Scaffold(
        body: Center(
          child: Chewie(controller: _chewieController),
        ),
      ),
    );
  }
}
