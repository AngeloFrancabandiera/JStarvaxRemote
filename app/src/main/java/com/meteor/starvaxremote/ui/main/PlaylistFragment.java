package com.meteor.starvaxremote.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meteor.remote.core.models.ListItem;
import com.meteor.starvaxremote.R;
import com.meteor.starvaxremote.repository.ShowRepository;

import java.util.Objects;

public class PlaylistFragment extends Fragment
        implements View.OnClickListener, ItemAdapter.ItemClickedListener {

   public interface PlaylistEventListener {

      /**
       * invoked when 'pause' button is pressed
       */
      void onPlayButtonClicked( String line);

      /**
       * invoked when 'play' button is pressed
       */
      void onPauseButtonClicked( String line);

      /**
       * invoked when 'stop' button is pressed
       */
      void onStopButtonClicked( String line);

      /**
       * invoked when 'rewind' button is pressed
       */
      void onRewindButtonClicked( String line);

      /**
       * invoked when an item in list is clicked
       * @param item holds label and data type (so it can be associated to model)
       */
      void onPlaylistItemClicked( String line, ListItem item);
   }

   private ImageButton mPlayButton;
   private ImageButton mPauseButton;
   private ImageButton mStopButton;
   private ImageButton mRewindButton;

   private PlaylistFragment.PlaylistEventListener mEventListener;
   private static ShowRepository mRepository;

   /* used by bundle */
   private static final String ARG_PARAM_INDEX_1 = "line";

   private String mLine;
//   private Context mContext;


   public PlaylistFragment() {
      // Required empty public constructor.
      // This is never called
   }

   /**
    * This works as the actual constructor
    */
   static PlaylistFragment newInstance( String line, ShowRepository repository) {
      PlaylistFragment fragment = new PlaylistFragment();

      mRepository = repository;

      Bundle args = new Bundle();
      args.putString(ARG_PARAM_INDEX_1, line);
      fragment.setArguments(args);
      return fragment;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (getArguments() != null) {
         mLine = getArguments().getString(ARG_PARAM_INDEX_1);
      }
   }

   @Override
   public View onCreateView(LayoutInflater inflater,
                            ViewGroup container,
                            Bundle savedInstanceState) {
      String resourceName;
      // Inflate the layout for this fragment
      View playlistView = inflater.inflate(R.layout.fragment_playlist, container, false);

      TextView caption = playlistView.findViewById(R.id.text_topCaption);
      caption.setText(getString(R.string.playlistPage_caption, mLine));

      // customize buttons with colors of playlist line
      mPlayButton = playlistView.findViewById(R.id.button_play);
      resourceName = "track_play_" + mLine.toLowerCase();
      mPlayButton.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", Objects.requireNonNull(this.getActivity()).getPackageName()));
      mPlayButton.setOnClickListener(this);

      mPauseButton = playlistView.findViewById(R.id.button_pause);
      resourceName = "track_pause_" + mLine.toLowerCase();
      mPauseButton.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", this.getActivity().getPackageName()));
      mPauseButton.setOnClickListener(this);

      mStopButton = playlistView.findViewById(R.id.button_stop);
      resourceName = "track_stop_" + mLine.toLowerCase();
      mStopButton.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", this.getActivity().getPackageName()));
      mStopButton.setOnClickListener(this);

      mRewindButton = playlistView.findViewById(R.id.button_rewind);
      resourceName = "track_rewind_" + mLine.toLowerCase();
      mRewindButton.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", this.getActivity().getPackageName()));
      mRewindButton.setOnClickListener(this);

      return playlistView;
   }

   public void setPlaylistEventListener(PlaylistEventListener eventListener) {
      mEventListener = eventListener;
   }


   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

      RecyclerView listView = Objects.requireNonNull(getView()).findViewById(R.id.playlist_items);

      ItemAdapter adapter;

      if (mLine.equals("A")) {
         adapter = new ItemAdapter(mRepository.getPlaylistData_A().getValue());
         listView.setAdapter(adapter);
         adapter.setOnItemClickedListener( this);
      } else if (mLine.equals("B")) {
         adapter = new ItemAdapter(mRepository.getPlaylistData_B().getValue());
         listView.setAdapter(adapter);
         adapter.setOnItemClickedListener( this);
      }

      listView.setLayoutManager(new LinearLayoutManager(getActivity()));
   }

   /** invoked by single item and propagated to this class's event listener */
   @Override
   public void onItemClicked(ListItem item) {
      if (mEventListener != null) {
         mEventListener.onPlaylistItemClicked( mLine, item);
      }
   }

   @Override
   public void onClick(View view) {

      if (mEventListener != null) {

         if (view == mPlayButton) {
            mEventListener.onPlayButtonClicked( mLine);
         }
         else if (view == mPauseButton) {
            mEventListener.onPauseButtonClicked( mLine);
         }
         else if (view == mStopButton) {
            mEventListener.onStopButtonClicked( mLine);
         }
         else if (view == mRewindButton) {
            mEventListener.onRewindButtonClicked( mLine);
         }
      }
   }
}
