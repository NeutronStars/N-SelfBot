package fr.neutronstars.nselfbot.plugin;

import java.io.IOException;

import fr.neutronstars.nselfbot.NSelfBot;
import fr.neutronstars.nselfbot.command.CommandManager;
import fr.neutronstars.nselfbot.logger.NSelfBotLogger;
import net.dv8tion.jda.core.JDA;

/**
 * Main class for the plugins.
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */

public abstract class NSelfBotPlugin {

	private NSelfBotClassLoader jdaClassLoader;
	private final String[] authors;
	private String name, version;
	
	public NSelfBotPlugin(String... authors){
		this.authors = authors;
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getVersion() {
		return version;
	}
	
	public final String[] getAuthors() {
		return authors;
	}
	
	public void setName(String name) {
		this.name = name != null ? name : "My_Plugin";
	}
	
	public void setVersion(String version) {
		this.version = version != null ? version : "1.0";
	}
	
	/**
	 * Register a command in the application.
	 * @param commandManager
	 * @since 1.0.0
	 */
	public final void registerCommand(CommandManager... commandManager){
		NSelfBot.getNSelfBot().getPluginManager().registerCommands(commandManager);
	}
	
	/**
	 * Retrieves the instance of the class JDA.
	 * @return JDA
	 * @since 1.0.0
	 */
	public final JDA getJDA(){
		return NSelfBot.getNSelfBot().getJDA();
	}
	
	/**
	 * Retrieves the instance of the class NSelfBotLogger.
	 * @return NSelfBotLogger
	 * @since 1.0.0
	 */
	public final NSelfBotLogger getLogger(){
		return NSelfBotLogger.getLogger();
	}
	
	/**
	 * Call when loading the plugin.
	 * @since 1.0.0
	 */
	public void onLoad(){}
	
	/**
	 * Call when disabling the plugin.
	 * @since 1.0.0
	 */
	public void onDisable(){}
	
	protected void setPluginClassLoader(NSelfBotClassLoader jdaClassLoader){
		this.jdaClassLoader = jdaClassLoader;
	}
	
	protected void close(){
		try {
			jdaClassLoader.close();
		} catch (IOException e) {
			getLogger().logThrowable(e);
		}
	}
}
