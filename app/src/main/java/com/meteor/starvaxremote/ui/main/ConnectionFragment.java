package com.meteor.starvaxremote.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.meteor.remote.Utils.Utils;
import com.meteor.remote.core.Request;
import com.meteor.remote.core.RequestAgent;
import com.meteor.remote.core.models.ConnectionServerModel;
import com.meteor.remote.core.protocol.RequestFormatter;
import com.meteor.remote.core.protocol.ServerConnection;
import com.meteor.starvaxremote.R;
import com.meteor.starvaxremote.repository.ShowRepository;

import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ConnectionFragment extends Fragment
        implements View.OnClickListener, View.OnFocusChangeListener {

   private final ShowRepository mRepository;
   private final RequestAgent mRequestAgent;
   private ServerConnection mServerConnection;
   private RequestFormatter mRequestFormatter;
   private String mLastShowTitle = "...";

   // GUI
   private Button mConnectButton;
   private Button mSendPwdButton;
   private Button mDisconnectButton;
   private Button mSynchronizeButton;
   private TextView mServerAddressText;
   private EditText mPasswordText;

   ConnectionFragment(ShowRepository repository,
                      ServerConnection serverConnection,
                      RequestFormatter requestFormatter,
                      RequestAgent requestAgent) {
      // Required empty public constructor
      mRepository = repository;
      mServerConnection = serverConnection;
      mRequestFormatter = requestFormatter;
      mRequestAgent = requestAgent;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_connection, container, false);

      mRepository.getConnectionData().observe(Objects.requireNonNull(getActivity()),
              new Observer<ConnectionServerModel>() {
                 @Override
                 public void onChanged(ConnectionServerModel connectionServerModel) {
                    updateConnectionData(connectionServerModel);
                 }
              });

      return root;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

      mConnectButton = Objects.requireNonNull(getView()).findViewById(R.id.connectionPage_connect);
      mConnectButton.setOnClickListener(this);

      mSendPwdButton = getView().findViewById(R.id.connectionPage_sendPassword);
      mSendPwdButton.setOnClickListener(this);

      mDisconnectButton = getView().findViewById(R.id.connectionPage_disconnect);
      mDisconnectButton.setOnClickListener(this);

      mSynchronizeButton = getView().findViewById(R.id.connectionPage_reload);
      mSynchronizeButton.setOnClickListener(this);

      mServerAddressText = getView().findViewById(R.id.text_serverIP);
      mServerAddressText.setOnFocusChangeListener(this);
      mPasswordText = getView().findViewById(R.id.text_Password);
      mPasswordText.setOnFocusChangeListener(this);

      TextView titleView = getView().findViewById(R.id.text_show_title);
      titleView.setText(mLastShowTitle);

      // initial state
      updateConnectionData(mRepository.getConnectionData().getValue());

      applyPreferences();
   }
   private void applyPreferences() {
      SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
      String key, value;

      key = getString(R.string.settings_server_ip_address);
      value = sharedPreferences.getString(key, "");
      mServerAddressText.setText(value);

      key = getString(R.string.settings_password);
      value = sharedPreferences.getString(key, "");
      mPasswordText.setText(value);
   }

   private void updateConnectionData(ConnectionServerModel model) {
      mLastShowTitle = model.getShowTitle();
      if (getView() != null) {
         TextView titleView = getView().findViewById(R.id.text_show_title);
         titleView.setText(mLastShowTitle);
      }

      try {

         switch (model.getConnectionState()) {
            default:
            case ConnectionServerModel.DISCONNECTED:
               mConnectButton.setEnabled(true);
               mSendPwdButton.setEnabled(false);
               mDisconnectButton.setEnabled(false);
               mSynchronizeButton.setEnabled(false);
               break;
            case ConnectionServerModel.CONNECTED:
               mConnectButton.setEnabled(false);
               mSendPwdButton.setEnabled(true);
               mDisconnectButton.setEnabled(true);
               mSynchronizeButton.setEnabled(false);
               break;
            case ConnectionServerModel.LOGGED_IN:
               mConnectButton.setEnabled(false);
               mSendPwdButton.setEnabled(false);
               mDisconnectButton.setEnabled(true);
               mSynchronizeButton.setEnabled(true);
               break;
         }
      } catch (NullPointerException ex) {
         // no problem; this happens on startup
      }
   }

   /*
    * Button management
    */
   @Override
   public void onClick(View view) {

      if (view == mConnectButton) {
         mServerConnection.connectToServer(mServerAddressText.getText().toString());
      } else if (view == mSendPwdButton) {
         /* build 'set-credential' message */
         String password = mPasswordText.getText().toString();
         List<Byte> message = mRequestFormatter.formatRequest(Request.SET_CREDENTIALS, password);

         mServerConnection.sendToServer(Utils.ByteListToArray(message));
      } else if (view == mDisconnectButton) {
         mServerConnection.disconnectFromServer();
      } else if (view == mSynchronizeButton) {
         mRequestAgent.updateShow();
      }
   }

   @SuppressLint("StringFormatInvalid")
   @Override
   public void onFocusChange(View view, boolean gained) {

      // check for focus lost
      if (gained == false) {
         SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
         SharedPreferences.Editor settingsEditor = sharedPreferences.edit();

         if (view.getId() == R.id.text_serverIP) {
            EditText widget = (EditText) view;
            String newValue = widget.getText().toString();

            // store server IP to persistent settings
            settingsEditor.putString(getString(R.string.settings_server_ip_address), newValue);
            settingsEditor.apply();
            System.out.println("saved setting");

         } else if (view.getId() == R.id.text_Password) {
            // store password to persistent settings
            EditText widget = (EditText) view;
            settingsEditor.putString(getString(R.string.settings_password),
                    widget.getText().toString());
            settingsEditor.apply();
         }
      }
   }
}