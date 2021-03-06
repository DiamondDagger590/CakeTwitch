package relampagorojo93.caketwitch.modules.eventsubpckg.pages;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchFollowerEvent;
import relampagorojo93.caketwitch.enums.ChannelData;
import relampagorojo93.caketwitch.httpserver.HTTPServer;
import relampagorojo93.caketwitch.ircbot.Status;
import relampagorojo93.caketwitch.jsonlib.JSONElement;
import relampagorojo93.caketwitch.jsonlib.JSONObject;
import relampagorojo93.caketwitch.jsonlib.JSONParser;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule.Event;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule.SubscriptionResponse;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.relautils.bukkit.TasksUtils;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebQuery;
import relampagorojo93.caketwitch.spigotdebug.DebugData;
import relampagorojo93.caketwitch.spigotdebug.data.DebugLogData;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.UUID;

public class EventRetrievePage implements HTTPServer.Page {

    @Override
    public HTTPServer.ServerResponse getResponse(WebQuery.ClientResponse response) {
        if (CakeTwitchAPI.getEventSub() == null) {
            return new HTTPServer.ServerResponse("", HTTPServer.ServerResponse.ResponseCode.SERVICE_UNAVAILABLE);
        }
        if (response.getResponseVerb() != WebQuery.ClientResponse.ResponseVerb.POST) {
            return new HTTPServer.ServerResponse("", HTTPServer.ServerResponse.ResponseCode.BAD_REQUEST);
        }
        String type = response.getHeader("twitch-eventsub-message-type");
        if (type == null) {
            type = "";
        }
        if (type.equals("webhook_callback_verification")) {
            return checkVerification(response);
        }
        return checkEvent(response);
    }

    private HTTPServer.ServerResponse checkVerification(WebQuery.ClientResponse response) {
        try {
            JSONElement element = JSONParser.parseJson(response.getContent());
            if (element.isObject() && element.asObject().hasKey("challenge")) {
                String challenge = element.asObject().getData("challenge").getAsString();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-type", "text/plain; charset=UTF-8");
                TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
                    try {
                        JSONObject obj = element.asObject().getObject("subscription")
                            .getObject("condition");
                        Streamer streamer = null;
                        if (obj.hasKey("broadcaster_user_id")) {
                            streamer = CakeTwitchAPI.getStreamers().getStreamerById(obj.getData("broadcaster_user_id").getAsString());
                        }
                        else if (obj.hasKey("user_id")) {
                            streamer = CakeTwitchAPI.getStreamers().getStreamerById(obj.getData("user_id").getAsString());
                        }
                        else if (obj.hasKey("to_broadcaster_user_id")) {
                            streamer = CakeTwitchAPI.getStreamers().getStreamerById(obj.getData("to_broadcaster_user_id").getAsString());
                        }
                        if (streamer != null) {
                            streamer.getEventSub().setResponse(
                                Event.fromEventName(element.asObject().getObject("subscription")
                                    .getData("type").getAsString()),
                                SubscriptionResponse.ACCEPTED);
                        }
                    }
                    catch (Exception e) {
                    }
                });
                return new HTTPServer.ServerResponse(challenge, HTTPServer.ServerResponse.ResponseCode.OK, headers);
            }
        }
        catch (Exception e) {
        }
        return new HTTPServer.ServerResponse("", HTTPServer.ServerResponse.ResponseCode.BAD_REQUEST);
    }

    private HTTPServer.ServerResponse checkEvent(WebQuery.ClientResponse response) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "text/plain; charset=UTF-8");
        String content = response.getContent();
        String hmac = String.valueOf(response.getHeader("twitch-eventsub-message-id"))
                          + response.getHeader("twitch-eventsub-message-timestamp") + content;
        try {
            if (!response.getHeader("twitch-eventsub-message-signature")
                .equals("sha256=" + generateHmac256(hmac, SettingString.TWITCH_EVENTSUB_SECRET.toString().getBytes()))) {
                return new HTTPServer.ServerResponse("", HTTPServer.ServerResponse.ResponseCode.FORBIDDEN, headers);
            }
            try {
                onEvent(JSONParser.parseJson(content));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return new HTTPServer.ServerResponse("", HTTPServer.ServerResponse.ResponseCode.OK, headers);
        }
        catch (Exception e) {
        }
        return new HTTPServer.ServerResponse("", HTTPServer.ServerResponse.ResponseCode.BAD_REQUEST, headers);
    }

    private void onEvent(JSONElement json) {
        if (CakeTwitchAPI.getStreamers() == null) {
            return;
        }
        try {
            Event event = Event.fromEventName(
                json.asObject().getObject("subscription").getData("type").getAsString());
            String channel = json.asObject().getObject("event").getData("broadcaster_user_login").getAsString();
            Streamer user = CakeTwitchAPI.getStreamers().getStreamerByLogin(channel);
            if (user != null && user.getSettings().isAuthorizedStreamer()
                    && user.getBot().getStatus() == Status.RUNNING) {
                switch (event) {
                    case CHANNEL_FOLLOW:
                        String follower = json.asObject().getObject("event").getData("user_name").getAsString();
                        user.getBot().getDebugController()
                            .addDebugData((DebugData) new DebugLogData("New Follower >> " + follower));
                        if (SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(follower.toLowerCase())) {
                            return;
                        }
                        if (user.getSettings().detectFollowers()) {
                            TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> Bukkit.getPluginManager()
                                .callEvent(new CakeTwitchFollowerEvent(UUID.randomUUID(), user, follower)));
                        }
                        return;
                    case STREAM_ONLINE:
                    case STREAM_OFFLINE:
                        if (event == Event.STREAM_ONLINE) {
                            user.getStats().setData(ChannelData.IS_STREAMING, true);
                            user.getBot().getDebugController().addDebugData(new DebugLogData("Streaming online!"));
                        }
                        else {
                            user.getStats().setData(ChannelData.IS_STREAMING, false);
                            user.getBot().getDebugController().addDebugData(new DebugLogData("Streaming offline!"));
                        }
                        return;
                    default:
                        return;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateHmac256(String message, byte[] key) throws Exception {
        byte[] bytes = hmac("HmacSHA256", key, message.getBytes());
        return bytesToHex(bytes);
    }

    private byte[] hmac(String algorithm, byte[] key, byte[] message) throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0xF];
        }
        return new String(hexChars);
    }

    public static String getURL() {
        return SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString()
                   + (SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString().endsWith("/") ? "" : "/")
                   + "event";
    }

}
