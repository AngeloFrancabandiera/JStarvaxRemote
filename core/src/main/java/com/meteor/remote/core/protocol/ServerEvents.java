package com.meteor.remote.core.protocol;
import java.util.Observable;

import com.meteor.remote.core.protocol.ServerConnection;

public class ServerEvents extends Observable {

   public enum ServerConnectionState
   {
      UNKNOWN,       /// default value
      DISCONNECTED,  /// not connected to server
      CONNECTING,    /// a request has been sent to server; no reply received yet
      CONNECTED,     /// server accepted connection; credentials not yet received
      LOGGED_IN      /// credentials received. Player can now be controlled
   };

   // data to be notified
   private ServerConnectionState _internalState;
   private int _lastErrorCode;
   private String _lastErrorDescription;

  /*
   *  to be called whenever connection state changes
   * @param newState
   */
   public void onServerConnectionStateChanged( ServerConnectionState newState) {
      _internalState = newState;

      // tell observers that the event is not an error
      _lastErrorCode = 0;
      setChanged();
      notifyObservers();
   }

  /*
   * to be called when an error is detected in connection layer
   * \param errorCode identifies the error
   * \param errorDescription is a human readable description
   */
   public void connectionError( int errorCode, final String errorDescription) {
      _lastErrorCode = errorCode;
      _lastErrorDescription = errorDescription;

      setChanged();
      notifyObservers();
   }
}
