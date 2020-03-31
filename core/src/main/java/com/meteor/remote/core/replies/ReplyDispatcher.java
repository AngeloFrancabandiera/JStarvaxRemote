package com.meteor.remote.core.replies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

///
/// \brief The ReplyDispatcher class holds a set of listeners for
/// the reply to a given request.
/// When lower level parser receives a reply message, a method that registered
/// for such request is invoked. If none requested such message, a default
/// listener is invoked, if one has been registered.
/// \a onReplyReceivedForRequest is invoked
///
public class ReplyDispatcher
{
   public ReplyDispatcher() {
   }

   public void registerForRequest( byte requestCode, ReplyListener listener) {
      _listenerTable.put( requestCode, listener);
   }

   /// register listener to be called for requests that do not have one.
   public void setDefaultReplyListener( ReplyListener listener) {
      _defaultListener = listener;
   }

   ///
   /// to be called when a reply is received.
   /// \param requestCode is the one that originated the reply
   /// \param replyCode tells if request was fullfilled or why it was not.
   /// \param params adds information to the reply
   ///
   public void onReplyReceivedForRequest(byte requestCode, byte replyCode,
                                  List<String> params) {
      ReplyListener listener = _listenerTable.get( requestCode);

      if (listener != null)
      {
         listener.onReplyReceived( replyCode, params);
      }
      else
      {
         if (_defaultListener != null)
         {
            _defaultListener.onReplyReceived( replyCode, params);
         }
      }
   }


   private Map<Byte, ReplyListener > _listenerTable = new HashMap<>();
   private ReplyListener _defaultListener;
};

