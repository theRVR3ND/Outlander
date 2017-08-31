/**
 * Outlander - Multiplayer Space Game | bg_Parts
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Spacecraft part information.
 */

public interface bg_Parts{
   
   /**
    * Spacecraft part ID.
    */
   public static final byte PROBE = 0,
                           ENGINE = 1,
                        FUEL_TANK = 2,
                            LASER = 3,
                            RADAR = 4,
                          ANTENNA = 5,
                          BATTERY = 6,
                        GENERATOR = 7,
                             NODE = 8,
                        NUM_PARTS = 9;
   
   /**
    * Part names.
    */
   public static final String[] partNames = new String[] {
      "Probe Core",
      "Engine",
      "Fuel Tank",
      "Laser",
      "Radar",
      "Antenna",
      "Battery",
      "Generator",
      "Structural Node"
   };
   
   /**
    * Mass of each part, in kilograms.
    */
   public static final short[] partMass = new short[] {
      200,
      300,
      500,
      250,
      100,
      50,
      275,
      300,
      50
   };
   
   /**
    * Power required per update, in kilowatts.
    */
   public static final byte[] partPower = new byte[] {
      15,
      -3,
      2,
      80,
      65,
      30,
      0,
      -40,
      0
   };
   
   //Units of fuel that a single fuel tank can store.
   public static final short TANK_CAPACITY = 520;
   
   //Max thrust of an engine.
   public static final short ENGINE_THRUST = 18000;
}