package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.*;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;

import java.io.Serializable;
import java.util.List;

/**
 * Class implements behavior for human players
 */
public class Human implements Behavior, Serializable {

    /**
     * Issue order overriden
     *
     * @param p_GE     Game Engine
     * @param p_Player Player
     */
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
        List<String> l_OrderStr = p_GE.getD_OrderStrBuffer().get(0);
        switch (l_OrderStr.get(0)) {
            // { "deploy", "", "countryFrom, numOfArmies" }
            case "deploy":
                String[] l_Temp = l_OrderStr.get(2).replaceAll(" ", "").split(",");
                Deploy l_DeployOrder = new Deploy(l_Temp[0], Integer.parseInt(l_Temp[1]), p_GE);
                p_Player.getD_OrderList().add(l_DeployOrder);
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued deploy command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                break;

            // { "advance", "", "countryFrom, countryTo, numOfArmies" }
            case "advance":
                String[] l_ArgsAdvance = l_OrderStr.get(2).replaceAll(" ", "").split(",");
                Advance l_AdvanceOrder = new Advance(l_ArgsAdvance[0], l_ArgsAdvance[1],
                        Integer.parseInt(l_ArgsAdvance[2]), p_GE);
                p_Player.getD_OrderList().add(l_AdvanceOrder);
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                break;

            // { "airlift", "", "countryFrom, countryTo, numOfArmies" }
            case "airlift":
                String[] l_ArgsAirlift = l_OrderStr.get(2).replaceAll(" ", "").split(",");
                Airlift l_AirliftOrder = new Airlift(l_ArgsAirlift[0], l_ArgsAirlift[1],
                        Integer.parseInt(l_ArgsAirlift[2]), p_GE);
                p_Player.getD_OrderList().add(l_AirliftOrder);
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued airlift command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                break;

            // { "blockade", "", "countryFrom, numOfArmies" }
            case "blockade":
                String[] l_ArgsBlockade = l_OrderStr.get(2).replaceAll(" ", "").split(",");
                Blockade l_BlockadeOrder = new Blockade(l_ArgsBlockade[0], p_GE);
                p_Player.getD_OrderList().add(l_BlockadeOrder);
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued blockade command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                break;

            // { "bomb", "", "countryFrom, numOfArmies" }
            case "bomb":
                String[] l_ArgsBomb = l_OrderStr.get(2).replaceAll(" ", "").split(",");
                Bomb l_BombOrder = new Bomb(l_ArgsBomb[0], p_GE);
                p_Player.getD_OrderList().add(l_BombOrder);
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued bomb command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                break;

            // { "diplomacy", "", "playerId" }
            case "diplomacy":
            case "negotiate":
                String[] l_ArgsDiplomacy = l_OrderStr.get(2).replaceAll(" ", "").split(",");
                Diplomacy l_DiplomacyOrder = new Diplomacy(l_ArgsDiplomacy[0], p_GE);
                p_Player.getD_OrderList().add(l_DiplomacyOrder);
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued diplomacy command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                break;

            default:
                Console.displayMsg("Error in issue order!");
                p_GE.getD_LogEntryBuffer().setD_log("Error in issue order!");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());

        }
        p_GE.clearOrderStrBuffer();
    }
}
