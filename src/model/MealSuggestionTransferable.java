package model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import dataObjects.Recipe;

/**
 * Transferable for a MealSuggestion. Used for Drag and drop.
 * @author jernlas
 *
 */
public class MealSuggestionTransferable implements Transferable {

   /**
    * The data flavour for MealSuggestion.
    */
   public static final DataFlavor FLAVOR = new DataFlavor(Recipe.class,
                                               "Meal Suggestion");
   
   /**
    * The recipe being transferred.
    */
   private final Recipe           m;

   /**
    * Creates a new transferable with the given recipe.
    * @param m The recipe to transfer.
    */
   public MealSuggestionTransferable(Recipe m) {
      this.m = m;
   }

   /*
    * (non-Javadoc)
    * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
    */
   public Object getTransferData(DataFlavor flavor)
         throws UnsupportedFlavorException, IOException {
      if (flavor == FLAVOR) {
         return m;
      } else {
         throw new UnsupportedFlavorException(flavor);
      }
   }

   /*
    * (non-Javadoc)
    * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
    */
   public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] flavors = { FLAVOR };
      return flavors;
   }

   /*
    *(non-Javadoc)
    * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
    */
   public boolean isDataFlavorSupported(DataFlavor flavor) {
      if (flavor == FLAVOR) {
         return true;
      } else {
         return false;
      }
   }

}
