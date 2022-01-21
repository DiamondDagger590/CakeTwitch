package relampagorojo93.caketwitch.modules.ConfigPckg.Configurations;

import relampagorojo93.LibsCollection.YAMLLib.Objects.Data;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Section;
import relampagorojo93.LibsCollection.YAMLLib.YAMLFile;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands.Command;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands.CommandList;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands.MessageCommandList;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Bits.BitOption;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Raids.RaidOption;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Subscriptions.SubscriptionOption;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Configuration {
	private String name;
	private String perm = "";
	private List<BitOption> bitoptions = new ArrayList<>();
	private List<SubscriptionOption> subscriptionoptions = new ArrayList<>();
	private List<RaidOption> raidoptions = new ArrayList<>();
	private CommandList followerscommands = new CommandList(new ArrayList<>());
	private HashMap<UUID, CommandList> rewardscommands = new HashMap<>();
	private MessageCommandList messagecommandlist;
	private String msgnmlformat = "", msgfrstformat = "", msghlformat = "", subicon = "";
	public Configuration(File f) throws Exception {
		this.name = f.getName().replace(".yml", "");
		YAMLFile yml = new YAMLFile(f);
		List<Condition> conditions = new ArrayList<>();
		List<Command> commands = new ArrayList<>();
		List<Command> ifgifted = new ArrayList<>();
		if (yml.getSection("bits") != null) {
			if (yml.getSection("bits").getStringList() != null) {
				conditions.clear();
				commands.clear();
				for (String command:yml.getSection("bits").getStringList()) commands.add(new Command(command));
				bitoptions.add(new BitOption(new ArrayList<>(conditions), new CommandList(new ArrayList<>(commands))));
			}
			else {
				for (Data data:yml.getSection("bits").getChilds()) {
					if (data instanceof Section) {
						Section id = (Section) data;
						conditions.clear();
						commands.clear();
						if (id.getChild("conditionals") != null) for (String condition:id.getChild("conditionals").getChild("bits-amount", "").getString().split(";")) conditions.add(new Condition(condition));
						for (String command:id.getChild("effects", new ArrayList<>()).getStringList()) commands.add(new Command(command));
						bitoptions.add(new BitOption(new ArrayList<>(conditions), new CommandList(new ArrayList<>(commands))));
					}
				}
			}
			
		}

		if (yml.getSection("subscriptions") != null) {
			if (yml.getNonNullSection("subscriptions.effects").getStringList() != null || yml.getNonNullSection("subscriptions.if-gifted").getStringList() != null) {
				conditions.clear();
				commands.clear();
				ifgifted.clear();
				for (String command:yml.getSection("subscriptions.effects", new ArrayList<>()).getStringList()) commands.add(new Command(command));
				for (String command:yml.getSection("subscriptions.if-gifted", new ArrayList<>()).getStringList()) ifgifted.add(new Command(command));
				subscriptionoptions.add(new SubscriptionOption(new ArrayList<>(conditions), new CommandList(new ArrayList<>(commands)), new CommandList(new ArrayList<>(ifgifted))));
			}
			else {
				for (Data data:yml.getSection("subscriptions").getChilds()) {
					if (data instanceof Section) {
						Section id = (Section) data;
						conditions.clear();
						commands.clear();
						ifgifted.clear();
						if (id.getChild("conditionals") != null) for (String condition:id.getChild("conditionals").getChild("months-amount", "").getString().split(";")) conditions.add(new Condition(condition));
						for (String command:id.getChild("effects", new ArrayList<>()).getStringList()) commands.add(new Command(command));
						for (String command:id.getChild("if-gifted", new ArrayList<>()).getStringList()) ifgifted.add(new Command(command));
						subscriptionoptions.add(new SubscriptionOption(new ArrayList<>(conditions), new CommandList(new ArrayList<>(commands)), new CommandList(new ArrayList<>(ifgifted))));
					}
				}
			}
		}
		if (yml.getSection("raids") != null) {
			if (yml.getSection("raids").getStringList() != null) {
				conditions.clear();
				commands.clear();
				for (String command:yml.getSection("raids").getStringList()) commands.add(new Command(command));
				raidoptions.add(new RaidOption(new ArrayList<>(conditions), new CommandList(new ArrayList<>(commands))));
			}
			else {
				for (Data data:yml.getSection("raids").getChilds()) {
					if (data instanceof Section) {
						Section id = (Section) data;
						conditions.clear();
						commands.clear();
						if (id.getChild("conditionals") != null) for (String condition:id.getChild("conditionals").getChild("raids-amount", "").getString().split(";")) conditions.add(new Condition(condition));
						for (String command:id.getChild("effects", new ArrayList<>()).getStringList()) commands.add(new Command(command));
						raidoptions.add(new RaidOption(new ArrayList<>(conditions), new CommandList(new ArrayList<>(commands))));
					}
				}
			}
			
		}
		if (yml.getSection("channelpoints") != null) for (Data data:yml.getSection("channelpoints").getChilds()) {
			if (data instanceof Section) {
				Section reward = (Section) data;
				commands.clear();
				for (String command:reward.getStringList()) commands.add(new Command(command));
				try {
					rewardscommands.put(UUID.fromString(reward.getName()), new CommandList(new ArrayList<>(commands)));
				} catch (IllegalArgumentException e) {}
			}
		}
		if (yml.getSection("followers") != null) {
			if (yml.getSection("followers").getStringList() != null) {
				commands.clear();
				for (String command:yml.getSection("followers").getStringList()) commands.add(new Command(command));
				followerscommands = new CommandList(new ArrayList<>(commands));
			}
		}
		List<Command> nmcmds, smcmds, hmcmds, rmcmds;
		nmcmds = new ArrayList<>();
		smcmds = new ArrayList<>();
		hmcmds = new ArrayList<>();
		rmcmds = new ArrayList<>();
		for (String command:yml.getSection("messages.commands-after-normal-message", new ArrayList<>()).getStringList()) nmcmds.add(new Command(command));
		for (String command:yml.getSection("messages.commands-after-subscriber-message", new ArrayList<>()).getStringList()) smcmds.add(new Command(command));
		for (String command:yml.getSection("messages.commands-after-highlighted-message", new ArrayList<>()).getStringList()) hmcmds.add(new Command(command));
		for (String command:yml.getSection("messages.commands-after-ritual-message", new ArrayList<>()).getStringList()) rmcmds.add(new Command(command));
		messagecommandlist = new MessageCommandList(nmcmds, hmcmds, rmcmds, smcmds);
		perm = yml.getSection("permission", "").getString();
		msgnmlformat = yml.getSection("messages.normal-messages-format", "pene").getString().replace("&", "\u00A7");
		msgfrstformat = yml.getSection("messages.ritual-messages-format", "").getString().replace("&", "\u00A7");
		msghlformat = yml.getSection("messages.highlighted-messages-format", "").getString().replace("&", "\u00A7");
		subicon = yml.getSection("messages.subscriber-icon", "").getString().replace("&", "\u00A7");
	}
	public String getName() { return name; }
	public String getPermission() { return perm; }
	public List<BitOption> getBitOptions() { return bitoptions; }
	public List<SubscriptionOption> getSubscriptionOptions() { return subscriptionoptions; }
	public List<RaidOption> getRaidOptions() { return raidoptions; }
	public List<UUID> getRewardsUUIDs() { return new ArrayList<>(rewardscommands.keySet()); }
	public CommandList getRewardCommands(UUID reward) { return rewardscommands.get(reward); }
	public CommandList getFollowersCommands() { return followerscommands; }
	public MessageCommandList getMessageCommandList() { return messagecommandlist; }
	public String normalMessageFormat() { return msgnmlformat; }
	public String ritualMessageFormat() { return msgfrstformat; }
	public String highlightedMessageFormat() { return msghlformat; }
	public String subscriberIcon() { return subicon; }
}
