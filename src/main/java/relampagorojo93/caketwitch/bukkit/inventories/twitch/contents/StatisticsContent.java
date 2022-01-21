package relampagorojo93.caketwitch.bukkit.inventories.twitch.contents;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory.SubSection;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.contents.statistics.StatisticsEventsContent;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.contents.statistics.StatisticsFollowersContent;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.contents.statistics.StatisticsSubscriptionsContent;
import relampagorojo93.caketwitch.relautils.bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Button;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

public class StatisticsContent {

    private static ItemStack followers, subscriptions, events;

    static {

        ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");

        ItemMeta im = null;

        // Followers item

        followers = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUwZTYzOGVlYzAwMDg5NDgzNzIwNmFiNzU4NzhlODU0MTVmZTk0ZTViZGU3N2IxYjEyMzdhODlmYjM5Yzc3YyJ9fX0=");
        im = followers.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lFollowers").toString());
        followers.setItemMeta(im);

        // Subscriptions item

        subscriptions = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRjMDAxNjQyMzhkOGFkOTlhZmFkNzkwYzMwNTBmZjllZmY0MTBlMmQ0YTNmZTg1NjAwMzg5ZGNiODE4YTg0OSJ9fX0=");
        im = subscriptions.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lSubscriptions").toString());
        subscriptions.setItemMeta(im);

        // Events item

        events = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY0YzIxZDE3YWQ2MzYzODdlYTNjNzM2YmZmNmFkZTg5NzMxN2UxMzc0Y2Q1ZDliMWMxNWU2ZTg5NTM0MzIifX19");
        im = events.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lEvents").toString());
        events.setItemMeta(im);

    }

    public static void setContent(StreamerInventory inv) {
        if (inv.getCurrentSubSection() != null) {
            switch (inv.getCurrentSubSection()) {
                case STATISTICS_EVENTS:
                    StatisticsEventsContent.setContent(inv);
                    break;
                case STATISTICS_FOLLOWERS:
                    StatisticsFollowersContent.setContent(inv);
                    break;
                case STATISTICS_SUBSCRIPTIONS:
                    StatisticsSubscriptionsContent.setContent(inv);
                    break;
            }
        }
        else {

            if (CakeTwitchAPI.getEventSub() != null) {

                // Followers button

                inv.setSlot(22, new Button(followers) {
                    @Override
                    public void onClick(InventoryClickEvent e) {
                        inv.setCurrentSubSection(SubSection.STATISTICS_FOLLOWERS);
                        inv.setPage(StatisticsFollowersContent.class, 1);
                        inv.updateInventory();
                    }
                });

            }

            // Subscriptions button

            inv.setSlot(20, new Button(subscriptions) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    inv.setCurrentSubSection(SubSection.STATISTICS_SUBSCRIPTIONS);
                    inv.setPage(StatisticsFollowersContent.class, 1);
                    inv.updateInventory();
                }
            });

            // Events button

            inv.setSlot(24, new Button(events) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    inv.setCurrentSubSection(SubSection.STATISTICS_EVENTS);
                    inv.setPage(StatisticsFollowersContent.class, 1);
                    inv.updateInventory();
                }
            });

        }
    }
}
