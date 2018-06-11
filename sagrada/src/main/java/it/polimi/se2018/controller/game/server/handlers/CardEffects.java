package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Bag;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Reserve;
import it.polimi.se2018.model.dice.RoundTracker;

/**
 * Class covering all the generic card effects
 * @author Alì El Wahsh
 */
public interface CardEffects {


    /**
     * Changes the value of a single die
     * @param d die
     * @param newValue new die value
     * @param invert true if it change the value using the one on the opposite face
     * @return null, if the operation is successful, an error string otherwise
     */
     static String changeValue(Die d, boolean invert, int newValue)
     {
         String invalidValue = "Valore invalido";
         if(invert)
         {
             d.setFace(Math.abs(d.getValue()-7));
             return null;
         }
         else
         {
             if(d.getValue() == 1 && newValue != 2)
                 return invalidValue;
             else if(d.getValue() == 6 && newValue != 5)
                 return invalidValue;
             else if(newValue == d.getValue()-1 || newValue == d.getValue()+1) {
                 d.setFace(newValue);
                 return null;
             }
         }
         return invalidValue;
     }

    /**
     * Adds a die to the grid ignoring a specific rule
     * @param player player who's trying to place the die
     * @param h height of the spot
     * @param w width of the spot
     * @param d the die to be placed
     * @param colorRestriction true if color restriction is enabled
     * @param valueRestriction true if value restriction is enabled
     * @param adjacentRestriction true if adjacency restriction is enabled
     * @return null if the requirements are respected, otherwise an error message
     */
     static String ignoringInsertionRule(Player player, int h, int w, Die d, boolean colorRestriction, boolean valueRestriction, boolean adjacentRestriction)
     {
         String result = DiePlacementLogic.insertDie(player,h,w,d,colorRestriction,valueRestriction,adjacentRestriction);
         if(result == null)
         {
             player.getGrid().setDie(h,w,d);
         }
         return result;
     }

    /**
     * Swaps a die from the reserve with one from the round track
     * @param reserveIndex die index inside the reserve
     * @param round round of the round track
     * @param diePosition position of the die inside the roundtrack
     * @param reserve reserve
     * @param roundTracker round track
     * @return null if it's a success, an error string otherwise
     */
     static String reserveRoundTrackSwap(int reserveIndex, int round, int diePosition, Reserve reserve, RoundTracker roundTracker)
     {
         if(reserve.get(reserveIndex) != null && roundTracker.getDie(round,diePosition) != null)
         {
             Die temp = reserve.popDie(reserveIndex);
             reserve.add(roundTracker.popDie(round,diePosition));
             roundTracker.addDie(temp,round);
             return null;
         }
         return "Die/dice selected are invalid";
     }

    /**
     * Rerolls a die/ all dice inside the reserve
     * @param all true if all dice are rerolle, false if it is just one
     * @param reserve reserve to be rerolled
     * @param d die to be rerolled
     */
     static void reRoll(boolean all, Reserve reserve, Die d)
     {
         if(all)
         {
             reserve.rollReserve();
         }
         else
             d.roll();
     }

    /**
     * Swaps a die form the reserve with one from the bag
     * @param reserveIndex die position inside the reserve
     * @param bag bag from which a die is taken
     * @param reserve reserve
     */
     static void reserveBagSwap(int reserveIndex, Bag bag, Reserve reserve)
     {
         bag.add(reserve.popDie(reserveIndex));
         reserve.addAll(bag.popDice(1));
     }
}
