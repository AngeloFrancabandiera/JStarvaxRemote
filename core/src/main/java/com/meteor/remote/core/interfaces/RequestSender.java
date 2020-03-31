package com.meteor.remote.core.interfaces;

import java.util.List;

/**
 * @brief The RequestSender class is an interface to send a message
 *    over any kind of channel. This class does not depend on how
 *    the channel is opened or closed
 */
public interface RequestSender
{
   /**
    * request to lower layer to send a packet with an operative code
    * and a list of parameters.
    * @param requestCode
    * @param params
    */
   public void sendRequest( byte requestCode, final List<String> params);
}


