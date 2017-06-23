package fr.neutronstars.nselfbot.entity;

import fr.neutronstars.nselfbot.NSelfBot;
import fr.neutronstars.nselfbot.logger.NSelfBotLogger;
import net.dv8tion.jda.core.JDA;

/**
 * The console entity.
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ConsoleEntity implements Entity, CommandSender{
	
	private final NSelfBotLogger logger = NSelfBotLogger.getLogger();
	
	public ConsoleEntity(){}

	public String getName() {
		return "Server";
	}

	public JDA getJDA() {
		return NSelfBot.getNSelfBot().getJDA();
	}
	
	public void sendMessage(String message){
		logger.log(message);
	}
	
	public void sendMessage(String format, Object... args) {
		sendMessage(String.format(format, args));
	}

	public String getAsMention() {
		return getName();
	}
	
	public boolean isConsoleEntity() {
		return true;
	}
}
