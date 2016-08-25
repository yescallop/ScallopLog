package cn.yescallop.scalloplog;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;

public class EventListener implements Listener {
    
    private ScallopLog plugin;
    
    public EventListener(ScallopLog plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Item item = event.getItem();
        Block block = event.getBlock();
        Item[] drops = event.getDrops();
        plugin.logPlayer(event.getPlayer(), "使用 "
            + item.getName() + " (" + item.getId() + ":" + item.getDamage() + ") 破坏了位于 ("
            + block.level.getName() + ", " + (int) block.x + ", " + (int) block.y + ", " + (int) block.z
            + ") 的 " + block.getName() + " (" + block.getId() + ":" + block.getDamage() + ")"
            + this.getItemsString(drops)
        );
    }
    
    private String getItemsString(Item[] items) {
        if (items.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder("，掉落物品：");
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (builder.length() != 0) {
                builder.append(", ");
            }
            builder.append(item.getName() + " (" + item.getId() + ":" + item.getDamage() + ") * " + item.getCount());
        }
        return builder.toString();
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block blockReplace = event.getBlockReplace();
        Block block = event.getBlockAgainst();
        plugin.logPlayer(event.getPlayer(), "在 ("
            + block.level.getName() + ", " + (int) block.x + ", " + (int) block.y + ", " + (int) block.z
            + ") 放置了 " + block.getName() + " (" + block.getId() + ":" + block.getDamage() + ")"
            + ((blockReplace.getId() == Block.AIR) ? "" : ("，覆盖了 "
            + blockReplace.getName() + " (" + blockReplace.getId() + ":" + blockReplace.getDamage() + ")"))
        );
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Block block = event.getBlockClicked().getSide(event.getBlockFace());
        Item bucket = event.getBucket();
        plugin.logPlayer(event.getPlayer(), "在 ("
            + block.level.getName() + ", " + (int) block.x + ", " + (int) block.y + ", " + (int) block.z
            + ") 倒了一桶 " + bucket.getName() + " (" + bucket.getId() + ":" + bucket.getDamage() + ")"
        );
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if (message.startsWith("/")) {
            plugin.logPlayer(event.getPlayer(), message);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == PlayerInteractEvent.RIGHT_CLICK_BLOCK) {
            Block block = event.getBlock();
            Item item = event.getItem();
            plugin.logPlayer(event.getPlayer(), "对位于 ("
                + block.level.getName() + ", " + (int) block.x + ", " + (int) block.y + ", " + (int) block.z
                + ") 的 " + block.getName() + " (" + block.getId() + ":" + block.getDamage() + ") 使用了 "
                + item.getName() + " (" + item.getId() + ":" + item.getDamage() + ")"
            );
        }
    }
}