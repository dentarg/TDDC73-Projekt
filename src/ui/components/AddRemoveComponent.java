package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.List;

public class AddRemoveComponent extends JPanel {
    private static final String ADD_BUTTON_TEXT = "Lägg till";
    private static final int MAX_COMPLETIONS = 20;

    private static final int IMAGE_WIDTH = 50;
    private static final int IMAGE_HEIGHT = 50;

    private static final int IMAGE_VERTICAL_PADDING = 10;
    private static final int IMAGE_LEFT_PADDING = 5;
    private static final int IMAGE_RIGHT_PADDING = 5;

    private static final int TEXT_REMOVE_BUTTON_PADDING = 30;
    private static final int SELECTION_LIST_TEXT_FIELD_PADDING = 7;

    private static final int ROW_HEIGHT = IMAGE_HEIGHT + IMAGE_VERTICAL_PADDING;

    private static final Color selectionColor = new Color(57, 112, 205);
    private static final Color selectionListBackgroundColor = Color.WHITE;

    class CompletionList extends JComponent {
        private List<Object> contents;

        int selectionIndex;

        public CompletionList() {
            selectionIndex = -1;

            addMouseMotionListener(new MouseMotionListener() {
                public void mouseDragged(MouseEvent e) {
                    selectRowAtPoint(e.getPoint());
                }

                public void mouseMoved(MouseEvent e) {
                    selectRowAtPoint(e.getPoint());
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    Object o = getSelectedCompletion();
                    if(o != null)
                        AddRemoveComponent.this.updateText(o);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    selectionIndex = -1;
                    repaint();
                }
            });
        }

        /**
         * Markerar den rad koordinaten 'point' ligger i höjdled med.
         */
        private void selectRowAtPoint(Point point) {
            selectionIndex = pointToIndex(point);
            repaint();
        }

        /**
         * Ger index för den rad koordinaten 'point' ligger i höjdled med.
         */
        private int pointToIndex(Point point) {
            return point.y / ROW_HEIGHT;
        }

        /**
         * Skickar tillbaka det valda elementet i kompletteringslistan, eller
         * null om inget element är valt.
         */
        public Object getSelectedCompletion() {
            return selectionIndex > -1 ? contents.get(selectionIndex) : null;
        }

        /**
         * Flyttar fram markören ett steg i kompletteringslistan.
         */
        public void selectNextRow() {
            selectionIndex = (selectionIndex + 1) % contents.size();

            repaint();
        }

        /**
         * Flyttar tillbaka markören ett steg i kompletteringslistan.
         */
        public void selectPreviousRow() {
            if(selectionIndex == 0) {
                selectionIndex = contents.size() - 1;
            }
            else {
                --selectionIndex;
            }

            repaint();
        }

        /**
         * Sätter innehållet i kompletteringslistan.
         */
        public void setContents(List<Object> contents) {
            assert contents.size() > 0;

            this.contents = contents;
            selectionIndex = 0;

            revalidate();
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            final int initialY = ROW_HEIGHT/2;

            // Rita markör
            if(selectionIndex != -1) {
                g.setColor(selectionColor);

                g.fillRect(0, selectionIndex * ROW_HEIGHT,
                           getSize().width, ROW_HEIGHT);
            }

            // Rita ikoner och textsträngar
            g.setColor(getForeground());
            for(int i = 0; i < contents.size(); ++i) {
                if(contents.get(i) instanceof Displayable) {
                    Image image = ((Displayable) contents.get(i)).getImage();
                    
                    if(image != null)
                        g.drawImage( ((Displayable) contents.get(i)).getImage(),
                                     IMAGE_LEFT_PADDING,
                                     IMAGE_VERTICAL_PADDING/2 + i*ROW_HEIGHT,
                                     null );
                }
                g.drawString(contents.get(i).toString(),
                             IMAGE_WIDTH + IMAGE_LEFT_PADDING + IMAGE_RIGHT_PADDING,
                             initialY + i*ROW_HEIGHT);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = new Dimension();

            d.height = contents.size() * ROW_HEIGHT;

            d.width = 0;
            FontMetrics fm = getFontMetrics(getFont());
            for(Object row : contents) {
                int width = fm.stringWidth(row.toString());
                if(width > d.width)
                    d.width = width;
            }

            d.width += (IMAGE_WIDTH + IMAGE_LEFT_PADDING + IMAGE_RIGHT_PADDING);

            return d;
        }
    }

    class SelectionList extends JPanel {
        private static final String REMOVE_BUTTON_TEXT = "Ta bort";

        private List<Row> rows;

        Row selectedRow;

        class Row extends JPanel {
            private Object object;

            public Row(Object object) {
                this.object = object;

                setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel textLabel = null;

                Image image = null;
                
                if(object instanceof Displayable)
                    image = ((Displayable) object).getImage();

                if(image != null) {
                    textLabel = new JLabel(object.toString(),
                                           new ImageIcon(image),
                                           SwingConstants.CENTER);
                }
                else {
                    textLabel = new JLabel(object.toString());
                }

                add(textLabel);

                JButton removeButton = new JButton(REMOVE_BUTTON_TEXT);
                add( Box.createHorizontalGlue() );
                add(removeButton);

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if(selectedRow != Row.this) {
                            if(selectedRow != null)
                                selectedRow.setBackground(selectionListBackgroundColor);

                            setBackground(selectionColor);
                            selectedRow = Row.this;

                            AddRemoveComponent.this.notifyObserversSelected(Row.this.object);
                        }
                    }
                });

                removeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rows.remove(Row.this);
                        AddRemoveComponent.this.insertSorted(Row.this.object);

                        if(Row.this == selectedRow) {
                            selectedRow = null;
                            AddRemoveComponent.this.notifyObserversSelectedObjectRemoved(Row.this.object);
                        }

                        layoutListPanel();
                        positionCompletionWindow();

                        AddRemoveComponent.this.notifyObserversRemoved(Row.this.getObject());
                    }
                });

                setBorder(BorderFactory.createRaisedBevelBorder());
                setBackground(selectionListBackgroundColor);
            }

            public Object getObject() {
                return object;
            }
        }

        public SelectionList() {
            rows = new ArrayList<Row>();
            selectedRow = null;

            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        }

        public void add(Object o) {
            boolean inserted = false;

            // Keep contents sorted
            for(int i = 0; i < rows.size(); ++i) {
                if( o.toString().compareToIgnoreCase(rows.get(i).getObject().toString()) < 0 ) {
                    rows.add(i, new Row(o));
                    inserted = true;
                    break;
                }
            }

            if(!inserted)
                rows.add(new Row(o));

            layoutListPanel();
            positionCompletionWindow();
        }

        public List<Object> getObjects() {
            List<Object> objects = new ArrayList<Object>();
            for(Row row : rows)
                objects.add(row.getObject());
            return objects;
        }

        public Object getSelectedObject() {
            return selectedRow == null ? null : selectedRow.getObject();
        }

        private void layoutListPanel() {
            removeAll();

            for(Row row : rows)
                add(row);

            /*
            if(rows.size() > 0) {
                add(rows.get(0));
            }
            for(int i = 0; i < rows.size(); ++i)
                add(rows.get(i));
            */

            add( Box.createVerticalStrut(SELECTION_LIST_TEXT_FIELD_PADDING) );

            revalidate();
            repaint();
        }
    }

    private List<Object> contents;

    private List<AddRemoveListener> listeners;

    private SelectionList selectionList;

    private JWindow completionWindow;
    private CompletionList completionList;

    private JTextField textField;

    private JButton addButton;

    private DocumentListener textChangeListener;

    public AddRemoveComponent(JFrame frame) {
        contents = new ArrayList<Object>();

        listeners = new ArrayList<AddRemoveListener>();

        //
        // Initialisera completion-fönstret
        //
        completionList = new CompletionList();
        completionWindow = new JWindow(frame);

        Container windowPanel = completionWindow.getContentPane();

        windowPanel.setLayout(new BorderLayout());
        windowPanel.add(completionList, BorderLayout.CENTER);

        //
        // Initialisera övriga komponenter
        //
        JPanel textFieldAndButtonPanel = new JPanel();
        textFieldAndButtonPanel.setLayout(new BoxLayout(textFieldAndButtonPanel, BoxLayout.LINE_AXIS));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Sökfält och lägg-till-knapp
        textField = new JTextField();
        addButton = new JButton(ADD_BUTTON_TEXT);

        // Lista med valda objekt
        selectionList = new SelectionList();

        // Lägg till och arrangera komponenterna
        GridBagLayout gridBag = new GridBagLayout();
        setLayout(gridBag);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1.0; c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(selectionList, c);

        c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c = new GridBagConstraints();
        c.gridx = 1; c.gridy = 1;
        add(addButton, c);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToSelectionList();
            }
        });

        textChangeListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { textModified(); }
            public void insertUpdate(DocumentEvent e) { textModified(); }
            public void removeUpdate(DocumentEvent e) { textModified(); }
        };

        textField.getDocument().addDocumentListener(textChangeListener);

        addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent e) {}
            public void ancestorMoved(AncestorEvent e) {
                positionCompletionWindow();
                completionWindow.toFront();
            }
            public void ancestorRemoved(AncestorEvent e) {}
        });

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(completionList.getSelectedCompletion() == null ||
                   !completionWindow.isVisible()) {
                    addToSelectionList();
                }
                else {
                    Object o = completionList.getSelectedCompletion();
                    if(o != null)
                        updateText(o);
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_KP_DOWN:
                        if(!completionWindow.isVisible())
                            showCompletions(textField.getText());
                        completionList.selectNextRow();
                        break;

                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_KP_UP:
                        completionList.selectPreviousRow();
                        break;
                }
            }
        });
    }

    /**
     * Lägger till ett objekt till mängden av element urvalet görs ur.
     */
    public void add(Object o) {
        insertSorted(o);
    }

    /**
     * Sätter mängden av objekt som urvalet görs ur.
     */
    public void setContents(List<Object> contents) {
        this.contents = contents;
    }

    /**
     * Väljer ett objekt (så att det flyttas till listan med valda element
     * ovanför textrutan)
     */
    public void setSelected(Object o) {
        assert contents.contains(o) : "Attempt to select object without first adding it";

        selectionList.add(o);
        contents.remove(o);

        notifyObserversAdded(o);
    }

    /**
     * Väljer alla element i en lista
     */
    public void setSelected(List<Object> objects) {
        for(Object o : objects)
            setSelected(o);
    }

    /**
     * Returnerar en lista med alla element som valts.
     */
    public List<Object> getAdded() {
        return selectionList.getObjects();
    }

    /** 
     * Lägger till en lyssnare som informeras då element väljs och tas bort.
     */
    public void addAddRemoveListener(AddRemoveListener listener) {
        listeners.add(listener);
    }

    /**
     * Tar bort en lyssnare.
     */
    public void removeAddRemoveListener(AddRemoveListener listener) {
        listeners.remove(listener);
    }

    public Object getSelectedObject() {
        return selectionList.getSelectedObject();
    }

    private void notifyObserversAdded(Object o) {
        for(AddRemoveListener listener : listeners)
            listener.objectAdded(o);
    }

    private void notifyObserversRemoved(Object o) {
        for(AddRemoveListener listener : listeners)
            listener.objectRemoved(o);
    }

    private void notifyObserversSelected(Object o) {
        for(AddRemoveListener listener : listeners)
            listener.objectSelected(o);
    }

    private void notifyObserversSelectedObjectRemoved(Object o) {
        for(AddRemoveListener listener : listeners)
            listener.selectedObjectRemoved(o);
    }

    // selectByString?
    private void addToSelectionList() {
        Object selected = null;

        // Is the object in the list?
        for(Object o : contents) {
            if( o.toString().compareToIgnoreCase(textField.getText()) == 0 ) {
                selectionList.add(o);
                selected = o;
                break;
            }
        }

        if(selected != null) {
            contents.remove(selected);
            notifyObserversAdded(selected);
        }
    }

    private void insertSorted(Object o) {
        // TODO: duplicerad kod med SelectionList..
        boolean inserted = false;

        // Keep contents sorted
        for(int i = 0; i < contents.size(); ++i) {
            if( o.toString().compareToIgnoreCase(contents.get(i).toString()) < 0 ) {
                contents.add(i, o);
                inserted = true;
                break;
            }
        }

        if(!inserted)
            contents.add(o);
    }

    /**
     * Hjälpfunktion som anropas då texten i textfältet ändras.
     */
    private void textModified() {
        showCompletions(textField.getText());
    }

    /**
     * Uppdaterar textfältet samt döljer kompletteringslistan om den är
     * synlig.
     */
    private void updateText(Object object) {
        // Undvik en event-kaskad
        textField.getDocument().removeDocumentListener(textChangeListener);
        textField.setText(object.toString());
        textField.getDocument().addDocumentListener(textChangeListener);
        hideCompletionWindow();
    }

    /**
     * Placerar kompletteringslistan under textfältet.
     */
    private void positionCompletionWindow() {
        if(textField.isVisible()) {
            Point loc = textField.getLocationOnScreen();
            loc.y += textField.getSize().height;
            completionWindow.setLocation(loc);
        }
    }

    /**
     * Gör kompletteringslistan synlig.
     */
    // !!! Dela upp i layout- och visa-del?
    private void showCompletionWindow() {
        completionWindow.pack();
        
        Dimension completionWindowSize = completionWindow.getSize();
        if(completionWindowSize.width < textField.getSize().width) {
            completionWindowSize.width = textField.getSize().width;
            completionWindow.setSize(completionWindowSize);
        }

        positionCompletionWindow();
        completionWindow.setVisible(true);
        completionWindow.toFront();
    }

    /**
     * Döljer kompletteringslistan.
     */
    private void hideCompletionWindow() {
        completionWindow.setVisible(false);
    }

    /**
     * Visar ett fönster med kompletteringsförslag för 'text'
     */
    private void showCompletions(String text) {
        ArrayList<Object> matchingObjects = new ArrayList<Object>();

        for(Object o : contents) {
            if(o.toString().toLowerCase().startsWith(text.toLowerCase()))
                matchingObjects.add(o);
        }

        hideCompletionWindow();

        if(matchingObjects.size() > 0) {
            completionList.setContents(matchingObjects);
            showCompletionWindow();
        }
    }
}
