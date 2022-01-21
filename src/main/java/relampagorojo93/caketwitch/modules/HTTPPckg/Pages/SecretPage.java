package relampagorojo93.caketwitch.modules.HTTPPckg.Pages;

import relampagorojo93.LibsCollection.HTTPServer.HTTPServer;
import relampagorojo93.LibsCollection.HTTPServer.HTTPServer.Page;
import relampagorojo93.LibsCollection.HTTPServer.HTTPServer.ServerResponse;
import relampagorojo93.LibsCollection.HTTPServer.HTTPServer.ServerResponse.ResponseCode;
import relampagorojo93.LibsCollection.Utils.Shared.WebQueries.WebQuery.ClientResponse;

import java.util.HashMap;

public class SecretPage implements Page {

	@Override
	public ServerResponse getResponse(ClientResponse response) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Location", "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		return new HTTPServer.ServerResponse("", ResponseCode.TEMPORARY_REDIRECT, headers);
	}

}
