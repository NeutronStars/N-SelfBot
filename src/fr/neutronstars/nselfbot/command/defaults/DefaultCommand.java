package fr.neutronstars.nselfbot.command.defaults;

import java.awt.Color;

import fr.neutronstars.nselfbot.NSelfBot;
import fr.neutronstars.nselfbot.command.Command;
import fr.neutronstars.nselfbot.command.Command.Executor;
import fr.neutronstars.nselfbot.command.CommandManager;
import fr.neutronstars.nselfbot.command.CommandMap;
import fr.neutronstars.nselfbot.command.SimpleCommand;
import fr.neutronstars.nselfbot.entity.Channel;
import fr.neutronstars.nselfbot.entity.CommandSender;
import fr.neutronstars.nselfbot.entity.ConsoleEntity;
import fr.neutronstars.nselfbot.entity.UserEntity;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

/**
 * Default Command of NSelfBot
 * @author NeutronStars
 * @version 1.1.0
 * @since 1.0.0
 */
public final class DefaultCommand implements CommandManager {

	private final CommandMap commandMap;
	
	public DefaultCommand(CommandMap commandMap){
		this.commandMap = commandMap;
	}
	
	/**
	 * Help Command.
	 * @param sender
	 * @param channel
	 * @since 1.0.0
	 */
	@Command(name="help",description="Shows the command list.",alias={"?"})
	private void help(CommandSender sender, Channel channel){
		if(sender instanceof ConsoleEntity) helpConsole(sender.getConsoleEntity());
		else helpUser(sender.getUserEntity(), channel);
	}
	
	private void helpConsole(ConsoleEntity sender){
		StringBuilder builder = new StringBuilder("\n========================================").append("\nCommands list :\n\n");
		for(SimpleCommand command : commandMap.getCommands()){
			if(command.getExecutor() == Executor.USER) continue;	
			builder.append("\n-> ").append(command.getName());
			if(command.hasAlias()) builder.append("\n     aliases -> ").append(command.getAliasString());
			builder.append("\n     description -> ").append(command.getDescription());
		}
		builder.append("\n========================================");
		sender.sendMessage(builder.toString());
	}
	
	private void helpUser(UserEntity user, Channel channel){
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(user.getName(), null, user.getAvatarUrl()+"?size=256");
		builder.setTitle("Commands list");
		builder.setDescription("Prefix command -> `"+commandMap.getTag()+"`");
		builder.setColor(Color.CYAN);
		for(SimpleCommand command : commandMap.getCommands()){
			if(command.getExecutor() == Executor.CONSOLE) continue;
			builder.addField(new Field(command.getName(), (command.hasAlias() ? "[>](1) Alias : "+command.getAliasString()+"\n" : "")+"[>](2) Description : "+command.getDescription(), false));
		}

		try {
			channel.sendMessage(builder.build());
		}catch(Exception e){
			channel.sendMessage(e.getMessage());
		}
	}

	/**
	 * Stop Command.
	 * @since 1.0.0
	 */
	@Command(name="stop",type=Executor.CONSOLE,description="Stop the selfbot.")
	private void stop(){
		NSelfBot.getNSelfBot().stop();
	}
	
	@Command(name="info",description="Info NSelfBot.")
	private void info(CommandSender sender, Channel channel){
		String info = "\nInfo NSelfBot: \n  -Created by NeutronStars"
				 	 +"\nVersion: "+ NSelfBot.getNSelfBot().getVersion();
		if(sender.isConsoleEntity())
			sender.getConsoleEntity().sendMessage("\n========================================"
												 + info
												 +"\n========================================");
		else channel.sendMessage("```yml\n"+info+"```");
		
			
	}
}
