package com.meteor.starvaxremote.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meteor.remote.core.models.ListItem;
import com.meteor.starvaxremote.R;
import com.meteor.starvaxremote.repository.ShowRepository;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LightPresetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightPresetFragment extends Fragment
        implements View.OnClickListener, ItemAdapter.ItemClickedListener {

   public interface LightPresetEventListener {

      /**
       * invoked when 'trigger' button is pressed
       */
      void onTriggerLightButtonClicked();

      /**
       * invoked when an item in list is clicked
        * @param item holds label and data type (so it can be associated to model)
       */
      void onLightItemClicked(ListItem item);
   }

   private ImageButton mTriggerButton;

   private LightPresetEventListener mEventListener;
   private static ShowRepository mRepository;

   public LightPresetFragment() {
      // Required empty public constructor.
      // This is never called
   }

   /**
    * This works as the actual constructor
    */
   static LightPresetFragment newInstance(ShowRepository repository) {
      LightPresetFragment fragment = new LightPresetFragment();

      mRepository = repository;

      Bundle args = new Bundle();
//      args.putString(ARG_PARAM1, param1);
//      args.putString(ARG_PARAM2, param2);
      fragment.setArguments(args);
      return fragment;
   }
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
//      if (getArguments() != null) {
//         mParam1 = getArguments().getString(ARG_PARAM1);
//         mParam2 = getArguments().getString(ARG_PARAM2);
//      }
   }

   @Override
   public View onCreateView(LayoutInflater inflater,
                            ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      View fragmentView = inflater.inflate(R.layout.fragment_light_preset, container, false);

      // 'trigger' button
      mTriggerButton = fragmentView.findViewById(R.id.lightPresetPage_triggerButton);
      mTriggerButton.setOnClickListener(this);
      mTriggerButton.setImageResource(this.getResources().getIdentifier("track_play",
              "drawable", Objects.requireNonNull(this.getActivity()).getPackageName()));

      return fragmentView;
   }

   public void setLightPresetEventListener(LightPresetEventListener eventListener) {
      mEventListener = eventListener;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

      RecyclerView listView = Objects.requireNonNull(getView()).findViewById(R.id.light_preset_items);

      ItemAdapter adapter;

      adapter = new ItemAdapter(mRepository.getLightPresets().getValue());
      listView.setAdapter(adapter);
      adapter.setOnItemClickedListener( this);

      listView.setLayoutManager(new LinearLayoutManager(getActivity()));

   }

   @Override
   /** invoked by single item and propagated to this class's event listener */
   public void onItemClicked(ListItem item) {
      if (mEventListener != null) {
         mEventListener.onLightItemClicked( item);
      }
   }

   @Override
   public void onClick(View view) {

      if (mEventListener != null) {

         if (view == mTriggerButton) {
            mEventListener.onTriggerLightButtonClicked();
         }
      }
   }
}
