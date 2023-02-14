package fr.zadiho.hepickstudio.olympiade.game;

public enum EGames {

    /*
     * Class Name : EGames
     * Description   : List of games
     * Version       : 1.3
     * Date          : 13/02/2023
     * Copyright     : HepickStudio
     */

    ///////////////////////GAME ENUMERATION///////////////////////
    WAITING("En attente", -1),
    PARKOUR("Jump", 20),
    RACE("Course d'Arpenteur", 10),
    PVE("PvE", 20),
    TNT("TNT Run", 10),
    PVP("FFA", 15),
    SURPRISE("Surprise", -1);
    /////////////////////////////////////////////////////////////

    ///////////////////////GAME VARIABLES////////////////////////
    private String name;
    private static EGames currentState;
    private Integer duration;
    /////////////////////////////////////////////////////////////

    ///////////////////////GAME CONSTRUCTOR//////////////////////
    EGames(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
    /////////////////////////////////////////////////////////////

    ///////////////////////GAME METHODS//////////////////////////
    public static EGames getCurrentState() {
        return currentState;
    }

    public static void setState(EGames currentState) {
        EGames.currentState = currentState;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }
    /////////////////////////////////////////////////////////////
}


