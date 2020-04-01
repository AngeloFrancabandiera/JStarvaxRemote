package com.meteor.starvaxremote.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.meteor.remote.core.RequestAgent;
import com.meteor.remote.core.interfaces.CoreEventListener;
import com.meteor.remote.core.protocol.RequestFormatter;
import com.meteor.remote.core.protocol.ServerConnection;
import com.meteor.starvaxremote.CoreEventsHandler;
import com.meteor.starvaxremote.PlaylistEventHandler;
import com.meteor.starvaxremote.R;
import com.meteor.starvaxremote.LightSetEventHandler;
import com.meteor.starvaxremote.factory.ApplicationFactory;
import com.meteor.starvaxremote.repository.ShowRepository;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


   @StringRes
   private static final int[] TAB_TITLES = new int[]{
           R.string.tab_text_connection,
           R.string.tab_text_playlistA,
           R.string.tab_text_playlistB,
           R.string.tab_text_lightPreset,
           R.string.tab_text_openWebNet
   };
   private final Context mContext;

   private final ShowRepository mRepository;
   private final ServerConnection mServerConnection;
   private final RequestFormatter mRequestFormatter;
   private final RequestAgent mRequestAgent;
   private final CoreEventsHandler mCoreEvents;

   public SectionsPagerAdapter(Context context,
                               FragmentManager fm) {
      super(fm);
      mContext = context;

      ApplicationFactory mApplicationFactory = new ApplicationFactory();
      mApplicationFactory.build(context);

      mRepository = new ShowRepository(
              mApplicationFactory.mediaModel_A(),
              mApplicationFactory.mediaModel_B(),
              mApplicationFactory.lightSetModel(),
              mApplicationFactory.openWebNetModel(),
              mApplicationFactory.connectionServerModel());

      mServerConnection = mApplicationFactory.serverConnection();
      mRequestFormatter = mApplicationFactory.requestFormatter();
      mRequestAgent = mApplicationFactory.requestAgent();

      mCoreEvents = mApplicationFactory.coreEventsHandler();

   }

   @Override
   @NonNull
   public Fragment getItem(int position) {
      // getItem is called to instantiate the fragment for the given page.
      // Return a PlaceholderFragment (defined as a static inner class below).
      Fragment fragment = new Fragment();
      switch (position) {
         case 0:
            fragment = new ConnectionFragment( mRepository,
                    mServerConnection, mRequestFormatter, mRequestAgent);
            break;

         case 1:
            fragment = PlaylistFragment.newInstance("A", mRepository, mCoreEvents);
            PlaylistEventHandler playlistEventHandlerA = new PlaylistEventHandler( (PlaylistFragment)fragment,
                    mRequestFormatter, mServerConnection);
            break;

         case 2:
            fragment = PlaylistFragment.newInstance("B", mRepository, mCoreEvents);
            PlaylistEventHandler playlistEventHandlerB = new PlaylistEventHandler( (PlaylistFragment)fragment,
                    mRequestFormatter, mServerConnection);
            break;

         case 3:
         case 4:
            fragment = LightPresetFragment.newInstance(mRepository, mCoreEvents);
            LightSetEventHandler lightHandler = new LightSetEventHandler( (LightPresetFragment)fragment,
                    mRequestFormatter, mServerConnection);
            break;

         default:
            break;
      }

      return fragment;
   }

   @Nullable
   @Override
   public CharSequence getPageTitle(int position) {
      return mContext.getResources().getString(TAB_TITLES[position]);
   }

   @Override
   public int getCount() {
      return TAB_TITLES.length;
   }
}