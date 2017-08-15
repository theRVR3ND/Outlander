/**
 * Kilo - Java Multiplayer Engine | bg_Constants
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * List of constant values for client and server. Only
 * accessible through implementation.
 */

public interface bg_Constants{
   
   /**
    * Port number to use for connections.
    */
   public static final short PORT = 27012;
   
   /**
    * Port number to use for server requests.
    */
   public static final short ECHO = 27013;
   
   /**
    * Standard format request from client for connection.
    */
   public static final String REQUEST_MESSAGE = "KILO_REQUEST";
   
   /**
    * Maximum number of clients allowed in a server.
    * Limiting is needed to not crash or something.
    */
   public static final byte MAX_PLAYERS = 24;
   
   /**
    * Stream tag. Identifies type of communication
    * being sent.
    */
   public static final byte INITIALIZE = 0,
                                ACTION = 1,
                               MESSAGE = 2,
                                UPDATE = 3;
   
   public static final byte MAX_PLAYER_NAME_LENGTH = 21;
   
   /**
    * Action trigger value.
    */
   /*
      This should match Binds.cfg exactly.
      First value must equal 0 and following
      values must increment up by one.
   */
   public static final byte MOVE_UP = 0,
                          MOVE_LEFT = 1,
                          MOVE_DOWN = 2,
                         MOVE_RIGHT = 3,
                               JUMP = 4,
                             CROUCH = 5,
                               FIRE = 6,
                             RELOAD = 7,
                      SWITCH_WEAPON = 8,
                               CHAT = 9;
   
   /**
    * Visible (to client) dimensions. Any entity within
    * range should be visible.
    */
   public static final short VIEW_WIDTH = 480,
                            VIEW_HEIGHT = 270;
   
   /**
    * Entity type.
    */
   public static final byte PLAYER = 0;
}