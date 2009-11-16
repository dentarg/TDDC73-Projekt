/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-jan-31 Time: 14:24:40
 */
package dataObjects;

import java.util.ArrayList;

import dataObjects.units.Measurement;

/**
 * Class representing a variable to be set by CS algorithms.
 */
public class MeasurementVariable extends Variable {
   private ArrayList<Measurement> domain;

   /**
    * Creates a new variable.
    * 
    * @param name
    *           Name of the variable.
    */
   public MeasurementVariable(String name) {
      super(name);
      domain = new ArrayList<Measurement>();
   }

   /**
    * Creates a new variable with domain.
    * 
    * @param name
    *           Name of the variable.
    * @param domain
    *           ArrayList of String.
    */
   @SuppressWarnings("unchecked")
   public MeasurementVariable(String name, ArrayList<Measurement> domain) {
      super(name);
      this.domain = (ArrayList<Measurement>) domain.clone();
   }

   /**
    * Adds a measurement to the domain of possible measurements.
    * @param m
    */
   public void addToDomain(Measurement m) {
      domain.add(m);
   }

   /*
    * (non-Javadoc)
    * @see dataObjects.Variable#addToDomain(java.lang.String)
    */
   @Override
   public void addToDomain(String s) {
      Measurement m = Measurement.createFromString(s);
      domain.add(m);
   }

   /*
    * (non-Javadoc)
    * @see dataObjects.Variable#getDomainElementAtIndex(int)
    */
   @Override
   public String getDomainElementAtIndex(int i) {
      return domain.get(i).toString();
   }

   /*
    * (non-Javadoc)
    * @see dataObjects.Variable#getDomainSize()
    */
   @Override
   public int getDomainSize() {
      return domain.size();
   }

   /*
    * (non-Javadoc)
    * @see dataObjects.Variable#removeDomainElementAtIndex(int)
    */
   @Override
   public void removeDomainElementAtIndex(int i) {
      domain.remove(i);
   }

}
