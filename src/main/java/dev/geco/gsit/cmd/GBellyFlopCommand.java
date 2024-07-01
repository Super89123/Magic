package dev.geco.gsit.cmd;

import org.jetbrains.annotations.*;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.util.*;

import dev.geco.gsit.GSitMain;
import dev.geco.gsit.objects.*;

public class GBellyFlopCommand implements CommandExecutor {

    private final GSitMain GPM;

    public GBellyFlopCommand(GSitMain GPluginMain) { GPM = GPluginMain; }

    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command Command, @NotNull String Label, String[] Args) {

        if(!(Sender instanceof Player)) {

            GPM.getMManager().sendMessage(Sender, "Messages.command-sender-error");
            return true;
        }

        Player player = (Player) Sender;

        if(!GPM.getPManager().hasPermission(Sender, "BellyFlop", "Pose.*")) {

            GPM.getMManager().sendMessage(Sender, "Messages.command-permission-error");
            return true;
        }

        if(!GPM.getPoseManager().isAvailable()) {

            String v = Bukkit.getServer().getClass().getPackage().getName();
            v = v.substring(v.lastIndexOf('.') + 1);

            GPM.getMManager().sendMessage(Sender, "Messages.command-version-error", "%Version%", v);
            return true;
        }

        IGPoseSeat currentPoseSeat = GPM.getPoseManager().getPose(player);

        if(currentPoseSeat != null && currentPoseSeat.getPose() == Pose.SWIMMING) {

            GPM.getPoseManager().removePose(player, GetUpReason.GET_UP);
            return true;
        }

        if(!player.isValid() || player.isSneaking() || !player.isOnGround() || player.getVehicle() != null || player.isSleeping()) {

            GPM.getMManager().sendMessage(Sender, "Messages.action-pose-now-error");
            return true;
        }

        if(!GPM.getEnvironmentUtil().isInAllowedWorld(player)) {

            GPM.getMManager().sendMessage(Sender, "Messages.action-pose-world-error");
            return true;
        }

        Location playerLocation = player.getLocation();

        Block block = playerLocation.getBlock().isPassable() ? playerLocation.subtract(0, 0.0625, 0).getBlock() : playerLocation.getBlock();

        if(GPM.getCManager().MATERIALBLACKLIST.contains(block.getType())) {

            GPM.getMManager().sendMessage(Sender, "Messages.action-pose-location-error");
            return true;
        }

        boolean overSize = false;

        try {

            for(BoundingBox boundingBox : block.getCollisionShape().getBoundingBoxes()) if(boundingBox.getMaxY() > 1.25) overSize = true;
        } catch (Throwable ignored) { }

        if(!GPM.getCManager().ALLOW_UNSAFE && !(block.getRelative(BlockFace.UP).isPassable() && !overSize && (!block.isPassable() || !GPM.getCManager().CENTER_BLOCK))) {

            GPM.getMManager().sendMessage(Sender, "Messages.action-pose-location-error");
            return true;
        }

        if(!GPM.getPManager().hasPermission(Sender, "ByPass.Region", "ByPass.*")) {






        }

        if(!GPM.getCManager().SAME_BLOCK_REST && !GPM.getPoseManager().kickPose(block, player)) {

            GPM.getMManager().sendMessage(Sender, "Messages.action-pose-kick-error");
            return true;
        }

        if(GPM.getPoseManager().createPose(block, player, Pose.SWIMMING) == null) GPM.getMManager().sendMessage(Sender, "Messages.action-pose-error");
        return true;
    }

}