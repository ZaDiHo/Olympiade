package fr.zadiho.hepickstudio.olympiade.game.pve;

import fr.zadiho.hepickstudio.olympiade.game.EGames;

public enum EPVE {

    ROUND1("Vague 1",1, 200),
    ROUND2("Vague 2",2, 150),
    ROUND3("Vague 3",3, 150),
    ROUND4("Vague 4",4, 100),
    ROUND5("Vague 5",5, 25);

    private final String name;
    private final Integer entities;
    private final Integer roundNumber;
    private static EPVE currentRound;

    EPVE(String name, int roundNumber, int entities) {
        this.name = name;
        this.roundNumber = roundNumber;
        this.entities = entities;
    }

    public String getName() {
        return name;
    }

    public Integer getEntities() {
        return entities;
    }

    public static EPVE getCurrentRound() {
        return currentRound;
    }

    public static void setCurrentRound(EPVE currentRound) {
        EPVE.currentRound = currentRound;
    }


}
