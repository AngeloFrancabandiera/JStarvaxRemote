package com.meteor.starvaxremote;

import com.meteor.remote.core.RequestAgent;
import com.meteor.remote.core.models.ListItem;
import com.meteor.starvaxremote.ui.main.LightPresetFragment;

public class LightSetEventHandler implements LightPresetFragment.LightPresetEventListener {

   private final RequestAgent mRequestAgent;

   private String mCurrentLightPreset;

   public LightSetEventHandler(LightPresetFragment lightSetFragment,
                               RequestAgent requestAgent) {
      mRequestAgent = requestAgent;

      lightSetFragment.setLightPresetEventListener( this);
   }

   @Override
   public void onTriggerLightButtonClicked() {
      mRequestAgent.triggerLightPreset( mCurrentLightPreset, true);
   }

   @Override
   public void onLightItemClicked(ListItem item) {
      mCurrentLightPreset = item.label;

      mRequestAgent.triggerLightPreset( mCurrentLightPreset, false);
   }
}
