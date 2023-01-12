package fr.zadiho.hepickstudio.olympiade.utils;

import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.SecureDirectoryStream;
import java.util.Objects;

public class WorldUtils {



    public static WorldCreator setBoost(WorldCreator creator, int boostCoal, int boostIron, int boostGold, int boostLapis, int boostRedstone, int boostDiamond, int boostCave, boolean villageEnable) {
        creator.generatorSettings(String.format("{\"coordinateScale\":684.412,\"heightScale\":684.412,\"lowerLimitScale\":512.0,\"upperLimitScale\":512.0,\"depthNoiseScaleX\":%s,\"depthNoiseScaleZ\":%s,\"depthNoiseScaleExponent\":0.5,\"mainNoiseScaleX\":80.0,\"mainNoiseScaleY\":160.0,\"mainNoiseScaleZ\":80.0,\"baseSize\":8.5,\"stretchY\":12.0,\"biomeDepthWeight\":1.0,\"biomeDepthOffset\":0.0,\"biomeScaleWeight\":1.0,\"biomeScaleOffset\":0.0,\"seaLevel\":63,\"useCaves\":true,\"useDungeons\":false,\"dungeonChance\":8,\"useStrongholds\":false,\"useVillages\":" + villageEnable + ",\"useMineShafts\":true,\"useTemples\":true,\"useMonuments\":true,\"useRavines\":true,\"useWaterLakes\":true,\"waterLakeChance\":4,\"useLavaLakes\":true,\"lavaLakeChance\":80,\"useLavaOceans\":false,\"fixedBiome\":-1,\"biomeSize\":4,\"riverSize\":4,\"dirtSize\":33,\"dirtCount\":10,\"dirtMinHeight\":0,\"dirtMaxHeight\":256,\"gravelSize\":33,\"gravelCount\":8,\"gravelMinHeight\":0,\"gravelMaxHeight\":256,\"graniteSize\":33,\"graniteCount\":10,\"graniteMinHeight\":0,\"graniteMaxHeight\":80,\"dioriteSize\":33,\"dioriteCount\":10,\"dioriteMinHeight\":0,\"dioriteMaxHeight\":80,\"andesiteSize\":33,\"andesiteCount\":10,\"andesiteMinHeight\":0,\"andesiteMaxHeight\":80,\"coalSize\":17,\"coalCount\":%s,\"coalMinHeight\":0,\"coalMaxHeight\":128,\"ironSize\":9,\"ironCount\":%s,\"ironMinHeight\":0,\"ironMaxHeight\":64,\"goldSize\":9,\"goldCount\":%s,\"goldMinHeight\":0,\"goldMaxHeight\":32,\"redstoneSize\":8,\"redstoneCount\":%s,\"redstoneMinHeight\":0,\"redstoneMaxHeight\":16,\"diamondSize\":8,\"diamondCount\":%s,\"diamondMinHeight\":0,\"diamondMaxHeight\":256,\"lapisSize\":7,\"lapisCount\":%s,\"lapisCenterHeight\":16,\"lapisSpread\":16}", 200 * boostCave, 200 * boostCave, 20 * boostCoal, 20 * boostIron, 2 * boostGold, 8 * boostRedstone, boostDiamond, boostLapis));
        return creator;
    }

    public static void deleteWorld(String name) {
        final World world = Bukkit.getWorld(name);
        if (world == null)
            return;
        world.getPlayers().forEach(player -> player.teleport(GameSettings.spawn));
        Bukkit.unloadWorld(world, false);
        final File file = new File(Bukkit.getWorldContainer(), world.getWorldFolder().getName());
        File folder = Bukkit.getWorld("uhcrun").getWorldFolder();
        Bukkit.unloadWorld(Objects.requireNonNull(Bukkit.getWorld("uhcrun")), false);
        folder.delete();
    }

}
