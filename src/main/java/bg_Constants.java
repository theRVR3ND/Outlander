/**
 * Outlander - Multiplayer Space Game | bg_Constants
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
   public static final String REQUEST_MESSAGE = "pLs_SeNpAi";
   
   /**
    * Maximum number of clients allowed in a server.
    * Limiting is needed to not crash or something.
    */
   public static final byte MAX_PLAYERS = 12;
   
   /**
    * Stream tag. Identifies type of communication
    * being sent.
    */
   public static final byte INITIALIZE = 0,
                                ACTION = 1,
                               MESSAGE = 2,
                                UPDATE = 3,
                                 CRAFT = 4;
   
   public static final byte MAX_PLAYER_NAME_LENGTH = 21;
   
   /**
    * Action trigger value.
    */
   /*
      This should match Binds.cfg exactly.
      First value must equal 0 and following
      values must increment up by one.
   */
   public static final byte ROTATE_LEFT = 0,
                           ROTATE_RIGHT = 1,
                            THROTTLE_UP = 2,
                          THROTTLE_DOWN = 3,
                                   CHAT = 4;
   
   /**
    * Visible (to client) dimensions. Any entity within
    * range should be visible.
    */
   public static final short VIEW_WIDTH = 480,
                            VIEW_HEIGHT = 270;
   
   /**
    * Entity type.
    */
   public static final byte PLAYER = 0,
                            PLANET = 1;
}