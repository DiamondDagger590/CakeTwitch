package relampagorojo93.caketwitch.modules.httppckg;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.httpserver.HTTPServer;
import relampagorojo93.caketwitch.modules.eventsubpckg.pages.EventRetrievePage;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingBoolean;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingInt;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.modules.httppckg.pages.SecretPage;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.pages.AuthorizationPage;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.pages.LastStatisticsPage;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebMethod;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebQuery;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;
import relampagorojo93.caketwitch.spigotthreads.objects.Thread;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class HTTPModule extends PluginModule {

    public boolean load() {
        try {

            if (SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString().isEmpty()) {
                System.out.println(MessageString.applyPrefix("(HTTP) There's no callback URL specified!"));
                return false;
            }

            try {
                this.server = (HTTPServer) CakeTwitchAPI.getThreadManager()
                    .registerThread(SettingBoolean.TWITCH_EVENTSUB_SSL_ENABLE.toBoolean()
                                        ? (Thread) new HTTPServer(
                        new File(SettingString.TWITCH_EVENTSUB_SSL_PRIVATEKEY.toString()),
                        new File(SettingString.TWITCH_EVENTSUB_SSL_PRIVATEKEY.toString()))
                                        : (Thread) new HTTPServer());
            }
            catch (Exception e) {
                try {
                    this.server = (HTTPServer) CakeTwitchAPI.getThreadManager().registerThread((Thread) new HTTPServer());
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                    throw e2;
                }
            }

            this.server.setPort(SettingInt.TWITCH_EVENTSUB_PORT.toInt());
            this.server.startSecure();

            UUID uuid = UUID.randomUUID();

            this.server.setPage("/", data -> new HTTPServer.ServerResponse(uuid.toString(), HTTPServer.ServerResponse.ResponseCode.OK));

            WebQuery.ClientResponse response = WebQuery.queryToClientResponse(SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString(), WebMethod.GET);
            if (response == null || !uuid.toString().equals(response.getContent())) {
                throw new Exception();
            }

        }
        catch (Exception e) {
            System.out.println(MessageString.applyPrefix("(HTTP) Not able to connect to HTTP Server through Callback URL!"));
            return false;
        }

        this.server.removePage("/");

        for (Entry<String, HTTPServer.Page> entry : pages.entrySet()) {
            this.server.setPage(entry.getKey(), entry.getValue());
        }

        return true;
    }

    public boolean unload() {
        if (this.server != null) {
            this.server.stopSecure();
            try {
                this.server.joinThread();
            }
            catch (Exception e) {
            }
            CakeTwitchAPI.getThreadManager().unregisterThread((Thread) this.server);
            this.server = null;
        }
        return true;
    }

    @Override
    public LoadOn loadOn() {
        return LoadOn.ENABLE;
    }

    @Override
    public boolean optional() {
        return true;
    }

    @Override
    public boolean allowReload() {
        return this.server == null || !this.server.isRunning();
    }

    private HTTPServer server;
    private HashMap<String, HTTPServer.Page> pages = new HashMap<>();

    public HTTPModule() {
        setPage("/up/up/down/down/left/right/left/right/b/a/start", new SecretPage());
        setPage("/event", new EventRetrievePage());
        setPage("/authorization", new AuthorizationPage());
        setPage("/last-statistics", new LastStatisticsPage());
    }

    public void setPage(String path, HTTPServer.Page page) {
        this.pages.put(path, page);
        if (this.server != null) {
            this.server.setPage(path, page);
        }
    }

}
