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
import fr.neutronstars.nselfbot.plugin.NSelfBotPlugin;
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
		if(sender.isConsoleEntity()) helpConsole(sender.getConsoleEntity());
		else helpUser(sender.getUserEntity(), channel);
	}

	@Command(name="plugins",description="Show the plugins list.",alias={"plgs"})
	private void plugins(CommandSender sender, Channel channel){
		if(sender.isConsoleEntity()){
			StringBuilder builder = new StringBuilder("Plugins list :");
			for(NSelfBotPlugin plugin : NSelfBot.getNSelfBot().getPluginManager().getPlugins()){
				builder.append("\n").append(plugin.getName()).append("\n   version : ").append(plugin.getVersion()).append("\n   author(s) : ").append(plugin.getAuthorsToString());
			}
			if(builder.length() < 15) builder.append("\nDon't use a plugin.");
			sender.getConsoleEntity().sendMessage(builder.toString());
			return;
		}
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Plugins list");
		for(NSelfBotPlugin plugin : NSelfBot.getNSelfBot().getPluginManager().getPlugins()){
			builder.addField(new Field(plugin.getName(), "[>](1) Version : "+plugin.getVersion()+"\n[>](2) Author(s) : "+plugin.getAuthorsToString(), true));
		}
		if(builder.getFields().size() == 0) builder.setDescription("Don't use a plugin.");
		builder.setFooter("Using API NSelfBot v"+NSelfBot.getNSelfBot().getVersion()+" created by NeutronStars", null);
		builder.setColor(Color.MAGENTA);
		try{
			channel.sendMessage(builder.build());
		}catch (Exception e){
			channel.sendMessage(e.getMessage());
		}
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
		if(sender.isConsoleEntity())
			sender.getConsoleEntity().sendMessage("\n========================================"
												 +"\nNSelfBot: \n  -Created by NeutronStars"
												 +"\nVersion: "+ NSelfBot.getNSelfBot().getVersion()
												 +"\nLink:\n  GitHub: https://github.com/NeutronStars/N-SelfBot"
												 +"\n========================================");
		else{
			EmbedBuilder builder = new EmbedBuilder();
			builder.setTitle("NSelfBot");
			builder.addField("Version", NSelfBot.getNSelfBot().getVersion(), true);
			builder.addField("GitHub", "[>](1) https://github.com/NeutronStars/N-SelfBot", true);
			builder.setFooter("Created by NeutronStars", null);
			builder.setColor(Color.BLUE);

			try {
				channel.sendMessage(builder.build());
			}catch (Exception e){
				channel.sendMessage(e.getMessage());
			}
		}
	}
}
