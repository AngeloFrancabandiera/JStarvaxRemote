package com.meteor.remote.Network;
import com.meteor.remote.Utils.Utils;
import com.meteor.remote.core.protocol.InputParser;
import com.meteor.remote.core.protocol.ServerConnection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class RxThread implements Runnable {

   private Socket _Socket;
   private final byte[] _InputBuffer = new byte[1024];
   private final BlockingQueue<List<Byte>> _RxQueue;
   private ServerConnection.RxListener _RxListener;
   private Semaphore _EventSemaphore = new Semaphore(0);

   public RxThread(BlockingQueue<List<Byte>> rxQueue) {
      _RxQueue = rxQueue;
   }

   @Override
   public void run() {

      while (true) {

         /* wait for a socket ready to be read from */
         _EventSemaphore.acquireUninterruptibly();

         runRxLoop();
      }
   }

   public void startRx(Socket socket) {

      _Socket = socket;

      /* unlock thread that is waiting to start reception */
      _EventSemaphore.release();
   }

   public void registerListener(ServerConnection.RxListener listener) {
      _RxListener = listener;
   }

   private void runRxLoop() {

      boolean keepRunning = true;

      /* keep receiving data as long as Socket is working */
      while ((_Socket != null) &&
             _Socket.isConnected() &&
             keepRunning) {

         try {
            int readSize = _Socket.getInputStream().read(_InputBuffer);
            List<Byte> rxBytes = Utils.ByteArrayToList(_InputBuffer, readSize);

            /* notify data received */
            _RxQueue.offer(rxBytes);

            if (_RxListener != null) {
               _RxListener.onDataAvailable();
            }

         } catch (IOException ex) {
            System.out.println("'read' exception: " + ex.getMessage());
            keepRunning = false;
         } catch (Exception e) {
            keepRunning = false;
         }
      }
   }
}
