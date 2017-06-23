package fr.neutronstars.nselfbot.listener;

import fr.neutronstars.nselfbot.NSelfBot;
import fr.neutronstars.nselfbot.command.CommandMap;
import fr.neutronstars.nselfbot.entity.Message;
import fr.neutronstars.nselfbot.entity.User;
import fr.neutronstars.nselfbot.logger.NSelfBotLogger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.impl.GuildImpl;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

/**
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */
public class SelfBotListener implements EventListener{

	private final NSelfBotLogger logger = NSelfBotLogger.getLogger();
	private final CommandMap commandMap;
	
	public SelfBotListener(CommandMap commandMap){
		this.commandMap = commandMap;
	}
	
	public void onEvent(Event event) {
		if(event instanceof ReadyEvent) readyEvent((ReadyEvent)event);
		if(event instanceof MessageReceivedEvent) messageReceivedEvent((MessageReceivedEvent)event);
	}

	public void messageReceivedEvent(MessageReceivedEvent event){
		if(!event.getAuthor().equals(event.getJDA().getSelfUser())) return;
		String msg = event.getMessage().getContent();
		if(msg.startsWith(commandMap.getTag())){
			Message message = new Message(event.getMessage());
			User userEntity = new User(event.getAuthor());
			msg = msg.replaceFirst(commandMap.getTag(), "");
			String[] commands = msg.replace("\\|", "&SPLITNSELFBOT&").split("&SPLITNSELFBOT&");
			boolean execute = false;
			for(int i = 0; i < commands.length; i++){
				String command = commands[i];
				while (command.startsWith(" ")) command = command.replaceFirst(" ", "");
				if(commandMap.onCommand(userEntity, command, message) && !execute) execute = true;
			}
			if(execute && !message.isDelete()) message.delete();
			return;
		}
	}
	
	private void readyEvent(ReadyEvent event) {
		NSelfBot.getNSelfBot().start();
		JDA jda = event.getJDA();
		StringBuilder builder = new StringBuilder("\n========================================\n")
		.append(jda.getSelfUser().getName()).append(" is ready.\n========================================\nGuilds :");
		jda.getGuilds().forEach(g-> builder.append("\n     -> ").append(g.getName()));
		builder.append("\n========================================");
		logger.log(builder.toString());
	}

}
