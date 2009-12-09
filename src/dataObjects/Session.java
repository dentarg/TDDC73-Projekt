/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataObjects;

import java.util.List;

/**
 *  A singelton class that contains the
 *  current user.
 *
 * @author jonas
 */
public class Session {
//current user

    Subject user = null;
    List subjects = null;
    
//only session
    private static Session instance = null;

    /**
     * Private constructor
     */
    private Session() {
    }

    /**
     * Gets the one and only instance of Session
     * @return  Session current instance of Session
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Sets the current user
     * @param user  Subject the user
     */
    public void setUser(Subject user) {
        this.user = user;
    }

    /**
     * Sets the current subjects
     * @param subjects  List the subject
     */
    public void setSubjects(List subjects) {
        this.subjects = subjects;
    }
    
    /**
     * Gets the user in this Session
     * @return  Subject current user;
     */
    public Subject getUser() {
        return user;
    }
    
    /**
     * Gets the subjects in this Session
     * @return  List current subjects;
     */
    public List getSubjects() {
        return subjects;
    }
}
