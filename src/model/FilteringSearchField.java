package model;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilteringSearchField extends JTextField implements
      DocumentListener {

   private static final long       serialVersionUID = 1L;
   
   /**
    * The model being filtered
    */
   private MealSuggestionListModel model;

   /**
    * Create a new filtering searchfield
    * @param model The model to filter.
    */
   public FilteringSearchField(MealSuggestionListModel model) {
      this.getDocument().addDocumentListener(this);
      this.model = model;
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
    */
   @Override
   public void changedUpdate(DocumentEvent e) {
      // ignore
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
    */
   @Override
   public void insertUpdate(DocumentEvent e) {
      model.applyFilter(model.copyRestrictingLastFilter().setSerachString(
            getText()));

   }

   /*
    * (non-Javadoc)
    * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
    */
   @Override
   public void removeUpdate(DocumentEvent e) {
      model.applyFilter(model.copyLastFilter().setSerachString(getText()));
   }

}
