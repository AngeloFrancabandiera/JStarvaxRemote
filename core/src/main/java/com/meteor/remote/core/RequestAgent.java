package com.meteor.remote.core;

import com.meteor.remote.core.interfaces.RequestSender;
import com.meteor.remote.core.models.OWN_ZoneToWhereTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/// This is QML interface to request data or actions to sever
public class RequestAgent {

   public RequestAgent(RequestSender aRequestSender,
                       OWN_ZoneToWhereTable ownWhereTable) {

      _requestSender = aRequestSender;
      _ownWhereTable = ownWhereTable;
   }

   /**
    * Request all show related data
    */
   public void updateShow() {
      _requestSender.sendRequest(Request.GET_SHOW_TITLE, _emptyString);
      _requestSender.sendRequest(Request.GET_TRACK_LIST, Collections.singletonList("A"));
      _requestSender.sendRequest(Request.GET_TRACK_LIST, Collections.singletonList("B"));
      _requestSender.sendRequest(Request.GET_LIGHT_LIST, _emptyString);
//      _requestSender.sendRequest(Request.GET_OPEN_WEB_NET_LIST, _emptyString);
//      _requestSender.sendRequest(Request.GET_SEQUENCE_ENTRIES_LIST, _emptyString);
   }

   public void playMedia(String lineTag) {
      _requestSender.sendRequest(Request.PLAY_MEDIA,
              Collections.singletonList(lineTag));
   }

   public void pauseMedia(String lineTag) {
      _requestSender.sendRequest(Request.PAUSE_MEDIA,
              Collections.singletonList(lineTag));
   }

   public void stopMedia(String lineTag) {
      _requestSender.sendRequest(Request.STOP_MEDIA,
              Collections.singletonList(lineTag));
   }

   public void rewindMedia(String lineTag) {
      _requestSender.sendRequest(Request.REWIND_MEDIA,
              Collections.singletonList(lineTag));
   }

   public void activateMedia(String lineTag, String mediaLabel) {
      _requestSender.sendRequest(Request.SET_ACTIVE_MEDIA,
              Collections.singletonList(mediaLabel));
   }

   public void triggerLightPreset(String presetLabel) {
      _requestSender.sendRequest(Request.CONTROL_LIGHT_PRESET,
              Collections.singletonList("START"));
   }

//   private void controlSequence(String action) {
//      _requestSender.sendRequest(Request.CONTROL_SEQUENCE,
//              Collections.singletonList(action));
//   }
//
//   private void setActiveSequenceEntry(String label) {
//      _requestSender.sendRequest(Request.SET_ACTIVE_SEQUENCE_ENTRY,
//              Collections.singletonList(label));
//   }

   public void controlOpenWebNet(String zoneName, String level) {
      String where = _ownWhereTable.where(zoneName);
      _requestSender.sendRequest(Request.CONTROL_OPEN_WEB_NET,
              Collections.unmodifiableList(Arrays.asList(where, level)));
   }

   private RequestSender _requestSender;
   private OWN_ZoneToWhereTable _ownWhereTable;
   private final List<String> _emptyString = new ArrayList<>();
};


