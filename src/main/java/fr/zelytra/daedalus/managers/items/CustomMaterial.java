package fr.zelytra.daedalus.managers.items;

import org.bukkit.Material;

public enum CustomMaterial {
    ZEUS_LIGHTNING("§e§lZeus's Lightning","zeus_lightning", 1, Material.PHANTOM_MEMBRANE),
    HADES_SCEPTER("§4§lHades's Scepter","hades_scepter", 2, Material.PHANTOM_MEMBRANE),
    APHRODITE_HEART("§d§lAphrodite's Heart","aphrodite_heart", 3, Material.PHANTOM_MEMBRANE),
    DEMETER_SICKLE("§2§lDemeter's Sickle","demeter_sickle", 4, Material.PHANTOM_MEMBRANE),
    DIONYSOS_CUP("§5§lDionysos's Cup","dionysos_cup", 5, Material.PHANTOM_MEMBRANE),

    MINOTAUR_HEAD("§5§lMinotaur's Head","minotaur_head", 6, Material.CARVED_PUMPKIN),
    DIVINE_FRAGMENT("§f§lDivine Fragment","divine_fragment", 7, Material.PHANTOM_MEMBRANE),
    DIVINE_HEART("§c§lDivine Heart","divine_heart", 8, Material.PHANTOM_MEMBRANE),

    ZEUS_TOTEM("§e§lZeus's Totem","zeus_totem", 9, Material.TOTEM_OF_UNDYING),
    POSEIDON_TOTEM("§3§lPoseidon's Totem","poseidon_totem", 10, Material.TOTEM_OF_UNDYING),
    HADES_TOTEM("§4§lHades's Totem","hades_totem", 11, Material.TOTEM_OF_UNDYING),
    ARES_TOTEM("§c§lAres's Totem","ares_totem", 12, Material.TOTEM_OF_UNDYING),
    APHRODITE_TOTEM("§d§lAphrodite's Totem","aphrodite_totem", 13, Material.TOTEM_OF_UNDYING),
    DEMETER_TOTEM("§2§lDemeter's Totem","demeter_totem", 14, Material.TOTEM_OF_UNDYING),
    HERMES_TOTEM("§b§lHermes's Totem","hermes_totem", 15, Material.TOTEM_OF_UNDYING),
    ARTEMIS_TOTEM("§9§lArtemis's Totem","artemis_totem", 16, Material.TOTEM_OF_UNDYING),
    ATHENA_TOTEM("§a§lAthenas's Totem","athenas_totem", 17, Material.TOTEM_OF_UNDYING),
    DIONYSOS_TOTEM("§5§lDionysos's Totem","dionysos_totem", 18, Material.TOTEM_OF_UNDYING),
    MINOTAUR_TOTEM("§6§lMinotaur's Totem","minotaur_totem", 19, Material.TOTEM_OF_UNDYING),
    MINOTAUR_CHARGE("§6§lMinotaur's Charge","minotaur_charge", 20, Material.PHANTOM_MEMBRANE),
    MEDUSA_HEAD("§7§lMedusa Head","medusa_head", 21, Material.CARVED_PUMPKIN);



    private final String displayName;
    private final String name;
    private final int customModelData;
    private final Material vanillaMaterial;

    CustomMaterial(String displayName,String name, int CMD, Material material) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
    }
    CustomMaterial(String displayName,String name,String description, int CMD, Material material) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public Material getVanillaMaterial() {
        return vanillaMaterial;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public static CustomMaterial getByName(String name){
        for(CustomMaterial material : CustomMaterial.values()){
            if(material.getName().equalsIgnoreCase(name)){
                return material;
            }
        }
        return null;
    }

}
