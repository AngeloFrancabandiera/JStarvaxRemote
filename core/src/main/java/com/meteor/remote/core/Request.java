package com.meteor.remote.core;

/**
 * This class is a set of operative codes that define the type
 * of a message received by the server.
 */
public class Request {

    // login messages
    public static final byte CONNECT = 1;
    public static final byte SET_CREDENTIALS = 2;
    public static final byte DISCONNECT = 3;

    // show-retrieve messages
    public static final byte GET_SHOW_TITLE = 10;
    public static final byte GET_TRACK_LIST = 11;
    public static final byte GET_LIGHT_LIST = 12;
    public static final byte GET_SEQUENCE_ENTRIES_LIST = 13;
    public static final byte GET_OPEN_WEB_NET_LIST = 14;

    // playlist messages
    public static final byte SET_ACTIVE_MEDIA = 20;
    public static final byte PLAY_MEDIA = 21;
    public static final byte PAUSE_MEDIA = 22;
    public static final byte STOP_MEDIA = 23;
    public static final byte REWIND_MEDIA = 24;

    // light preset messages
    public static final byte CONTROL_LIGHT_PRESET = 30;

    // sequence messages
    public static final byte SET_ACTIVE_SEQUENCE_ENTRY = 40;
    public static final byte CONTROL_SEQUENCE = 41;

    // Open Web Net messages
    public static final byte CONTROL_OPEN_WEB_NET = 50;

}
