package com.meteor.remote.core.interfaces;

public interface CoreEventListener {

   /// the connection with server has been established and
   /// server is waiting for credentials for logging-in
   void onConnectionToServerEstablished();

   /// Credentials have been accepted and log-in is granted
   void onLogInDone();

   /// Connection with server has been broken because of protocol
   /// reasons (wrong password, invalid SW version, ...).
   /// This does not include disconnection for media errors (eg. Network failure)
   void onDisconnectedFromServer();

   ///
   /// A show has been reloaded
   /// \param showTitle is the title of show
   ///
   void onShowReloaded( String showTitle);
}
