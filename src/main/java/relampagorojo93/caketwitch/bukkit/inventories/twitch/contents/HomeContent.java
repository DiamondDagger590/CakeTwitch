package relampagorojo93.caketwitch.bukkit.inventories.twitch.contents;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.events.ChatEvents;
import relampagorojo93.caketwitch.bukkit.events.chateventsobjects.InputData;
import relampagorojo93.caketwitch.bukkit.events.chateventsobjects.SpecifyChannelInputData;
import relampagorojo93.caketwitch.bukkit.inventories.general.ConfirmationInventory;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.RegistrationMethodInventory;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.relautils.bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Button;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Item;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebMethod;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebQuery;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotmessages.enums.ChatColor;
import relampagorojo93.caketwitch.spigotmessages.instances.ClickEvent;
import relampagorojo93.caketwitch.spigotmessages.instances.TextReplacement;
import relampagorojo93.caketwitch.spigotmessages.objects.RGBColor;

import java.util.HashMap;
import java.util.List;

public class HomeContent {

    private static ItemStack cake, register, unregister, registerNotSame, streaming, streamData, checkStream;

    static {
        ItemMeta im = null;

        cake = new ItemStack(Material.CAKE);
        im = cake.getItemMeta();
        im.setDisplayName(
            MessagesUtils.getMessageBuilder()
                .createMessage(ChatColor.rgbIsAvailable()
                                   ? MessagesUtils.applyGradient("CakeTwitch Network", new RGBColor(245, 116, 185),
                    new RGBColor(89, 97, 223))
                                   : "&6Cake&5Twitch &aNetwork")
                .toString());
        cake.setItemMeta(im);

        register = new ItemStack(Material.BOOK);
        im = register.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eRegister your acount").toString());
        register.setItemMeta(im);

        registerNotSame = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 14, "RED_STAINED_GLASS_PANE");
        im = registerNotSame.getItemMeta();
        im.setDisplayName(
            MessagesUtils.getMessageBuilder().createMessage("&cTwitch account not linked yet").toString());
        registerNotSame.setItemMeta(im);

        unregister = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 14, "RED_STAINED_GLASS_PANE");
        im = unregister.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&cUnregister twitch account").toString());
        unregister.setItemMeta(im);

        streaming = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiZTY1ZjQ0Y2QyMTAxNGM4Y2RkZDAxNThiZjc1MjI3YWRjYjFmZDE3OWY0YzFhY2QxNThjODg4NzFhMTNmIn19fQ==");
        im = streaming.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eStreaming: &a%param%").toString());
        streaming.setItemMeta(im);

        streamData = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQzM2IxNmQ5OGUwZDlkMzM1MDI3ZjIzMzMyZTIwOGI3YzNmZmYwZDc5ODQ3OTJlYTQ4YzkzY2E1Y2JjZjFlMSJ9fX0=");
        im = streamData.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eStream data").toString());
        im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0", "&0  &8> &eTitle: &a%param%",
            "&0  &8> &eGame: &a%param%", "&0  &8> &eIs mature: &a%param%").getStrings());
        streamData.setItemMeta(im);

        checkStream = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=");
        im = checkStream.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eCheck stream").toString());
        checkStream.setItemMeta(im);

    }

    public static void setContent(StreamerInventory inv) {

        boolean same = inv.getPlayer().getUniqueId().compareTo(inv.getStreamer().getUniqueID()) == 0;
        boolean registered = inv.getStreamer().isRegistered();

        ItemStack i = null;
        ItemMeta im = null;
        List<String> lore = null;

        i = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
        im = registerNotSame.getItemMeta();
        im.setDisplayName(ChatColor.BLACK.toString());
        registerNotSame.setItemMeta(im);

        inv.setSlot(13, new Item(cake));

        if (registered) {

            i = streaming.clone();
            im = i.getItemMeta();
            im.setDisplayName(im.getDisplayName().replace("%param%", MessagesUtils.getMessageBuilder()
                .createMessage(inv.getStreamer().getStats().isStreaming() ? "&aOn" : "&cOff").toString()));
            i.setItemMeta(im);
            inv.setSlot(30, new Item(i));

            i = streamData.clone();
            im = i.getItemMeta();
            lore = im.getLore();
            String streamtitle = inv.getStreamer().getStats().getStreamTitle();
            lore.set(1, im.getLore().get(1).replace("%param%",
                streamtitle.length() > 32 ? streamtitle.substring(0, 32) + "..." : streamtitle));
            lore.set(2, im.getLore().get(2).replace("%param%", inv.getStreamer().getStats().getStreamGame()));
            lore.set(3, im.getLore().get(3).replace("%param%", MessagesUtils.getMessageBuilder()
                .createMessage(inv.getStreamer().getStats().getStreamIsMature() ? "&aYes" : "&cNo").toString()));
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setSlot(31, new Item(i));

            inv.setSlot(32, new Button(checkStream) {

                @Override
                public void onClick(InventoryClickEvent e) {
                    if (inv.getStreamer().getChannelLogin() != null) {
                        inv.closeInventory(CakeTwitchAPI.getPlugin());
                        MessagesUtils.getMessageBuilder()
                            .createMessage(
                                new TextReplacement[]{new TextReplacement("%url%",
                                    () -> "&ehttps://www.twitch.tv/" + inv.getStreamer().getChannelLogin(),
                                    new ClickEvent(ClickEvent.Action.OPEN_URL,
                                        "https://www.twitch.tv/"
                                            + inv.getStreamer().getChannelLogin()))},
                                true, "%url%")
                            .sendMessage(e.getWhoClicked());
                    }
                }
            });

			if (same) {
				inv.setSlot(49, new Button(unregister) {

					@Override
					public void onClick(InventoryClickEvent e) {
						new ConfirmationInventory(inv.getPlayer(), () -> {
							if (inv.getStreamer().getToken() != null) {
								WebQuery.queryToResponse(
									"https://id.twitch.tv/oauth2/revoke?client_id="
										+ inv.getStreamer().getToken().getClientId() + "&token="
										+ inv.getStreamer().getToken().getAccessToken(),
									WebMethod.POST, new HashMap<>());
							}
							inv.getStreamer().setChannelLogin(null);
							MessagesUtils.getMessageBuilder()
								.createMessage(MessageString.applyPrefix("Account unlinked successfully!"))
								.sendMessage(inv.getPlayer());
						}).setPreviousHolder(inv).openInventory(CakeTwitchAPI.getPlugin());
					}
				});
			}
        }
        else {
			if (same) {
				inv.setSlot(31, new Button(register) {

					@Override
					public void onClick(InventoryClickEvent e) {
						if (!inv.getStreamer().isRegistered()) {
							if (CakeTwitchAPI.getWebQuery() == null || CakeTwitchAPI.getHTTP() == null) {
								inv.closeInventory(CakeTwitchAPI.getPlugin());
								MessagesUtils.getMessageBuilder().sendTitle(inv.getPlayer(), true, "REGISTRATION",
									"Type the username of your channel\n Type 'cancel' to stop the registration",
									20, 100, 20);
								ChatEvents.register((InputData) new SpecifyChannelInputData(inv.getPlayer()));
							}
							else {
								new RegistrationMethodInventory(inv.getPlayer()).setPreviousHolder(inv.getHolder())
									.openInventory(CakeTwitchAPI.getPlugin());
							}
						}
					}

				});
			}
			else {
				inv.setSlot(31, new Item(registerNotSame));
			}
        }

    }
}
