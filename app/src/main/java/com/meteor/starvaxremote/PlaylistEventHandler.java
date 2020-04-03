package com.meteor.starvaxremote;
import com.meteor.remote.core.RequestAgent;
import com.meteor.remote.core.models.ListItem;
import com.meteor.starvaxremote.ui.main.PlaylistFragment;

public class PlaylistEventHandler implements PlaylistFragment.PlaylistEventListener {

   private final RequestAgent mRequestAgent;

   public PlaylistEventHandler(PlaylistFragment playlistFragment,
                               RequestAgent requestAgent) {
      mRequestAgent = requestAgent;

      playlistFragment.setPlaylistEventListener(this);
   }

   @Override
   public void onPlayButtonClicked(String line) {
      mRequestAgent.playMedia( line);
   }

   @Override
   public void onPauseButtonClicked(String line) {
      mRequestAgent.pauseMedia( line);
   }

   @Override
   public void onStopButtonClicked(String line) {
      mRequestAgent.stopMedia( line);
   }

   @Override
   public void onRewindButtonClicked(String line) {
      mRequestAgent.rewindMedia( line);
   }

   @Override
   public void onPlaylistItemClicked(String line, ListItem item) {
      mRequestAgent.activateMedia( line, item.label);
   }

}
