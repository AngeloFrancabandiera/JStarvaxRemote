package com.meteor.starvaxremote.factory;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.meteor.remote.Network.ConnectionThread;
import com.meteor.remote.Network.NetworkServerConnection;
import com.meteor.remote.Network.RxThread;
import com.meteor.remote.core.Request;
import com.meteor.remote.core.RequestAgent;
import com.meteor.remote.core.SupportedFiles;
import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.models.ConnectionServerModel;
import com.meteor.remote.core.models.GeneralListModel;
import com.meteor.remote.core.models.OWN_ZoneToWhereTable;
import com.meteor.remote.core.protocol.InputParser;
import com.meteor.remote.core.protocol.RequestFormatter;
import com.meteor.remote.core.protocol.ServerConnection;
import com.meteor.remote.core.protocol.WireRequestSender;
import com.meteor.remote.core.replies.ConnectReply;
import com.meteor.remote.core.replies.DefaultReply;
import com.meteor.remote.core.replies.GetLightListReply;
import com.meteor.remote.core.replies.GetOwnModelReply;
import com.meteor.remote.core.replies.GetSequenceEntriesReply;
import com.meteor.remote.core.replies.GetTrackListReply;
import com.meteor.remote.core.replies.ReplyDispatcher;
import com.meteor.remote.core.replies.SetCredentialsReply;
import com.meteor.remote.core.replies.ShowTitleReply;
import com.meteor.starvaxremote.CoreEventsHandler;
import com.meteor.starvaxremote.repository.RxThreadBridge;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ApplicationFactory {

   private ConnectionServerModel ConnectionServerData_instance;
   private GeneralListModel MediaModel_lineA_instance;
   private GeneralListModel MediaModel_lineB_instance;
   private GeneralListModel LightSetModel_instance;
   private GeneralListModel OwnModel_instance;

   private NetworkServerConnection NetworkServerConnection_instance;
   private RequestAgent RequestAgent_instance;
   private RequestFormatter requestFormatter_instance;
   private CoreEventsHandler coreEvents_instance;

   public void build(final Context context) {
      SupportedFiles supportedFiles_instance = new SupportedFiles();
      OWN_ZoneToWhereTable OWN_ZoneToWhereTable_instance = new OWN_ZoneToWhereTable();

      MediaModel_lineA_instance = new GeneralListModel();
      MediaModel_lineB_instance = new GeneralListModel();
      LightSetModel_instance = new GeneralListModel();
      GeneralListModel sequenceModel_instance = new GeneralListModel();
      OwnModel_instance = new GeneralListModel();

      ConnectionServerData_instance = new ConnectionServerModel();
      coreEvents_instance = new CoreEventsHandler(ConnectionServerData_instance);

      ReplyDispatcher replyDispatcher_instance = new ReplyDispatcher();
      InputParser inputParser_instance = new InputParser(replyDispatcher_instance);
      BlockingQueue<List<Byte>> rxDataQueue_instance = new LinkedBlockingQueue<>();
      RxThread rxThread_instance = new RxThread(rxDataQueue_instance, coreEvents_instance);
      ConnectionThread connectionThread_instance = new ConnectionThread(rxThread_instance);
      NetworkServerConnection_instance = new NetworkServerConnection(connectionThread_instance,
              rxThread_instance,
              rxDataQueue_instance);
      requestFormatter_instance = new RequestFormatter();
      WireRequestSender requestSender_instance = new WireRequestSender(NetworkServerConnection_instance,
              requestFormatter_instance);

      SystemNotification theSystemNotification = new SystemNotification() {
         @Override
         public void message(String message) {
            Toast.makeText(context, "MESSAGE:" + message, Toast.LENGTH_SHORT).show();
         }
         @Override
         public void error(String message) {
            Toast.makeText(context, "ERROR:" + message, Toast.LENGTH_SHORT).show();
         }
         @Override
         public void warning(String message) {
            Toast.makeText(context, "WARNING:" + message, Toast.LENGTH_SHORT).show();
         }
      };

      Handler rxNetworkHandler = new Handler();
      RxThreadBridge rxBridge = new RxThreadBridge(rxNetworkHandler, inputParser_instance,
              NetworkServerConnection_instance);

      RequestAgent_instance = new RequestAgent(requestSender_instance,
              OWN_ZoneToWhereTable_instance);

      DefaultReply defaultReply_instance = new DefaultReply(theSystemNotification);
      ConnectReply connectReply_instance = new ConnectReply(coreEvents_instance,
              theSystemNotification);
      SetCredentialsReply setCredentialsReply_instance = new SetCredentialsReply(coreEvents_instance,
              theSystemNotification);
      ShowTitleReply showTitleReply_instance = new ShowTitleReply(coreEvents_instance);
      GetTrackListReply getTrackListReply_instance = new GetTrackListReply(theSystemNotification,
              supportedFiles_instance,
              MediaModel_lineA_instance, MediaModel_lineB_instance);
      GetLightListReply getLightListReply_instance = new GetLightListReply(theSystemNotification,
              LightSetModel_instance);
      GetSequenceEntriesReply getSequenceEntriesReply_instance = new GetSequenceEntriesReply(theSystemNotification,
              sequenceModel_instance);
      GetOwnModelReply getOwnModelReply_instance = new GetOwnModelReply(theSystemNotification,
              OwnModel_instance,
              OWN_ZoneToWhereTable_instance);


      // connections
      replyDispatcher_instance.setDefaultReplyListener(defaultReply_instance);
      replyDispatcher_instance.registerForRequest(Request.CONNECT,
              connectReply_instance);
      replyDispatcher_instance.registerForRequest(Request.SET_CREDENTIALS,
              setCredentialsReply_instance);
      replyDispatcher_instance.registerForRequest(Request.GET_SHOW_TITLE,
              showTitleReply_instance);
      replyDispatcher_instance.registerForRequest(Request.GET_TRACK_LIST,
              getTrackListReply_instance);
      replyDispatcher_instance.registerForRequest(Request.GET_LIGHT_LIST,
              getLightListReply_instance);
      replyDispatcher_instance.registerForRequest(Request.GET_SEQUENCE_ENTRIES_LIST,
              getSequenceEntriesReply_instance);
      replyDispatcher_instance.registerForRequest(Request.GET_OPEN_WEB_NET_LIST,
              getOwnModelReply_instance);
   }

//   public CoreEventListener coreEvents() {
//      return CoreEvents_instance;
//   }

   public ServerConnection serverConnection() {
      return NetworkServerConnection_instance;
   }

//   public RequestAgent requestAgent() {
//      return RequestAgent_instance;
//   }

//   public RequestSender getRequestSender() {
//      return RequestSender_instance;
//   }

//OWN_ZoneToWhereTable getOwnZoneToWhereTable()
//{
//   return OWN_ZoneToWhereTable_instance;
//}

   public GeneralListModel mediaModel_A() {
      return MediaModel_lineA_instance;
   }

   public GeneralListModel mediaModel_B() {
      return MediaModel_lineB_instance;
   }

   public GeneralListModel lightSetModel() {
      return LightSetModel_instance;
   }

//   public GeneralListModel sequenceModel() {
//      return SequenceModel;
//   }

   public GeneralListModel openWebNetModel() {
      return OwnModel_instance;
   }

   public ConnectionServerModel connectionServerModel() {
      return ConnectionServerData_instance;
   }

   public RequestFormatter requestFormatter() {
      return requestFormatter_instance;
   }

   public RequestAgent requestAgent() {
      return RequestAgent_instance;
   }

   public CoreEventsHandler coreEventsHandler() {
      return coreEvents_instance;
   }
}
