package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import ui.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class AddRemoveComponent extends JPanel {
    private static final String ADD_BUTTON_TEXT = "Lägg till";
    private static final int MAX_VISIBLE_COMPLETIONS = 20;
    private static final int MINIMUM_WIDTH = 250; 

    private static final int IMAGE_WIDTH = 50;
    private static final int IMAGE_HEIGHT = 50;

    private static final int IMAGE_VERTICAL_PADDING = 10;
    private static final int IMAGE_LEFT_PADDING = 5;
    private static final int IMAGE_RIGHT_PADDING = 5;

    private static final int TEXT_REMOVE_BUTTON_PADDING = 30;
    private static final int SELECTION_LIST_ROW_PADDING = 4;
    private static final int SELECTION_LIST_TEXT_FIELD_PADDING = 7;

    private static final int TEXT_LEFT_OFFSET_WITHOUT_ICONS = 1;

    private static final int ROW_HEIGHT_WITH_ICONS = IMAGE_HEIGHT + IMAGE_VERTICAL_PADDING;
    private static final int TEXT_LEFT_OFFSET_WITH_ICONS = IMAGE_WIDTH + IMAGE_LEFT_PADDING + IMAGE_RIGHT_PADDING;

    private static final Color selectionColor = new Color(57, 112, 205);
    private static final Color selectionListBackgroundColor = Color.white;

    class CompletionList extends JComponent {
        private List<Object> contents;

        int selectionIndex;
        int scrollOffset;

        JScrollBar scrollBar;

        public CompletionList() {
            selectionIndex = -1;
            scrollOffset = 0;

            setLayout(null);

            scrollBar = new JScrollBar(JScrollBar.VERTICAL);
            add(scrollBar);

            scrollBar.setVisible(false);

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

            addMouseWheelListener(new MouseWheelListener() {
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if(contents.size() > MAX_VISIBLE_COMPLETIONS) {
                        if(scrollOffset + e.getWheelRotation() * e.getScrollAmount() > contents.size() - MAX_VISIBLE_COMPLETIONS)
                            scrollOffset = contents.size() - MAX_VISIBLE_COMPLETIONS;
                        else if(scrollOffset + e.getWheelRotation() * e.getScrollAmount() < 0)
                            scrollOffset = 0;
                        else
                            scrollOffset += e.getWheelRotation() * e.getScrollAmount();

                        scrollBar.setValue(scrollOffset);
                        selectionIndex = pointToIndex(e.getPoint());
                        CompletionList.this.repaint();
                    }
                }
            });

            scrollBar.addAdjustmentListener(new AdjustmentListener() {
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    scrollOffset = e.getValue();
                    CompletionList.this.repaint();
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
            if(useIcons) {
                return scrollOffset + point.y/ROW_HEIGHT_WITH_ICONS;
            }
            else {
                FontMetrics fm = getFontMetrics(getFont());
                return scrollOffset + point.y/fm.getHeight();
            }
        }

        private int getPreferredTextWidth() {
            FontMetrics fm = getFontMetrics(getFont());

            int maxWidth = 0;
            for(Object row : contents) {
                int width = fm.stringWidth(row.toString());
                if(width > maxWidth)
                    maxWidth = width;
            }

            return Math.max(maxWidth, AddRemoveComponent.this.textField.getSize().width);
        }

        private int getListHeight() {
            FontMetrics fm = getFontMetrics(getFont());

            if(useIcons)
                return Math.min(contents.size(), MAX_VISIBLE_COMPLETIONS) *
                       ROW_HEIGHT_WITH_ICONS;
            else
                return Math.min(contents.size(), MAX_VISIBLE_COMPLETIONS) *
                       fm.getHeight();
        }

        private void ensureSelectionIsVisible() {
            if(contents.size() > MAX_VISIBLE_COMPLETIONS) {
                final int offsetOnScreen = selectionIndex - scrollOffset;

                if(offsetOnScreen < 0) {
                    scrollOffset += offsetOnScreen;
                    scrollBar.setValue(scrollOffset);
                }
                else if(offsetOnScreen >= MAX_VISIBLE_COMPLETIONS) {
                    scrollOffset += offsetOnScreen - MAX_VISIBLE_COMPLETIONS + 1;
                    scrollBar.setValue(scrollOffset);
                }
            }
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

            ensureSelectionIsVisible();
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

            ensureSelectionIsVisible();
            repaint();
        }

        /**
         * Sätter innehållet i kompletteringslistan.
         */
        public void setContents(List<Object> contents) {
            assert contents.size() > 0;

            this.contents = contents;
            selectionIndex = 0;
            scrollOffset = 0;

            if(contents.size() > MAX_VISIBLE_COMPLETIONS) {
                Dimension preferredSize = scrollBar.getPreferredSize();

                final int textLeftOffset = useIcons ?
                    TEXT_LEFT_OFFSET_WITH_ICONS : TEXT_LEFT_OFFSET_WITHOUT_ICONS;

                scrollBar.setBounds(textLeftOffset + getPreferredTextWidth(), 0,
                                    preferredSize.width, getListHeight());

                scrollBar.setValues(0,                       // Value
                                    MAX_VISIBLE_COMPLETIONS, // Extent
                                    0,                       // Min
                                    contents.size());        // Max

                scrollBar.setVisible(true);
            }
            else {
                scrollBar.setVisible(false);
            }

            revalidate();
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            int initialY;
            int rowHeight;
            int textLeftOffset;

            if(useIcons) {
                rowHeight = ROW_HEIGHT_WITH_ICONS;
                initialY = rowHeight/2;
                textLeftOffset = TEXT_LEFT_OFFSET_WITH_ICONS;
            }
            else {
                FontMetrics fm = getFontMetrics(getFont());
                rowHeight = fm.getHeight();
                initialY = rowHeight - fm.getDescent();
                textLeftOffset = TEXT_LEFT_OFFSET_WITHOUT_ICONS;
            }

            // Rita markör
            if(selectionIndex != -1) {
                g.setColor(selectionColor);

                g.fillRect(0, (selectionIndex - scrollOffset) * rowHeight,
                           getSize().width, rowHeight);
            }

            // Rita ikoner och textsträngar
            g.setColor(getForeground());

            final int upto = Math.min(contents.size(), MAX_VISIBLE_COMPLETIONS);

            for(int i = 0; i < upto; ++i) {
                if(useIcons && contents.get(i) instanceof Displayable) {
                    Image image = ((Displayable) contents.get(scrollOffset + i)).getImage();
                    
                    if(image != null)
                        g.drawImage( ((Displayable) contents.get(scrollOffset + i)).getImage(),
                                     IMAGE_LEFT_PADDING,
                                     IMAGE_VERTICAL_PADDING/2 + i*rowHeight,
                                     null );
                }
                g.drawString(contents.get(scrollOffset + i).toString(),
                             textLeftOffset,
                             initialY + i*rowHeight);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = new Dimension();
            FontMetrics fm = getFontMetrics(getFont());

            d.width = (useIcons ? TEXT_LEFT_OFFSET_WITH_ICONS : TEXT_LEFT_OFFSET_WITHOUT_ICONS)
                      + getPreferredTextWidth();
            d.height = getListHeight();

            if(contents.size() > MAX_VISIBLE_COMPLETIONS)
                d.width += scrollBar.getPreferredSize().width; 

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

                        if(!createInsteadOfAdding)
                            AddRemoveComponent.this.insertSorted(Row.this.object);

                        layoutListPanel();
                        positionCompletionWindow();

                        AddRemoveComponent.this.notifyObserversRemoved
                            (Row.this.getObject(), Row.this == selectedRow);
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
            boolean addLast = true;

            // Keep contents sorted
            for(int i = 0; i < rows.size(); ++i) {
                int order = o.toString().compareToIgnoreCase( rows.get(i).getObject().toString() );

                if(order < 0) {
                    rows.add(i, new Row(o));
                    addLast = false;
                    break;
                }
                else if(order == 0) {
                    // Avoid adding duplicates
                    addLast = false;
                    break;
                }
            }

            if(addLast)
                rows.add(new Row(o));

            layoutListPanel();
            positionCompletionWindow();
        }
        
        public void clear() {
        	selectedRow = null;
        	rows.clear();
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

            if(rows.size() > 0)
                add(rows.get(0));

            for(int i = 1; i < rows.size(); ++i) {
                add( Box.createVerticalStrut(SELECTION_LIST_ROW_PADDING) );
                add(rows.get(i));
            }

            add( Box.createVerticalStrut(SELECTION_LIST_TEXT_FIELD_PADDING) );

            revalidate();
            repaint();
        }
    }

    private static ArrayList<AddRemoveComponent> instances = new ArrayList<AddRemoveComponent>();

    private boolean createInsteadOfAdding;
    private boolean useIcons;

    private List<Object> contents;

    private List<AddRemoveListener> listeners;

    private SelectionList selectionList;

    private JWindow completionWindow;
    private CompletionList completionList;

    private JTextField textField;

    private JButton addButton;

    private DocumentListener textChangeListener;

    public AddRemoveComponent() {
        this(false, false);
    }

    public AddRemoveComponent(boolean useIcons, boolean createInsteadOfAdding) {
        this.useIcons = useIcons;
        this.createInsteadOfAdding = createInsteadOfAdding;

        instances.add(this);
        
        contents = new ArrayList<Object>();

        listeners = new ArrayList<AddRemoveListener>();

        //
        // Initialisera completion-fönstret
        //
        completionList = new CompletionList();
        completionWindow = new JWindow(MainFrame.mainFrame);

        completionWindow.setAlwaysOnTop(true);

        JScrollPane completionWindowScrollPane = new JScrollPane(completionList);

	JPanel completionWindowPanel = new JPanel();
	completionWindow.setContentPane(completionWindowPanel);

	completionWindowPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	completionWindowPanel.setBackground(Color.white);

        completionWindowPanel.setLayout(new BorderLayout());
        completionWindowPanel.add(completionList, BorderLayout.CENTER);
        //completionWindowPanel.add(completionWindowScrollPane, BorderLayout.CENTER);

        //
        // Initialisera övriga komponenter
        //

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

        // Registrera lyssnare
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(!AddRemoveComponent.this.createInsteadOfAdding)
                    showCompletions(textField.getText());
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToSelectionList(textField.getText());
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
                    addToSelectionList(textField.getText());
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
                        if( !(AddRemoveComponent.this.createInsteadOfAdding ||
                              completionWindow.isVisible()) ) {
                            showCompletions(textField.getText());
                        }
                        else {
                            completionList.selectNextRow();
                        }
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
     * 
     */
    public void clearSelected() {
    	selectionList.clear();
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

    @Override
    public Dimension getMinimumSize() {
        Dimension minimumSize = super.getMinimumSize();
        minimumSize.width = MINIMUM_WIDTH;
        return minimumSize;
    }

    // Hack for GridBagLayout, which ignores the minimum size
    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        if(preferredSize.width < MINIMUM_WIDTH)
            preferredSize.width = MINIMUM_WIDTH;
        return preferredSize;
    }

    /**
     * Döljer kompletteringslistan.
     */
    public void hideCompletionWindow() {
        completionWindow.setVisible(false);
    }

    /**
     * Döljer kompletterings för samtliga AddRemoveComponent-instanser
     */
    public static void hideCompletionWindows() {
        for(AddRemoveComponent c : instances)
            c.hideCompletionWindow();
    }

    private void notifyObserversAdded(Object o) {
        for(AddRemoveListener listener : listeners)
            listener.objectAdded(o);
    }

    private void notifyObserversRemoved(Object o, boolean wasSelected) {
        for(AddRemoveListener listener : listeners)
            listener.objectRemoved(o, wasSelected);
    }

    private void notifyObserversSelected(Object o) {
        for(AddRemoveListener listener : listeners)
            listener.objectSelected(o);
    }

    private void addToSelectionList(String identifier) {
        if(createInsteadOfAdding) {
            if(!identifier.isEmpty()) {
                selectionList.add(identifier);
                notifyObserversAdded(identifier);

                textField.getDocument().removeDocumentListener(textChangeListener);
                textField.setText("");
                textField.getDocument().addDocumentListener(textChangeListener);
            }
        }
        else {
            // Is the object in the list?
            for(Object o : contents) {
                if( o.toString().compareToIgnoreCase(identifier) == 0 ) {
                    selectionList.add(o);
                    contents.remove(o);

                    textField.getDocument().removeDocumentListener(textChangeListener);
                    textField.setText("");
                    textField.getDocument().addDocumentListener(textChangeListener);

                    notifyObserversAdded(o);
                    break;
                }
            }
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
        if(!createInsteadOfAdding)
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
        if(textField.isShowing()) {
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
        
        /*
        Dimension completionWindowSize = completionWindow.getSize();
        if(completionWindowSize.width < textField.getSize().width) {
            completionWindowSize.width = textField.getSize().width;
            completionWindow.setSize(completionWindowSize);
        }
        */

        positionCompletionWindow();
        completionWindow.setVisible(true);
        completionWindow.toFront();
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
