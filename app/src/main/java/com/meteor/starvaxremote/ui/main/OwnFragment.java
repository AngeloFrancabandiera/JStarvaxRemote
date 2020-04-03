package com.meteor.starvaxremote.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.meteor.remote.core.models.ListItem;
import com.meteor.starvaxremote.CoreEventsHandler;
import com.meteor.starvaxremote.R;
import com.meteor.starvaxremote.repository.ShowRepository;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnFragment extends Fragment
        implements View.OnClickListener, ItemAdapter.ItemClickedListener {

   public interface OwnEventListener {
      /**
       * invoked when 'ON' button is pressed
       */
      void onOnButtonClicked();

      /**
       * invoked when 'OFF' button is pressed
       */
      void onOffButtonClicked();

      /**
       * invoked when 'APPLY' button is pressed
       */
      void onApplyButtonClicked(int level);

      /**
       * invoked when an item in list is clicked
       * @param item holds label and data type (so it can be associated to model)
       */
      void onOwnItemClicked( ListItem item);
   }

   private ImageButton mOnButton;
   private ImageButton mOffButton;
   private ImageButton mApplyButton;
   private SeekBar mLevelBar;

   private OwnFragment.OwnEventListener mEventListener;
   private static ShowRepository mRepository;
   private static CoreEventsHandler mCoreEventsHandler;

   public OwnFragment() {
      // Required empty public constructor
   }

   /**
    * Use this factory method to create a new instance of
    * this fragment using the provided parameters.
    */
   public static OwnFragment newInstance(ShowRepository repository,
                                         CoreEventsHandler coreEventsHandler) {
      OwnFragment fragment = new OwnFragment();

      mRepository = repository;
      mCoreEventsHandler = coreEventsHandler;

      return fragment;
   }
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      String resourceName;
      // Inflate the layout for this fragment
      View playlistView = inflater.inflate(R.layout.fragment_own, container, false);

      TextView caption = playlistView.findViewById(R.id.own_topCaption);
      caption.setText(getString(R.string.ownPage_caption));

      // customize buttons with colors of playlist line
      mOnButton = playlistView.findViewById(R.id.button_on);
      mOnButton.setImageResource(this.getResources().getIdentifier("light",
              "drawable", Objects.requireNonNull(this.getActivity()).getPackageName()));
      mOnButton.setOnClickListener(this);

      mOffButton = playlistView.findViewById(R.id.button_off);
      mOffButton.setImageResource(this.getResources().getIdentifier("light_off",
              "drawable", Objects.requireNonNull(this.getActivity()).getPackageName()));
      mOffButton.setOnClickListener(this);

      mApplyButton = playlistView.findViewById(R.id.button_apply);
      mApplyButton.setImageResource(this.getResources().getIdentifier("track_play",
              "drawable", Objects.requireNonNull(this.getActivity()).getPackageName()));
      mApplyButton.setOnClickListener(this);

      mLevelBar = playlistView.findViewById(R.id.own_light_level_bar);

      return playlistView;
   }

   public void setOwnEventListener(OwnFragment.OwnEventListener eventListener) {
      mEventListener = eventListener;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

      RecyclerView listView = Objects.requireNonNull(getView()).findViewById(R.id.own_items);

      ItemAdapter adapter;

      adapter = new ItemAdapter(mRepository.getOwnLights().getValue());
      listView.setAdapter(adapter);
      adapter.setOnItemClickedListener(this);
      mCoreEventsHandler.addListeningAdapter(adapter);

      listView.setLayoutManager(new LinearLayoutManager(getActivity()));
   }

   /** invoked by single item and propagated to this class's event listener */
   @Override
   public void onItemClicked(ListItem item) {
      if (mEventListener != null) {
         mEventListener.onOwnItemClicked( item);
      }
   }

   @Override
   public void onClick(View view) {

      if (mEventListener != null) {

         if (view == mOnButton) {
            mEventListener.onOnButtonClicked();
         }
         else if (view == mOffButton) {
            mEventListener.onOffButtonClicked();
         }
         else if (view == mApplyButton) {
            // a SeekBar is always 0 based
            mEventListener.onApplyButtonClicked( mLevelBar.getProgress() + 1);
         }
      }
   }

}
