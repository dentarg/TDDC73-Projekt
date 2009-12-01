package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class LifeStylePanel extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -4204456733995810769L;
	private JList contentList;
	private Vector<LifeStyle> styles;

	public class LifeStyle {
		private String name;
		private String[] donoteat;
		public LifeStyle(String name, String[] donoteat) {
			this.name = name;
			this.donoteat = donoteat;
		}
		public String[] getDoNotEat() {
			return donoteat;
		}
		public String getName() {
			return name;
		}
	}

	public void addLifeStyle(String name, String[] donoteat) {
		styles.add(new LifeStyle(name, donoteat));
	}

    public LifeStylePanel() {
        super(new BorderLayout());
        styles = new Vector<LifeStyle>();

        String[] nothing 	= {};
        contentList = new JList(nothing);
        contentList.setSelectionBackground(contentList.getBackground());
        contentList.setSelectionForeground(contentList.getForeground());

        /*
         * LifeStyle's is saved in a global list that is used when
         * creating buttons and viewing the list that belongs to the LifeStyle
         */

        String[] veganDoNotEat = {"one", "two", "three", "four"};
        addLifeStyle("vegan",veganDoNotEat);
        String[] leetDoNotEat = {"noobs", "foo", "bar"};
        addLifeStyle("leet",leetDoNotEat);

        add(createButtons(), BorderLayout.LINE_START);
        add(showList(contentList), BorderLayout.CENTER);
    }

    public JPanel createButtons() {
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup group = new ButtonGroup();

        for (LifeStyle style : styles) {
        	String name =  style.getName();
        	JRadioButton b = new JRadioButton(name);
        	b.setActionCommand(name);
        	b.addActionListener(this);
        	group.add(b);
        	radioPanel.add(b);
        }

		TitledBorder tb = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				"Lifestyle",
				TitledBorder.CENTER,
				TitledBorder.CENTER,
				new Font("Arial", Font.BOLD, 15)
		);
		radioPanel.setBorder(tb);
		return radioPanel;
    }

    public JPanel showList(JList list) {
    	JPanel panel = new JPanel();
    	panel.add(list);
		TitledBorder tb = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				"exkluderas",
				TitledBorder.CENTER,
				TitledBorder.CENTER,
				new Font("Arial", Font.BOLD, 15)
		);
		panel.setBorder(tb);
		return panel;
    }

    // View the LifeStyle list depending on which one is selected for the moment
    // TODO: Should probably update some user variable
    public void actionPerformed(ActionEvent e) {
    	for (LifeStyle style : styles) {
    		if(style.getName().equals(e.getActionCommand())) {
    			contentList.setListData(style.getDoNotEat());
    		}
		}
    }
}
