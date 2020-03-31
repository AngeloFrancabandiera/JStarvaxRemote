package com.meteor.remote.core.replies;

import com.meteor.remote.core.interfaces.CoreEventListener;
import com.meteor.remote.core.replies.ReplyListener;

import java.util.List;

public class ShowTitleReply implements ReplyListener {
   public ShowTitleReply(CoreEventListener theCoreEvents) {
      _coreEvents = theCoreEvents;
   }

   String currentShowTitle() {
      return _currentShowTitle;
   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, final List<String> args) {
      if ((replyCode == Reply.COMMAND_ACK) && (args.size() > 0)) {
         _currentShowTitle = args.get(0);
         _coreEvents.onShowReloaded(_currentShowTitle);
      } else {
         // do not hold old file path because it may not be up do date
         _currentShowTitle = "";
         _coreEvents.onShowReloaded("");
      }
   }

   private CoreEventListener _coreEvents;
   private String _currentShowTitle;
};

