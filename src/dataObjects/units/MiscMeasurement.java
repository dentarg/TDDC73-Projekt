package dataObjects.units;

/**
 * A measurement in non weight units.
 * @author jernlas
 *
 */
public class MiscMeasurement extends Measurement {

   /**
    * The unit for this measurement
    */
   private final String unit;

   public MiscMeasurement(String unit) {
      this.unit = new String(unit);
   }

   @Override
   public Measurement Copy() {
      Measurement m = new MiscMeasurement(getUnit());
      m.setQuantity(getQuantity());
      return m;
   }

   @Override
   public Float getGrams() {
      return null;
   }

   @Override
   public String getUnit() {
      return unit;
   }

   @Override
   public boolean isWeightUnit() {
      return false;
   }

}
