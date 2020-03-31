package com.meteor.starvaxremote.ui.main;

import android.content.Context;
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

import com.meteor.starvaxremote.R;
import com.meteor.starvaxremote.repository.ShowRepository;

import java.util.Objects;

public class PlaylistFragment extends Fragment {

   private String mLine;
   private Context mContext;
   private ShowRepository mRepository;

   /**
    * Use this factory method to create a new instance of
    * this fragment using the provided parameters.
    *
    * @param line should be 'A' or 'B'
    */
   PlaylistFragment(String line, Context aContext, ShowRepository repository) {
      mLine = line;
      mContext = aContext;
      mRepository = repository;
   }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      String resourceName;
      ImageButton button;
      // Inflate the layout for this fragment
      View playlistView = inflater.inflate(R.layout.fragment_playlist, container, false);

      TextView caption = playlistView.findViewById(R.id.text_topCaption);
      caption.setText(getString(R.string.playlistPage_caption, mLine));

      // customize buttons with colors of playlist line
      button = playlistView.findViewById(R.id.button_play);
      resourceName = "track_play_" + mLine.toLowerCase();
      button.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", Objects.requireNonNull(this.getActivity()).getPackageName()));

      button = playlistView.findViewById(R.id.button_pause);
      resourceName = "track_pause_" + mLine.toLowerCase();
      button.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", this.getActivity().getPackageName()));

      button = playlistView.findViewById(R.id.button_stop);
      resourceName = "track_stop_" + mLine.toLowerCase();
      button.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", this.getActivity().getPackageName()));

      button = playlistView.findViewById(R.id.button_rewind);
      resourceName = "track_rewind_" + mLine.toLowerCase();
      button.setImageResource(this.getResources().getIdentifier(resourceName,
              "drawable", this.getActivity().getPackageName()));

      return playlistView;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

      RecyclerView playlistView = Objects.requireNonNull(getView()).findViewById(R.id.playlist_items);

      ItemAdapter adapter;

      if (mLine.equals("A")) {
         adapter = new ItemAdapter(mRepository.getPlaylistData_A().getValue());
         playlistView.setAdapter(adapter);
      } else if (mLine.equals("B")) {
         adapter = new ItemAdapter(mRepository.getPlaylistData_B().getValue());
         playlistView.setAdapter(adapter);
      }

      playlistView.setLayoutManager(new LinearLayoutManager(mContext));
   }
}
