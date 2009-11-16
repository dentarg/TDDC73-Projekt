package ui.panels.mealplan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.event.EventListenerList;

import model.MealSuggestionTransferable;
import model.RecipeSelectionListener;
import ui.components.RecipePicker;
import dataObjects.DayPlan;
import dataObjects.MealDate;
import dataObjects.Recipe;

/**
 * Panel for organizing the meal plan.
 * 
 * @author jernlas
 */
public class MealOrganizerPanel extends JPanel implements RecipePicker {

   /**
    * Panel that accepts dropped Recipes.
    * 
    * @author jernlas
    */
   private class MealDropPanel extends JPanel {

      /**
       * Handler for Dropped Recipes.
       * 
       * @author jernlas
       */
      private class MealDropHandler extends TransferHandler {

         private static final long serialVersionUID = -4444118835483763797L;

         /*
          * (non-Javadoc)
          * 
          * @see
          * javax.swing.TransferHandler#canImport(javax.swing.TransferHandler
          * .TransferSupport)
          */
         @Override
         public boolean canImport(TransferSupport support) {
            for (DataFlavor f : support.getDataFlavors()) {
               if (f != MealSuggestionTransferable.FLAVOR) {
                  return false;
               }
            }

            MealDropPanel p = (MealDropPanel) support.getComponent();
            if (p.isExporting()) {
               return false;
            }

            return true;
         }

         /*
          * (non-Javadoc)
          * 
          * @see
          * javax.swing.TransferHandler#createTransferable(javax.swing.JComponent
          * )
          */
         @Override
         public Transferable createTransferable(JComponent c) {
            if (!(c instanceof MealDropPanel)) {
               throw new IllegalArgumentException(
                     "Argument must be a MealDropPanel");
            }
            MealDropPanel p = (MealDropPanel) c;
            Recipe val = p.getLocalValue();
            return new MealSuggestionTransferable(val);
         }

         /*
          * (non-Javadoc)
          * 
          * @see
          * javax.swing.TransferHandler#exportAsDrag(javax.swing.JComponent,
          * java.awt.event.InputEvent, int)
          */
         @Override
         public void exportAsDrag(JComponent comp, InputEvent e, int action) {
            MealDropPanel p = (MealDropPanel) comp;
            p.setExporting(true);
            super.exportAsDrag(p, e, action);
         }

         /*
          * (non-Javadoc)
          * 
          * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent,
          * java.awt.datatransfer.Transferable, int)
          */
         @Override
         public void exportDone(JComponent c, Transferable t, int action) {
            MealDropPanel p = (MealDropPanel) c;
            if (action == MOVE) {
               p.setLocalValue(null);
            }
            p.setExporting(false);
         }

         /*
          * (non-Javadoc)
          * 
          * @see
          * javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
          */
         @Override
         public int getSourceActions(JComponent c) {
            return MOVE;
         }

         /*
          * (non-Javadoc)
          * 
          * @see
          * javax.swing.TransferHandler#importData(javax.swing.TransferHandler
          * .TransferSupport)
          */
         @Override
         public boolean importData(TransferSupport support) {

            // Fail if wrong type
            if (!canImport(support)) {
               return false;
            }

            try {
               setLocalValue((Recipe) support.getTransferable()
                     .getTransferData(MealSuggestionTransferable.FLAVOR));
            } catch (UnsupportedFlavorException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (Exception e) {
               e.printStackTrace();
            }

            return true;
         }

      }

      private static final long   serialVersionUID = -2616261363454005115L;

      /**
       * The recipe info.
       */
      private JLabel              content          = new JLabel();

      /**
       * Date info
       */
      private JLabel              header           = new JLabel();

      /**
       * Reference to self, used by internal classes.
       */
      private final MealDropPanel self             = this;

      /**
       * Is dragging from here?
       */
      private boolean             isExporting      = false;

      /**
       * The date associated with this panel
       */
      volatile private MealDate   date             = null;

      /**
       * The meal name
       */
      private String              meal             = null;

      /**
       * How high must a panel be to fit a Recipe.
       */
      private int                 minHeight        = 70;

      /**
       * Creates a drop panel for the given date and meal name.
       * 
       * @param mealDate
       *           The date to represent
       * @param mealName
       *           The name of the meal
       */
      public MealDropPanel(MealDate mealDate, String mealName) {
         this.date = mealDate;
         this.meal = mealName;
         setSize(new Dimension(50, 50));
         setMaximumSize(new Dimension(50, 50));
         setMinimumSize(new Dimension(50, 50));
         setPreferredSize(new Dimension(50, 50));
         setBorder(new LineBorder(Color.black));
         setOpaque(true);
         setBackground(Color.WHITE);

         setTransferHandler(new MealDropHandler());
         this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

         header.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 4));
         content.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 4));
         add(header);
         add(content);

         MouseListener m = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               if (plannedRecipes.get(date) != null) {
                  setCurrentSelection(getMeal(date, meal));
                  TransferHandler th = self.getTransferHandler();
                  th.exportAsDrag(self, e, TransferHandler.MOVE);
               }
            }
         };
         content.addMouseListener(m);
         setLocalValue(getMeal(date, meal));

      }

      /**
       * Gets the recipe in this panel
       * 
       * @return The recipe or null
       */
      private synchronized Recipe getLocalValue() {
         return getMeal(date, meal);
      }

      /*
       * (non-Javadoc)
       * 
       * @see javax.swing.JComponent#getPreferredSize()
       */
      @Override
      public synchronized Dimension getPreferredSize() {
         Dimension d = super.getPreferredSize();
         if (d.height < minHeight) {
            d.height = minHeight;
         }
         return d;
      }

      /**
       * Is the component exporting a recipe (the current drag source).
       * 
       * @return True if it is.
       */
      public boolean isExporting() {
         return isExporting;
      }

      /**
       * Sets the date of this panel.
       * 
       * @param d
       *           The date
       */
      public synchronized void setDate(MealDate d) {
         date = d;
         update();
      }

      /**
       * Sets the exporting status (is the panel the source of a drag)
       * 
       * @param isExporting
       *           True if it is.
       */
      public void setExporting(boolean isExporting) {
         this.isExporting = isExporting;
      }

      /**
       * Sets the local recipe value.
       * 
       * @param r
       *           The recipe to set.
       */
      private synchronized void setLocalValue(Recipe r) {
         setMeal(date, meal, r);
         update();
      }

      /**
       * Updates info in child component when recipe changesm, calls repaint.
       */
      public synchronized void update() {
         Recipe r = getMeal(date, meal);
         if (r == null) {
            content.setForeground(Color.gray);
            content.setFont(new Font(content.getFont().getFontName(),
                  Font.BOLD, 30));
            content.setText("Tom");
            content.setIcon(null);
         } else {
            content.setForeground(Color.black);
            content.setFont(new Font(content.getFont().getFontName(),
                  Font.BOLD, 12));
            content.setText(r.getName());
            content.setIcon(r.getImg());
         }
         header.setText(date.toNiceString());
         repaint();
      }

   }

   /**
    * Button for scrolling in the list of MealOrganizerPanels
    * 
    * @author jernlas
    */
   private class ScrollButton extends JButton implements ActionListener {

      private static final long serialVersionUID = 1L;

      /**
       * How many rows to scroll at once.
       */
      private int               scrollDelta      = 0;

      /**
       * Creaters a scroll button with given text and delta.
       * 
       * @param text
       *           The text to display on the button.
       * @param scrollDelta
       *           How far to scroll, 1 = one row, 2 = half screen, 3 = whole
       *           screen. negative for backwards.
       */
      public ScrollButton(String text, int scrollDelta) {
         super(text);
         setIcon(getIcon());
         addActionListener(this);
         this.scrollDelta = scrollDelta;
      }

      /*
       * (non-Javadoc)
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       */
      @Override
      public void actionPerformed(ActionEvent e) {
         if (scrollDelta == 3)
            scroll(daysShown);
         if (scrollDelta == 2)
            scroll(daysShown / 2);
         if (scrollDelta == 1)
            scroll(1);
         if (scrollDelta == -1)
            scroll(-1);
         if (scrollDelta == -2)
            scroll(-daysShown / 2);
         if (scrollDelta == -3)
            scroll(-daysShown);

      }
   }

   private static final long      serialVersionUID = 1514828903031540413L;
   
   /**
    * Listeners for recipe picks.
    */
   private EventListenerList      listeners        = new EventListenerList();

   /**
    * Currently selected recipe.
    */
   private Recipe                 currentSelection = null;
   
   /**
    * The planned meals
    */
   private Map<MealDate, DayPlan> plannedRecipes   = new HashMap<MealDate, DayPlan>();
   
   /**
    * The drop panels used
    */
   private List<MealDropPanel>    dropPanels       = new ArrayList<MealDropPanel>();
   
   /**
    * First displayed date
    */
   private MealDate               firstShown       = new MealDate();
   
   /**
    * The meal names to plan for.
    */
   private List<String>           meals            = new ArrayList<String>();
   
   /**
    * The meal dates
    */
   private List<MealDate>         dates            = new ArrayList<MealDate>();
   
   /**
    * Number of days to show at once
    */
   private int                    daysShown        = 5;
   
   /**
    * The panel containing all the drop panels
    */
   private JPanel                 mealPanel        = new JPanel();
   
   /**
    * Panel for up button and meal names
    */
   private JPanel                 upPanel          = new JPanel();
   
   /**
    * Panel for down button.
    */
   private JPanel                 downPanel        = new JPanel();

   
   /**
    * Creates a defauilt meal organizer panel with the meals breakfast, lunch and dinner.
    */
   public MealOrganizerPanel() {

      meals.add("Frukost");
      meals.add("Lunch");
      meals.add("Middag");
      createContentView(new MealDate());
   }

   /**
    * Adds a displayed date to the component.
    */
   private void addDay() {
      MealDate d;
      if (dropPanels.size() == 0) {
         d = new MealDate(firstShown);
      } else {
         d = dropPanels.get(dropPanels.size() - 1).date;
         d = d.add(1);
      }

      for (String meal : meals) {
         MealDropPanel mdp = new MealDropPanel(d, meal);
         dropPanels.add(mdp);
         mealPanel.add(mdp);
      }
   }

   /**
    * Calcualtes how many days can be shown in the component.
    * @param height The height if the entire component.
    * @return The correct number of panel rows.
    */
   private int calculateDaysShown(int height) {
      Dimension pref = new MealDropPanel(new MealDate(), meals.get(0))
            .getPreferredSize();
      int rightNumberOfDays = (height - upPanel.getSize().height - downPanel
            .getSize().height)
            / (pref.height + 5);
      return rightNumberOfDays;
   }

   /**
    * Creates the contents of this component. This includes up and down buttons and the drop panels.
    * @param start The first date in the plan.
    */
   private void createContentView(MealDate start) {
      this.removeAll();
      firstShown = start;

      setLayout(new BorderLayout());

      upPanel.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 0;
      c.gridwidth = 3;
      upPanel.add(new ScrollButton("upp", -1), c);

      for (int i = 0; i < meals.size(); i++) {
         c.fill = GridBagConstraints.HORIZONTAL;
         c.weightx = 0.5;
         c.gridx = i;
         c.gridy = 2;
         c.gridwidth = 1;
         if (i == 0) {
            c.insets = new Insets(5, 0, 5, 5);
         } else if (i == meals.size() - 1) {
            c.insets = new Insets(5, 5, 5, 0);
         } else {
            c.insets = new Insets(5, 0, 5, 5);
         }
         upPanel.add(
               new JLabel("<html><big>" + meals.get(i) + "</big></html>"), c);
      }

      add(upPanel, BorderLayout.NORTH);

      downPanel.setLayout(new GridLayout(0, 1, 5, 5));
      downPanel.add(new ScrollButton("ner", 1));
      add(downPanel, BorderLayout.SOUTH);

      mealPanel.setLayout(new GridLayout(0, 3, 5, 5));
      add(mealPanel, BorderLayout.CENTER);

      daysShown = calculateDaysShown(100);
   }

   /**
    * Gets the recipe planned for a certain date, and meal.
    * @param d The date to find
    * @param name The meal to find.
    * @return The recipe planned for that day and meal.
    */
   private Recipe getMeal(MealDate d, String name) {
      DayPlan plan = plannedRecipes.get(d);
      if (plan == null) {
         return null;
      } else {
         return plan.getMeal(name);
      }
   }

   /*
    * (non-Javadoc)
    * @see ui.components.RecipePicker#getSelectedRecipe()
    */
   @Override
   public Recipe getSelectedRecipe() {
      return currentSelection;
   }

   /*
    * (non-Javadoc)
    * @see ui.components.RecipePicker#registerNewRecipeListener(model.RecipeSelectionListener)
    */
   @Override
   public void registerNewRecipeListener(RecipeSelectionListener l) {
      listeners.add(RecipeSelectionListener.class, l);
   }

   /**
    * Removes a day from the list of days in the drop panel view.
    */
   private void removeDay() {
      if (dropPanels.size() > 0) {
         for (@SuppressWarnings("unused") String meal : meals) {
            MealDropPanel mdp = dropPanels.remove(dropPanels.size() - 1);
            mealPanel.remove(mdp);
         }
      }
   }

   /**
    * Scrolls the view of days in the plan by delta. Can not scroll before first date.
    * @param delta The number of days to scroll. Negative for backwards.
    */
   private void scroll(int delta) {
      firstShown = firstShown.add(delta);
      MealDate today = new MealDate();
      if (MealDate.lessThan(firstShown, today)) {
         firstShown = today;
      }
      MealDate tmp = new MealDate(firstShown);
      int meals = this.meals.size();
      int currentMeal = 0;
      for (MealDropPanel mdp : dropPanels) {
         mdp.setDate(tmp);
         if (++currentMeal % meals == 0) {
            tmp = tmp.add(1);
         }
      }
   }

   /**
    * Sets the currently selected recipe.
    * @param r The recipe to set.
    */
   private void setCurrentSelection(Recipe r) {
      currentSelection = r;
      for (RecipeSelectionListener l : listeners
            .getListeners(RecipeSelectionListener.class)) {
         l.valueChanged(this);
      }
   }

   /**
    * Sets a current recipe at a certain date and meal.
    * @param d The date to set
    * @param name The meal to set
    * @param r The recipe to put there.
    */
   private void setMeal(MealDate d, String name, Recipe r) {
      DayPlan plan = plannedRecipes.get(d);
      if (plan == null) {
         dates.add(d);
         plannedRecipes.put(d, new DayPlan(meals));
         plan = plannedRecipes.get(d);
      }
      plan.setMeal(name, r);
   }

   /*
    * (non-Javadoc)
    * @see java.awt.Component#setSize(java.awt.Dimension)
    */
   @Override
   public void setSize(Dimension d) {
      setSize(d.width, d.height);
   }

   /*
    * (non-Javadoc)
    * @see java.awt.Component#setSize(int, int)
    */
   @Override
   public void setSize(int width, int height) {
      int rightNumberOfDays = calculateDaysShown(height);
      int change = Math.abs(rightNumberOfDays - daysShown);
      if (rightNumberOfDays > daysShown) {
         for (int i = 0; i < change; i++) {
            addDay();
         }
      } else if (rightNumberOfDays < daysShown) {
         for (int i = 0; i < change; i++) {
            removeDay();
         }
      }
      daysShown = rightNumberOfDays;

      super.setSize(width, height);
   }

}
