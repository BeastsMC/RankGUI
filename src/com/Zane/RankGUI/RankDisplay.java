package com.Zane.RankGUI;

import java.util.HashMap;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class RankDisplay {
    private final Player player;
    private final RankGUI plugin;
    private HashMap<ItemStack, RankIcon> itemToRank;

    public RankDisplay(Player p, RankGUI main) {
        this.player = p;
        this.plugin = main;
        this.itemToRank = new HashMap<ItemStack, RankIcon>();
    }

    public void open() {
        PermissionUser user = PermissionsEx.getUser(player);
        Inventory inven = plugin.getServer().createInventory(player, 36, "Ranks");
        for (RankIcon rank : plugin.rankIcons) {
            byte colorByte = user.inGroup(rank.getName()) ? RankIcon.UNLOCKED_COLOR.getData() : RankIcon.LOCKED_COLOR.getData();
            ItemStack item = new ItemStack(RankIcon.ICON_MATERIAL, 1, colorByte);
            ItemMeta metaData = item.getItemMeta();
            metaData.setDisplayName(rank.getDisplayName());
            metaData.setLore(rank.getInfo());
            item.setItemMeta(metaData);
            itemToRank.put(item, rank);
            inven.addItem(item);
        }
        player.openInventory(inven);
    }

    public List<String> getAssociatedCommands(ItemStack item) {
        RankIcon rank = itemToRank.get(item);
        if (rank == null) return null;
        byte colorData = item.getData().getData();
        if (colorData == DyeColor.LIME.getData()) {
            return rank.getUnlockedCommands();
        } else if (colorData == DyeColor.RED.getData()) {
            return rank.getLockedCommands();
        } else {
            return null;
        }
    }
}
