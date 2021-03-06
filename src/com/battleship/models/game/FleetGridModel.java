/* 
 * Creation : Feb 9, 2015
 * Project Computer Science L2 Semester 4 - BattleShip
 */
package com.battleship.models.game;

import com.battleship.asset.Config;
import com.battleship.asset.RandomManager;
import com.battleship.constants.GameConstants;
import com.battleship.dynamic.EventApp;
import com.battleship.dynamic.ExplosionEvent;
import com.battleship.dynamic.UiEventApp;
import com.battleship.models.sprites.Boat;
import java.awt.Point;
import java.util.ArrayList;



/**
 * <h1>FleetGridModel</h1>
 * <p>
 * public abstract class FleetGridModel<br/>
 * extends Model
 * </p>
 * 
 * <p>
 * Grid for a fleet. Each player has got a fleet placed on a gridFleetModel. 
 * FleetGridModel is bound with one player (Its owner).
 * </p>
 * 
 * <h2>Grid Type</h2>
 * <p> There are different kind of FleetGrid</p>
 *  <ul>
 *      <li>FleetGridSquare</li>
 *      <li>FleetGridHexagon</li>
 *  </ul>
 * <h3>FleetGridSquare</h3>
 * <p>Use square coordinates like commons array displaying.</p>
 * <h3>FleetGridHexagon</h3>
 * <p>Hexagon grid</p>
 *
 * 
 * @since   Feb 9, 2015
 * @author  Constantin MASSON
 * @author  Jessica FAVIN
 * @author  Anthony CHAFFOT
 */
public abstract class FleetGridModel extends Model implements UiEventApp, GameConstants{
    //**************************************************************************
    // Constants - Variables
    //**************************************************************************
    protected   int                         gridWidth;             
    protected   int                         gridHeight;
    protected   Player                      owner;
    protected   GameModel                   game;
    protected   BoxMap[][]                  tabBoxMap;
    protected   ArrayList<Boat>             listBoats; //Boats placed on the grid
    protected   int                         nbBoatToPlace; //Determined by config
    protected   ArrayList<Integer>          listOrientations; //Available orientation
    protected   int                         currentOrientation; //Selected orientation atm
    
    private     ArrayList<ExplosionEvent>   listExplosions;
    
    
    
    
    
    //**************************************************************************
    // Constructor - Initialization
    //**************************************************************************
    /**
     * Initialize commons data for a new FleetGridMode. 
     * Debug note : Owner is not used anymore, in order to link a grid with a 
     * player, you have to do that from player class, with setFleetGrid
     * @param pWidth    Grid width
     * @param pHeight   Grid Height
     * @param pOwner    Fleet owner (It is a Player)
     * 
     * @see Player      Further information about Linking grid with a player
     */
    protected FleetGridModel(int pWidth, int pHeight, Player pOwner){
        this.gridWidth          = pWidth;
        this.gridHeight         = pHeight;
        this.owner              = pOwner;
        this.listBoats          = new ArrayList();
        this.listOrientations   = new ArrayList();
        this.currentOrientation = 0;
        this.nbBoatToPlace      = Config.getGameValues_int("nb-boats");
        this.listExplosions     = new ArrayList();
    }
    
    /**
     * Reset all boxMap at water value. 
     * Reset position of all boat (Take off from grid) and reset listBoat.
     */
    public void resetFleetGrid(){
        for(int y=0; y<this.gridHeight; y++){
            for(int x=0; x<this.gridWidth; x++){
                this.getBoxMapAt(x, y).restContent();
            }
        }
        //Reset boat position in compartment and reinitialize list of boats
        for(Boat b : this.listBoats){
            b.resetPosition();
        }
        this.listBoats      = new ArrayList();
        this.listExplosions = new ArrayList();
        this.notifyObserversModel(null);
    }
    
    
    
    
    
    //**************************************************************************
    // Functions
    //**************************************************************************
    /**
     * Return the BoxMap at position x:y 
     * If this position is not in the grid, return null
     * @param pX x coordinate
     * @param pY y coordinate
     * @return BoxMap at x:y if exists, otherwise, return null
     */
    public BoxMap getBoxMapAt(int pX, int pY){
        if (pX<0 || pX>=this.gridWidth || pY<0 || pY>=this.gridHeight){
            return null;
        }
        return this.tabBoxMap[pY][pX];
    }
    
    /**
     * Check if this grid is valid, means all boats are placed in
     * @return true if is valid fleet (Ready to be played), otherwise, return false
     */
    public boolean isValidFleetGrid(){
        return this.listBoats.size() == this.nbBoatToPlace;
    }
    
    /**
     * Check if all boat in the fleet are dead
     * @return true if all dead, otherwise, return false
     */
    public boolean isFleetDestroyed(){
        for (Boat b : this.listBoats){
            if (!b.isDead()){
                return false;
            }
        }
        return true;
    }
    
    
    
    
    
    //**************************************************************************
    // Event Functions
    //**************************************************************************
    /**
     * Add an explosion in this grid
     * @param pDelay        frequency of refresh
     * @param pIdImg        first image id
     * @param pEventType    event type
     * @param pPos          where to place event (Coordinate in grid)
     * @param pImg          id of image to place
     */
    public void addExplosion(int pDelay, int pIdImg, int pEventType, Point pPos, int pImg){
        ExplosionEvent explosion = new ExplosionEvent(pDelay, pIdImg, pEventType, this, pPos, pImg);
        this.listExplosions.add(explosion);
        explosion.startTimer();
    }

    @Override
    public void startUiEvent(EventApp pEvent){
        this.notifyObserversModel(pEvent);//pEvent is not used, null could be sent
    }

    @Override
    public void updateUiEvent(EventApp pEvent){
        this.notifyObserversModel(pEvent);//pEvent is not used, null could be sent
    }
    
    @Override
    public void stopUiEvent(EventApp pEvent){
        this.listExplosions.remove((ExplosionEvent)pEvent);
        this.notifyObserversModel(pEvent);//pEvent is not used, null could be sent
    }
    
    
    
    
    
    //**************************************************************************
    // Hover and target Functions
    //**************************************************************************
    /**
     * Hover one box map at position point p
     * @param p coordinate where target is located in the grid
     */
    public void hoverBoxMap(Point p){
        this.stopAllHover();
        if(p!=null){
            BoxMap box = this.getBoxMapAt(p.x, p.y);
            if(box!=null){
                box.hover();
            }
        }
        this.notifyObserversModel(null);
    }
    
    /**
     * Hover several BoxMap
     * @param tab list of Point coordinate where to apply hover
     */
    public void hoverSeveralBoxMap(Point[] tab){
        this.stopAllHover();
        for(Point p : tab){
            BoxMap box = this.getBoxMapAt(p.x, p.y);
            if(box!=null){
                box.hover();
            }
        }
        this.notifyObserversModel(null);
    }
    
    /*
     * Stop hover and aim for all BoxMap 
     */
    public void stopHoverAndAim(){
        for(int y=0; y<this.gridHeight; y++){
            for(int x=0; x<this.gridWidth; x++){
                this.tabBoxMap[y][x].stopHover();
                this.tabBoxMap[y][x].stopAim();
            }
        }
        this.notifyObserversModel(null);
    }
    
    /**
     * stop aiming all tab in this fleetGridModel
     */
    public void stopAllHover(){
        for(int y=0; y<this.gridHeight; y++){
            for(int x=0; x<this.gridWidth; x++){
                this.tabBoxMap[y][x].stopHover();
            }
        }
        this.notifyObserversModel(null);
    }
    
    /**
     * stop aiming all tab in this fleetGridModel
     */
    public void stopAiming(){
        for(int y=0; y<this.gridHeight; y++){
            for(int x=0; x<this.gridWidth; x++){
                this.tabBoxMap[y][x].stopAim();
            }
        }
        this.notifyObserversModel(null);
    }
    
    
    
    
    

    //**************************************************************************
    // Functions for orientation - Placement
    //**************************************************************************
    /**
     * Add pBoat in the list of boat placed in the FleetGrid. 
     * <strong>
     * Note this function doesn't add the boat in the grid! It is 
     * used only for adding in the list a boat placed on the grid. Placement 
     * in the grid is done by over function from Player
     * </strong>
     * @param pBoat Boat to add
     */
    public void addBoat(Boat pBoat){
        if(this.listBoats.contains(pBoat) != true){
            this.listBoats.add(pBoat);
        }
    }
    
    /**
     * Switch current orientation for a boat to place
     */
    public void switchNextOrientation() {
        currentOrientation++;
        if(currentOrientation>=listOrientations.size()){
            currentOrientation = 0;
        }
        this.notifyObserversModel(null);
    }

    /**
     * Switch previous orientation for a boat to place
     */
    public void switchPreviousOrientation() {
        currentOrientation--;
        if(currentOrientation<0){
            currentOrientation = (listOrientations.size()-1);
        }
        this.notifyObserversModel(null);
    }
    
    
    
    
    
    //**************************************************************************
    // Getters - Setters
    //**************************************************************************
    /**
     * Return the Fleet Grid by using a matrix
     * @return matrix with BoxMap
     */
    public BoxMap[][] getTabBoxMap(){
        return this.tabBoxMap;
    }
    
    /**
     * Return grid owner, null if no owner
     * @return Player
     */
    public Player getOwner(){
        return this.owner;
    }
    
    /**
     * Return list boats linked with this grid
     * @return ArrayList (Type Boat) list of boats
     */
    public ArrayList<Boat> getListBoats(){
        return this.listBoats;
    }
    
    /**
     * Return the list of available orientation for this grid
     * @return ArrayList of Integer
     */
    public ArrayList<Integer> getListOrientations(){
        return this.listOrientations;
    }
    
    /**
     * Return list of explosion on this FleetGridModel
     * @return ArrayList of ExplosionEvent
     */
    public ArrayList<ExplosionEvent> getListExplosions(){
        return this.listExplosions;
    }
    
    /**
     * Return current orientation value
     * @return int current orientation
     */
    public int getCurrentOrientation(){
        return this.listOrientations.get(currentOrientation);
    }
    
    /**
     * Return a random orientation valid for this kind of grid
     * @return random orientation
     */
    public int getRandomOrientation(){
        int randomPos = RandomManager.getRandomBetween(0, this.listOrientations.size()-1);
        return this.listOrientations.get(randomPos);
    }
    
    /**
     * Return game where FleetGrid is used
     * @return GameModel where FleetGrid is placed
     */
    public GameModel getGameModel(){
        return this.game;
    }
    
    //**************************************************************************
    /**
     * Set owner of this fleet. Beware, if owner already exists, will be replaced 
     * by this new owner. If null given, fleet will be owned by no one. (Owner is 
     * null)
     * @param pOwner Player owner of this fleetGrid
     */
    public void setOwner(Player pOwner){
        this.owner = pOwner;
    }
    
    /**
     * Set the game where this FleetGrid is used. Do nothing if null
     * @param pGame game where FleetGrid is used
     */
    public void setGame(GameModel pGame){
        this.game = pGame;
    }
    
    
    
    
    
    //**************************************************************************
    // Function for network 
    //**************************************************************************
}
