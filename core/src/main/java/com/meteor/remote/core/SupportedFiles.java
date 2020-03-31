package com.meteor.remote.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SupportedFiles {

   public enum MediaType {
      UNSUPPORTED,  ///< file not supported by application
      AUDIO,
      PICTURE,
      VIDEO
   }

   private final List<String> AUDIO_EXTENSION =
           Arrays.asList("wav", "mp3", "au", "ogg", "opus");
   private final List<String> PICTURE_EXTENSION =
           Arrays.asList("png", "jpg", "jpeg", "gif", "svg");
   private final List<String> VIDEO_EXTENSION =
           Arrays.asList("avi", "mp4", "mpg", "flv", "mkv", "mts", "mov", "ogv");

   public SupportedFiles() {

      for (int i = 0; i < AUDIO_EXTENSION.size(); ++i) {
         FileTypeLookupTable.put(AUDIO_EXTENSION.get(i), MediaType.AUDIO);
      }

      for (int i = 0; i < PICTURE_EXTENSION.size(); ++i) {
         FileTypeLookupTable.put(PICTURE_EXTENSION.get(i), MediaType.PICTURE);
      }

      for (int i = 0; i < VIDEO_EXTENSION.size(); ++i) {
         FileTypeLookupTable.put(VIDEO_EXTENSION.get(i), MediaType.VIDEO);
      }

      IconTagBaseLookupTable.put( MediaType.UNSUPPORTED, "OTHER");
      IconTagBaseLookupTable.put( MediaType.AUDIO, "AUDIO");
      IconTagBaseLookupTable.put( MediaType.PICTURE, "PICTURE");
      IconTagBaseLookupTable.put( MediaType.VIDEO, "VIDEO");
   }

   /**
    * this functions associates a type to the extension of
    * given media label, (based on extension)
    *
    * @param mediaName
    * @return
    */
   public MediaType typeForMediaName( /*const*/ String mediaName) {

      MediaType mediaType = MediaType.UNSUPPORTED;

      // only extract extension
      String[] parts = mediaName.split("\\.");

      if (parts.length > 1) {

         mediaType = FileTypeLookupTable.get(parts[parts.length - 1]);
         if (mediaType == null) {
            mediaType = MediaType.UNSUPPORTED;
         }
      }

      return mediaType;
   }

   /**
    * \brief return the icon tag for the kind of file.
    * The name of this function contains 'Base' because a suffix for
    * line must be appended to make full icon tag
    *
    * @param mediaType \return
    */
   public String iconTagBaseForType(MediaType mediaType) /*const*/ {

       return IconTagBaseLookupTable.get(mediaType);
   }

   /**
    * associate a file extension to an enumerated kind of media
    */
   private Map<String, MediaType> FileTypeLookupTable = new HashMap<>();

   /**
    * associate an enumerated kind of media to an icon tag
    */
   private Map<MediaType, String> IconTagBaseLookupTable = new HashMap<>();

};
