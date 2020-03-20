package com.ipedg.minecraft;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class bound extends JavaPlugin implements Listener {
    private String Msg;
    private String libao;

    @Override
    public void onEnable() {
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
        }
        saveDefaultConfig();
        ReloadConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()&&args.length==1){
            if (args[0].equalsIgnoreCase("reload")){
                saveConfig();
                ReloadConfig();
                sender.sendMessage("重载成功");
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    private void ReloadConfig(){
        reloadConfig();
        libao = getConfig().getString("ItemName");
        Msg = getConfig().getString("Msg");
    }

    @EventHandler
    public void PlayerInventoryAdd(InventoryClickEvent event){
        Player whoClicked = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item!=null&&item.getType()!= Material.AIR){
            if (item.hasItemMeta()){
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.hasLore()){
                    if (itemMeta.getLore().contains(libao)){
                        int num = ItemHashBound(item, whoClicked.getName());
                        switch (num){
                            case 1:
                                break;
                            case 2:
                                event.setCancelled(true);
                                event.setCurrentItem(null);
                                whoClicked.sendMessage(Msg);
                                break;
                            case 3:
                                event.setCancelled(true);
                                event.setCurrentItem(setNbt(item,whoClicked.getName()));
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerInventoryAdd(InventoryDragEvent event){
        Player whoClicked = (Player) event.getWhoClicked();
        ItemStack item = event.getCursor();
        if (item!=null&&item.getType()!= Material.AIR){
            if (item.hasItemMeta()){
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.hasLore()){
                    if (itemMeta.getLore().contains(libao)){
                        int num = ItemHashBound(item, whoClicked.getName());
                        switch (num){
                            case 1:
                                break;
                            case 2:
                                event.setCancelled(true);
                                event.setCursor(null);
                                whoClicked.sendMessage(Msg);
                                break;
                            case 3:
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerInventoryAdd(PlayerPickupItemEvent event){
        ItemStack item = event.getItem().getItemStack();
        Player player = event.getPlayer();
        if (item!=null&&item.getType()!= Material.AIR){
            if (item.hasItemMeta()){
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.hasLore()){
                    if (itemMeta.getLore().contains(libao)){
                        int num = ItemHashBound(item, player.getName());
                        switch (num){
                            case 1:
                                break;
                            case 2:
                                event.setCancelled(true);
                                event.getItem().setItemStack(null);
                                player.sendMessage(Msg);
                                break;
                            case 3:
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerChangeItem(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item!=null&&item.getType()!= Material.AIR){
            if (item.hasItemMeta()){
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.hasLore()){
                    if (itemMeta.getLore().contains(libao)){
                        int num = ItemHashBound(item, player.getName());
                        switch (num){
                            case 1:
                                break;
                            case 2:
                                event.setCancelled(true);
                                player.getInventory().setItemInMainHand(null);
                                player.sendMessage(Msg);
                                break;
                            case 3:
                                event.setCancelled(true);
                                player.getInventory().setItemInMainHand(setNbt(item,player.getName()));
                                break;
                        }
                    }
                }
            }
        }
    }

//    @EventHandler
//    public void PlayerInventoryAdd(InventoryCloseEvent event){
//        System.out.print(event.getInventory().getType());
//        if (event.getInventory().getType().equals(InventoryType.PLAYER)){
//            Inventory inventory = event.getInventory();
//            Player player = (Player) event.getPlayer();
//            for (int i=0;i<35;i++){
//                ItemStack item = inventory.getItem(i);
//                if (item!=null&&item.getType()!= Material.AIR){
//                    if (item.hasItemMeta()){
//                        ItemMeta itemMeta = item.getItemMeta();
//                        if (itemMeta.hasLore()){
//                            if (itemMeta.getLore().contains(libao)){
//                                int num = ItemHashBound(item, player.getName());
//                                switch (num){
//                                    case 1:
//                                        break;
//                                    case 2:
//                                        inventory.setItem(i,null);
//                                        player.sendMessage("有物品已经不属于你已经移除");
//                                        break;
//                                    case 3:
//                                        inventory.setItem(i,setNbt(item,player.getName()));
//                                        break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public int ItemHashBound(ItemStack itemStack,String name){
        net.minecraft.item.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsCopy.func_77978_p();
        if (nbtTagCompound.func_74764_b("PlayerNew")){
            String player = nbtTagCompound.func_74779_i("PlayerNew");
            if (player.equalsIgnoreCase(name)){
                return 1;
            }else {
                return 2;
            }
        }
        return 3;
    }

    public ItemStack setNbt(ItemStack itemStack,String name){
        net.minecraft.item.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsCopy.func_77978_p();
        nbtTagCompound.func_74778_a("PlayerNew",name);
        nmsCopy.func_77982_d(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsCopy);
    }
}
