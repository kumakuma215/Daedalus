package fr.zelytra.daedalus.events.running.environnement.gods;

import fr.zelytra.daedalus.Daedalus;
import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.gods.GodsEnum;
import fr.zelytra.daedalus.managers.gods.list.Athena;
import fr.zelytra.daedalus.managers.items.CustomItemStack;
import fr.zelytra.daedalus.managers.items.CustomMaterial;
import fr.zelytra.daedalus.managers.structure.Structure;
import fr.zelytra.daedalus.managers.structure.StructureType;
import fr.zelytra.daedalus.utils.Message;
import fr.zelytra.daedalus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AthenaHandler implements Listener {
    private final Material invocationBlock = Material.LODESTONE;
    private final CustomMaterial invocMaterial = CustomMaterial.ATHENA_TOTEM;

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        if (Daedalus.getInstance().getGameManager().isRunning()) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(invocMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(invocMaterial.getName(), e.getPlayer()))) {
                    if (e.getClickedBlock().getType() == invocationBlock) {
                        Player player = e.getPlayer();
                        for (Map.Entry<BoundingBox, Structure> entry : Daedalus.getInstance().getStructureManager().getStructuresPosition().entrySet()) {
                            if (entry.getKey().contains(e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ()) && entry.getValue().getType() == StructureType.TEMPLE && entry.getValue().getGod() == GodsEnum.ATHENA) {
                                try {
                                    Faction playerFaction = Daedalus.getInstance().getGameManager().getFactionManager().getFactionOf(player);
                                    if (playerFaction.getGod() != null) {
                                        player.sendMessage(Message.getPlayerPrefixe() + "§cYou cannot summon more than one god.");
                                        return;
                                    }
                                    playerFaction.setGod(player, GodsEnum.ATHENA);
                                    new Athena(playerFaction);
                                    vfx(player);
                                    removeHeldItem(e, invocMaterial);
                                    e.getClickedBlock().setType(Material.CHISELED_STONE_BRICKS);
                                } catch (Exception exception) {
                                    System.out.println("ERROR team not found");
                                }
                                return;
                            }
                        }
                        player.sendMessage(Message.getPlayerPrefixe() + "§cYou cannot summon this god here.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void entityKillEvent(EntityDeathEvent e) {
        if (Daedalus.getInstance().getGameManager().isRunning()) {
            if (e.getEntity().getKiller() == null) {
                return;
            }
            try {
                Faction playerFaction = Daedalus.getInstance().getGameManager().getFactionManager().getFactionOf(e.getEntity().getKiller());
                if (playerFaction.getGodsEnum() == GodsEnum.ATHENA) {
                    switch (e.getEntity().getType()) {
                        case PLAYER:
                            int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
                            if (random <= 60) {//lvl 3
                                int loot = ThreadLocalRandom.current().nextInt(0, Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier3").getLootsEnum().size());
                                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier3").getLootsEnum().get(loot).getItem());

                            } else if (random > 60 && random <= 85) {//lvl 2
                                int loot = ThreadLocalRandom.current().nextInt(0, Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier2").getLootsEnum().size());
                                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier2").getLootsEnum().get(loot).getItem());
                            } else if (random > 85) {//lvl 1
                                int loot = ThreadLocalRandom.current().nextInt(0, Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier1").getLootsEnum().size());
                                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier1").getLootsEnum().get(loot).getItem());
                            }
                            break;
                        default:
                            random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
                            if (random == 1) {//lvl 3
                                int loot = ThreadLocalRandom.current().nextInt(0, Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier3").getLootsEnum().size());
                                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier3").getLootsEnum().get(loot).getItem());
                            } else if (random > 1 && random <= 5) {//lvl 2
                                int loot = ThreadLocalRandom.current().nextInt(0, Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier2").getLootsEnum().size());
                                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier2").getLootsEnum().get(loot).getItem());
                            } else if (random > 5 && random <= 20) {//lvl 1
                                int loot = ThreadLocalRandom.current().nextInt(0, Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier1").getLootsEnum().size());
                                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Daedalus.getInstance().getStructureManager().getLootTableManager().getByName(GodsEnum.ATHENA.getName() + "_tier1").getLootsEnum().get(loot).getItem());
                            }
                            break;
                    }
                }
            } catch (Exception exception) {
                System.out.println("ERROR team not found");
            }
        }
    }


    private void vfx(Player player) {
        Bukkit.broadcastMessage("§a§l➹ Athena has appeared in the maze ➹");
        Utils.runTotemDisplay(player);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 10, 0.1f);
        }
    }

    private void removeHeldItem(PlayerInteractEvent e, CustomMaterial material) {
        switch (e.getHand()) {
            case HAND:
                if (CustomItemStack.hasCustomItemInMainHand(material.getName(), e.getPlayer()))
                    e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                break;
            case OFF_HAND:
                if (CustomItemStack.hasCustomItemInOffHand(material.getName(), e.getPlayer()))
                    e.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                break;
            default:
                break;
        }
    }

}
