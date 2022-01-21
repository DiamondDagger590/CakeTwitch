package relampagorojo93.caketwitch.modules.httppckg.pages;

import relampagorojo93.caketwitch.httpserver.HTTPServer;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebQuery;

import java.util.HashMap;

public class SecretPage implements HTTPServer.Page {

    @Override
    public HTTPServer.ServerResponse getResponse(WebQuery.ClientResponse response) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Location", "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        return new HTTPServer.ServerResponse("", HTTPServer.ServerResponse.ResponseCode.TEMPORARY_REDIRECT, headers);
    }

}
