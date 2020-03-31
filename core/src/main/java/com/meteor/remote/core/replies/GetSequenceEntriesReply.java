package com.meteor.remote.core.replies;

import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.models.GeneralListModel;

import java.util.List;

public class GetSequenceEntriesReply implements ReplyListener {

   public GetSequenceEntriesReply(SystemNotification aSystemNotification,
                                  GeneralListModel sequenceModel) {
      _systemNotification = aSystemNotification;
      _sequenceModel = sequenceModel;
   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, final List<String> args) {
   // TBD
   }

   private SystemNotification _systemNotification;
   private GeneralListModel _sequenceModel;

   private void clearModel() {
      _sequenceModel.clear();
   }

   void fillModel( List<String>  args) {
     // TBD
   }
};

