package ui.components;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import dataObjects.Recipe;

/**
 * Renderer for the recipes in the filtered list.
 * @author jernlas
 *
 */
public class MealSuggestionRenderer implements ListCellRenderer {

   @Override
   public Component getListCellRendererComponent(JList list, Object value,
         int index, boolean isSelected, boolean cellHasFocus) {
      Recipe selectedRecipe = (Recipe) value;

      JLabel p = new JLabel();
      p.setText(selectedRecipe.getName());
      p.setIcon(selectedRecipe.getImg());
      p.setOpaque(true);

      if (isSelected) {
         p.setBackground(list.getSelectionBackground());
         p.setForeground(list.getSelectionForeground());
      } else {
         p.setBackground(list.getBackground());
         p.setForeground(list.getForeground());
      }

      return p;

   }

}
