package ui.components;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicSliderUI;

public class RangeSlider extends JPanel implements DocumentListener, ChangeListener {
	
	private Slider slider;
	private JTextField lowerField;
	private JTextField upperField;
	
	private String title;

	public RangeSlider() {
		setSlider(new Slider());
		init();
	}
	
	public RangeSlider(String title) {
		setTitle(title);
		setSlider(new Slider());
		init();
	}
	
	public RangeSlider(String title, int min, int max) {
		setTitle(title);
		setSlider(new Slider(min, max));
		init();
	}
	

	
	private void init() {

		slider.setMajorTickSpacing(slider.getMaximum()/10);
		slider.setMinorTickSpacing(slider.getMaximum()/20);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);

		
		TitledBorder tb = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				title,
				TitledBorder.CENTER,
				TitledBorder.CENTER,
				new Font("Arial", Font.BOLD, 15)
			);
		
		setBorder(tb);
		
		
		
		lowerField = new JTextField();
		lowerField.setPreferredSize(new Dimension(40, 20));
		lowerField.setText(slider.getValue() + "");
		lowerField.getDocument().addDocumentListener(this);
		
		upperField = new JTextField();
		upperField.setPreferredSize(new Dimension(40, 20));
		upperField.setText(slider.getUpperValue() + "");
		upperField.getDocument().addDocumentListener(this);
		
		
		slider.addChangeListener(this);

		
		slider.setFocusable(false);
		
		add(lowerField);
		add(slider);
		add(upperField);

	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setSlider(RangeSlider.Slider slider) {
		this.slider = slider;
	}
	
	public RangeSlider.Slider getSlider() {
		return slider;
	}

	
	public void changedUpdate(DocumentEvent e) {
		int intValue;
		String stringValue;
		if(e.getDocument().equals(lowerField.getDocument())) {
			stringValue = lowerField.getText(); 
		} else {
			stringValue = upperField.getText();
		}
		
		try {
			intValue = Integer.parseInt(stringValue);
		} catch(NumberFormatException ex) {
			intValue = 0;
		}
		
		slider.removeChangeListener(this);
		if(e.getDocument().equals(lowerField.getDocument())) {
			slider.setValue(intValue);
		} else {
			slider.setUpperValue(intValue);
		}
		slider.addChangeListener(this);
		
	}

	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
		
	}

	public void removeUpdate(DocumentEvent e) {
		
	}

	public void stateChanged(ChangeEvent arg0) {
		lowerField.getDocument().removeDocumentListener(this);
		lowerField.setText(slider.getValue() + "");
		upperField.setText(slider.getUpperValue() + "");
		lowerField.getDocument().addDocumentListener(this);
	}
	
	
	public class Slider extends JSlider {	

		/**
		 * Constructs a RangeSlider with default minimum and maximum values of 0
		 * and 100.
		 */
		public Slider() {
		}

		/**
		 * Constructs a RangeSlider with the specified default minimum and maximum 
		 * values.
		 */
		public Slider(int min, int max) {
			super(min, max);
		}

		/**
		 * Overrides the superclass method to install the UI delegate to draw two
		 * thumbs.
		 */
		@Override
		public void updateUI() {
			setUI(new RangeSliderUI(this));
			// Update UI for slider labels.  This must be called after updating the
			// UI of the slider.  Refer to JSlider.updateUI().
			updateLabelUIs();
		}

		/**
		 * Returns the lower value in the range.
		 */
		@Override
		public int getValue() {
			return super.getValue();
		}

		/**
		 * Sets the lower value in the range.
		 */
		@Override
		public void setValue(int value) {
			int oldValue = getValue();
			if (oldValue == value) {
				return;
			}

			// Compute new value and extent to maintain upper value.
			int oldExtent = getExtent();
			int newValue = Math.min(Math.max(getMinimum(), value), oldValue + oldExtent);
			int newExtent = oldExtent + oldValue - newValue;

			// Set new value and extent, and fire a single change event.
			getModel().setRangeProperties(newValue, newExtent, getMinimum(), 
					getMaximum(), getValueIsAdjusting());
		}

		public void setValue(Float value) {
			setValue(value.intValue());
		}

		/**
		 * Returns the upper value in the range.
		 */
		public int getUpperValue() {
			return getValue() + getExtent();
		}

		/**
		 * Sets the upper value in the range.
		 */
		public void setUpperValue(int value) {
			// Compute new extent.
			int lowerValue = getValue();
			int newExtent = Math.min(Math.max(0, value - lowerValue), getMaximum() - lowerValue);

			// Set extent to set upper value.
			setExtent(newExtent);
		}
		
		public void setUpperValue(Float value) {
			setUpperValue(value.intValue());
		}

		/**
		 * Get both the lower and upper value
		 * Lower is placed at 0 and upper at 1 in the array
		 * @return int[] both upper and lower value for this slider
		 */
		public int[] getCombinedValues() {
			int[] values = new int[]{getValue(), getUpperValue()};
			return values;
		}


		protected class RangeSliderUI extends BasicSliderUI {

			/** Color of selected range. */
			private Color rangeColor = Color.DARK_GRAY;

			/** Location and size of thumb for upper value. */
			private Rectangle upperThumbRect;
			/** Indicator that determines whether upper thumb is selected. */
			private boolean upperThumbSelected;

			/** Indicator set when lower thumb is being dragged. */
			private transient boolean lowerDragging;
			/** Indicator set when upper thumb is being dragged. */
			private transient boolean upperDragging;

			/**
			 * Constructs a RangeSliderUI for the specified slider component.
			 * @param b RangeSlider
			 */
			public RangeSliderUI(Slider b) {
				super(b);
			}

			/**
			 * Installs this UI delegate on the specified component. 
			 */
			@Override
			public void installUI(JComponent c) {
				upperThumbRect = new Rectangle();
				super.installUI(c);
			}

			/**
			 * Creates a listener to handle track events in the specified slider.
			 */
			@Override
			protected TrackListener createTrackListener(JSlider slider) {
				return new RangeTrackListener();
			}

			/**
			 * Creates a listener to handle change events in the specified slider.
			 */
			@Override
			protected ChangeListener createChangeListener(JSlider slider) {
				return new ChangeHandler();
			}

			/**
			 * Updates the dimensions for both thumbs. 
			 */
			@Override
			protected void calculateThumbSize() {
				// Call superclass method for lower thumb size.
				super.calculateThumbSize();

				// Set upper thumb size.
				upperThumbRect.setSize(thumbRect.width, thumbRect.height);
			}

			/**
			 * Updates the locations for both thumbs.
			 */
			@Override
			protected void calculateThumbLocation() {
				// Call superclass method for lower thumb location.
				super.calculateThumbLocation();

				// Adjust upper value to snap to ticks if necessary.
				if (slider.getSnapToTicks()) {
					int upperValue = slider.getValue() + slider.getExtent();
					int snappedValue = upperValue; 
					int majorTickSpacing = slider.getMajorTickSpacing();
					int minorTickSpacing = slider.getMinorTickSpacing();
					int tickSpacing = 0;

					if (minorTickSpacing > 0) {
						tickSpacing = minorTickSpacing;
					} else if (majorTickSpacing > 0) {
						tickSpacing = majorTickSpacing;
					}

					if (tickSpacing != 0) {
						// If it's not on a tick, change the value
						if ((upperValue - slider.getMinimum()) % tickSpacing != 0) {
							float temp = (float)(upperValue - slider.getMinimum()) / (float)tickSpacing;
							int whichTick = Math.round(temp);
							snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
						}

						if (snappedValue != upperValue) { 
							slider.setExtent(snappedValue - slider.getValue());
						}
					}
				}

				// Calculate upper thumb location.  The thumb is centered over its 
				// value on the track.
				if (slider.getOrientation() == JSlider.HORIZONTAL) {
					int upperPosition = xPositionForValue(slider.getValue() + slider.getExtent());
					upperThumbRect.x = upperPosition - (upperThumbRect.width / 2);
					upperThumbRect.y = trackRect.y;

				} else {
					int upperPosition = yPositionForValue(slider.getValue() + slider.getExtent());
					upperThumbRect.x = trackRect.x;
					upperThumbRect.y = upperPosition - (upperThumbRect.height / 2);
				}
			}

			/**
			 * Returns the size of a thumb.
			 */
			@Override
			protected Dimension getThumbSize() {
				return new Dimension(12, 12);
			}

			/**
			 * Paints the slider.  The selected thumb is always painted on top of the
			 * other thumb.
			 */
			@Override
			public void paint(Graphics g, JComponent c) {
				super.paint(g, c);

				Rectangle clipRect = g.getClipBounds();
				if (upperThumbSelected) {
					// Paint lower thumb first, then upper thumb.
					if (clipRect.intersects(thumbRect)) {
						paintLowerThumb(g);
					}
					if (clipRect.intersects(upperThumbRect)) {
						paintUpperThumb(g);
					}

				} else {
					// Paint upper thumb first, then lower thumb.
					if (clipRect.intersects(upperThumbRect)) {
						paintUpperThumb(g);
					}
					if (clipRect.intersects(thumbRect)) {
						paintLowerThumb(g);
					}
				}
			}

			/**
			 * Paints the track.
			 */
			@Override
			public void paintTrack(Graphics g) {
				// Draw track.
				super.paintTrack(g);

				Rectangle trackBounds = trackRect;

				if (slider.getOrientation() == JSlider.HORIZONTAL) {
					// Determine position of selected range by moving from the middle
					// of one thumb to the other.
					int lowerX = thumbRect.x + (thumbRect.width / 2);
					int upperX = upperThumbRect.x + (upperThumbRect.width / 2);

					// Determine track position.
					int cy = (trackBounds.height / 2) - 2;

					// Save color and shift position.
					Color oldColor = g.getColor();
					g.translate(trackBounds.x, trackBounds.y + cy);

					// Draw selected range.
					g.setColor(rangeColor);
					for (int y = 0; y <= 7; y++) {
						g.drawLine(lowerX - trackBounds.x, y, upperX - trackBounds.x, y);
					}

					// Restore position and color.
					g.translate(-trackBounds.x, -(trackBounds.y + cy));
					g.setColor(oldColor);

				} else {
					// Determine position of selected range by moving from the middle
					// of one thumb to the other.
					int lowerY = thumbRect.y + (thumbRect.height / 2);
					int upperY = upperThumbRect.y + (upperThumbRect.height / 2);

					// Determine track position.
					int cx = (trackBounds.width / 2) - 2;

					// Save color and shift position.
					Color oldColor = g.getColor();
					g.translate(trackBounds.x + cx, trackBounds.y);

					// Draw selected range.
					g.setColor(rangeColor);
					for (int x = 0; x <= 3; x++) {
						g.drawLine(x, lowerY - trackBounds.y, x, upperY - trackBounds.y);
					}

					// Restore position and color.
					g.translate(-(trackBounds.x + cx), -trackBounds.y);
					g.setColor(oldColor);
				}
			}

			/**
			 * Overrides superclass method to do nothing.  Thumb painting is handled
			 * within the <code>paint()</code> method.
			 */
			@Override
			public void paintThumb(Graphics g) {
				// Do nothing.
			}

			/**
			 * Paints the thumb for the lower value using the specified graphics object.
			 */
			private void paintLowerThumb(Graphics g) {
				Rectangle knobBounds = thumbRect;
				int w = knobBounds.width;
				int h = knobBounds.height;      

				// Create graphics copy.
				Graphics2D g2d = (Graphics2D) g.create();

				// Create default thumb shape.
				Shape thumbShape = createThumbShape(w - 1, h - 1);

				// Draw thumb.
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.translate(knobBounds.x, knobBounds.y);

				g2d.setColor(Color.GRAY);
				g2d.fill(thumbShape);

				g2d.setColor(Color.BLACK);
				g2d.draw(thumbShape);

				// Dispose graphics.
				g2d.dispose();
			}

			/**
			 * Paints the thumb for the upper value using the specified graphics object.
			 */
			private void paintUpperThumb(Graphics g) {
				Rectangle knobBounds = upperThumbRect;
				int w = knobBounds.width;
				int h = knobBounds.height;      

				// Create graphics copy.
				Graphics2D g2d = (Graphics2D) g.create();

				// Create default thumb shape.
				Shape thumbShape = createThumbShape(w - 1, h - 1);

				// Draw thumb.
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.translate(knobBounds.x, knobBounds.y);

				g2d.setColor(Color.GRAY);
				g2d.fill(thumbShape);

				g2d.setColor(Color.BLACK);
				g2d.draw(thumbShape);

				// Dispose graphics.
				g2d.dispose();
			}

			/**
			 * Returns a Shape representing a thumb.
			 */
			private Shape createThumbShape(int width, int height) {
				Polygon p = new Polygon();
				p.addPoint(width/2+1, height);
				p.addPoint(0, 0);
				p.addPoint(width, 0);
				return p;
			}

			/** 
			 * Sets the location of the upper thumb, and repaints the slider.  This is
			 * called when the upper thumb is dragged to repaint the slider.  The
			 * <code>setThumbLocation()</code> method performs the same task for the
			 * lower thumb.
			 */
			private void setUpperThumbLocation(int x, int y) {
				Rectangle upperUnionRect = new Rectangle();
				upperUnionRect.setBounds(upperThumbRect);

				upperThumbRect.setLocation(x, y);

				SwingUtilities.computeUnion(upperThumbRect.x, upperThumbRect.y, upperThumbRect.width, upperThumbRect.height, upperUnionRect);
				slider.repaint(upperUnionRect.x, upperUnionRect.y, upperUnionRect.width, upperUnionRect.height);
			}

			/**
			 * Moves the selected thumb in the specified direction by a block increment.
			 * This method is called when the user presses the Page Up or Down keys.
			 */
			@Override
			public void scrollByBlock(int direction) {
				synchronized (slider) {
					int blockIncrement = (slider.getMaximum() - slider.getMinimum()) / 10;
					if (blockIncrement <= 0 && slider.getMaximum() > slider.getMinimum()) {
						blockIncrement = 1;
					}
					int delta = blockIncrement * ((direction > 0) ? POSITIVE_SCROLL : NEGATIVE_SCROLL);

					if (upperThumbSelected) {
						int oldValue = ((Slider) slider).getUpperValue();
						((Slider) slider).setUpperValue(oldValue + delta);
					} else {
						int oldValue = slider.getValue();
						slider.setValue(oldValue + delta);
					}
				}
			}

			/**
			 * Moves the selected thumb in the specified direction by a unit increment.
			 * This method is called when the user presses one of the arrow keys.
			 */
			@Override
			public void scrollByUnit(int direction) {
				synchronized (slider) {
					int delta = 1 * ((direction > 0) ? POSITIVE_SCROLL : NEGATIVE_SCROLL);

					if (upperThumbSelected) {
						int oldValue = ((Slider) slider).getUpperValue();
						((Slider) slider).setUpperValue(oldValue + delta);
					} else {
						int oldValue = slider.getValue();
						slider.setValue(oldValue + delta);
					}
				}       
			}

			/**
			 * Listener to handle model change events.  This calculates the thumb 
			 * locations and repaints the slider if the value change is not caused by
			 * dragging a thumb.
			 */
			public class ChangeHandler implements ChangeListener {
				public void stateChanged(ChangeEvent arg0) {
					if (!lowerDragging && !upperDragging) {
						calculateThumbLocation();
						slider.repaint();
					}
				}
			}

			/**
			 * Listener to handle mouse movements in the slider track.
			 */
			public class RangeTrackListener extends TrackListener {

				@Override
				public void mousePressed(MouseEvent e) {
					if (!slider.isEnabled()) {
						return;
					}

					currentMouseX = e.getX();
					currentMouseY = e.getY();

					if (slider.isRequestFocusEnabled()) {
						slider.requestFocus();
					}

					// Determine which thumb is pressed.  If the upper thumb is 
					// selected (last one dragged), then check its position first;
					// otherwise check the position of the lower thumb first.
					boolean lowerPressed = false;
					boolean upperPressed = false;
					if (upperThumbSelected) {
						if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
							upperPressed = true;
						} else if (thumbRect.contains(currentMouseX, currentMouseY)) {
							lowerPressed = true;
						}
					} else {
						if (thumbRect.contains(currentMouseX, currentMouseY)) {
							lowerPressed = true;
						} else if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
							upperPressed = true;
						}
					}

					// Handle lower thumb pressed.
					if (lowerPressed) {
						switch (slider.getOrientation()) {
						case JSlider.VERTICAL:
							offset = currentMouseY - thumbRect.y;
							break;
						case JSlider.HORIZONTAL:
							offset = currentMouseX - thumbRect.x;
							break;
						}
						upperThumbSelected = false;
						lowerDragging = true;
						return;
					}
					lowerDragging = false;

					// Handle upper thumb pressed.
					if (upperPressed) {
						switch (slider.getOrientation()) {
						case JSlider.VERTICAL:
							offset = currentMouseY - upperThumbRect.y;
							break;
						case JSlider.HORIZONTAL:
							offset = currentMouseX - upperThumbRect.x;
							break;
						}
						upperThumbSelected = true;
						upperDragging = true;
						return;
					}
					upperDragging = false;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					lowerDragging = false;
					upperDragging = false;
					slider.setValueIsAdjusting(false);
					super.mouseReleased(e);
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					if (!slider.isEnabled()) {
						return;
					}

					currentMouseX = e.getX();
					currentMouseY = e.getY();

					if (lowerDragging) {
						slider.setValueIsAdjusting(true);
						moveLowerThumb();

					} else if (upperDragging) {
						slider.setValueIsAdjusting(true);
						moveUpperThumb();
					}
				}

				@Override
				public boolean shouldScroll(int direction) {
					return false;
				}

				/**
				 * Moves the location of the lower thumb, and sets its corresponding 
				 * value in the slider.
				 */
				 private void moveLowerThumb() {
					int thumbMiddle = 0;

					switch (slider.getOrientation()) {
					case JSlider.VERTICAL:      
						int halfThumbHeight = thumbRect.height / 2;
						int thumbTop = currentMouseY - offset;
						int trackTop = trackRect.y;
						int trackBottom = trackRect.y + (trackRect.height - 1);
						int vMax = yPositionForValue(slider.getValue() + slider.getExtent());

						// Apply bounds to thumb position.
						if (drawInverted()) {
							trackBottom = vMax;
						} else {
							trackTop = vMax;
						}
						thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
						thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

						setThumbLocation(thumbRect.x, thumbTop);

						// Update slider value.
						thumbMiddle = thumbTop + halfThumbHeight;
						slider.setValue(valueForYPosition(thumbMiddle));
						break;

					case JSlider.HORIZONTAL:
						int halfThumbWidth = thumbRect.width / 2;
						int thumbLeft = currentMouseX - offset;
						int trackLeft = trackRect.x;
						int trackRight = trackRect.x + (trackRect.width - 1);
						int hMax = xPositionForValue(slider.getValue() + slider.getExtent());

						// Apply bounds to thumb position.
						if (drawInverted()) {
							trackLeft = hMax;
						} else {
							trackRight = hMax;
						}
						thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
						thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

						setThumbLocation(thumbLeft, thumbRect.y);

						// Update slider value.
						thumbMiddle = thumbLeft + halfThumbWidth;
						slider.setValue(valueForXPosition(thumbMiddle));
						break;

					default:
						return;
					}
				 }

				 /**
				  * Moves the location of the upper thumb, and sets its corresponding 
				  * value in the slider.
				  */
				 private void moveUpperThumb() {
					 int thumbMiddle = 0;

					 switch (slider.getOrientation()) {
					 case JSlider.VERTICAL:      
						 int halfThumbHeight = thumbRect.height / 2;
						 int thumbTop = currentMouseY - offset;
						 int trackTop = trackRect.y;
						 int trackBottom = trackRect.y + (trackRect.height - 1);
						 int vMin = yPositionForValue(slider.getValue());

						 // Apply bounds to thumb position.
						 if (drawInverted()) {
							 trackTop = vMin;
						 } else {
							 trackBottom = vMin;
						 }
						 thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
						 thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

						 setUpperThumbLocation(thumbRect.x, thumbTop);

						 // Update slider extent.
						 thumbMiddle = thumbTop + halfThumbHeight;
						 slider.setExtent(valueForYPosition(thumbMiddle) - slider.getValue());
						 break;

					 case JSlider.HORIZONTAL:
						 int halfThumbWidth = thumbRect.width / 2;
						 int thumbLeft = currentMouseX - offset;
						 int trackLeft = trackRect.x;
						 int trackRight = trackRect.x + (trackRect.width - 1);
						 int hMin = xPositionForValue(slider.getValue());

						 // Apply bounds to thumb position.
						 if (drawInverted()) {
							 trackRight = hMin;
						 } else {
							 trackLeft = hMin;
						 }
						 thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
						 thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

						 setUpperThumbLocation(thumbLeft, thumbRect.y);

						 // Update slider extent.
						 thumbMiddle = thumbLeft + halfThumbWidth;
						 slider.setExtent(valueForXPosition(thumbMiddle) - slider.getValue());
						 break;

					 default:
						 return;
					 }
				 }
			}
		}
	}
	
}