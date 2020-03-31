package com.meteor.remote.Network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionThread implements Runnable {

   private static final int SERVER_DEFAULT_PORT = 12400;

   /* no command to be executed */
   private static final int COMMAND_NONE = 0;
   private static final int COMMAND_CONNECT = 1;
   private static final int COMMAND_SEND = 2;

   private String _Server = "";
   private Socket _Socket;
   private final Object _Sync = new Object();
   private final RxThread _RxThread;
   private int _Command = COMMAND_NONE;
   private byte[] _SendBuffer;

   public ConnectionThread(RxThread rxThread) {
      _RxThread = rxThread;

      Thread thread = new Thread(_RxThread);
      thread.start();
   }

   @Override
   /**
    * Wait for a command to open a connection with remote server
    */
   public void run() {

      while (true) {

         try {
            // wait for a connection command by calling 'connectToServer'
            synchronized (_Sync) {
               _Sync.wait();

               runSocketCommand();

               _Command = COMMAND_NONE;
            }

         } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());

         } catch (IOException ex) {
            System.out.println("I/O error (open): " + ex.getMessage());

         } catch (InterruptedException ex) {
            System.out.println("wait exception: " + ex.getMessage());

         } catch (Exception ex) {
            System.out.println("generic exception (open): " + ex.getMessage());

         }
      }
   }

   public void connectToServer(final String server) {

      synchronized (_Sync) {
         _Server = server;
         _Command = COMMAND_CONNECT;
         _Sync.notify();
      }
   }

   public void disconnectFromServer() {
      try {
         _Socket.close();
      } catch (IOException ex) {
         System.out.println("closing socket exception: " + ex.getMessage());
      }
      catch (NullPointerException ex) {
         System.out.println("closing a NULL socket: " + ex.getMessage());
      }
   }

   public void sendToServer(final byte[] data) {

      _SendBuffer = data;

      if ((_Socket != null) &&
          (_Socket.isConnected())) {
         synchronized (_Sync) {
            _Command = COMMAND_SEND;
            _Sync.notify();
         }
      }
   }

   private void runSocketCommand() throws IOException {
      switch (_Command) {
         case COMMAND_CONNECT:
            _Socket = new Socket(_Server, SERVER_DEFAULT_PORT);
            _RxThread.startRx(_Socket);
            break;
         case COMMAND_SEND:
            /* we won't reach here if _Socket is not connected */
            _Socket.getOutputStream().write(_SendBuffer);
            break;
         default:
            break;
      }
   }
}
