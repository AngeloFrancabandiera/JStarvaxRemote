package com.meteor.starvaxremote;
import com.meteor.remote.core.RequestAgent;
import com.meteor.remote.core.models.ListItem;
import com.meteor.starvaxremote.ui.main.OwnFragment;

import java.util.Locale;

public class OwnEventHandler implements OwnFragment.OwnEventListener {

   private final RequestAgent mRequestAgent;

   private String mCurrentLightName = "";

   public OwnEventHandler(OwnFragment ownFragment,
                          RequestAgent requestAgent) {
      mRequestAgent = requestAgent;
      ownFragment.setOwnEventListener(this);
   }

   @Override
   public void onOnButtonClicked() {
      mRequestAgent.controlOpenWebNet( mCurrentLightName, "ON");
   }

   @Override
   public void onOffButtonClicked() {
      mRequestAgent.controlOpenWebNet( mCurrentLightName, "OFF");
   }

   @Override
   public void onApplyButtonClicked(int level) {
      mRequestAgent.controlOpenWebNet( mCurrentLightName, String.format(Locale.getDefault(),
              "DIMMER %d", level));
   }

   @Override
   public void onOwnItemClicked(ListItem item) {
      mCurrentLightName = item.label;
   }
}
