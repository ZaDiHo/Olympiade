package fr.zadiho.hepickstudio.olympiade.game;

public enum EGames {

    WAITING("En attente"),
    PARKOUR("Jump"),
    RACE("Course d'Arpenteur"),
    PVE("PvE"),
    TNT("TNT Run"),
    PVP("FFA"),
    SURPRISE("Surprise");

    private String name;
    private static EGames currentState;

    public static EGames getCurrentState() {
        return currentState;
    }

    public static void setState(EGames currentState) {
        EGames.currentState = currentState;
    }

    EGames(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


