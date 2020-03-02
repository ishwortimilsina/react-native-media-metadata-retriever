## Forked to fix [Error: setDataSource failed: status = 0xFFFFFFEA]. Returning few more datapoints.

# react-native-media-metadata-retriever
React Native Media Metadata Retriever API for Android

`npm install https://github.com/ishwortimilsina/react-native-media-metadata-retriever.git --save`

Implementation of [MediaMetadataRetriever](https://developer.android.com/reference/kotlin/android/media/MediaMetadataRetriever) for react native

#### Import Module:

`import RNMediaMetadataRetriever from 'react-native-media-metadata-retriever' `

#### Retrieve Metadata from A song

```
RNMediaMetadataRetriever.getMetadata(uri)
  .then((info) => {
      console.log(info)
  })
  .catch((error) => {
      console.log(error)
  })
```

#### Response :

```
{ 
  title: 'Give In To Me',
  artist: 'Michael Jackson',
  duration: '328829',
  album: 'Dangerous',
  genre: 'Hip Hop',
  mime_type: 'audio/aac',
  sample_rate: 44100, // 0 for wav files
  num_channels: 2, // 0 for wav files
  bit_rate: 64000 // 0 for wav files
}
```

#### Get Embeded picture :
```
RNMediaMetadataRetriever.getPicture(this.state.active.url)
  .then((response) => {
      if(response.artcover){
          let track = this.state.active;
          track.artcover = response.artcover;
          this.setState({
              active: track
          })
      }
  })
  .catch((error) => {
      console.log(error);
  })
  
  ```
  
  #### Response :
  `{ artcover: http://kannadasongs.cc/thumbs/42.png }`
