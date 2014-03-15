package com.gmail.stevenpcc.arrowhitblockevent;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
    }
    
    @EventHandler
    private void onProjectileHit(final ProjectileHitEvent e) {
        if (e.getEntityType() == EntityType.ARROW) {
            // Must be run in a delayed task otherwise it won't be able to find the block
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    try {

                        net.minecraft.server.v1_7_R1.EntityArrow entityArrow = ((CraftArrow) e
                                .getEntity()).getHandle();

                        Field fieldX = net.minecraft.server.v1_7_R1.EntityArrow.class
                                .getDeclaredField("d");
                        Field fieldY = net.minecraft.server.v1_7_R1.EntityArrow.class
                                .getDeclaredField("e");
                        Field fieldZ = net.minecraft.server.v1_7_R1.EntityArrow.class
                                .getDeclaredField("f");

                        fieldX.setAccessible(true);
                        fieldY.setAccessible(true);
                        fieldZ.setAccessible(true);

                        int x = fieldX.getInt(entityArrow);
                        int y = fieldY.getInt(entityArrow);
                        int z = fieldZ.getInt(entityArrow);

                        if (isValidBlock(x, y, z)) {
                            Block block = e.getEntity().getWorld().getBlockAt(x, y, z);
                            Bukkit.getServer()
                                    .getPluginManager()
                                    .callEvent(
                                            new ArrowHitBlockEvent((Arrow) e
                                                    .getEntity(), block));
                        }

                    } catch (NoSuchFieldException e1) {
                        e1.printStackTrace();
                    } catch (SecurityException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
            });

        }
    }

    // If the arrow hits a mob or player the coords will be -1
    private boolean isValidBlock(int x, int y, int z) {
        return x != -1 && y != -1 && z != -1;
    }

}
