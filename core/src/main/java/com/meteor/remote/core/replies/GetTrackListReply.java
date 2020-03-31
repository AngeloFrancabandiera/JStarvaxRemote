package com.meteor.remote.core.replies;

import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.models.GeneralListModel;
import com.meteor.remote.core.SupportedFiles;
import com.meteor.remote.core.models.ListItem;

import java.util.Iterator;
import java.util.List;
import java.lang.String;


public class GetTrackListReply implements ReplyListener {

   public GetTrackListReply(SystemNotification aSystemNotification,
                            SupportedFiles theSupportedFiles,
                            GeneralListModel modelLineA,
                            GeneralListModel modelLineB) {
      _systemNotification = aSystemNotification;
      _supportedFiles = theSupportedFiles;
      _modelLineA = modelLineA;
      _modelLineB = modelLineB;
   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, final List<String> args) {

      switch (replyCode) {
         case Reply.COMMAND_ACK:
            // 'args' is expected to hold the line (A or B), followed by track list
            fillModel(args);
            _systemNotification.message(String.format("model %s filled.", args.get(0)));
            break;

         default:
            Iterator argsIterator = args.iterator();

            while (argsIterator.hasNext()) {
               String str = (String) argsIterator.next();
               _systemNotification.warning(str);
            }
            break;
      }
   }

   private SystemNotification _systemNotification;
   private SupportedFiles _supportedFiles;
   private GeneralListModel _modelLineA;
   private GeneralListModel _modelLineB;

   private void clearModel(GeneralListModel model) {
      model.clear();
   }

   private void fillModel(List<String> args) {

      if (args.size() >= 1)
      {
         String line = args.get(0);

         if (line.equals("A"))
         {
            clearModel( _modelLineA);
            fillModelInstance( _modelLineA, args, line);

            _systemNotification.message("Track A list filled");
         }
         else if (line.equals("B"))
         {
            clearModel( _modelLineB);
            fillModelInstance( _modelLineB, args, line);

            _systemNotification.message("Track B list filled");
         }
         else
         {
            _systemNotification.warning( String.format("Track list: bad line: %s", line));
         }
      }
      else
      {
         _systemNotification.warning( "Track list: bad reply (not enough parameters)");
      }
   }

   private void fillModelInstance(GeneralListModel model,
                                  List<String> params,
                                  String line) {
      String iconTag;

      // skip first parameter, that holds line tag
      for (int i=1; i < params.size(); i++)
      {
         SupportedFiles.MediaType type;

         // append item at the end
         type = _supportedFiles.typeForMediaName( params.get(i));
         iconTag = _supportedFiles.iconTagBaseForType( type);
         // append suffix for line
         iconTag = iconTag + "_" + line;

         model.append( new ListItem(params.get(i),iconTag));
      }
   }
};

