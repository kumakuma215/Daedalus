package fr.zelytra.daedalus.managers.gods.list;

import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.gods.Gods;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;

public class Artemis implements Gods {

    public Artemis(Faction team) {
        init(team);
    }

    @Override
    public ArrayList<ItemStack> godItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack item = new ItemStack(Material.BOW);
        item.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        items.add(item);
        return items;
    }

    @Override
    public ArrayList<ItemStack> teamItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        item.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        items.add(item);
        return items;
    }

    @Override
    public Collection<PotionEffect> godEffects() {
        Collection<PotionEffect> potions = new ArrayList<>();
        potions.add(new PotionEffect(PotionEffectType.SPEED,99999999,0,false,false,true));
        return potions;
    }

    @Override
    public Collection<PotionEffect> teamEffects() {
        Collection<PotionEffect> potions = new ArrayList<>();
        potions.add(new PotionEffect(PotionEffectType.SPEED,99999999,0,false,false,true));
        return potions;
    }
}
