package dataObjects.units;

/**
 * A measurement in kilograms.
 * @author jernlas
 *
 */
public class KiloGramMeasurement extends Measurement {

   private static final String unit = "kg";

   public KiloGramMeasurement() {
   }

   public KiloGramMeasurement(float quantity) {
      setQuantity(quantity);
   }

   @Override
   public Measurement Copy() {
      return new KiloGramMeasurement(getQuantity());
   }

   @Override
   public Float getGrams() {
      return 1000f * getQuantity();
   }

   @Override
   public String getUnit() {
      return unit;
   }

}
