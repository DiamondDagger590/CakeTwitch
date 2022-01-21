package relampagorojo93.caketwitch.modules.resourcepackspckg.objects;

import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

public class ResourcePack {
    private String name, permission, url;

    public ResourcePack(String name, String permission, String url) {
        this.name = MessagesUtils.getMessageBuilder().createMessage(name).toString();
        this.permission = permission;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getURL() {
        return url;
    }
}
