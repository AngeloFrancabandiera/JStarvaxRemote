package com.meteor.remote.core.replies;

import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.models.GeneralListModel;
import com.meteor.remote.core.models.ListItem;

import java.util.Iterator;
import java.util.List;

/**
 * @brief This is the replay to the request for list of light preset.
 */
public class GetLightListReply implements ReplyListener {

   public GetLightListReply(SystemNotification aSystemNotification,
                            GeneralListModel lightPresetModel) {
      _systemNotification = aSystemNotification;
      _lightPresetModel = lightPresetModel;
   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, final List<String> args) {

      // is reply is ACK, fill model with received
      if (replyCode == Reply.COMMAND_ACK) {
         fillModel(args);

         _systemNotification.message("Light-preset list filled");
      } else {
         Iterator argsIterator = args.iterator();

         while (argsIterator.hasNext()) {
            String arg = (String) argsIterator.next();
            _systemNotification.warning(arg);
         }
      }
   }

   private SystemNotification _systemNotification;
   private GeneralListModel _lightPresetModel;

   private void fillModel(List<String> params) {
      clearModel();

      for (int i=0; i < params.size(); i++)
      {
         // append item at the end
         ListItem newItem;
         newItem = new ListItem(params.get(i),"LIGHT");
         _lightPresetModel.append( newItem );
      }

   }

   private void clearModel() {
      _lightPresetModel.clear();
   }
}

