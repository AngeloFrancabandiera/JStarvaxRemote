package com.meteor.remote.core.replies;

import java.util.List;

/** callback invoked when the reply is received for a given request.
 * The interface has no information about the request that each
 * child subscribes for.
 */
public interface ReplyListener
{

  /**
   * @brief called when the reply for a request is received
   * @param replyCode is a code that says if request was succesfull
   *    or why it was not. Possible values are in \file Replies.h
   * @param args is a set of strings received as answers
   */
  public void onReplyReceived( byte replyCode, final List<String> args);
}

