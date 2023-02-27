package fr.zadiho.hepickstudio.olympiade.tasks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class WinFireworks extends BukkitRunnable {

    private int timer = 10;
    private static Player winner;

    public static void setWinner(Player player) {
        winner = player;
    }

    @Override
    public void run() {
        Location loc = winner.getLocation();
        final Firework f = winner.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder()
                .flicker(true)
                .trail(true)
                .with(FireworkEffect.Type.STAR)
                .with(FireworkEffect.Type.BALL)
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.AQUA)
                .withColor(Color.YELLOW)
                .withColor(Color.RED)
                .withColor(Color.WHITE)
                .build());

        fm.setPower(1);
        f.setFireworkMeta(fm);
        if (timer == 0) {
            cancel();
        }

        timer--;

    }
}
