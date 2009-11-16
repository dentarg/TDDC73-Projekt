package ui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import model.IngredientCategoryTransferable;
import model.IngredientTransferable;
import model.IngredientTransferable.IngredientData;
import constraints.IngredientConstraint;
import dataObjects.IngredientAvailability;
import dataObjects.IngredientCategory;
import dataObjects.requirements.IngredientRequirement;
import dataObjects.requirements.RequirementManager;

/**
 * A tree component used to select ingredients and categories.
 * 
 * @author jernlas
 */
public class IngredientTree extends JComponent {

   /**
    * Transfer handler for dragging and dropping ingredients and categories
    * between trees.
    * 
    * @author jernlas
    */
   private class IngredientTransferHandler extends TransferHandler {
      private static final long serialVersionUID = -4444118835483763797L;

      /*
       * (non-Javadoc)
       * 
       * @seejavax.swing.TransferHandler#canImport(javax.swing.TransferHandler.
       * TransferSupport)
       */
      @Override
      public boolean canImport(TransferSupport support) {

         for (DataFlavor f : support.getDataFlavors()) {
            if (f != IngredientCategoryTransferable.FLAVOR
                  && f != IngredientTransferable.FLAVOR) {
               return false;
            }
         }
         return true;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
       */
      @Override
      public Transferable createTransferable(JComponent c) {
         if (!(c instanceof JTree)) {
            throw new IllegalArgumentException(
                  "Argument must be a IngredientTree, is " + c.getClass());
         }
         JTree p = (JTree) c;
         TreePath path = p.getSelectionPath();
         Object obj = ((DefaultMutableTreeNode) path.getLastPathComponent())
               .getUserObject();
         Transferable transferable = null;
         if (obj instanceof IngredientAvailability) {
            IngredientAvailability i = (IngredientAvailability) obj;
            Object catObj = ((DefaultMutableTreeNode) path.getParentPath()
                  .getLastPathComponent()).getUserObject();
            IngredientCategory cat = (IngredientCategory) catObj;
            transferable = new IngredientTransferable(i, cat);
         } else if (obj instanceof IngredientCategory) {
            IngredientCategory i = (IngredientCategory) obj;
            transferable = new IngredientCategoryTransferable(i);
         }
         return transferable;
      }

      /*
       * (non-Javadoc)
       * 
       * @see javax.swing.TransferHandler#exportAsDrag(javax.swing.JComponent,
       * java.awt.event.InputEvent, int)
       */
      @Override
      public void exportAsDrag(JComponent comp, InputEvent e, int action) {
         super.exportAsDrag(comp, e, action);
      }

      /*
       * (non-Javadoc)
       * 
       * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent,
       * java.awt.datatransfer.Transferable, int)
       */
      @Override
      public void exportDone(JComponent c, Transferable t, int action) {
         for (DataFlavor f : t.getTransferDataFlavors()) {
            try {
               if (f.equals(IngredientCategoryTransferable.FLAVOR)) {
                  handleDragged((IngredientCategory) t
                        .getTransferData(IngredientCategoryTransferable.FLAVOR));
               } else if (f.equals(IngredientTransferable.FLAVOR)) {
                  handleDragged((IngredientData) t
                        .getTransferData(IngredientTransferable.FLAVOR));
               }

            } catch (UnsupportedFlavorException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
       */
      @Override
      public int getSourceActions(JComponent c) {
         if (removesDrag) {
            return MOVE;
         } else {
            return COPY;
         }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * javax.swing.TransferHandler#importData(javax.swing.TransferHandler.
       * TransferSupport)
       */
      @Override
      public boolean importData(TransferSupport support) {

         // Fail if wrong type
         if (!canImport(support)) {
            return false;
         }

         if (addsDrop) {
            for (DataFlavor f : support.getDataFlavors()) {
               try {
                  if (f.equals(IngredientCategoryTransferable.FLAVOR)) {
                     handleDropped((IngredientCategory) support
                           .getTransferable().getTransferData(
                                 IngredientCategoryTransferable.FLAVOR));
                  } else if (f.equals(IngredientTransferable.FLAVOR)) {
                     handleDropped((IngredientData) support.getTransferable()
                           .getTransferData(IngredientTransferable.FLAVOR));
                  } else {
                  }
               } catch (UnsupportedFlavorException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
            }
         }

         return true;
      }

   }

   private static final long      serialVersionUID = -8489810280211460270L;

   /**
    * The actual tree used.
    */
   private final JTree            tree;

   /**
    * This tree adds the stuff that is dropped into it.
    */
   private final boolean          addsDrop;

   /**
    * This tree removes the stuff that is dragged out oif it.
    */
   private final boolean          removesDrag;

   /**
    * The model belonging to this tree.
    */
   private final DefaultTreeModel model;

   /**
    * Is this tree representing ingredeints to avopid.
    */
   private final boolean          isAvoid;

   /**
    * Create a new ingredient tree.
    * 
    * @param isSource
    *           True if this is the source tree with all ingredients.
    * @param isAvoid
    *           True uf this is the tree with ingredients to avoid.
    */
   public IngredientTree(boolean isSource, boolean isAvoid) {
      this(!isSource, !isSource, isSource, isAvoid);
   }

   /**
    * Create a new ingredient tree.
    * 
    * @param addDropped
    *           True if dropped ingredients are added to this tree.
    * @param removeDragged
    *           True if dragged items are removed from this tree.
    * @param generateDefault
    *           True if this tree should start out filled with all ingredeints.
    * @param isAvoid
    *           True if this tree represents ingredients to avoid.
    */
   public IngredientTree(boolean addDropped, boolean removeDragged,
         boolean generateDefault, boolean isAvoid) {

      this.isAvoid = isAvoid;
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("Mat");
      model = new DefaultTreeModel(root);
      tree = new JTree(model);

      if (generateDefault) {
         List<IngredientCategory> categories = IngredientCategory
               .getIngredientCategoriesFromFile();

         for (IngredientCategory category : categories) {
            addWholeCategory(category);
         }
      }

      addsDrop = addDropped;
      removesDrag = removeDragged;

      JScrollPane scrollPane = new JScrollPane(tree);
      setLayout(new BorderLayout());
      setPreferredSize(new Dimension(100, 100));
      setMinimumSize(new Dimension(100, 100));
      add(scrollPane, BorderLayout.CENTER);
      tree.setDragEnabled(true);
      tree.setTransferHandler(new IngredientTransferHandler());
   }

   /**
    * Adds an Ingredient (and possibly its category if it does not exist) to the
    * tree.
    * 
    * @param category
    *           The ingredient's category.
    * @param ingredient
    *           The ingredient.
    * @return The root node of the tree.
    */
   private DefaultMutableTreeNode addIngredient(IngredientCategory category,
         IngredientAvailability ingredient) {
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
      DefaultMutableTreeNode catNode = null;
      for (int catNr = 0; catNr < root.getChildCount(); catNr++) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode) root
               .getChildAt(catNr);
         tree.collapsePath(new TreePath(node.getPath()));
         if (node.getUserObject().equals(category)) {
            catNode = node;

         }
      }
      if (catNode == null) {
         catNode = new DefaultMutableTreeNode();
         catNode.setUserObject(category);
         model.insertNodeInto(catNode, root, 0);
      }

      tree.expandPath(new TreePath(catNode.getPath()));

      DefaultMutableTreeNode ingNode = null;
      for (int ingNr = 0; ingNr < catNode.getChildCount(); ingNr++) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode) catNode
               .getChildAt(ingNr);
         if (node.getUserObject().equals(ingredient)) {
            ingNode = node;
            break;
         }
      }
      if (ingNode == null) {
         ingNode = new DefaultMutableTreeNode();
         ingNode.setUserObject(ingredient);
         model.insertNodeInto(ingNode, catNode, 0);
      }

      return root;
   }

   /**
    * Adds all ingredients in a whole category.
    * 
    * @param category
    *           The category to add.
    * @return The root node of the tree.
    */
   private DefaultMutableTreeNode addWholeCategory(IngredientCategory category) {
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
      if (category != null) {

         DefaultMutableTreeNode catNode = new DefaultMutableTreeNode();
         catNode.setUserObject(category);
         model.insertNodeInto(catNode, root, 0);
         ArrayList<IngredientAvailability> ingredients = category
               .getIngredients();
         for (int i = 0; i < ingredients.size(); i++) {
            addIngredient(category, ingredients.get(i));
         }
      }
      return root;
   }

   /**
    * Gets a list of all the ingredients in this tree.
    * 
    * @return The list.
    */
   public List<IngredientRequirement> getIngredients() {
      ArrayList<IngredientRequirement> res = new ArrayList<IngredientRequirement>();
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

      for (int i = root.getChildCount() - 1; i >= 0; i--) {
         DefaultMutableTreeNode catNode = (DefaultMutableTreeNode) root
               .getChildAt(i);
         for (int j = catNode.getChildCount() - 1; j >= 0; j--) {
            DefaultMutableTreeNode ingNode = (DefaultMutableTreeNode) catNode
                  .getChildAt(j);
            res.add((IngredientRequirement) ingNode.getUserObject());
         }
      }

      return res;
   }

   /**
    * Handles drag (out) of categories, according to the tree's policy.
    * 
    * @param category
    *           The dragged category.
    */
   public void handleDragged(IngredientCategory category) {
      if (removesDrag) {
         removeWholeCategory(category);
         updateRequirementManager();
      }

   }

   /**
    * Handles drag (out) of ingredients, according to the tree's policy.
    * 
    * @param category
    *           The dragged ingredient.
    */
   public void handleDragged(IngredientData transferData) {
      if (removesDrag) {
         removeIngredient(transferData.category, transferData.ingredient);
         updateRequirementManager();
      }
   }

   /**
    * Handles drop (in) of categories, according to the tree's policy.
    * 
    * @param category
    *           The dropped category.
    */
   public void handleDropped(IngredientCategory category) {
      if (addsDrop) {
         addWholeCategory(category);
         updateRequirementManager();
      }
   }

   /**
    * Handles drop (in) of ingredients, according to the tree's policy.
    * 
    * @param category
    *           The dropped ingredient.
    */
   public void handleDropped(IngredientData transferData) {
      if (addsDrop) {
         addIngredient(transferData.category, transferData.ingredient);
         updateRequirementManager();
      }
   }

   /**
    * Removes a single ingredient from the tree. If this leaves a category
    * empty, it is also removed.
    * 
    * @param category The category of the removed ingredient
    * @param ingredient The ingredient to remove.
    * @return The root.
    */
   private DefaultMutableTreeNode removeIngredient(IngredientCategory category,
         IngredientAvailability ingredient) {
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
      if (category != null && ingredient != null) {

         for (int i = root.getChildCount() - 1; i >= 0; i--) {
            DefaultMutableTreeNode catNode = (DefaultMutableTreeNode) root
                  .getChildAt(i);
            if (catNode.getUserObject().equals(category)) {
               for (int j = catNode.getChildCount() - 1; j >= 0; j--) {
                  DefaultMutableTreeNode ingNode = (DefaultMutableTreeNode) catNode
                        .getChildAt(j);
                  if (ingNode.getUserObject().equals(ingredient)) {
                     DefaultMutableTreeNode node = (DefaultMutableTreeNode) catNode
                           .getChildAt(j);
                     model.removeNodeFromParent(node);
                     break;
                  }
               }
               if (catNode.getChildCount() == 0) {
                  DefaultMutableTreeNode node = (DefaultMutableTreeNode) root
                        .getChildAt(i);
                  model.removeNodeFromParent(node);
               }
               break;
            }
         }
      }

      return root;
   }

   /**
    * Removes a whole category of ingredients from the tree.
    * @param category The category to remove.
    * @return The root.
    */
   private DefaultMutableTreeNode removeWholeCategory(
         IngredientCategory category) {
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
      if (category != null) {

         for (int i = root.getChildCount() - 1; i >= 0; i--) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) root
                  .getChildAt(i);
            if (node.getUserObject().equals(category)) {
               model.removeNodeFromParent(node);
               break;
            }
         }
      }
      return root;
   }

   /**
    * Updates the requirement manager of changes in the requirements constructed
    * by this tree.
    */
   private void updateRequirementManager() {
      List<String> ingredients = new ArrayList<String>();

      DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
      for (int i = root.getChildCount() - 1; i >= 0; i--) {
         DefaultMutableTreeNode catNode = (DefaultMutableTreeNode) root
               .getChildAt(i);
         for (int j = catNode.getChildCount() - 1; j >= 0; j--) {
            DefaultMutableTreeNode ingNode = (DefaultMutableTreeNode) catNode
                  .getChildAt(j);
            ingredients.add(((IngredientAvailability) ingNode.getUserObject())
                  .getName());
         }
      }

      if (isAvoid) {
         RequirementManager.setExcludeIngredients(new IngredientConstraint(
               ingredients, false));
      } else {
         RequirementManager.setIncludeIngredients(new IngredientConstraint(
               ingredients, true));
      }

   }

}
