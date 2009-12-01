package ui.panels.mealplan;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import constraints.CalciumConstraint;
import constraints.CarbonHydratesConstraint;
import constraints.CholestrolConstraint;
import constraints.EnergyConstraint;
import constraints.FatConstraint;
import constraints.ProteinConstraint;
import constraints.SodiumConstraint;
import constraints.SingleRecipeConstraint;
import dataObjects.requirements.RequirementManager;
import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;

class CalciumField extends MinMaxFields {
   public CalciumField(String text) {
      super(text);
   }

   @Override
   public SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min) {
      return new CalciumConstraint(max, min);
   }

   @Override
   public void setConstraint(SingleRecipeConstraint c) {
      RequirementManager.setCalciumRequirement((CalciumConstraint) c);
   }
}

class CarbohydratesField extends MinMaxFields {
   public CarbohydratesField(String text) {
      super(text);
   }

   @Override
   public SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min) {
      return new CarbonHydratesConstraint(max, min);
   }

   @Override
   public void setConstraint(SingleRecipeConstraint c) {
      RequirementManager.setCarbRequirement((CarbonHydratesConstraint) c);
   }
}

class CholestrolField extends MinMaxFields {
   public CholestrolField(String text) {
      super(text);
   }

   @Override
   public SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min) {
      return new CholestrolConstraint(max, min);
   }

   @Override
   public void setConstraint(SingleRecipeConstraint c) {
      RequirementManager.setCholestrolRequirement((CholestrolConstraint) c);
   }
}

class EnergyField extends MinMaxFields {
   public EnergyField(String text) {
      super(text);
   }

   @Override
   public SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min) {
      return new EnergyConstraint(max, min);
   }

   @Override
   public void setConstraint(SingleRecipeConstraint c) {
      RequirementManager.setEnergyRequirement((EnergyConstraint) c);
   }
}

class FatField extends MinMaxFields {
   public FatField(String text) {
      super(text);
   }

   @Override
   public SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min) {
      return new FatConstraint(max, min);
   }

   @Override
   public void setConstraint(SingleRecipeConstraint c) {
      RequirementManager.setFatRequirement((FatConstraint) c);
   }
}

/**
 * Class representing a minimum and a maximum filed for a constraint.
 * @author jernlas
 *
 */
abstract class MinMaxFields {
   public JLabel     title = new JLabel();
   public JTextField min   = new JTextField();
   public JTextField max   = new JTextField();

   public MinMaxFields(String text) {
      title.setText(text);

      DocumentListener listener = new DocumentListener() {

         public void changedUpdate(DocumentEvent e) {
            update();
         }

         public void insertUpdate(DocumentEvent e) {
            update();
         }

         public void removeUpdate(DocumentEvent e) {
            update();
         }
      };

      min.getDocument().addDocumentListener(listener);
      max.getDocument().addDocumentListener(listener);
   }

   public abstract SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min);

   public abstract void setConstraint(SingleRecipeConstraint c);

   public void update() {
      Measurement minMeas = null;
      Measurement maxMeas = null;

      try {
         float minval = Float.parseFloat(min.getText());
         minMeas = new GramMeasurement(minval);
         min.setBackground(Color.GREEN);
      } catch (Exception e) {
         minMeas = null;
         if (min.getText().length() == 0) {
            min.setBackground(Color.WHITE);
         } else {
            min.setBackground(Color.RED);
         }
      }

      try {
         float maxval = Float.parseFloat(max.getText());
         maxMeas = new GramMeasurement(maxval);
         max.setBackground(Color.GREEN);
      } catch (Exception e) {
         maxMeas = null;
         if (max.getText().length() == 0) {
            max.setBackground(Color.WHITE);
         } else {
            max.setBackground(Color.RED);
         }
      }

      setConstraint(createConstraint(minMeas, maxMeas));
   }
}

class ProteinField extends MinMaxFields {
   public ProteinField(String text) {
      super(text);
   }

   @Override
   public SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min) {
      return new ProteinConstraint(max, min);
   }

   @Override
   public void setConstraint(SingleRecipeConstraint c) {
      RequirementManager.setProteinRequirement((ProteinConstraint) c);
   }
}

class SodiumField extends MinMaxFields {
   public SodiumField(String text) {
      super(text);
   }

   @Override
   public SingleRecipeConstraint createConstraint(Measurement max,
         Measurement min) {
      return new SodiumConstraint(max, min);
   }

   @Override
   public void setConstraint(SingleRecipeConstraint c) {
      RequirementManager.setSodiumRequirement((SodiumConstraint) c);
   }
}
