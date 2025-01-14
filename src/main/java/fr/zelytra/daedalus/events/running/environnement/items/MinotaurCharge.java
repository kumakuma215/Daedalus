package fr.zelytra.daedalus.events.running.environnement.items;

import fr.zelytra.daedalus.Daedalus;
import fr.zelytra.daedalus.managers.cooldown.Cooldown;
import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.items.CustomItemStack;
import fr.zelytra.daedalus.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class MinotaurCharge implements Listener {
    private BukkitTask taskID;
    private long timeOut;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        final int itemCooldown = 30;
        final int radius = 2;


        if (Daedalus.getInstance().getGameManager().isRunning()) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(CustomMaterial.MINOTAUR_CHARGE.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(CustomMaterial.MINOTAUR_CHARGE.getName(), e.getPlayer()))) {
                    Player player = e.getPlayer();
                    //Cooldown check
                    if(!Cooldown.cooldownCheck(player,CustomMaterial.MINOTAUR_CHARGE.getName())){
                        return;
                    }
                    new Cooldown(player, itemCooldown, CustomMaterial.MINOTAUR_CHARGE.getName());
                    //Item action
                    final int chargeCoef = 4;
                    final double yCoef = 0.3;
                    final double thresholdVelocity = 0.1;
                    //Charge direction vector
                    double radianYaw = player.getEyeLocation().getYaw();

                    if (radianYaw > 180) {
                        radianYaw -= 360;
                    } else if (radianYaw < -180) {
                        radianYaw += 360;
                    }
                    radianYaw *= Math.PI / 180.0;
                    Vector dir = new Vector(-Math.sin(radianYaw) * chargeCoef, yCoef, Math.cos(radianYaw) * chargeCoef);
                    player.setVelocity(dir);
                    this.timeOut = System.currentTimeMillis();
                    this.taskID = Bukkit.getScheduler().runTaskTimer(Daedalus.getInstance(), () -> {
                        if (Math.abs(e.getPlayer().getVelocity().getX()) <= thresholdVelocity || Math.abs(e.getPlayer().getVelocity().getZ()) <= thresholdVelocity) {
                            cancelTask();
                        }
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
                                    toStrike.add(entity);
                                } else if (entity instanceof LivingEntity) {
                                    toStrike.add(entity);
                                }
                            }
                            for (Entity entity : toStrike) {
                                //Entity air ejection
                                Vector delta = new Vector(entity.getLocation().getX() - player.getLocation().getX(), 0, entity.getLocation().getZ() - player.getLocation().getZ());
                                double norme = Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2) + Math.pow(delta.getZ(), 2));
                                int coef = 2;
                                Vector direction = new Vector((delta.getX() / norme) * coef, (delta.getY() / norme) + 1.5, (delta.getZ() / norme) * coef);
                                entity.setVelocity(direction);
                                ((LivingEntity)entity).damage(4.0);
                            }

                            if(System.currentTimeMillis()-this.timeOut>=5000){
                                cancelTask();
                            }
                        } catch (Exception exception) {
                            System.out.println("ERROR team not found");
                        }

                    }, 0, 1);

                }
            }
        }
    }

    private void cancelTask() {
        Bukkit.getServer().getScheduler().cancelTask(this.taskID.getTaskId());
    }
}
