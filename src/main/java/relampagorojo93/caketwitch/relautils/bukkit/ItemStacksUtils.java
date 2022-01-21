package relampagorojo93.caketwitch.relautils.bukkit;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class ItemStacksUtils {

	public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",")
			.split(",")[3];
	private static Method IS_UNBREAKABLE, HAS_LOCALIZEDNAME, GET_LOCALIZEDNAME;
	private static Method GET_ITEMINMAINHAND, GET_ITEMINOFFHAND, SET_ITEMINMAINHAND, SET_ITEMINOFFHAND;
	private static Class<?> NAMESPACED_KEY;
	private static Method DISCOVER_RECIPE, UNDISCOVER_RECIPE, GET_KEY;
	private static Class<?> CRAFTITEMSTACK, ITEMSTACK, NBT_TAGCOMPOUND, NBT_TAGLIST, NBT_TAGSTRING, NBT_BASE;

	static {
		try {
			CRAFTITEMSTACK = Class.forName("org.bukkit.craftbukkit." + VERSION + ".inventory.CraftItemStack");
			ITEMSTACK = Class.forName("net.minecraft.server." + VERSION + ".ItemStack");
			NBT_TAGCOMPOUND = Class.forName("net.minecraft.server." + VERSION + ".NBTTagCompound");
			NBT_TAGLIST = Class.forName("net.minecraft.server." + VERSION + ".NBTTagList");
			NBT_TAGSTRING = Class.forName("net.minecraft.server." + VERSION + ".NBTTagString");
			NBT_BASE = Class.forName("net.minecraft.server." + VERSION + ".NBTBase");
		} catch (Exception e) {
			try {
				ITEMSTACK = Class.forName("net.minecraft.world.item.ItemStack");
				NBT_TAGCOMPOUND = Class.forName("net.minecraft.nbt.NBTTagCompound");
				NBT_TAGLIST = Class.forName("net.minecraft.nbt.NBTTagList");
				NBT_TAGSTRING = Class.forName("net.minecraft.nbt.NBTTagString");
				NBT_BASE = Class.forName("net.minecraft.nbt.NBTBase");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		try {
			NAMESPACED_KEY = Class.forName("org.bukkit.NamespacedKey");
			GET_KEY = ShapedRecipe.class.getMethod("getKey");
			DISCOVER_RECIPE = Player.class.getMethod("discoverRecipe", NAMESPACED_KEY);
			UNDISCOVER_RECIPE = Player.class.getMethod("undiscoverRecipe", NAMESPACED_KEY);
		} catch (Exception e) {
			NAMESPACED_KEY = null;
			GET_KEY = null;
			DISCOVER_RECIPE = null;
			UNDISCOVER_RECIPE = null;
			System.out.println(
					"Method " + e.getMessage() + " not found. Omitting discovery and advanced recipes implementation!");
		}
		try {
			IS_UNBREAKABLE = ItemMeta.class.getMethod("isUnbreakable");
			HAS_LOCALIZEDNAME = ItemMeta.class.getMethod("hasLocalizedName");
			GET_LOCALIZEDNAME = ItemMeta.class.getMethod("getLocalizedName");
		} catch (Exception e) {
			IS_UNBREAKABLE = null;
			HAS_LOCALIZEDNAME = null;
			GET_LOCALIZEDNAME = null;
		}
		try {
			GET_ITEMINMAINHAND = PlayerInventory.class.getMethod("getItemInMainHand");
			GET_ITEMINOFFHAND = PlayerInventory.class.getMethod("getItemInOffHand");
			SET_ITEMINMAINHAND = PlayerInventory.class.getMethod("setItemInMainHand", ItemStack.class);
			SET_ITEMINOFFHAND = PlayerInventory.class.getMethod("setItemInOffHand", ItemStack.class);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				GET_ITEMINMAINHAND = PlayerInventory.class.getMethod("getItemInHand");
				GET_ITEMINOFFHAND = null;
				SET_ITEMINMAINHAND = PlayerInventory.class.getMethod("setItemInHand", ItemStack.class);
				SET_ITEMINOFFHAND = null;
			} catch (Exception e2) {
				GET_ITEMINMAINHAND = null;
				GET_ITEMINOFFHAND = null;
				SET_ITEMINMAINHAND = null;
				SET_ITEMINOFFHAND = null;
			}
		}
	}

	public static ItemStack setData(ItemStack hat, String id, String value) {
		try {
			Object nmsStack = CRAFTITEMSTACK.getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null,
					new Object[] { hat });
			Object compound = ((Boolean) nmsStack.getClass().getMethod("hasTag", new Class[0]).invoke(nmsStack,
					new Object[0])).booleanValue()
							? nmsStack.getClass().getMethod("getTag", new Class[0]).invoke(nmsStack, new Object[0])
							: NBT_TAGCOMPOUND.getConstructor(new Class[0]).newInstance(new Object[0]);
			Method set = compound.getClass().getMethod("set", new Class[] { String.class, NBT_BASE });
			try {
				set.invoke(compound, new Object[] { id, NBT_TAGSTRING.getConstructor(new Class[] { String.class })
						.newInstance(new Object[] { value }) });
			} catch (Exception e) {
				set.invoke(compound, new Object[] { id, NBT_TAGSTRING.getMethod("a", new Class[] { String.class })
						.invoke(null, new Object[] { value }) });
			}
			nmsStack.getClass().getMethod("setTag", new Class[] { NBT_TAGCOMPOUND }).invoke(nmsStack,
					new Object[] { compound });
			return (ItemStack) CRAFTITEMSTACK.getMethod("asBukkitCopy", new Class[] { ITEMSTACK })
					.invoke(null, new Object[] { nmsStack });
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getData(ItemStack hat, String id) {
		try {
			Object nmsStack = CRAFTITEMSTACK.getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null,
					new Object[] { hat });
			Object compound = ((Boolean) nmsStack.getClass().getMethod("hasTag", new Class[0]).invoke(nmsStack,
					new Object[0])).booleanValue()
							? nmsStack.getClass().getMethod("getTag", new Class[0]).invoke(nmsStack, new Object[0])
							: NBT_TAGCOMPOUND.getConstructor(new Class[0]).newInstance(new Object[0]);
			return (String) compound.getClass().getMethod("getString", new Class[] { String.class }).invoke(compound,
					new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static Material getMaterial(String oldname, String newname) {
		try {
			return Material.valueOf(oldname);
		} catch (IllegalArgumentException e) {
			try {
				return Material.valueOf(newname);
			} catch (IllegalArgumentException e2) {
				return null;
			}
		}
	}

	public static ItemStack getItemStack(String name) {
		return getItemStack(name, name);
	}

	public static ItemStack getItemStack(String oldname, String newname) {
		return getItemStack(oldname, (short) 0, newname);
	}

	public static ItemStack getItemStack(String oldname, short olddata, String newname) {
		return getItemStack(oldname, olddata, newname, 1);
	}

	public static ItemStack getItemStack(String oldname, short olddata, String newname, int amount) {
		try {
			ItemStack i = new ItemStack(Material.valueOf(oldname), amount);
			i.getClass().getMethod("setDurability", new Class[] { short.class }).invoke(i,
					new Object[] { Short.valueOf(olddata) });
			return i;
		} catch (Exception e) {
			try {
				return new ItemStack(Material.valueOf(newname), amount);
			} catch (Exception e2) {
				return null;
			}
		}
	}

	public static ItemStack getItemInMainHand(Player pl) {
		try {
			if (GET_ITEMINMAINHAND != null)
				return (ItemStack) GET_ITEMINMAINHAND.invoke(pl.getInventory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ItemStack getItemInOffHand(Player pl) {
		try {
			if (GET_ITEMINOFFHAND != null)
				return (ItemStack) GET_ITEMINOFFHAND.invoke(pl.getInventory());
		} catch (Exception e) {
		}
		return null;
	}

	public static void setItemInMainHand(Player pl, ItemStack item) {
		try {
			if (SET_ITEMINMAINHAND != null)
				SET_ITEMINMAINHAND.invoke(pl.getInventory(), item);
		} catch (Exception e) {
		}
	}

	public static void setItemInOffHand(Player pl, ItemStack item) {
		try {
			if (SET_ITEMINOFFHAND != null)
				SET_ITEMINOFFHAND.invoke(pl.getInventory(), item);
		} catch (Exception e) {
		}
	}

	public static ItemStack getPlayerHead(OfflinePlayer pl) {
		ItemStack i = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		SkullMeta im = (SkullMeta) i.getItemMeta();
		try {
			SkullMeta.class.getMethod("setOwner", String.class).invoke(im, pl != null ? pl.getName() : "");
		} catch (Exception e) {
			try {
				SkullMeta.class.getMethod("setOwningPlayer", OfflinePlayer.class).invoke(im, pl);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack setSkin(ItemStack item, String value) {
		ItemStack i = item.clone();
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		try {
			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			profile.getProperties().put("textures", new Property("textures", value, ""));
			Field pfield = meta.getClass().getDeclaredField("profile");
			pfield.setAccessible(true);
			pfield.set(meta, profile);
			pfield.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		i.setItemMeta((ItemMeta) meta);
		return i;
	}

	public static byte[] itemsParse(ItemStack[] items) {
		try {
			ByteArrayOutputStream str = new ByteArrayOutputStream();
			BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
			data.writeInt(items.length);
			byte b;
			int i;
			ItemStack[] arrayOfItemStack;
			for (i = (arrayOfItemStack = items).length, b = 0; b < i;) {
				ItemStack itemStack = arrayOfItemStack[b];
				data.writeObject((itemStack != null) ? itemStack : new ItemStack(Material.AIR));
				b++;
			}
			data.close();
			return str.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public static ItemStack[] itemsParse(byte[] items) {
		if (items != null)
			try {
				ByteArrayInputStream stream = new ByteArrayInputStream(items);
				BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
				ItemStack[] its = new ItemStack[data.readInt()];
				for (int i = 0; i < its.length; i++) {
					its[i] = (ItemStack) data.readObject();
					if (its[i] != null && its[i].getType() == Material.AIR)
						its[i] = null;
				}
				data.close();
				return its;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return new ItemStack[0];
	}

	public static ItemStack removeAttributes(ItemStack item) {
		ItemStack i = item.clone();
		try {
			Object nmsStack = CRAFTITEMSTACK.getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null,
					new Object[] { i });
			Object compound = ((Boolean) nmsStack.getClass().getMethod("hasTag", new Class[0]).invoke(nmsStack,
					new Object[0])).booleanValue()
							? nmsStack.getClass().getMethod("getTag", new Class[0]).invoke(nmsStack, new Object[0])
							: NBT_TAGCOMPOUND.getDeclaredConstructor().newInstance();
			Object modifiers = NBT_TAGLIST.getDeclaredConstructor().newInstance();
			compound.getClass().getMethod("set", new Class[] { String.class, NBT_BASE }).invoke(compound,
					new Object[] { "AttributeModifiers", modifiers });
			nmsStack.getClass().getMethod("setTag", new Class[] { NBT_TAGCOMPOUND }).invoke(nmsStack,
					new Object[] { compound });
			return (ItemStack) CRAFTITEMSTACK.getMethod("asBukkitCopy", new Class[] { ITEMSTACK }).invoke(null,
					new Object[] { nmsStack });
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void switchGlowEffect(ItemStack item) {
		if (item != null) {
			ItemMeta im = item.getItemMeta();
			if (im.getEnchants().isEmpty()) {
				im.addEnchant(Enchantment.DURABILITY, 1, false);
				im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
			} else {
				im.getEnchants().keySet().forEach(im::removeEnchant);
				im.removeItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
			}
			item.setItemMeta(im);
		}
	}

	public static boolean hasGlowEffect(ItemStack item) {
		return (item != null && item.hasItemMeta() && item.getItemMeta().hasEnchant(Enchantment.DURABILITY)
				&& item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS));
	}

	public static boolean isSimilar(ItemStack item1, ItemStack item2) {
		if (item1 == item2)
			return true;
		if (item1.getType() != item2.getType())
			return false;
		if (item1 == null || item2 == null || item1.hasItemMeta() != item2.hasItemMeta())
			return false;
		if (item1.hasItemMeta()) {
			ItemMeta im1 = item1.getItemMeta(), im2 = item2.getItemMeta();
			if (im1.hasDisplayName() != im2.hasDisplayName())
				return false;
			if (im1.hasDisplayName() && !im1.getDisplayName().equals(im2.getDisplayName()))
				return false;
			if (im1.hasLore() != im2.hasLore())
				return false;
			if (im1.hasLore() && !im1.getLore().equals(im2.getLore()))
				return false;
			if (im1.hasEnchants() != im2.hasEnchants())
				return false;
			if (im1.hasEnchants() && !im1.getEnchants().equals(im2.getEnchants()))
				return false;
			try {
				if (IS_UNBREAKABLE != null
						&& (boolean) IS_UNBREAKABLE.invoke(im1) != (boolean) IS_UNBREAKABLE.invoke(im2))
					return false;
				if (HAS_LOCALIZEDNAME != null
						&& (boolean) HAS_LOCALIZEDNAME.invoke(im1) != (boolean) HAS_LOCALIZEDNAME.invoke(im2))
					return false;
				if (HAS_LOCALIZEDNAME != null && GET_LOCALIZEDNAME != null && (boolean) HAS_LOCALIZEDNAME.invoke(im1)
						&& !((String) GET_LOCALIZEDNAME.invoke(im1)).equals(((String) GET_LOCALIZEDNAME.invoke(im2))))
					return false;
			} catch (Exception e) {
			}
			for (ItemFlag flag : ItemFlag.values())
				if (im1.hasItemFlag(flag) != im2.hasItemFlag(flag))
					return false;
		}
		return true;
	}

	public static void reduceItemInMainHand(Player pl) {
		if (pl.getGameMode() == GameMode.CREATIVE)
			return;
		ItemStack i = ItemStacksUtils.getItemInMainHand(pl);
		if (i.getAmount() != 1)
			i.setAmount(i.getAmount() - 1);
		else
			ItemStacksUtils.setItemInMainHand(pl, null);
	}

	// ------------------------------------------------------------//
	// Recipe methods
	// ------------------------------------------------------------//

	public static boolean discoverRecipe(Player player, ShapedRecipe namespace) {
		try {
			if (NAMESPACED_KEY != null && GET_KEY != null && DISCOVER_RECIPE != null)
				return (boolean) DISCOVER_RECIPE.invoke(player, GET_KEY.invoke(namespace));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean undiscoverRecipe(Player player, ShapedRecipe namespace) {
		try {
			if (NAMESPACED_KEY != null && GET_KEY != null && UNDISCOVER_RECIPE != null)
				return (boolean) UNDISCOVER_RECIPE.invoke(player, GET_KEY.invoke(namespace));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
