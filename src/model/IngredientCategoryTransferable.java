package model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import dataObjects.IngredientCategory;

/**
 * Transferable for an IngredeintCategory. Used for Drag and drop.
 * @author jernlas
 *
 */
public class IngredientCategoryTransferable implements Transferable {

   /**
    * The data flavour for IngredientCategory.
    */
   public static final DataFlavor   FLAVOR = new DataFlavor(
                                                 IngredientCategory.class,
                                                 "IngredientCategory");
   /**
    * The data being transferred.
    */
   private final IngredientCategory data;

   /**
    * Create a new Transferable for a given category.
    * @param category The category to transfer.
    */
   public IngredientCategoryTransferable(IngredientCategory category) {
      data = category;
   }

   /*
    * (non-Javadoc)
    * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
    */
   @Override
   public IngredientCategory getTransferData(DataFlavor flavor)
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
