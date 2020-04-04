package com.meteor.starvaxremote.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meteor.remote.core.models.GeneralListModel;
import com.meteor.remote.core.models.ListItem;
import com.meteor.starvaxremote.R;

import java.util.HashMap;
import java.util.Map;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

   public interface ItemClickedListener {

      /**
       * called when an item has been clicked
       */
      void onItemClicked(ListItem item);
   }

   private GeneralListModel mModel;
   private ItemClickedListener mItemClickedListener;

   private int mSelectedItemPosition = -1;

   private static final Map<String, Integer> IconResourceMap = new HashMap<String, Integer>();

   static {
      IconResourceMap.put("AUDIO_A", R.drawable.audio_a);
      IconResourceMap.put("AUDIO_B", R.drawable.audio_b);
      IconResourceMap.put("PICTURE_A", R.drawable.picture_a);
      IconResourceMap.put("PICTURE_B", R.drawable.picture_b);
      IconResourceMap.put("VIDEO_A", R.drawable.video_a);
      IconResourceMap.put("VIDEO_B", R.drawable.video_b);
      IconResourceMap.put("OTHER_A", R.drawable.unknown);
      IconResourceMap.put("OTHER_B", R.drawable.unknown);
      IconResourceMap.put("LIGHT", R.drawable.light);
      IconResourceMap.put("TRACK_A", R.drawable.video_a);
      IconResourceMap.put("TRACK_B", R.drawable.video_b);
      IconResourceMap.put("DIMMER", R.drawable.dimmer);
      IconResourceMap.put("NO_DIMMER", R.drawable.no_dimmer);
   }

   ItemAdapter(GeneralListModel model) {
      mModel = model;
   }

   static class ViewHolder extends RecyclerView.ViewHolder {

      ImageView iconView;
      TextView labelView;
      LinearLayout parentLayout;

      ViewHolder(@NonNull View itemView) {
         super(itemView);

         iconView = itemView.findViewById(R.id.icon_holder);
         labelView = itemView.findViewById(R.id.label_holder);
         parentLayout = itemView.findViewById(R.id.item_layout);
      }
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view;

      if (viewType == R.layout.singleitem_selected_layout) {
         view = LayoutInflater.from(parent.getContext()).
                 inflate(R.layout.singleitem_selected_layout, parent, false);
      } else {
         view = LayoutInflater.from(parent.getContext()).
                 inflate(R.layout.singleitem_layout, parent, false);
      }

      return new ViewHolder(view);
   }
   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

      ListItem item = mModel.getItem(position);

      Integer resource = IconResourceMap.get(item.kind);

      holder.iconView.setImageResource(resource);
      holder.labelView.setText(item.label);

      holder.parentLayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            mSelectedItemPosition = position;
            notifyItemChanged( position);

            if (mItemClickedListener != null) {
               mItemClickedListener.onItemClicked(mModel.getItem(position));
            }
         }
      });
   }

   @Override
   public int getItemCount() {
      return mModel.length();
   }

   @Override
   public int getItemViewType(int position) {

      if (position == mSelectedItemPosition) {
         return R.layout.singleitem_selected_layout;
      } else {
         return R.layout.singleitem_layout;
      }
   }

   public void setOnItemClickedListener(ItemClickedListener itemClickedListener) {
      mItemClickedListener = itemClickedListener;
   }

}
