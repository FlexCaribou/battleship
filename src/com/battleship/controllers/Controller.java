/* 
 * Creation:    Mar 12, 2015
 * Project Computer Science L2 Semester 4 - BattleShip
 */
package com.battleship.controllers;

import com.battleship.exceptions.ExecError;





/**
 * <h1>Controller</h1>
 * <p>public abstract class Controller</p>
 * <p>Each controller extends this class. Define a controller</p>
 *
 * @since   Mar 12, 2015
 * @author  Constantin MASSON
 * @author  Jessica FAVIN
 * @author  Anthony CHAFFOT
 * 
 * @param <V> View linked with this controller
 * @param <M> Model managed by this controller
 */
public abstract class Controller <V,M>{
    //**************************************************************************
    // Constants - Variables
    //**************************************************************************
    protected   V       v;
    protected   M       m;
    
    
    
    
    
    //**************************************************************************
    // Constructor - Initialization
    //**************************************************************************
    protected Controller(M pModel) throws ExecError{
        if(pModel==null){
            throw new ExecError(430, "controller");
        }
        this.m = pModel;
    }
    
    /**
     * Create a new Controller linked with a view a model
     * @param pView     View linked with this controller
     * @param pModel    Model managed by this controller
     * @throws ExecError Exception if pView or pModel is null
     */
    protected Controller(V pView, M pModel) throws ExecError{
        if(pView==null || pModel==null){
            throw new ExecError();
        }
        this.v = pView;
        this.m = pModel;
    }
    
    
    
    
    
    //**************************************************************************
    // Getters - Setters
    //**************************************************************************
    /**
     * Add a view for this controller.
     * @param pView view to link with this controller
     * @throws ExecError thrown if unable to set the view (Null_
     */
    public void setView(V pView) throws ExecError{
        if(pView==null){
            throw new ExecError();
        }
        this.v = pView;
    }
}
