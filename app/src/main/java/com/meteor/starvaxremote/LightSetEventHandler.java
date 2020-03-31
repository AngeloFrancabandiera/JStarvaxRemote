package com.meteor.starvaxremote;

import com.meteor.remote.Utils.Utils;
import com.meteor.remote.core.Request;
import com.meteor.remote.core.models.ListItem;
import com.meteor.remote.core.protocol.RequestFormatter;
import com.meteor.remote.core.protocol.ServerConnection;
import com.meteor.starvaxremote.ui.main.LightPresetFragment;

import java.util.ArrayList;
import java.util.List;

public class LightSetEventHandler implements LightPresetFragment.LightPresetEventListener {

//   private final LightPresetFragment mLightSetFragment;
   private final RequestFormatter mRequestFormatter;
   private final ServerConnection mServerConnection;

   private String mCurrentLightPreset;

   public LightSetEventHandler(LightPresetFragment lightSetFragment,
                               RequestFormatter requestFormatter,
                               ServerConnection serverConnection) {
//      mLightSetFragment = lightSetFragment;
      mRequestFormatter = requestFormatter;
      mServerConnection = serverConnection;

      lightSetFragment.setLightPresetEventListener( this);
   }

   @Override
   public void onTriggerLightButtonClicked() {
      activateCurrentPreset( true);
   }

   @Override
   public void onLightItemClicked(ListItem item) {
      mCurrentLightPreset = item.label;

      activateCurrentPreset( false);
   }

   private void activateCurrentPreset( boolean start) {

      List<String> params = new ArrayList<>();
      params.add( mCurrentLightPreset);

      if (start) {
         params.add("START");
      }
      else {
         params.add("DONT_START");
      }

      List<Byte> request = mRequestFormatter.formatRequest(Request.CONTROL_LIGHT_PRESET, params);
      mServerConnection.sendToServer(Utils.ByteListToArray( request));
   }
}
