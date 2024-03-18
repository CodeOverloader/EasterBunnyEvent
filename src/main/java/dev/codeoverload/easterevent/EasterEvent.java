package dev.codeoverload.easterevent;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.Random;

public class EasterEvent extends JavaPlugin implements Listener {

    private static final double DROP_CHANCE = ((double) 1 / 2); //adjusting this should change the chance of spawning the easter bunny is 50%
    private ItemStack easterEgg;
    private ItemStack easterHelm;
    private ItemStack easterTunic;
    private ItemStack easterPants;
    private ItemStack easterBoots;


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        createCustomItem();
    }

    private void createCustomItem() {
        easterEgg = new ItemStack(Material.EGG);
        ItemMeta eggmeta = easterEgg.getItemMeta();
        eggmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Easter Bunny Egg");
        easterEgg.setItemMeta(eggmeta);
        easterHelm = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta helmmeta = easterHelm.getItemMeta();
        helmmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Easter Helmet");
        easterHelm.setItemMeta(helmmeta);
        easterTunic = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta tunicmeta = easterTunic.getItemMeta();
        tunicmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Easter Chestplate");
        easterTunic.setItemMeta(tunicmeta);
        easterPants = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemMeta pantsmeta = easterPants.getItemMeta();
        pantsmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Easter Leggings");
        easterPants.setItemMeta(pantsmeta);
        easterBoots = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta bootsmeta = easterHelm.getItemMeta();
        bootsmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Easter Boots");
        easterBoots.setItemMeta(bootsmeta);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.GRASS_BLOCK) {
            Random random = new Random();
            if (random.nextDouble() <= DROP_CHANCE) {
                PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 69);
                PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 999999999, 4);
                PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, 999999999, 4);
                World world = getServer().getWorlds().get(0);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), easterEgg);
                Entity easterBunny = world.spawnEntity(event.getBlock().getLocation(), EntityType.RABBIT);
                resistance.apply((LivingEntity) easterBunny);
                speed.apply((LivingEntity) easterBunny);
                jump.apply((LivingEntity) easterBunny);
                Component bunnyName = Component.text(ChatColor.LIGHT_PURPLE + "Easter Bunny");
                easterBunny.customName(bunnyName);
                easterBunny.setCustomNameVisible(true);
                easterBunny.setMetadata("Easter Bunny", new FixedMetadataValue(this, true));

            }
        }

    }
    @EventHandler
    public void onEasterBunnyDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() == EntityType.RABBIT && entity.hasMetadata("Easter Bunny")) {
            Location deathLocation = entity.getLocation();

            // Randomly choose an item to drop
            double itemChance = new Random().nextDouble();
            if (itemChance <= 0.20) {
                deathLocation.getWorld().dropItemNaturally(deathLocation, easterEgg);
            } else if (itemChance <= 0.40) {
                deathLocation.getWorld().dropItemNaturally(deathLocation, easterHelm);
            } else if (itemChance <= 0.60) {
                deathLocation.getWorld().dropItemNaturally(deathLocation, easterTunic);
            } else if (itemChance <= 0.80) {
                deathLocation.getWorld().dropItemNaturally(deathLocation, easterPants);
            } else {
                deathLocation.getWorld().dropItemNaturally(deathLocation, easterBoots);
            }
        }
    }

}