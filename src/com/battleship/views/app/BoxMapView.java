/* 
 * Creation:    Apr 1, 2015
 * Project Computer Science L2 Semester 4 - BattleShip
 */
package com.battleship.views.app;

import com.battleship.observers.ObserverModel;
import com.battleship.views.tools.Config;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;





/**
 * <h1>BoxMapView</h1>
 * <p>
 * public class BoxMapView<br/>
 * implements ObserverModel
 * </p>
 *
 * @date    Apr 1, 2015
 * @author  Constantin MASSON
 * @author  Anthony CHAFFOT
 * @author  Jessica FAVIN
 */
public abstract class BoxMapView implements ObserverModel{
    //**************************************************************************
    // Constants - Variables
    //**************************************************************************
    protected   Point       coordinate;
    protected   boolean     isVisible;
    protected   boolean     isTargeted;
    protected   Dimension   dimension;
    protected   int         borderSize;
    
    
    
    
    

    //**************************************************************************
    // Constructor - Initialization
    //**************************************************************************
    /**
     * Create a new BoxMapView at position given (Grid coordinates)
     * @param x x coordinate
     * @param y y coordinate
     * @param pDimension BoxMap default dimension
     */
    protected BoxMapView(int x, int y, Dimension pDimension){
        this.coordinate = new Point(x, y);
        this.dimension  = pDimension;
        this.isVisible  = true;
        this.isTargeted = false;
        this.borderSize = Config.getDimValues_int("boxmap-border-size");
    }
    
    
    
    
    

    //**************************************************************************
    // Functions
    //**************************************************************************
    /**
     * Draw the BoxMap
     * @param g 
     */
    public abstract void draw(Graphics2D g);
    
    /*
     * Draw the box with hidden status
     * @param g 
     */
    protected abstract void drawHidden(Graphics2D g);
    
    
    
    
    

    //**************************************************************************
    // Getters - Setters
    //**************************************************************************
    /**
     * Change current visible value. Value false will apply a hidden context to 
     * current BoxMapView
     * @param pValue boolean value
     */
    public void setVisible(boolean pValue){
        this.isVisible = pValue;
    }
}