package dataObjects.units;

/**
 * A measurement in milligrams.
 * @author jernlas
 *
 */
public class MilliGramMeasurement extends Measurement {

   private static final String unit = "mg";

   public MilliGramMeasurement() {
   }

   public MilliGramMeasurement(float quantity) {
      setQuantity(quantity);
   }

   @Override
   public Measurement Copy() {
      return new MilliGramMeasurement(getQuantity());
   }

   @Override
   public Float getGrams() {
      return 0.001f * getQuantity();
   }

   @Override
   public String getUnit() {
      return unit;
   }

}
