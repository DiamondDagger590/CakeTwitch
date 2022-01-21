package relampagorojo93.caketwitch.spigotcommands.objects;

import org.bukkit.command.CommandSender;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotmessages.instances.ClickEvent;
import relampagorojo93.caketwitch.spigotmessages.instances.TextReplacement;
import relampagorojo93.caketwitch.spigotmessages.instances.TextResult;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends SubCommand {

    public ListProvider header, body, footer;
    public StringProvider unavailableleftarrow, unavailablerightarrow, availableleftarrow, availablerightarrow, errornumbers;

    public HelpCommand(Command command, String name, String permission, String description, String usage, List<String> aliases) {
        super(command, "help", name, permission, description, usage, aliases);
        this.header = this.body = this.footer = () -> new ArrayList<>();
        this.unavailableleftarrow = this.unavailablerightarrow = this.availableleftarrow = this.availablerightarrow = errornumbers = () -> "";
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                List<SubCommand> available = new ArrayList<>();
				for (SubCommand command : cmd.getCommands()) {
					if (command.getPermission().isEmpty() || sender.hasPermission(command.getPermission())) {
						available.add(command);
					}
				}
                int max = (int) (((double) available.size() / 4D) + 0.99D);
				for (int i = 0; i < max; i++) {
					list.add(String.valueOf(i + 1));
				}
                break;
            default:
                break;

        }
        return list;
    }

    public HelpCommand setHeader(ListProvider header) {
        this.header = header;
        return this;
    }

    public HelpCommand setBody(ListProvider body) {
        this.body = body;
        return this;
    }

    public HelpCommand setFooter(ListProvider footer) {
        this.footer = footer;
        return this;
    }

    public HelpCommand setUnavailableLeftArrow(StringProvider unavailableleftarrow) {
        this.unavailableleftarrow = unavailableleftarrow;
        return this;
    }

    public HelpCommand setUnavailableRightArrow(StringProvider unavailablerightarrow) {
        this.unavailablerightarrow = unavailablerightarrow;
        return this;
    }

    public HelpCommand setAvailableLeftArrow(StringProvider availableleftarrow) {
        this.availableleftarrow = availableleftarrow;
        return this;
    }

    public HelpCommand setAvailableRightArrow(StringProvider availablerightarrow) {
        this.availablerightarrow = availablerightarrow;
        return this;
    }

    public HelpCommand setErrorNumbers(StringProvider errornumbers) {
        this.errornumbers = errornumbers;
        return this;
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        try {
            args[0] = args[0].toLowerCase();
            int page = args.length > 1 && (args[0].equalsIgnoreCase(getCommand()) || getAliases().contains(args[0]))
                           ? Integer.parseInt(args[1])
                           : 1;
            List<SubCommand> available = new ArrayList<>();
			for (SubCommand command : cmd.getCommands()) {
				if (command.getPermission().isEmpty() || sender.hasPermission(command.getPermission())) {
					available.add(command);
				}
			}
            int max = (int) (((double) available.size() / 4D) + 0.99D);
			if (page > max) {
				page = max;
			}
			for (String s : header.getList()) {
				applyHelpHeaderFootPlaceholders(s, this, page, max).sendMessage(sender);
			}
			for (int i = 4 * (page - 1); i < 4 * page && i < available.size(); i++) {
				if (i < available.size()) {
					for (String s : body.getList()) {
						applyHelpBodyPlaceholders(s, available.get(i)).sendMessage(sender);
					}
				}
			}
			for (String s : footer.getList()) {
				applyHelpHeaderFootPlaceholders(s, this, page, max).sendMessage(sender);
			}
        }
        catch (NumberFormatException e) {
            MessagesUtils.getMessageBuilder().createMessage(errornumbers.getString()).sendMessage(sender);
        }
        return true;
    }

    public TextResult applyHelpHeaderFootPlaceholders(String message, SubCommand scmd, int currentpage, int maxpage) {
        return MessagesUtils.getMessageBuilder()
            .createMessage(
                new TextReplacement[]{
                    new TextReplacement("%current_page%", () -> String.valueOf(currentpage)),
                    new TextReplacement("%max_page%", () -> String.valueOf(maxpage)),
                    new TextReplacement("%left_arrow%",
                        () -> (currentpage > 1 ? availableleftarrow
                                   : unavailableleftarrow).getString(),
                        (currentpage > 1
                             ? new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                            "/" + scmd.getCommandPath() + " " + (currentpage - 1))
                             : null)),
                    new TextReplacement("%right_arrow%",
                        () -> (currentpage < maxpage ? availablerightarrow
                                   : unavailablerightarrow).getString(),
                        (currentpage < maxpage
                             ? new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                            "/" + scmd.getCommandPath() + " " + (currentpage + 1))
                             : null))},
                true, message);
    }

    public TextResult applyHelpBodyPlaceholders(String message, SubCommand cmd) {
        return MessagesUtils.getMessageBuilder()
            .createMessage(
                new TextReplacement[]{
                    new TextReplacement("%command_usage%", () -> cmd.getUsage(),
                        new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd.getUsage())),
                    new TextReplacement("%command_description%", () -> cmd.getDescription()),
                    new TextReplacement("%command_name%", () -> cmd.getCommand()),
                    new TextReplacement("%command_permission%", () -> cmd.getPermission())},
                true, message);
    }

    public interface StringProvider {
        String getString();
    }

    public interface ListProvider {
        List<String> getList();
    }
}
