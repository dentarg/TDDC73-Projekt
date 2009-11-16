package model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import dataObjects.IngredientAvailability;
import dataObjects.IngredientCategory;

/**
 * Transferable for an Ingredeint. Used for Drag and drop.
 * @author jernlas
 *
 */
public class IngredientTransferable implements Transferable {

   /**
    * Holder class for the data being transferred. Only one object can be returend.
    * @author jernlas
    *
    */
   public class IngredientData {
      /**
       * The transferred ingredeint.
       */
      public final IngredientAvailability ingredient;
      
      /**
       * The ingredient's category.
       */
      public final IngredientCategory     category;

      IngredientData(IngredientAvailability ingredient,
            IngredientCategory category) {
         this.ingredient = ingredient;
         this.category = category;
      }
   }

   /**
    * The data flavour for Ingredient.
    */
   public static final DataFlavor FLAVOR = new DataFlavor(
                                               IngredientAvailability.class,
                                               "Ingredient");

   /**
    * The data being transferred
    */
   private final IngredientData   data;

   /**
    * Create a new Transferable.
    * @param ingredient The ingredient.
    * @param category The ingredient's category.
    */
   public IngredientTransferable(IngredientAvailability ingredient,
         IngredientCategory category) {
      data = new IngredientData(ingredient, category);
   }

   /*
    * (non-Javadoc)
    * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
    */
   @Override
   public IngredientData getTransferData(DataFlavor flavor)
         throws UnsupportedFlavorException, IOException {
      if (flavor == FLAVOR) {
         return data;
      } else {
         throw new UnsupportedFlavorException(flavor);
      }
   }

   /*
    * (non-Javadoc)
    * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
    */
   @Override
   public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] flavors = { FLAVOR };
      return flavors;
   }

   /*
    * (non-Javadoc)
    * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
    */
   @Override
   public boolean isDataFlavorSupported(DataFlavor flavor) {
      if (flavor == FLAVOR) {
         return true;
      } else {
         return false;
      }
   }

}
