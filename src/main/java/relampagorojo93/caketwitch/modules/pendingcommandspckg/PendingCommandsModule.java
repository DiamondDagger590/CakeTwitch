package relampagorojo93.caketwitch.modules.pendingcommandspckg;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchBitsDonationEvent;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchChannelRaidEvent;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchEvent;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchMessageEvent;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchRewardEvent;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchSubscriptionEvent;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchSubscriptionGiftEvent;
import relampagorojo93.caketwitch.enums.CommandStatus;
import relampagorojo93.caketwitch.enums.EventType;
import relampagorojo93.caketwitch.jsonlib.JSONArray;
import relampagorojo93.caketwitch.jsonlib.JSONData;
import relampagorojo93.caketwitch.jsonlib.JSONElement;
import relampagorojo93.caketwitch.jsonlib.JSONObject;
import relampagorojo93.caketwitch.jsonlib.JSONParser;
import relampagorojo93.caketwitch.modules.configpckg.configurations.commands.Command;
import relampagorojo93.caketwitch.modules.configpckg.configurations.commands.ExecutableCommand;
import relampagorojo93.caketwitch.modules.filepckg.FileModule;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class PendingCommandsModule extends PluginModule {
    public boolean load() {
        try {
            if (!FileModule.PENDINGCOMMANDS_FILE.exists()) {
                return true;
            }
            for (JSONElement element : JSONParser.parseJson(FileModule.PENDINGCOMMANDS_FILE).asArray()
                .getObjects()) {
                if (element.isObject()) {
                    try {
                        JSONObject object = element.asObject();
                        JSONObject eventdata = object.getObject("event-data");
                        HashMap<String, String> map = new HashMap<>();
                        for (String key : eventdata.getKeys()) {
                            map.put(key, eventdata.getData(key).getAsString());
                        }
                        Streamer streamer = CakeTwitchAPI.getStreamers().getStreamerByLogin(eventdata.getData("channel").getAsString());
                        if (streamer == null || !streamer.getSettings().isAuthorizedStreamer()) {
                            continue;
                        }
                        CakeTwitchEvent event = null;
                        UUID id = UUID.fromString(eventdata.getData("id").getAsString());
                        String username = eventdata.getDataOrDefault("display-name", new JSONData("")).getAsString();
                        String message = eventdata.getDataOrDefault("message", new JSONData("")).getAsString();
                        switch (EventType.valueOf(eventdata.getData("event-type").getAsString())) {
                            case BITS:
                                event = new CakeTwitchBitsDonationEvent(id, streamer, username, message,
                                    eventdata.getDataOrDefault("bits", new JSONData(0)).getAsInteger(), map);
                                break;
                            case MESSAGE:
                                event = new CakeTwitchMessageEvent(id, streamer, username, message, map);
                                break;
                            case RAID:
                                event = new CakeTwitchChannelRaidEvent(id, streamer, username,
                                    eventdata.getDataOrDefault("msg-param-viewerCount", new JSONData(0)).getAsInteger(),
                                    map);
                                break;
                            case REWARD:
                                event = new CakeTwitchRewardEvent(
                                    id, streamer, username, message, UUID.fromString(eventdata
                                    .getDataOrDefault("custom-reward-id", new JSONData("")).getAsString()),
                                    map);
                                break;
                            case SUBSCRIPTION:
                                event = new CakeTwitchSubscriptionEvent(id, streamer, username, message, eventdata
                                    .getDataOrDefault("msg-param-cumulative-months", new JSONData(0)).getAsInteger(),
                                    map);
                                break;
                            case SUBSCRIPTIONGIFT:
                                event = new CakeTwitchSubscriptionGiftEvent(id, streamer, username, message,
                                    eventdata.getDataOrDefault("msg-param-months", new JSONData(0)).getAsInteger(),
                                    eventdata.getDataOrDefault("msg-param-recipient-user-name", new JSONData(""))
                                        .getAsString(),
                                    map);
                                break;
                            default:
                                break;
                        }
                        if (event == null) {
                            continue;
                        }
                        List<Command> commands = new ArrayList<>();
                        for (JSONElement command : object.getArray("commands").getObjects()) {
                            commands.add(new Command(command.asData().getAsString()));
                        }
                        String type = object.getDataOrDefault("pending-type", new JSONData("normal")).getAsString();
                        if (type.equalsIgnoreCase("normal")) {
                            if (!this.commands.containsKey(event)) {
                                this.commands.put(event, new ArrayList<>());
                            }
                            this.commands.get(event).addAll(commands);
                        }
                        if (type.equalsIgnoreCase("world")) {
                            if (!this.wcommands.containsKey(event)) {
                                this.wcommands.put(event, new ArrayList<>());
                            }
                            this.wcommands.get(event).addAll(commands);
                        }
                    }
                    catch (Exception exception) {
                    }
                }
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unload() {
        try {
            JSONArray array = new JSONArray();
            for (Entry<CakeTwitchEvent, List<Command>> event : this.commands.entrySet()) {
                JSONObject object = commandToJSON(event);
                object.addObject("pending-type", "normal");
                array.addObject(new JSONElement[]{(JSONElement) object});

            }
            for (Entry<CakeTwitchEvent, List<Command>> event : this.wcommands.entrySet()) {
                JSONObject object = commandToJSON(event);
                object.addObject("pending-type", "world");
                array.addObject(new JSONElement[]{(JSONElement) object});
            }
            if (!(CakeTwitchAPI.getFile()).PENDINGCOMMANDS_FILE.exists()) {
                (CakeTwitchAPI.getFile()).PENDINGCOMMANDS_FILE.createNewFile();
            }
            array.save((CakeTwitchAPI.getFile()).PENDINGCOMMANDS_FILE);
            this.commands.clear();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoadOn loadOn() {
        return LoadOn.ENABLE;
    }

    public boolean optional() {
        return false;
    }

    public boolean allowReload() {
        return true;
    }

    private JSONObject commandToJSON(Entry<CakeTwitchEvent, List<Command>> event) {
        JSONObject object = new JSONObject();
        JSONArray commands = new JSONArray();
        for (Command cmd : event.getValue()) {
            commands.addObject(cmd.getOriginalCommand());
        }
        object.addObject("commands", (JSONElement) commands);
        JSONObject twitcheventdata = new JSONObject();
        twitcheventdata.addObject("event-type", event.getKey().getEventType().name());
        if (event.getKey().getTags() != null) {
            for (Entry<String, String> entry : (Iterable<Entry<String, String>>) event.getKey().getTags().entrySet()) {
                twitcheventdata.addObject(entry.getKey(), entry.getValue());
            }
        }
        twitcheventdata.addObject("channel", event.getKey().getStreamer().getChannelLogin());
        twitcheventdata.addObject("display-name", event.getKey().getUser());
        if (event instanceof CakeTwitchMessageEvent) {
            twitcheventdata.addObject("message", ((CakeTwitchMessageEvent) event).getMessage());
        }
        object.addObject("event-data", (JSONElement) twitcheventdata);
        return object;
    }

    private HashMap<CakeTwitchEvent, List<Command>> commands = new HashMap<>();

    private HashMap<CakeTwitchEvent, List<Command>> wcommands = new HashMap<>();

    public void addPendingCommand(CakeTwitchEvent event, Command command) {
        if (!commands.containsKey(event)) {
            commands.put(event, new ArrayList<>());
        }
        commands.get(event).add(command);
    }

    public void addWorldPendingCommand(CakeTwitchEvent event, Command command) {
        if (!wcommands.containsKey(event)) {
            wcommands.put(event, new ArrayList<>());
        }
        wcommands.get(event).add(command);
    }

    public void execPendingPlayerCommands(String channel) {
        channel = channel.toLowerCase();
        for (Entry<CakeTwitchEvent, List<Command>> event : commands.entrySet()) {
            if (event.getKey().getUser().toLowerCase().equals(channel)) {
                for (Command cmd : new ArrayList<>(event.getValue())) {
                    CommandStatus status = Command.checkConditions(cmd, event.getKey());
                    switch (status) {
                        case QUEUE:
                            CakeTwitchAPI.getCommandsQueue().addCommand(new ExecutableCommand(cmd, event.getKey()));
                            event.getValue().remove(cmd);
                            break;
                        case EXECUTABLE:
                            Command.execute(cmd, event.getKey());
                            event.getValue().remove(cmd);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        for (Entry<CakeTwitchEvent, List<Command>> event : wcommands.entrySet()) {
            if (event.getKey().getStreamer().getChannelLogin().toLowerCase().equals(channel)) {
                for (Command cmd : new ArrayList<>(event.getValue())) {
                    CommandStatus status = Command.checkConditions(cmd, event.getKey());
                    switch (status) {
                        case QUEUE:
                            CakeTwitchAPI.getCommandsQueue().addCommand(new ExecutableCommand(cmd, event.getKey()));
                            event.getValue().remove(cmd);
                            break;
                        case EXECUTABLE:
                            Command.execute(cmd, event.getKey());
                            event.getValue().remove(cmd);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
