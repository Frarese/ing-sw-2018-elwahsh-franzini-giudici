package it.polimi.se2018.controller.game.server.handlers;


import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.*;
import it.polimi.se2018.events.messages.*;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.toolcards.*;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.util.Pair;


import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handler for all tool cards effects
 * @author Alì El Wahsh
 */
public class ToolCardsHandler implements Runnable,Observer  {

    private final Board board;
    private final Player player;
    private final UseToolCardMove move;
    private final boolean firsTurn;
    private final MatchNetworkInterface network;
    private static final String NO_PERMISSION = "Non hai i permessi per usare questa carta";
    private static final String TOO_SLOW = "Troppo lento";
    private static final String OH_NO = "Qualcosa è andato storto";
    private static final String DIESET = "DieSet";
    private static final String NEWVALUE = "NewValue";
    private PlayerMove response;
    private CountDownLatch latch = new CountDownLatch(0);
    private int cardPosition;
    private final RandomDice randomDice;
    private boolean isDead = false;
    /**
     * Constructor
     * @param move card move
     * @param player player using the card
     * @param board game board
     * @param firstTurn true if first turn, false otherwise
     * @param networkInterface network interface
     */
    public ToolCardsHandler(UseToolCardMove move, Player player, Board board, boolean firstTurn, MatchNetworkInterface networkInterface, RandomDice randomDice)
    {
        this.move = move;
        this.player = player;
        this.board = board;
        this.firsTurn = firstTurn;
        this.network = networkInterface;
        this.randomDice = randomDice;
    }

    /**
     * Notifies failure sending a invalid move message
     * @param error error message
     */
    private void notifyFailure(String error)
    {
        network.sendReq(new InvalidMove(move,error,false),player.getName());
    }

    /**
     * Notifies success sending a confirm move message
     */
    private void notifySuccess()
    {

        network.sendReq(new ConfirmMove(move,false),player.getName());
    }

    /**
     * For each card checks its permission and executes its effect
     */
    private void checksEffect()
    {
        boolean isInGame = false;
        boolean canPlay = false;
        for(int i = 0; i<3; i++)
        {
            if(board.getTool(i).getId() == move.getCardID())
            {
                isInGame = true;
                cardPosition = i;
            }
        }

        if(player.getFavourPoints()>=2 || !board.getTool(cardPosition).isUsed() && player.getFavourPoints()>=1)
            canPlay = true;

        if(isInGame && canPlay) {
            switch (move.getCardID()) {
                case 20:
                    grozingPliers();
                    break;
                case 21:
                    eglomiseBrush();
                    break;
                case 22:
                    copperFoilBurnisher();
                    break;
                case 23:
                    lathekin();
                    break;
                case 24:
                    lensCutter();
                    break;
                case 25:
                    fluxBrush();
                    break;
                case 26:
                    glazinHammer();
                    break;
                case 27:
                    runningPliers();
                    break;
                case 28:
                    corkBackedStraightedge();
                    break;
                case 29:
                    grindingStone();
                    break;
                case 30:
                    fluxRemover();
                    break;
                case 31:
                    tapWheel();
                    break;
                default:
                    notifyFailure("Carta inesistente");
            }
        }
        else
            notifyFailure("Non puoi giocare questa carta");

        isDead = true;
    }

    @Override
    public void run() {
        checksEffect();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!isDead) {
            if (arg != null) {
                response = (PlayerMove) arg;
                if (latch.getCount() > 0)
                    latch.countDown();
            }
        }else o.deleteObserver(this);
    }

    /**
     * Grozing Pliers Card effect
     */
    private void grozingPliers() {
        int index;
        int newValue;
        int oldValue;
        if (new GrozingPliers().isUsable(player, firsTurn)) {

            index = askDieFromReserve();
            if(index >-1)
            {
                oldValue = board.getReserve().get(index).getValue();
                newValue = askNewValue(index);
                String result = CardEffects.changeValue(board.getReserve().get(index),false,newValue);
                network.sendReq(new ReserveStatus(board.getReserve()),player.getName());
                if(result == null)
                {
                        result = setDie(index,true);
                        if (result == null) {
                            player.setCardRights(firsTurn, false);
                            player.setPlacementRights(firsTurn, false);
                            useCard(player);
                            updateGameState();
                            notifySuccess();
                        } else
                        {
                            board.getReserve().get(index).setFace(oldValue);
                            updateGameState();
                            notifyFailure(result);

                        }
                }
                else
                    notifyFailure(result);
                }
        }
        else
            notifyFailure(NO_PERMISSION);
        }

    /**
     * Eglomise Brush card effect
     */
    private void eglomiseBrush()
        {
            int h;
            int w;
            Pair<Integer,Integer> coor;
            if(new EglomiseBrush().isUsable(player,firsTurn))
            {
                coor = askDieFromGrid();
                if(coor != null)
                {
                     h = coor.getFirst();
                     w = coor.getSecond();
                     String result = setDieFromGrid(h,w,false,true);
                     if(result == null)
                     {
                         player.setCardRights(firsTurn, false);
                         useCard(player);
                         updateGameState();
                         notifySuccess();
                     }
                     else
                     {
                         notifyFailure(result);
                     }
                }
                else
                    notifyFailure(OH_NO);
            }
            else notifyFailure(NO_PERMISSION);

        }

    /**
     * Copper Foil Burnisher card effect
     */
    private void copperFoilBurnisher()
    {
        int h;
        int w;
        Pair<Integer,Integer> coor;
        if(new CopperFoilBurnisher().isUsable(player,firsTurn))
        {

            coor =askDieFromGrid();
            if(coor != null)
            {
                h = coor.getFirst();
                w = coor.getSecond();
                String result = setDieFromGrid(h,w,true,false);
                if(result == null)
                {
                    player.setCardRights(firsTurn, false);
                    useCard(player);
                    updateGameState();
                    notifySuccess();
                }
                else
                {
                    notifyFailure(result);
                }
            }
            else
                notifyFailure(OH_NO);
        }
        else
            notifyFailure(NO_PERMISSION);
    }

    /**
     * Lathekin card effect
     */
    private void lathekin()
    {
        Pair<Integer,Integer> coor;
        Pair<Integer,Integer> coor2;

        if(new Lathekin().isUsable(player,firsTurn))
        {
            coor = askDieFromGrid();
            coor2 = askDieFromGrid();
            if(coor != null && coor2 != null )
            {
                String result = setDoubleDieFromGrid(coor.getFirst(),coor.getSecond(),coor2.getFirst(),coor2.getSecond());
                if(result == null)
                {
                    player.setCardRights(firsTurn,false);
                    useCard(player);
                    updateGameState();
                    notifySuccess();
                }
                else
                    notifyFailure(result);
            }
            else
                notifyFailure(OH_NO);
        }
        else notifyFailure(NO_PERMISSION);
    }

    /**
     * Lens Cutter card effect
     */
    private void lensCutter()
    {
        int round;
        int diePosition;
        int reserveIndex;
        Pair<Integer,Integer> die;
        if(new LensCutter().isUsable(player,firsTurn) && board.getRoundTrack().lastFilledRound()>0)
        {

            reserveIndex = askDieFromReserve();
            if(reserveIndex >-1)
            {
                die = askDieFromRoundTrack();
                if(die != null)
                {
                    round = die.getFirst();
                    diePosition = die.getSecond();
                    String result = setDieFromRoundTrack(round,diePosition,reserveIndex);
                    if(result == null)
                    {
                        player.setCardRights(firsTurn,false);
                        player.setPlacementRights(firsTurn,false);
                        useCard(player);
                        updateGameState();
                        notifySuccess();

                    }
                    else
                        notifyFailure(result);
                }
                else notifyFailure(OH_NO);
            }
            else notifyFailure(OH_NO);
        }
        else
            notifyFailure(NO_PERMISSION);
    }

    /**
     * Flux Brush card effect
     */
    private void fluxBrush()
    {
        int index;
        int oldValue;
        if(new FluxBrush().isUsable(player,firsTurn))
        {

            if(randomDice.getRollDieIndex()<0) {
                index = askDieFromReserve();
                if (index >-1)
                {
                   oldValue = board.getReserve().get(index).getValue();
                   board.getReserve().get(index).roll();
                   network.sendReq(new ReserveStatus(board.getReserve()),player.getName());
                   String result = setDie(index,true);
                   if(result == null)
                   {
                       player.setCardRights(firsTurn,false);
                       player.setPlacementRights(firsTurn,false);
                       useCard(player);
                       updateGameState();
                       notifySuccess();
                   }
                   else
                   {
                       randomDice.setRollDieIndex(index);
                       randomDice.setRollDie(board.getReserve().get(index));
                       board.getReserve().get(index).setFace(oldValue);
                       updateGameState();
                       notifyFailure(result);
                   }
                }
                else notifyFailure(OH_NO);
            }
            else
            {
               noCheatFluxBrush();
            }
        }
        else
            notifyFailure(NO_PERMISSION);
    }

    /**
     * Does not permit player to call the card again with a new value
     */
    private void noCheatFluxBrush()
    {
        int index = randomDice.getRollDieIndex();
        int oldValue = board.getReserve().get(index).getValue();
        board.getReserve().get(index).setFace(randomDice.getRollDie().getValue());
        String result = setDie(index,true);
        if(result == null)
        {
            player.setCardRights(firsTurn,false);
            player.setPlacementRights(firsTurn,false);
            useCard(player);
            updateGameState();
            notifySuccess();
        }
        else
        {
            board.getReserve().get(index).setFace(oldValue);
            notifyFailure(result);
        }
    }

    /**
     * Glazing Hammer card effect
     */
    private void glazinHammer()
    {
        if(new GlazingHammer().isUsable(player,firsTurn))
        {

            CardEffects.reRoll(true,board.getReserve(),null);
            player.setCardRights(firsTurn,false);
            useCard(player);
            updateGameState();
            notifySuccess();
        }
        else notifyFailure(NO_PERMISSION);
    }


    /**
     * Cork Backed Straightedge card effect
     */
    private void corkBackedStraightedge()
    {
        int index;
        if(new CorkBackedStraightedge().isUsable(player,firsTurn))
        {

            index = askDieFromReserve();
            if(index >-1)
            {
                String result = setDie(index,false);
                if(result == null)
                {
                    player.setCardRights(firsTurn,false);
                    player.setPlacementRights(firsTurn,false);
                    useCard(player);
                    updateGameState();
                    notifySuccess();
                }
                else
                    notifyFailure(result);
            }
            else
                notifyFailure(OH_NO);
        }
        else
            notifyFailure(NO_PERMISSION);
    }


    /**
     * Running Pliers card effect
     */
    private void runningPliers()
    {
        int reserveIndex;
        if(new RunningPliers().isUsable(player,firsTurn))
        {

            reserveIndex = askDieFromReserve();
            if(reserveIndex >-1)
            {
                String result = setDie(reserveIndex,true);
                if(result == null)
                {
                    player.setPlacementRights(false,false);
                    player.setCardRights(false,false);
                    player.setCardRights(true,false);
                    useCard(player);
                    updateGameState();
                    notifySuccess();
                }
                else
                    notifyFailure(result);
            }
            else
                notifyFailure(OH_NO);
        }
        else
            notifyFailure(NO_PERMISSION);
    }

    /**
     * Grinding stone card effect
     */
    private void grindingStone()
    {
        int index;
        if(new GrindingStone().isUsable(player,firsTurn))
        {

            index = askDieFromReserve();
            if(index >-1)
            {
                CardEffects.changeValue(board.getReserve().get(index),true,0);
                network.sendReq(new ReserveStatus(board.getReserve()),player.getName());
                String result = setDie(index,true);
                if(result == null)
                {
                    player.setPlacementRights(firsTurn,false);
                    player.setCardRights(firsTurn,false);
                    useCard(player);
                    updateGameState();
                    notifySuccess();
                }
                else
                {
                    CardEffects.changeValue(board.getReserve().get(index),true,0);
                    updateGameState();
                    notifyFailure(result);
                }
            }
            else
                notifyFailure(OH_NO);
        }
        else
            notifyFailure(NO_PERMISSION);
    }

    /**
     * Flux Remover Card effect
     */
    private void fluxRemover()
    {
        int index;
        if(new FluxRemover().isUsable(player,firsTurn))
        {

            if(randomDice.getBagDie() == -1) {
                index = askDieFromReserve();
                if (index > -1) {
                    String result = setDieFromBag(index);
                    if (result == null) {
                        player.setPlacementRights(firsTurn, false);
                        player.setCardRights(firsTurn, false);
                        useCard(player);
                        updateGameState();
                        notifySuccess();
                    } else {
                       notifyFailure(result);
                        randomDice.setBagDie(index);
                    }
                }
                else
                    notifyFailure(OH_NO);
            }
            else
            {
                noCheatFluxRemover();
            }
        }
        else
            notifyFailure(NO_PERMISSION);
    }

    /**
     * Avoids cheats from players calling more than one time the same card
     */
    private void noCheatFluxRemover()
    {
        String result = setDieFromBag(randomDice.getBagDie());
        if (result == null) {
            player.setPlacementRights(firsTurn, false);
            player.setCardRights(firsTurn, false);
            useCard(player);
            updateGameState();
            notifySuccess();
        }
        else
            notifyFailure(result);
    }


    /**
     * Tap Wheel Card Effect
     */
    private void tapWheel()
    {
        int numberOfPlacements;
        if(new TapWheel().isUsable(player,firsTurn))
        {
            numberOfPlacements = askNumberOfPlacement();
            if(numberOfPlacements == 2)
            {
                tapWheelTwoDice();
            }
            else if(numberOfPlacements == 1)
            {
                tapWheelOneDie();
            }
            else notifyFailure(OH_NO);
        }
        else notifyFailure(NO_PERMISSION);
    }

    /**
     * Tap wheel with one placement
     */
    private void tapWheelOneDie()
    {
        Pair<Integer,Integer> roundDie = askDieFromRoundTrack();
        Pair<Integer,Integer> coor = askDieFromGrid();
        if(coor!=null && roundDie!=null)
        {
            Die roundTrackDie = board.getRoundTrack().getDie(roundDie.getFirst(),roundDie.getSecond());
            Die d1 = player.getGrid().getDie(coor.getFirst(),coor.getSecond());
            if( d1.getColor() == roundTrackDie.getColor() ) {
                String result = setDieFromGrid(coor.getFirst(),coor.getSecond(),true,true);
                if (result == null) {
                    player.setCardRights(firsTurn, false);
                    useCard(player);
                    updateGameState();
                    notifySuccess();
                } else
                    notifyFailure(result);
            }
            else
                notifyFailure("Colori non uguali!");
        }
        else
            notifyFailure(OH_NO);
    }

    /**
     * Tap Wheel with two placements
     */
    private void tapWheelTwoDice()
    {
        Pair<Integer,Integer> roundDie = askDieFromRoundTrack();
        Pair<Integer,Integer> coor = askDieFromGrid();
        Pair<Integer,Integer> coor2 = askDieFromGrid();
        if(coor != null && coor2 != null && roundDie != null)
        {
            Die roundTrackDie = board.getRoundTrack().getDie(roundDie.getFirst(),roundDie.getSecond());
            Die d1 = player.getGrid().getDie(coor.getFirst(),coor.getSecond());
            Die d2 = player.getGrid().getDie(coor2.getFirst(),coor2.getSecond());

            if( isSameColor(d1,d2,roundTrackDie) ) {
                String result = setDoubleDieFromGrid(coor.getFirst(), coor.getSecond(), coor2.getFirst(), coor2.getSecond());
                if (result == null) {
                    player.setCardRights(firsTurn, false);
                    useCard(player);
                    updateGameState();
                    notifySuccess();
                } else
                    notifyFailure(result);
            }
            else
                notifyFailure("Colori non uguali!");
        }
        else
            notifyFailure(OH_NO);
    }

    /**
     * Checks if dice are of the same color of the third one
     * @param d1 first die
     * @param d2 second die
     * @param d3 third die
     * @return true if all dice are of the same color
     */
    private boolean isSameColor(Die d1, Die d2, Die d3)
    {

        return (d1!= null && d2!= null && d3!= null &&d1.getColor() == d3.getColor() && d2.getColor() == d3.getColor());

    }

    /**
     * Ask the number of placements for tap wheel
     * @return number of placements
     */
    private int askNumberOfPlacement()
    {
        latch = new CountDownLatch(1);
        network.sendReq(new AskPlacements(),player.getName());
        if (waitUpdate() && response.toString().equals(NEWVALUE)) {
            NewValue newValue = (NewValue) response;
            return newValue.getNewValue();
        }
        return -1;
    }

    /**
     * Ask for a new face for a die
     * @param die die to be changed
     * @return new value for die
     */
    private int askNewFace(Die die)
    {
        latch = new CountDownLatch(1);
        network.sendReq(new AskNewFace(die.getColor(),die.getValue()),player.getName());
        if (waitUpdate() && response.toString().equals(NEWVALUE)) {
            NewValue newValue = (NewValue) response;
            return newValue.getNewValue();
        }
        return -1;
    }

    /**
     * Asks and wait for a die from the reserve
     * @return die's position inside the reserve
     */
    private int askDieFromReserve() {
        latch = new CountDownLatch(1);
        network.sendReq(new AskDieFromReserve(), player.getName());
        if (waitUpdate() && response.toString().equals("DieFromReserve")) {
            DieFromReserve dieFromReserve = (DieFromReserve) response;
            return dieFromReserve.getIndex();
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return -1;
    }


    /**
     * Asks and wait for a die from the grid
     * @return die's position inside the grid
     */
    private Pair<Integer,Integer> askDieFromGrid() {
        latch = new CountDownLatch(1);
        network.sendReq(new AskDieFromGrid(), player.getName());
        if (waitUpdate() && response.toString().equals("DieFromGrid")) {
            DieFromGrid dieFromGrid = (DieFromGrid) response;
            return new Pair<>(dieFromGrid.getH(),dieFromGrid.getW());
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return null;
    }

    /**
     * Asks and wait for a die from the round track
     * @return die's position inside the round track
     */
    private Pair<Integer,Integer> askDieFromRoundTrack() {
        latch = new CountDownLatch(1);
        network.sendReq(new AskDieFromRoundTrack(), player.getName());
        if (waitUpdate() && response.toString().equals("DieFromRoundTrack")) {
            DieFromRoundTrack dieFromRoundTrack = (DieFromRoundTrack) response;
            return new Pair<>(dieFromRoundTrack.getRound(),dieFromRoundTrack.getDiePosition());
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return null;
    }


    /**
     * Asks and wait for a new value for a die
     * @param index die's position inside the reserve
     * @return die' new value
     */
    private int askNewValue(int index)
    {
        latch = new CountDownLatch(1);
        int val1 = board.getReserve().get(index).getValue() -1;
        int val2 = board.getReserve().get(index).getValue() +1;
        network.sendReq(new AskNewValue(val1,val2), player.getName());
        if (waitUpdate() && response.toString().equals(NEWVALUE)) {
            NewValue newValue = (NewValue) response;
            return newValue.getNewValue();
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return -1;
    }

    /**
     * Asks for a die to be set and sets it if everything is respected
     * @param index die's position inside the reserve
     * @return Error message or null if no error occurred
     */
    private String setDie(int index, boolean adjacency)
    {
        latch = new CountDownLatch(1);
        network.sendReq(new SetDie(index), player.getName());
        if (waitUpdate() && response.toString().equals(DIESET)) {
            DieSet dieSet = (DieSet) response;
            String result = DiePlacementLogic.insertDie(player,dieSet.getH(),dieSet.getW(),board.getReserve().get(index),true,true,adjacency);
            if(result == null)
            {
                player.getGrid().setDie(dieSet.getH(),dieSet.getW(),board.getReserve().popDie(index));
            }
            return result;
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return OH_NO ;
    }


    /**
     * Asks for a die to be set from inside the grid and sets it if everything is respected
     * @param h height inside the grid
     * @param w width inside the grid
     * @param color restriction enabled
     * @param value value restriction enabled
     * @return Error message or null if no error occurred
     */
    private String setDieFromGrid(int h, int w, boolean color, boolean value)
    {
        latch = new CountDownLatch(1);
        Die die = player.getGrid().setDie(h,w,null);
        network.sendReq(new SetThisDie(new IntColorPair(die.getValue(),die.getColor())), player.getName());
        if (waitUpdate() && response.toString().equals(DIESET)) {
            DieSet dieSet = (DieSet) response;
            String result = DiePlacementLogic.insertDie(player,dieSet.getH(),dieSet.getW(),die,color,value,true);
            if(result == null)
            {
                player.getGrid().setDie(dieSet.getH(),dieSet.getW(),die);
            }
            else
            {
               player.getGrid().setDie(h,w,die);
            }
            return result;
        }
        else {
            player.getGrid().setDie(h,w,die);
            network.sendReq(new CardExecutionError(TOO_SLOW), player.getName());
        }
        return OH_NO;
    }


    /**
     * Sets a die from the bag and puts a die from the reserve inside the bag
     * @param diePosition position of the die inside the reserve
     * @return null, or an error message
     */
    private String setDieFromBag(int diePosition)
    {
        latch = new CountDownLatch(1);
        Die die = board.getBag().getDie(0);
        int newVal = askNewFace(die);
        if(newVal>0)
            die.setFace(newVal);
        else
            return OH_NO;

        network.sendReq(new SetThisDie(new IntColorPair(die.getValue(),die.getColor())),player.getName());
        if(waitUpdate() && response.toString().equals(DIESET))
        {
            DieSet dieSet = (DieSet) response;
            String result = DiePlacementLogic.insertDie(player,dieSet.getH(),dieSet.getW(),die,true,true,true);
            if(result == null)
            {
                player.getGrid().setDie(dieSet.getH(),dieSet.getW(),board.getBag().popDice(1).get(0));
                board.getBag().add(board.getReserve().popDie(diePosition));
            }
                return result;
        }

            return OH_NO;
    }


    /**
     * Asks for a die to be set from roundTrack and it swap the die with one from the reserve
     * @param round round inside round track
     * @param diePosition die's position inside the round
     * @param index die position inside the round track
     * @return Error message or null if no error occurred
     */
    private String setDieFromRoundTrack(int round, int diePosition, int index)
    {
        latch = new CountDownLatch(1);
        Die die = board.getRoundTrack().getDie(round,diePosition);
        network.sendReq(new SetThisDie(new IntColorPair(die.getValue(),die.getColor())), player.getName());
        if (waitUpdate() && response.toString().equals(DIESET)) {
            DieSet dieSet = (DieSet) response;
            String result = DiePlacementLogic.insertDie(player,dieSet.getH(),dieSet.getW(),die,true,true,true);
            if(result == null)
            {
                CardEffects.reserveRoundTrackSwap(index,round,diePosition,board.getReserve(),board.getRoundTrack());
                player.getGrid().setDie(dieSet.getH(),dieSet.getW(), board.getReserve().popDie(board.getReserve().size()-1));
            }
            return result;
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return OH_NO ;
    }

    /**
     * Asks and wait for a double die placement
     * @param h height of the first die
     * @param w width of the first die
     * @param h2 height of the second die
     * @param w2 width of the second die
     * @return error message or null in case of no error
     */
    private String setDoubleDieFromGrid(int h, int w, int h2, int w2)
    {
        int newH;
        int newW;
        latch = new CountDownLatch(1);
        Die die = player.getGrid().setDie(h,w,null);
        network.sendReq(new SetThisDie(new IntColorPair(die.getValue(),die.getColor())), player.getName());
        if (waitUpdate() && response.toString().equals(DIESET)) {
            DieSet dieSet = (DieSet) response;
            String result = DiePlacementLogic.insertDie(player, dieSet.getH(), dieSet.getW(), die, true,true,true);
            if(result == null)
            {
                newH = dieSet.getH();
                newW = dieSet.getW();
                latch = new CountDownLatch(1);
                Die die2 = player.getGrid().setDie(h2,w2,null);
                network.sendReq(new SetThisDie(new IntColorPair(die2.getValue(),die2.getColor())), player.getName());
                if (waitUpdate() && response.toString().equals(DIESET)) {
                     dieSet = (DieSet) response;
                     result = DiePlacementLogic.insertDie(player, dieSet.getH(), dieSet.getW(), die2, true, true, true);
                     if(result == null)
                     {
                         player.getGrid().setDie(dieSet.getH(),dieSet.getW(),die2);
                         player.getGrid().setDie(newH,newW,die);
                     }
                     else
                     {
                         player.getGrid().setDie(h2,w2,die2);
                         player.getGrid().setDie(h,w,die);
                     }
                }
                else
                    player.getGrid().setDie(h,w,die);
            }
            return result;
        }
        return OH_NO;
    }

    /**
     * Waits for the requested response
     * @return true if a response has come, false otherwise
     */
    protected boolean waitUpdate()
    {
        try {
            return latch.await(20, TimeUnit.SECONDS);
        } catch (InterruptedException e)
        {
            Logger.getGlobal().log(Level.WARNING, "Interrupted while waiting");
            Thread.currentThread().interrupt();
        }

        return false;
    }


    /**
     * Sets all parameters after using a card
     * @param player player affected
     */
    private void useCard(Player player)
    {
        board.getTool(cardPosition).burnFavourPoints(player);
        board.getTool(cardPosition).updateUsed();
    }

    /**
     * Updates game stets on all clients
     */
    private void updateGameState()
    {
        network.sendObj(new ReserveStatus(board.getReserve()));
        network.sendObj(new RoundTrackStatus(board.getRoundTrack()));
        network.sendObj(new CardInfo(board.getTools(),board.getObjectives()));
        network.sendObj(new PlayerStatus(player, firsTurn));
    }

}

