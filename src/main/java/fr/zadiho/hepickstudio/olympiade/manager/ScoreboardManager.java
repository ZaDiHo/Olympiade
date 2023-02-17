package fr.zadiho.hepickstudio.olympiade.manager;

import fr.mrmicky.fastboard.FastBoard;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.game.pve.EPVE;
import fr.zadiho.hepickstudio.olympiade.tasks.*;
import fr.zadiho.hepickstudio.olympiade.utils.Chrono;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ScoreboardManager {

    private static FastBoard board;
    private static HashMap<Player, FastBoard> boards = new HashMap<>();

    public static void createScoreboard(Player player){
        boards.put(player, new FastBoard(player));
        boards.get(player).updateTitle("§c§nOlympiade III");
    }

    public static void deleteScoreboard(Player player){
        boards.remove(player);
    }

    public static void updateScoreboard(Player player){
        if (EGames.getCurrentState() == EGames.WAITING) {
            boards.get(player).updateTitle("§c§nOlympiade III");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fCompte §7➢ §6" + player.getName(),
                    "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(player),
                    "",
                    "§8■ §fÉpreuve §7➢ §cEn attente...",
                    "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                    "§7§m------------------",
                    "§6play.olympiade.fr"
            );
        }
        if (EGames.getCurrentState() == EGames.SURPRISE) {
            boards.get(player).updateTitle("§c§nOlympiade III");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fCompte §7➢ §6" + player.getName(),
                    "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(player),
                    "",
                    "§8■ §fÉpreuve §7➢ §cSURPRISE !",
                    "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                    "§7§m------------------",
                    "§6play.olympiade.fr"
            );
        }
        if (EGames.getCurrentState() == EGames.RACE) {
            boards.get(player).updateTitle("§c§nOlympiade III");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fCompte §7➢ §6" + player.getName(),
                    "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(player),
                    "",
                    "§8■ §fÉpreuve §7➢ §cCourse d'Arpenteur",
                    "§8■ §fChrono §7➢ §e" + Chrono.format(RaceTask.time),
                    "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                    "§7§m------------------",
                    "§6play.olympiade.fr"
            );
        }
        if (EGames.getCurrentState() == EGames.PARKOUR) {
            boards.get(player).updateTitle("§c§nOlympiade III");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fCompte §7➢ §6" + player.getName(),
                    "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(player),
                    "",
                    "§8■ §fÉpreuve §7➢ §cParcours",
                    "§8■ §fChrono §7➢ §e" + Chrono.format(JumpTask.time),
                    "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                    "§7§m------------------",
                    "§6play.olympiade.fr"
            );
        }
        if (EGames.getCurrentState() == EGames.TNT) {
            boards.get(player).updateTitle("§c§nOlympiade III");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fCompte §7➢ §6" + player.getName(),
                    "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(player),
                    "",
                    "§8■ §fÉpreuve §7➢ §cTnt Run",
                    "§8■ §fVivants §7➢ §e" + TNTTask.alives.size(),
                    "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                    "§7§m------------------",
                    "§6play.olympiade.fr"
            );
        }
        if (EGames.getCurrentState() == EGames.PVP) {
            boards.get(player).updateTitle("§c§nOlympiade III");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fCompte §7➢ §6" + player.getName(),
                    "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(player),
                    "",
                    "§8■ §fÉpreuve §7➢ §cPVP",
                    "§8■ §fVivants §7➢ §e" + PVPTask.alives.size(),
                    "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                    "§7§m------------------",
                    "§6play.olympiade.fr"
            );
        }

        if (EGames.getCurrentState() == EGames.PVE) {
            boards.get(player).updateTitle("§c§nOlympiade III");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fCompte §7➢ §6" + player.getName(),
                    "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(player),
                    "",
                    "§8■ §fÉpreuve §7➢ §cPVP",
                    "§8■ §fRestant §7➢ §e" + PVETask.entities,
                    "§8■ §fVague §7➢ §e" + EPVE.getCurrentRound().getName(),
                    "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                    "§7§m------------------",
                    "§6play.olympiade.fr"
            );
        }
    }

    public static FastBoard getScoreboard(Player player){
        return boards.get(player);
    }
}
