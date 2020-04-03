package com.meteor.remote.core.replies;

import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.models.GeneralListModel;
import com.meteor.remote.core.models.ListItem;
import com.meteor.remote.core.models.OWN_ZoneToWhereTable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The GetOwnModelReply class manages
 * the request of Open Web Net (OWN) model
 */
public class GetOwnModelReply implements ReplyListener {
   public GetOwnModelReply(SystemNotification aSystemNotification,
                           GeneralListModel ownModel,
                           OWN_ZoneToWhereTable zoneToWhere) {
      _systemNotification = aSystemNotification;
      _ownModel = ownModel;
      _zoneToWhere = zoneToWhere;

   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, final List<String> args) {

      if (replyCode == Reply.COMMAND_ACK) {
         clearModel();
         fillModel(args);

         _systemNotification.message("Open-Web-Net model filled");
      } else {
         _systemNotification.warning(String.format("OWN Error: %1", (int) replyCode));
      }
   }

   private SystemNotification _systemNotification;
   private GeneralListModel _ownModel;
   private OWN_ZoneToWhereTable _zoneToWhere;

   private void clearModel() {
      _ownModel.clear();
   }

   private void fillModel(List<String> args) {
      // each entry has three token separated by ';'
      // These are: zone name, OWN-where and dimmer support.
      List<String> tokens;
      String zone, where, dimmerable;

      // skip first parameter, that holds line tag
      Iterator argsIterator = args.iterator();

      while (argsIterator.hasNext()) {
         String arg = (String) argsIterator.next();

         tokens = Arrays.asList(arg.split(";"));

         if (tokens.size() >= 3) {
            zone = tokens.get(0);
            where = tokens.get(1);
            dimmerable = tokens.get(2);

            // append item at the end
            if (dimmerable.equals("DIMMER")) {
               _ownModel.append(new ListItem(zone, "DIMMER"));
            } else {
               _ownModel.append(new ListItem(zone, "NO_DIMMER"));
            }

            _zoneToWhere.insert(zone, where);
         } else {
            _systemNotification.warning(String.format("Sequence entry invalid: %s", arg));
         }
      }

   }
}