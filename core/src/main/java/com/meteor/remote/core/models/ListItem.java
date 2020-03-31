package com.meteor.remote.core.models;


public class ListItem {
   public String label;  // element specific label
   public String kind;   // kind of item. Defines an icon and the model it belongs

   public ListItem(String aLabel,
                   String aIcon) {
      label = aLabel;
      kind = aIcon;
   }
}
