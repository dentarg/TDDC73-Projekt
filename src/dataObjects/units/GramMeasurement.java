package dataObjects.units;

/**
 * A measurement in grams.
 * @author jernlas
 *
 */
public class GramMeasurement extends Measurement {
   private static final String unit = "g";

   public GramMeasurement() {
   }

   public GramMeasurement(float quantity) {
      setQuantity(quantity);
   }

   @Override
   public Measurement Copy() {
      return new GramMeasurement(getQuantity());
   }

   @Override
   public Float getGrams() {
      return getQuantity();
   }

   @Override
   public String getUnit() {
      return unit;
   }

}
