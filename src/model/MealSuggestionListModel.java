package model;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.TransferHandler;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import dataObjects.Recipe;

/**
 * Model for a filtered meal suggestion list.
 * 
 * @author jernlas
 */
public class MealSuggestionListModel extends TransferHandler implements
      ListModel, MealSuggestionFilterListener {

   private static final long                 serialVersionUID = 1L;

   /**
    * The original list containing all the recipes.
    */
   private final ArrayList<Recipe>           originalList     = new ArrayList<Recipe>();

   /**
    * The filtered version of the original list.
    */
   private ArrayList<Recipe>                 filteredList     = new ArrayList<Recipe>();

   /**
    * List data listeners (allows for use with JList).
    */
   private final ArrayList<ListDataListener> listeners        = new ArrayList<ListDataListener>();

   /**
    * The filter last run.
    */
   private SearchStringMealSuggestionFilter  lastFilter       = new SearchStringMealSuggestionFilter(
                                                                    originalList,
                                                                    this)
                                                                    .setSerachString("");

   /**
    * Only one filtering at a time.
    */
   private final ReentrantLock               filterLock       = new ReentrantLock();

   public MealSuggestionListModel() {
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener
    * )
    */
   @Override
   public void addListDataListener(ListDataListener l) {
      listeners.add(l);
   }

   /**
    * Add a recipe to the original recipe list.
    * 
    * @param m
    *           The recipe to add.
    */
   public void addRecipe(Recipe m) {
      originalList.add(m);
      if (!lastFilter.isItemFiltered(m)) {
         filteredList.add(m);
         int index = filteredList.indexOf(m);
         for (int i = listeners.size() - 1; i >= 0; i--) {
            listeners.get(i).intervalAdded(
                  new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index,
                        index));
         }
      }
   }

   /**
    * Apply a new filter. The lock is released in updetFilteredData.
    * 
    * @param filter
    *           The filter to apply.
    */
   public synchronized void applyFilter(SearchStringMealSuggestionFilter filter) {
      filterLock.lock();
      lastFilter = filter;
      filter.execute();
   }

   /*
    * (non-Javadoc)
    * 
    * @seejavax.swing.TransferHandler#canImport(javax.swing.TransferHandler.
    * TransferSupport)
    */
   @Override
   public boolean canImport(TransferHandler.TransferSupport support) {
      if (!support.isDrop()) {
         return false;
      }

      if (!support.isDataFlavorSupported(MealSuggestionTransferable.FLAVOR)) {
         return false;
      }

      try {
         Object data = support.getTransferable().getTransferData(
               MealSuggestionTransferable.FLAVOR);
         if (originalList.contains(data)) {
            return false;
         }
      } catch (UnsupportedFlavorException e) {
         e.printStackTrace();
         return false;
      } catch (IOException e) {
         e.printStackTrace();
         return false;
      }

      boolean copySupported = (COPY & support.getSourceDropActions()) == COPY;
      if (copySupported) {
         support.setDropAction(COPY);
         return true;
      }

      // COPY is not supported, so reject the transfer
      return false;
   }

   /**
    * Get a copy of the last filter, to be run on the original data (ok for use
    * on all new filters).
    * 
    * @return The copy to modify and run.
    */
   public SearchStringMealSuggestionFilter copyLastFilter() {
      return lastFilter.copy(originalList);
   }

   /**
    * Gets a restricting copy of the last run filter. Only ok for use when the
    * new result is guaranteed to be a subset of the previous results.
    * 
    * @return The copy to modify and run.
    */
   public SearchStringMealSuggestionFilter copyRestrictingLastFilter() {
      return lastFilter.copy(filteredList);
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
    */
   @Override
   public Transferable createTransferable(JComponent comp) {
      if (!(comp instanceof JList)) {
         throw new IllegalArgumentException();
      }
      JList dragFrom = (JList) comp;

      return new MealSuggestionTransferable((Recipe) dragFrom
            .getSelectedValue());
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
    */
   @Override
   public void exportDone(JComponent comp, Transferable trans, int action) {

   }

   /*
    * (non-Javadoc)
    * @see javax.swing.ListModel#getElementAt(int)
    */
   @Override
   public Object getElementAt(int index) {
      return filteredList.get(index);
   }

   /**
    * @return the filteredList
    */
   public ArrayList<Recipe> getFilteredList() {
      return filteredList;
   }

   /**
    * @return the originalList
    */
   public ArrayList<Recipe> getOriginalList() {
      return originalList;
   }

   /**
    * Get a specific recipe
    * @param index The number to get.
    * @return The recipe at number index.
    */
   public Recipe getRecipeAt(int index) {
      return filteredList.get(index);
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.ListModel#getSize()
    */
   @Override
   public int getSize() {
      return filteredList.size();
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
    */
   @Override
   public int getSourceActions(JComponent comp) {
      return COPY_OR_MOVE;
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.TransferSupport)
    */
   @Override
   public boolean importData(TransferHandler.TransferSupport support) {
      
      if (!canImport(support)) {
         return false;
      }

      Recipe data;
      try {
         data = (Recipe) support.getTransferable().getTransferData(
               MealSuggestionTransferable.FLAVOR);
      } catch (UnsupportedFlavorException e) {
         return false;
      } catch (java.io.IOException e) {
         return false;
      }

      addRecipe(data);

      return true;
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
    */
   @Override
   public void removeListDataListener(ListDataListener l) {
      listeners.remove(l);
   }

   /**
    * Removes a specific recipe from the original list.
    * @param m The recipe to remove.
    */
   public void removeRecipe(Recipe m) {
      originalList.remove(m);
      if (!lastFilter.isItemFiltered(m)) {
         int index = filteredList.indexOf(m);
         filteredList.remove(m);
         for (int i = listeners.size() - 1; i >= 0; i--) {
            listeners.get(i).intervalRemoved(
                  new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED,
                        index, index));
         }
      }
   }

   /**
    * @param filteredList
    *           the filteredList to set
    */
   public void setFilteredList(ArrayList<Recipe> filteredList) {
      this.filteredList = filteredList;
   }

   /*
    * (non-Javadoc)
    * @see model.MealSuggestionFilterListener#updateFilteredData(java.util.ArrayList)
    */
   public void updateFilteredData(ArrayList<Recipe> data) {
      filteredList = data;
      int indexMax = filteredList.size() - 1;
      for (int i = listeners.size() - 1; i >= 0; i--) {
         listeners.get(i).contentsChanged(
               new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0,
                     indexMax));
      }
      filterLock.unlock();
   }

}
