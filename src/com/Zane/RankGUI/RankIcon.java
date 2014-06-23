package com.Zane.RankGUI;

import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class RankIcon {
    public static final Material ICON_MATERIAL = Material.STAINED_GLASS_PANE;
    public static final DyeColor LOCKED_COLOR = DyeColor.RED;
    public static final DyeColor UNLOCKED_COLOR = DyeColor.LIME;

    private final String name;
    private final String displayName;
    private final List<String> info;
    private final List<String> unlockedCommands;
    private final List<String> lockedCommands;

    public RankIcon(String name, String displayName, List<String> info, List<String> unlockedCommands, List<String> lockedCommands) {
        this.name = name;
        this.displayName = displayName;
        this.info = info;
        this.unlockedCommands = unlockedCommands;
        this.lockedCommands = lockedCommands;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public List<String> getInfo() {
        return info;
    }

    public List<String> getUnlockedCommands() {
        return unlockedCommands;
    }

    public List<String> getLockedCommands() {
        return lockedCommands;
    }
}
