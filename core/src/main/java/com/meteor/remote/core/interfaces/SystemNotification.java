package com.meteor.remote.core.interfaces;

import java.lang.String;

/// interface to notify messages to the user in the GUI.
/// This class makes the notifier GUI independent
public interface SystemNotification
{
   /**
    * show in GUI a message that is never a problem, just a notification
    * @param message
    */
   public void message( String  message);

   /**
    * show in GUI a message that something happen that was not expected to
    * happen, but is not a fatal problem.
    * @param message
    */
   public void error( String  message);

   /**
    * show in GUI a message that something wrong happened.
    * @param message
    */
   public void warning( String  message);
};


