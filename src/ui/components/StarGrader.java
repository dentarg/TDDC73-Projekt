package ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.EventListener;

import javax.swing.JComponent;
import javax.swing.event.EventListenerList;
import javax.swing.event.MouseInputListener;

/**
 * A classical grader component whit stars that light up on mouse over.
 * 
 * @author jernlas
 */
public class StarGrader extends JComponent implements MouseInputListener {

   /**
    * Grade shange listener interface.
    */
   public interface GradeChangeListener extends EventListener {
      public void gradeChanged(int to);
   }

   /**
    * Width of a star.
    */
   private static final int  STAR_WIDTH       = 20;

   /**
    * Hlaf width of a star.
    */
   private static final int  HALF_STAR_WIDTH  = STAR_WIDTH / 2;

   /**
    * The maximum grade (number of stars.
    */
   private static final int  MAX_GRADE        = 5;

   private static final long serialVersionUID = 1L;

   /**
    * The number of outer points of the star
    * ("knives extending from the center").
    */
   private static final int  STAR_POINTS      = 5;

   /**
    * Pre generated polygons for a the stars.
    */
   private static Polygon[]  starPolygon      = new Polygon[MAX_GRADE];

   
   /**
    * Pre generate polygons for the stars.
    */
   static {
      double angleIncrement = 2 * Math.PI / (STAR_POINTS * 2);
      double currentAngle = -Math.PI / 2;
      final int points = STAR_POINTS * 2;
      int[] xPos = new int[points];
      int[] yPos = new int[points];
      for (int n = 0; n < points; n++) {
         xPos[n] = (int) (Math.cos(currentAngle)
               * (HALF_STAR_WIDTH / (1 + (1.6 * (n % 2)))) + HALF_STAR_WIDTH);
         yPos[n] = (int) (Math.sin(currentAngle)
               * (HALF_STAR_WIDTH / (1 + (1.6 * (n % 2)))) + HALF_STAR_WIDTH);
         currentAngle += angleIncrement;
      }
      for (int n = 0; n < MAX_GRADE; n++) {
         Polygon p = new Polygon(xPos, yPos, points);
         p.translate((STAR_WIDTH + HALF_STAR_WIDTH) * n, 0);
         starPolygon[n] = p;
      }

   }

   /**
    * Checks if the given grade is within accepted range.
    * @param grade The new grade.
    * @return True if the grade is allowed.
    */
   private static boolean isGradeAllowed(int grade) {
      return ((grade >= 0) && (grade <= MAX_GRADE));
   }

   /**
    * The current grade value.
    */
   private int                     currentGrade   = 0;

   /**
    * The listeners for grade updates.
    */
   private final EventListenerList gradeListeners = new EventListenerList();
   
   /**
    * Basically equivalent to if the mouse is inside the component.
    */
   private boolean                 isArmed        = false;

   /**
    * What grade would the user set if he clicked right now.
    */
   private int                     mouseOverGrade = 0;

   
   /**
    * Creates a new default StarGrader. 
    */
   public StarGrader() {
      int width = (STAR_WIDTH + HALF_STAR_WIDTH) * MAX_GRADE;
      setBounds(new Rectangle(width, STAR_WIDTH));
      setPreferredSize(new Dimension(width, STAR_WIDTH));
      setMinimumSize(new Dimension(width, STAR_WIDTH));
      setMaximumSize(new Dimension(width, STAR_WIDTH));
      addMouseListener(this);
      addMouseMotionListener(this);
   }

   
   /**
    * Adds a GradeChangeListener
    * @param gel the listener
    */
   public void addGradeEventListener(GradeChangeListener gel) {
      gradeListeners.add(GradeChangeListener.class, gel);
   }

   /**
    * Gets the currently set grade.
    * @return The grade
    */
   public int getGrade() {
      return currentGrade;
   }

   /**
    * Gets the star that is hit at point p
    * @param p The point to check
    * @return The number of the star hit.
    */
   private int getHitStar(Point p) {
      double x = p.getX();
      return (int) (x / (STAR_WIDTH + HALF_STAR_WIDTH));
   }

   /**
    * Gets the grade the user would set if he clicked.
    * @return the mouseOverGrade
    */
   public int getMouseOverGrade() {
      return mouseOverGrade;
   }

   /**
    * Should the faint change-stars be drawn?
    * @param star The new number of stars
    * @return True if the new faint stars should be indicated. 
    */
   private boolean isChanging(int star) {
      return (isArmed && (((star <= currentGrade) && (star > mouseOverGrade)) || ((star > currentGrade) && (star <= mouseOverGrade))));
   }

   /**
    * Is the star currently selected?
    * @param star The star to check
    * @return True iof the star is selected.
    */
   private boolean isCurrent(int star) {
      return ((star <= currentGrade) && !isChanging(star));
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
    */
   public void mouseClicked(MouseEvent e) {
      // ignore
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
    */
   public void mouseDragged(MouseEvent e) {
      // ignore
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
    */
   public void mouseEntered(MouseEvent e) {
      isArmed = true;
      repaint();
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
    */
   public void mouseExited(MouseEvent e) {
      isArmed = false;
      repaint();
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
    */
   public void mouseMoved(MouseEvent e) {
      int target = getHitStar(e.getPoint());
      setMouseOverGrade(target);
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
    */
   public void mousePressed(MouseEvent e) {
      // ignore
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
    */
   public void mouseReleased(MouseEvent e) {
      int target = getHitStar(e.getPoint());
      setGrade(target);
   }

   /**
    * Update all listeners of a grade change.
    */
   private void notifyNewGrade() {
      GradeChangeListener[] listeners = gradeListeners
            .getListeners(GradeChangeListener.class);
      for (int i = listeners.length - 1; i >= 0; i--) {
         listeners[i].gradeChanged(getGrade());
      }
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
    */
   @Override
   public void paintComponent(Graphics g) {
      Graphics movingG = g.create();
      for (int i = 0; i < MAX_GRADE; i++) {
         paintStar(movingG, i);
      }
   }

   /**
    * Paints a single star
    * @param g The graphics to paint on
    * @param pos The star to paint.
    */
   private void paintStar(Graphics g, int pos) {
      Color c;
      if (isChanging(pos)) {
         c = Color.ORANGE;
      } else if (isCurrent(pos)) {
         c = Color.YELLOW;
      } else {
         c = Color.gray;
      }
      g.setColor(c);
      g.fillPolygon(starPolygon[pos]);
      g.setColor(Color.BLACK);
      g.drawPolygon(starPolygon[pos]);
   }

   
   /**
    * Unregister a grade listener
    * @param gel The listener
    */
   public void removeGradeEventListener(GradeChangeListener gel) {
      gradeListeners.remove(GradeChangeListener.class, gel);
   }

   
   /**
    * Sets a grade.
    * @param grade The grade to set.
    */
   public void setGrade(int grade) {
      if ((grade != this.currentGrade) && isGradeAllowed(grade)) {
         this.currentGrade = grade;
         notifyNewGrade();
         repaint();
      }
   }

   /**
    * Sets the mouse over grade (the one indicated by faint painting).
    * @param mouseOverGrade
    *           the mouseOverGrade to set
    */
   public void setMouseOverGrade(int grade) {
      if (isGradeAllowed(grade) && (grade != mouseOverGrade)) {
         mouseOverGrade = grade;
         repaint();
      }
   }

}
