package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Pages;

import relampagorojo93.LibsCollection.HTTPServer.HTTPServer.Page;
import relampagorojo93.LibsCollection.HTTPServer.HTTPServer.ServerResponse;
import relampagorojo93.LibsCollection.JSONLib.JSONArray;
import relampagorojo93.LibsCollection.JSONLib.JSONObject;
import relampagorojo93.LibsCollection.SpigotDebug.DebugData;
import relampagorojo93.LibsCollection.Utils.Shared.WebQueries.WebQuery.ClientResponse;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LastStatisticsPage implements Page {

	@Override
	public ServerResponse getResponse(ClientResponse response) {
		try {
			String rawstreamer = response.getParameter("streamer");
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if (rawstreamer != null) {
				Streamer streamer = CakeTwitchAPI.getStreamers().getStreamerByLogin(rawstreamer);
				if (streamer != null) {
					JSONObject obj = new JSONObject();
					obj.addObject("Streamer", rawstreamer);
					JSONArray array = new JSONArray();
					for (int i = 0; i < streamer.getStatistics().getFollowers().getDebugLength(); i++) {
						DebugData follower = streamer.getStatistics().getFollowers().getDebugData(i);
						if (follower != null) {
							String[] fdata = follower.getMessage().split("\\|");
							JSONObject fobj = new JSONObject();
							fobj.addObject("Timestamp", format.format(new Date(Long.parseLong(fdata[0]))));
							fobj.addObject("User", fdata[1]);
							array.addObject(fobj);
						}
					}
					obj.addObject("Followers", array);
					array = new JSONArray();
					for (int i = 0; i < streamer.getStatistics().getSubscriptions().getDebugLength(); i++) {
						DebugData subscription = streamer.getStatistics().getSubscriptions()
								.getDebugData(i);
						if (subscription != null) {
							String[] fdata = subscription.getMessage().split("\\|");
							JSONObject fobj = new JSONObject();
							switch (fdata[0]) {
							case "sub":
								fobj.addObject("Timestamp",
										format.format(new Date(Long.parseLong(fdata[1]))));
								fobj.addObject("Type", "Subscription");
								fobj.addObject("User", fdata[2]);
								fobj.addObject("Months", Integer.parseInt(fdata[3]));
								array.addObject(fobj);
								break;
							case "subgift":
								fobj.addObject("Timestamp",
										format.format(new Date(Long.parseLong(fdata[1]))));
								fobj.addObject("Type", "Gifted subscription");
								fobj.addObject("User", fdata[2]);
								fobj.addObject("Destination", fdata[3]);
								fobj.addObject("Months", Integer.parseInt(fdata[4]));
								array.addObject(fobj);
								break;
							default:
								break;
							}
						}
					}
					obj.addObject("Subscriptions", array);
					return new ServerResponse(obj.toString(), ServerResponse.ResponseCode.OK);
				}
			}
		} catch (Exception e) {
		}
		return new ServerResponse("", ServerResponse.ResponseCode.BAD_REQUEST);
	}
	
	public static String getURL() {
		return SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString()
				+ (SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString().endsWith("/") ? "" : "/")
				+ "last-statistics";
	}

}
