package com.meteor.starvaxremote.repository;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meteor.remote.core.SupportedFiles;
import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.models.ConnectionServerModel;
import com.meteor.remote.core.models.GeneralListModel;
import com.meteor.remote.core.replies.GetTrackListReply;
import com.meteor.remote.core.replies.Reply;
import com.meteor.starvaxremote.factory.ApplicationFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

// This class instantiates all objects that can be updated by GUI
// or Network and that make upo the model for the stage-show.
// Instanced objects are passed as constructors in core objects.
public class ShowRepository implements Observer {

   GeneralListModel mPlaylistA;
   GeneralListModel mPlaylistB;
   GeneralListModel mLightPresets;
   GeneralListModel mOwnLights;
   ConnectionServerModel mConnectionData;

   private MutableLiveData<ConnectionServerModel> mConnectionData_ref = new MutableLiveData<>();
   private MutableLiveData<GeneralListModel> mPlaylistA_ref = new MutableLiveData<>();
   private MutableLiveData<GeneralListModel> mPlaylistB_ref = new MutableLiveData<>();
   private MutableLiveData<GeneralListModel> mLightPresets_ref = new MutableLiveData<>();
   private MutableLiveData<GeneralListModel> mOwnLights_ref = new MutableLiveData<>();

   public ShowRepository(GeneralListModel modelPlaylistA,
                         GeneralListModel modelPlaylistB,
                         GeneralListModel modelLightPreset,
                         GeneralListModel modelOwnLights,
                         ConnectionServerModel connectionData) {
      super();

      mPlaylistA = modelPlaylistA;
      mPlaylistB = modelPlaylistB;
      mConnectionData = connectionData;
      mLightPresets = modelLightPreset;
      mOwnLights = modelOwnLights;

      mConnectionData_ref.setValue(mConnectionData);
      mPlaylistA_ref.setValue(mPlaylistA);
      mPlaylistB_ref.setValue(mPlaylistB);
      mLightPresets_ref.setValue(mLightPresets);
      mOwnLights_ref.setValue(mOwnLights);

      mConnectionData.addObserver(this);
      mPlaylistA.addObserver(this);
      mPlaylistB.addObserver(this);
      mLightPresets.addObserver(this);
      mOwnLights.addObserver(this);
   }

   public LiveData<ConnectionServerModel> getConnectionData() {
      return mConnectionData_ref;
   }

   public LiveData<GeneralListModel> getPlaylistData_A() {
      return mPlaylistA_ref;
   }

   public LiveData<GeneralListModel> getPlaylistData_B() {
      return mPlaylistB_ref;
   }

   public LiveData<GeneralListModel> getLightPresets() {
      return mLightPresets_ref;
   }

   public LiveData<GeneralListModel> getOwnLights() {
      return mOwnLights_ref;
   }

   @Override
   /**
    * This is a bridge between android-independent observable objects
    * and lifecycle-aware mutable-live-data objects
    */
   public void update(Observable observable, Object o) {

      if (observable == mConnectionData) {
         mConnectionData_ref.setValue(mConnectionData);
      } else if (observable == mPlaylistA) {
         mPlaylistA_ref.setValue(mPlaylistA);
      } else if (observable == mPlaylistB) {
         mPlaylistB_ref.setValue(mPlaylistB);
      } else if (observable == mLightPresets) {
         mLightPresets_ref.setValue(mLightPresets);
      } else if (observable == mOwnLights) {
         mOwnLights_ref.setValue(mOwnLights);
      }
   }
}
