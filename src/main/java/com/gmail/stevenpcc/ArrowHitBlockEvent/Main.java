package com.gmail.stevenpcc.ArrowHitBlockEvent;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftArrow;
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
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    try {
                        World world = e.getEntity().getWorld();

                        net.minecraft.server.v1_6_R3.EntityArrow entityArrow = ((CraftArrow) e
                                .getEntity()).getHandle();

                        Field fieldX = net.minecraft.server.v1_6_R3.EntityArrow.class
                                .getDeclaredField("d");
                        Field fieldY = net.minecraft.server.v1_6_R3.EntityArrow.class
                                .getDeclaredField("e");
                        Field fieldZ = net.minecraft.server.v1_6_R3.EntityArrow.class
                                .getDeclaredField("f");

                        fieldX.setAccessible(true);
                        fieldY.setAccessible(true);
                        fieldZ.setAccessible(true);

                        int x = fieldX.getInt(entityArrow);
                        int y = fieldY.getInt(entityArrow);
                        int z = fieldZ.getInt(entityArrow);

                        if (!isNotValidBlock(x, y, z)) {
                            Block block = world.getBlockAt(x, y, z);
                            Bukkit.getServer()
                                    .getPluginManager()
                                    .callEvent(
                                            new ArrowHitBlockEvent((Arrow) e
                                                    .getEntity(), block));
                        }

                    } catch (NoSuchFieldException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (SecurityException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

        }
    }

    private boolean isNotValidBlock(int x, int y, int z) {
        return x == -1 || y == -1 || z == -1;
    }

}
