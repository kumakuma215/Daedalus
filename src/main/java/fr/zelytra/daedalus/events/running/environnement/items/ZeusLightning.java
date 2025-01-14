package fr.zelytra.daedalus.events.running.environnement.items;

import fr.zelytra.daedalus.Daedalus;
import fr.zelytra.daedalus.managers.cooldown.Cooldown;
import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.items.CustomItemStack;
import fr.zelytra.daedalus.managers.items.CustomMaterial;
import fr.zelytra.daedalus.utils.Message;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.Collection;

public class ZeusLightning implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        int itemCooldown = 30;
        int radius = 5;
        if (Daedalus.getInstance().getGameManager().isRunning()) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(CustomMaterial.ZEUS_LIGHTNING.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(CustomMaterial.ZEUS_LIGHTNING.getName(), e.getPlayer()))) {
                    Player player = e.getPlayer();

                    //Item action
                    try {
                        Faction playerFaction = Daedalus.getInstance().getGameManager().getFactionManager().getFactionOf(player);

                        Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(player.getLocation(), radius, radius, radius);
                        Collection<Entity> toStrike = new ArrayList<>();
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof Player) {
                                Player target = (Player) entity;
                                Faction targetPlayerTeam = Daedalus.getInstance().getGameManager().getFactionManager().getFactionOf(target);
                                if (targetPlayerTeam.getType() == playerFaction.getType()) {
                                    continue;
                                }
                            }
                            if (entity instanceof LivingEntity) {
                                toStrike.add(entity);
                            }
                        }
                        if (toStrike.isEmpty()) {
                            player.sendMessage(Message.getPlayerPrefixe() + "§6There's no entity to strike around you");
                            return;
                        }
                        //Cooldown check
                        if(!Cooldown.cooldownCheck(player,CustomMaterial.ZEUS_LIGHTNING.getName())){
                            return;
                        }
                        Cooldown cd = new Cooldown(player, itemCooldown, CustomMaterial.ZEUS_LIGHTNING.getName());

                        for (Entity entity : toStrike) {
                            if (entity instanceof LivingEntity) {
                                player.getWorld().strikeLightningEffect(entity.getLocation());
                                ((LivingEntity) entity).damage(4.0);
                                entity.setFireTicks(160);
                            }
                        }

                    } catch (Exception exception) {
                        System.out.println("ERROR team not found");
                    }
                }
            }
        }
    }
}
