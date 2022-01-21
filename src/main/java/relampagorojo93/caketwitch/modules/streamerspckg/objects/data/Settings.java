package relampagorojo93.caketwitch.modules.streamerspckg.objects.data;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;

public class Settings {

    private Streamer streamer;
    private boolean isauthorizedstreamer;
    private String configuration = "";
    private boolean detectsubscriptions = false, detectbits = false, detectraids = false, detectchannelpoints = false;
    private boolean detectfollowers = false;
    private boolean shownormalmessages = false, showsubscribermessages = false, showhighlightedmessages = false,
        showritualmessages = false;

    public Settings(Streamer streamer) {
        this.streamer = streamer;
    }

    public void importSettings(Settings newsettings) {
        this.isauthorizedstreamer = newsettings.isauthorizedstreamer;
        this.configuration = newsettings.configuration;
        this.detectsubscriptions = newsettings.detectsubscriptions;
        this.detectbits = newsettings.detectbits;
        this.detectraids = newsettings.detectraids;
        this.detectchannelpoints = newsettings.detectchannelpoints;
        this.detectfollowers = newsettings.detectfollowers;
        this.shownormalmessages = newsettings.shownormalmessages;
        this.showsubscribermessages = newsettings.showsubscribermessages;
        this.showhighlightedmessages = newsettings.showhighlightedmessages;
        this.showritualmessages = newsettings.showritualmessages;
    }

    public boolean isAuthorizedStreamer() {
        return isauthorizedstreamer;
    }

    public boolean setIsAuthorizedStreamer(boolean isauthorizedstreamer) {
        return this.setIsAuthorizedStreamer(isauthorizedstreamer, true);
    }

    public boolean setIsAuthorizedStreamer(boolean isauthorizedstreamer, boolean sqlupdate) {
        this.isauthorizedstreamer = isauthorizedstreamer;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setIsAuthorizedStreamer(this.streamer.getUniqueID(), isauthorizedstreamer);
		}
		else {
			return true;
		}
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public boolean setConfiguration(String configuration) {
        return this.setConfiguration(configuration, true);
    }

    public boolean setConfiguration(String configuration, boolean sqlupdate) {
        this.configuration = configuration;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setTwitchConfiguration(this.streamer.getUniqueID(), configuration);
		}
		else {
			return true;
		}
    }

    public boolean detectSubscriptions() {
        return this.detectsubscriptions;
    }

    public boolean setDetectSubscriptions(boolean detectsubscriptions) {
        return this.setDetectSubscriptions(detectsubscriptions, true);
    }

    public boolean setDetectSubscriptions(boolean detectsubscriptions, boolean sqlupdate) {
        this.detectsubscriptions = detectsubscriptions;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setDetectTwitchSubscriptions(this.streamer.getUniqueID(),
				detectsubscriptions);
		}
		else {
			return true;
		}
    }

    public boolean detectBits() {
        return this.detectbits;
    }

    public boolean setDetectBits(boolean detectbits) {
        return this.setDetectBits(detectbits, true);
    }

    public boolean setDetectBits(boolean detectbits, boolean sqlupdate) {
        this.detectbits = detectbits;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setDetectTwitchBits(this.streamer.getUniqueID(), detectbits);
		}
		else {
			return true;
		}
    }

    public boolean detectRaids() {
        return this.detectraids;
    }

    public boolean setDetectRaids(boolean detectraids) {
        return this.setDetectRaids(detectraids, true);
    }

    public boolean setDetectRaids(boolean detectraids, boolean sqlupdate) {
        this.detectraids = detectraids;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setDetectTwitchRaids(this.streamer.getUniqueID(), detectraids);
		}
		else {
			return true;
		}
    }

    public boolean detectChannelPoints() {
        return this.detectchannelpoints;
    }

    public boolean setDetectChannelPoints(boolean detectchannelpoints) {
        return this.setDetectChannelPoints(detectchannelpoints, true);
    }

    public boolean setDetectChannelPoints(boolean detectchannelpoints, boolean sqlupdate) {
        this.detectchannelpoints = detectchannelpoints;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setDetectTwitchChannelPoints(this.streamer.getUniqueID(),
				detectchannelpoints);
		}
		else {
			return true;
		}
    }

    public boolean detectFollowers() {
        return this.detectfollowers;
    }

    public boolean setDetectFollowers(boolean detectfollowers) {
        return this.setDetectFollowers(detectfollowers, true);
    }

    public boolean setDetectFollowers(boolean detectfollowers, boolean sqlupdate) {
        this.detectfollowers = detectfollowers;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setDetectTwitchFollowers(this.streamer.getUniqueID(), detectfollowers);
		}
		else {
			return true;
		}
    }

    public boolean showNormalMessages() {
        return this.shownormalmessages;
    }

    public boolean setShowNormalMessages(boolean shownormalmessages) {
        return this.setShowNormalMessages(shownormalmessages, true);
    }

    public boolean setShowNormalMessages(boolean shownormalmessages, boolean sqlupdate) {
        this.shownormalmessages = shownormalmessages;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setShowNormalMessages(this.streamer.getUniqueID(), shownormalmessages);
		}
		else {
			return true;
		}
    }

    public boolean showSubscriberMessages() {
        return this.showsubscribermessages;
    }

    public boolean setShowSubscriberMessages(boolean showsubscribermessages) {
        return this.setShowSubscriberMessages(showsubscribermessages, true);
    }

    public boolean setShowSubscriberMessages(boolean showsubscribermessages, boolean sqlupdate) {
        this.showsubscribermessages = showsubscribermessages;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setShowSubscriberMessages(this.streamer.getUniqueID(),
				showsubscribermessages);
		}
		else {
			return true;
		}
    }

    public boolean showHighlightedMessages() {
        return this.showhighlightedmessages;
    }

    public boolean setShowHighlightedMessages(boolean showhighlightedmessages) {
        return this.setShowHighlightedMessages(showhighlightedmessages, true);
    }

    public boolean setShowHighlightedMessages(boolean showhighlightedmessages, boolean sqlupdate) {
        this.showhighlightedmessages = showhighlightedmessages;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setShowHighlightedMessages(this.streamer.getUniqueID(),
				showhighlightedmessages);
		}
		else {
			return true;
		}
    }

    public boolean showRitualMessages() {
        return this.showritualmessages;
    }

    public boolean setShowRitualMessages(boolean showritualmessages) {
        return this.setShowRitualMessages(showritualmessages, true);
    }

    public boolean setShowRitualMessages(boolean showritualmessages, boolean sqlupdate) {
        this.showritualmessages = showritualmessages;
		if (sqlupdate) {
			return CakeTwitchAPI.getSQL().setShowRitualMessages(this.streamer.getUniqueID(), showritualmessages);
		}
		else {
			return true;
		}
    }
}
