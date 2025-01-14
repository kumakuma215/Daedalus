package fr.zelytra.daedalus.managers.gods.list;

import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.gods.Gods;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;

public class Poseidon implements Gods {

    public Poseidon(Faction faction) {
        init(faction);
        Player god = faction.getGod();
        ArrayList<Player> playerList = (ArrayList<Player>) faction.getPlayerList().clone();
        playerList.remove(god.getUniqueId());
        for (Player player : playerList) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(22.0);
        }
    }

    @Override
    public ArrayList<ItemStack> godItems() {
        ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        item.addEnchantment(Enchantment.DEPTH_STRIDER, 3);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        items.add(item);

        item = new ItemStack(Material.TRIDENT);
        item.addEnchantment(Enchantment.LOYALTY, 3);
        item.addEnchantment(Enchantment.IMPALING, 3);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        items.add(item);
        meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return items;
    }

    @Override
    public ArrayList<ItemStack> teamItems() {
        ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        item.addEnchantment(Enchantment.DEPTH_STRIDER, 2);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        items.add(item);

        item = new ItemStack(Material.TRIDENT);
        item.addEnchantment(Enchantment.LOYALTY, 3);
        items.add(item);
        return items;
    }

    @Override
    public Collection<PotionEffect> godEffects() {
        return null;
    }

    @Override
    public Collection<PotionEffect> teamEffects() {
        return null;
    }
}
