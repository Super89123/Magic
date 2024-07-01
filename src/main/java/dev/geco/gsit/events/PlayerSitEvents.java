package dev.geco.gsit.events;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

import dev.geco.gsit.GSitMain;
import dev.geco.gsit.objects.*;
import org.super89.supermegamod.magic.Magic;

public class PlayerSitEvents implements Listener {

    private final Magic GPM;

    public PlayerSitEvents(Magic GPluginMain) { GPM = GPluginMain; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void PTogSE(PlayerToggleSneakEvent Event) {

        Player player = Event.getPlayer();

        if(!GPM.getCManager().PS_SNEAK_EJECTS || !Event.isSneaking() || player.isFlying() || player.getVehicle() != null || GPM.getPlayerSitManager().WAIT_EJECT.contains(player) || player.getPassengers().isEmpty()) return;

        GPM.getPlayerSitManager().stopPlayerSit(player, GetUpReason.KICKED);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void PGamMCE(PlayerGameModeChangeEvent Event) { if(Event.getNewGameMode() == GameMode.SPECTATOR) GPM.getPlayerSitManager().stopPlayerSit(Event.getPlayer(), GetUpReason.ACTION); }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PDeaE(PlayerDeathEvent Event) { if(Event.getEntity().getVehicle() != null) GPM.getPlayerSitManager().stopPlayerSit(Event.getEntity(), GetUpReason.DEATH); }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PQuiE(PlayerQuitEvent Event) { if(Event.getPlayer().getVehicle() != null) GPM.getPlayerSitManager().stopPlayerSit(Event.getPlayer(), GetUpReason.QUIT); }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void PTelE(PlayerTeleportEvent Event) { GPM.getPlayerSitManager().stopPlayerSit(Event.getPlayer(), GetUpReason.TELEPORT); }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void EDamE(EntityDamageEvent Event) { if(Event.getCause() == EntityDamageEvent.DamageCause.FALL && Event.getEntity() instanceof LivingEntity && Event.getEntity().getVehicle() != null && Event.getEntity().getVehicle().getScoreboardTags().contains(GPM.NAME + "_PlayerSeatEntity")) Event.setCancelled(true); }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void PIntAEE(PlayerInteractAtEntityEvent Event) {

        Entity rightClicked = Event.getRightClicked();

        if(!(rightClicked instanceof Player)) return;

        Player player = Event.getPlayer();

        Player target = (Player) rightClicked;

        if(!GPM.getCManager().PS_ALLOW_SIT && !GPM.getCManager().PS_ALLOW_SIT_NPC) return;

        if(!GPM.getPManager().hasPermission(player, "PlayerSit", "PlayerSit.*")) return;

        if(!GPM.getEnvironmentUtil().isInAllowedWorld(player)) return;

        if(GPM.getCManager().PS_EMPTY_HAND_ONLY && player.getInventory().getItemInMainHand().getType() != Material.AIR) return;

        if(!player.isValid() || !target.isValid() || player.isSneaking() || player.getGameMode() == GameMode.SPECTATOR) return;

        if(GPM.getCManager().FEATUREFLAGS.contains("DISABLE_PLAYERSIT_ELYTRA") && player.isGliding()) return;

        if(GPM.getCrawlManager().isCrawling(player)) return;

        double distance = GPM.getCManager().PS_MAX_DISTANCE;

        if(distance > 0d && target.getLocation().add(0, target.getHeight() / 2, 0).distance(player.getLocation().clone().add(0, player.getHeight() / 2, 0)) > distance) return;



        if(GPM.getPassengerUtil().isInPassengerList(target, player) || GPM.getPassengerUtil().isInPassengerList(player, target)) return;

        long amount = GPM.getPassengerUtil().getPassengerAmount(target) + 1 + GPM.getPassengerUtil().getVehicleAmount(target) + GPM.getPassengerUtil().getPassengerAmount(player);

        if(GPM.getCManager().PS_MAX_STACK > 0 && GPM.getCManager().PS_MAX_STACK <= amount) return;

        Entity highestEntity = GPM.getPassengerUtil().getHighestEntity(target);

        if(!(highestEntity instanceof Player)) return;

        Player highestPlayer = (Player) highestEntity;

        boolean isNPC = GPM.getPassengerUtil().isNPC(highestPlayer);

        if((isNPC && !GPM.getCManager().PS_ALLOW_SIT_NPC) || (!isNPC && !GPM.getCManager().PS_ALLOW_SIT)) return;

        if(!GPM.getToggleManager().canPlayerSit(player.getUniqueId()) || !GPM.getToggleManager().canPlayerSit(highestPlayer.getUniqueId())) return;

        GPM.getPlayerSitManager().sitOnPlayer(player, highestPlayer);
    }

}