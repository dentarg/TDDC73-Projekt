package dataObjects;

import java.util.ArrayList;

/**
 * Class representing an assignment used in CS algorithms.
 */
public class Assignment {
   private ArrayList<SingleAssignment> assignments;

   /**
    * Create a new assignment.
    */
   public Assignment() {
      assignments = new ArrayList<SingleAssignment>();
   }

   /**
    * Create a new assignment with initial values.
    * 
    * @param assign
    *           ArrayList of SingleAssignment.
    */
   public Assignment(ArrayList<SingleAssignment> assign) {
      assignments = assign;
   }

   /**
    * Add an assignment.
    * 
    * @param assign
    *           A SingleAssignment to add.
    */
   public void addAssignment(SingleAssignment assign) {
      assignments.add(assign);
   }

   /**
    * Checks if this is a complete recipe assignment.
    * 
    * @return true if it is, false otherwise.
    */
   // XXX Is there a way to not use a magic number here?
   public boolean completeAssignment() {
      return assignments.size() == 10;
   }

   /**
    * Get an assignment.
    * 
    * @param index
    *           int index of assignment.
    * @return SingleAssignment
    */
   public SingleAssignment getAssignment(int index) {
      return assignments.get(index);
   }

   /**
    * Makes a copy of the assignment array.
    * 
    * @return ArrayList
    */
   @SuppressWarnings("unchecked")
   public ArrayList<SingleAssignment> makeArrayCopy() {
      return (ArrayList<SingleAssignment>) assignments.clone();
   }

   /**
    * Removes an assignment.
    * 
    * @param index
    *           Index of assignment to remove, int.
    */
   public void removeAssignment(int index) {
      assignments.remove(index);
   }

   /**
    * Size of the assignment.
    * 
    * @return Size of the assignment.
    */
   public int size() {
      return assignments.size();
   }

   /**
    * String representation of an assignment.
    * 
    * @return String
    */
   @Override
   public String toString() {
      String s = "";
      for (int index = 0; index < assignments.size(); index++) {
         s += assignments.get(index) + "\n";
      }
      return s;
   }
}
