package com.rnmediametadataretriever;

import android.media.MediaMetadataRetriever;
import android.media.MediaFormat;
import android.media.MediaExtractor;
import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.io.IOException;



public class RNMediaMetadataRetrieverModule extends ReactContextBaseJavaModule {
  
  private static final String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";
  private static final String IO_ERROR = "IO_ERROR";

  @Override
  public String getName() {
    return "RNMediaMetadataRetriever";
  }


  @ReactMethod
  public void getMetadata(String uri, Promise promise) {
     WritableMap map = Arguments.createMap();
     MediaMetadataRetriever mmr = new MediaMetadataRetriever();
     MediaExtractor mex = new MediaExtractor();
    try{
      mmr.setDataSource(uri);

      String songGenre = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
      String album =  mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
      String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
      String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
      String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
      String mimeType = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
      int bitRate = 0;
      int sampleRate = 0;
      int numChannels = 0;

      // MediaFormat cannot grab these data from wav files, so we will exclude wav files
      if (mimeType != null && !mimeType.contains("wav")) {
        try {
          mex.setDataSource(uri);
        } catch (IOException e) {
          promise.reject(IO_ERROR, e);
        }
        MediaFormat mf = mex.getTrackFormat(0);

        if (mf.containsKey(MediaFormat.KEY_BIT_RATE)) {
          bitRate = mf.getInteger(MediaFormat.KEY_BIT_RATE);
        }
        if (mf.containsKey(MediaFormat.KEY_SAMPLE_RATE)) {
          sampleRate = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        }
        if (mf.containsKey(MediaFormat.KEY_CHANNEL_COUNT)) {
          numChannels = mf.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        }
      }

      map.putString("genre"  ,songGenre);
      map.putString("album",album);
      map.putString("duration",duration);
      map.putString("artist", artist);
      map.putString("title", title);
      map.putString("mime_type", mimeType);
      map.putInt("bit_rate", bitRate);
      map.putInt("sample_rate", sampleRate);
      map.putInt("num_channels", numChannels);

      promise.resolve(map);

    } catch(RuntimeException exp){

       promise.reject(NOT_FOUND_ERROR,exp);

    } finally {
      mmr.release();
    }
  }

  @ReactMethod
  public void getPicture(String uri, Promise promise){
    WritableMap map = Arguments.createMap();
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    try{
      byte[] data = mmr.getEmbeddedPicture();
      if(data != null){
        String artcover = new String();
        map.putString("artcover", artcover);
      } else{
        map.putString("artcover", null);
      }
      promise.resolve(map);
    } catch(RuntimeException exp){
      promise.reject(NOT_FOUND_ERROR,exp);
    } finally {
      mmr.release();
    }
  }

  public RNMediaMetadataRetrieverModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }
}
