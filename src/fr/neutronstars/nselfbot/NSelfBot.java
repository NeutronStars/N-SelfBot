package fr.neutronstars.nselfbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import fr.neutronstars.nselfbot.command.CommandMap;
import fr.neutronstars.nselfbot.entity.ConsoleEntity;
import fr.neutronstars.nselfbot.listener.SelfBotListener;
import fr.neutronstars.nselfbot.logger.NSelfBotLogger;
import fr.neutronstars.nselfbot.plugin.PluginManager;
import fr.neutronstars.nselfbot.runnable.NSelfBotRunnable;
import fr.neutronstars.nselfbot.runnable.TaskManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;

/**
 * Main Class.
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */

public final class NSelfBot implements Runnable{

	private static NSelfBot nBot;
	
	/**
	 * Retrieves the instance of the class NSelfBot.
	 * @return NSelfBot
	 * @since 1.0.0
	 */
	public static NSelfBot getNSelfBot() {
		return nBot;
	}

	private final ConsoleEntity consoleEntity = new ConsoleEntity();
	private final Thread thread = new Thread(this, "nselfbot");
	private final Scanner scanner = new Scanner(System.in);
	private final Random random = new Random();
	private final PluginManager pluginManager;
	private final String version = "1.0.0";
	private final CommandMap commandMap;
	private final JDA jda;
	
	/**
	 * Tasks List
	 * @since 1.0.0
	 */
	private final TaskManager tasks = new TaskManager();
	
	private boolean running;
	
	private NSelfBot(String token, String tag) throws Exception{
		commandMap = new CommandMap(tag);
		pluginManager = new PluginManager(commandMap);
		jda = new JDABuilder(AccountType.CLIENT).setToken(token).buildAsync();
		jda.addEventListener(new SelfBotListener(commandMap));
	}
	
	/**
	 * Retrieves the instance of the class JDA.
	 * @return {@link JDA}
	 * @since 1.0.0
	 */
	public JDA getJDA() {
		return jda;
	}

	/**
	 * Get API version 
	 * @return version
	 * @since 1.0.0
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Get the Console.
	 * @return {@link ConsoleEntity}
	 * @since 1.0.0
	 */
	public ConsoleEntity getConsoleEntity() {
		return consoleEntity;
	}
	
	/**
	 * Retrieves the instance of the class PluginManager.
	 * @return {@link PluginManager}
	 * @since 1.0.0
	 */
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	/**
	 * Retrieves the tag of command.
	 * @return String
	 * @since 1.0.0
	 */
	public String getCommandTag() {
		return commandMap.getTag();
	}
	
	/**
	 * The nextInt of the class {@link Random}
	 * @param index
	 * @return Integer
	 * @since 1.0.0
	 */
	public int nextInt(int index){
		return random.nextInt(index);
	}
	
	/**
	 * Start the application.
	 * @since 1.0.0
	 */
	public void start(){
		if(running) return;
		running = true;
		thread.start();
		tasks.start();
	}
	
	/**
	 * Stop the application.
	 * @since 1.0.0
	 */
	public void stop(){
		if(!running) return;
		running = false;
		tasks.stop();
	}
	
	/**
	 * Register NSelfBotRunnable
	 * @param runnable
	 * @since 1.0.0
	 */
	public void registerRunnable(NSelfBotRunnable runnable){
		tasks.registerRunnable(runnable);
	}
	
	/**
	 * Run task
	 * @param runnable
	 * @since 1.0.0
	 */
	public void runTask(NSelfBotRunnable runnable){
		tasks.runTask(runnable);
	}
	
	
	public void run() {
		while (running){
			if(scanner.hasNextLine()){
				String[] commands = scanner.nextLine().replace("\\|", "&SPLITNSELFBOT&").split("&SPLITNSELFBOT&");
				for(int i = 0; i < commands.length; i++){
					String command = commands[i];
					while(command.startsWith(" ")) command = command.replaceFirst(" ", "");
					commandMap.onCommand(consoleEntity, command, null);
				}
			}
		}
		
		consoleEntity.sendMessage("Stopping a selfbot...");
		pluginManager.disablePlugins();
		jda.shutdown(false);
		consoleEntity.sendMessage("SelfBot stopped.");
		NSelfBotLogger.getLogger().close();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		NSelfBotLogger logger = NSelfBotLogger.getLogger();
		try{
			File folder = new File("config");
			if(!folder.exists()) folder.mkdirs();
			File file = new File(folder, "info.txt");
			if(!file.exists() && args.length < 1){
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write("token=Insert your token\n");
				writer.write("tag=selfbot.\n");
				writer.flush();
				writer.close();
				logger.log("Please, complete the file \"info.txt\" in the folder \"config\".");
				return;
			}
			if(args.length < 1){
				args = new String[2];
				BufferedReader reader = new BufferedReader(new FileReader(file));
				while (reader.ready()){
					final String line = reader.readLine();
					if(line.length() < 2) continue;
					final String[] option = line.split("=");
					if(option.length == 1 && option[0].length() == 0){
						reader.close();
						logger.logThrowable(new IllegalArgumentException("error to the file \"info.txt\"."));
						return;
					}
					switch (option[0]){
						case "token": args[0] = line.replaceFirst("token=", ""); break;
						case "tag": args[1] = line.replaceFirst("tag=", ""); break;
					}
				}
				reader.close();
			}
			
			nBot = new NSelfBot(args[0], args.length < 2 ? "selfbot." : args[1]);
			nBot.pluginManager.loadPlugins();
		}catch(Exception exception){
			logger.logThrowable(exception);
		}
	}
}
