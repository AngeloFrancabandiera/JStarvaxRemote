package com.meteor.starvaxremote;
import com.meteor.remote.Utils.Utils;
import com.meteor.remote.core.Request;
import com.meteor.remote.core.models.ListItem;
import com.meteor.remote.core.protocol.RequestFormatter;
import com.meteor.remote.core.protocol.ServerConnection;
import com.meteor.starvaxremote.ui.main.PlaylistFragment;

import java.util.ArrayList;
import java.util.List;

public class PlaylistEventHandler implements PlaylistFragment.PlaylistEventListener {

   private final RequestFormatter mRequestFormatter;
   private final ServerConnection mServerConnection;

   private String mCurrentTrackLabel="";


   public PlaylistEventHandler(PlaylistFragment playlistFragment,
                               RequestFormatter requestFormatter,
                               ServerConnection serverConnection ) {
      mRequestFormatter = requestFormatter;
      mServerConnection = serverConnection;

      playlistFragment.setPlaylistEventListener( this);
   }

   @Override
   public void onPlayButtonClicked(String line) {
      sendPlaylistCommand( Request.PLAY_MEDIA, line);
   }

   @Override
   public void onPauseButtonClicked(String line) {
      sendPlaylistCommand( Request.PAUSE_MEDIA, line);
   }

   @Override
   public void onStopButtonClicked(String line) {
      sendPlaylistCommand( Request.STOP_MEDIA, line);
   }

   @Override
   public void onRewindButtonClicked(String line) {
      sendPlaylistCommand( Request.REWIND_MEDIA, line);
   }

   @Override
   public void onPlaylistItemClicked(String line, ListItem item) {
      mCurrentTrackLabel = item.label;

      sendPlaylistCommand( Request.SET_ACTIVE_MEDIA, line);
   }

   private void sendPlaylistCommand( byte opcode, String line) {
      List<String> params = new ArrayList<>();

      params.add( line);
      params.add( mCurrentTrackLabel);

      List<Byte> request = mRequestFormatter.formatRequest(opcode, params);
      mServerConnection.sendToServer(Utils.ByteListToArray( request));
   }
}
