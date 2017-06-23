package fr.neutronstars.nselfbot.command;

import java.lang.reflect.Method;

import fr.neutronstars.nselfbot.command.Command.Executor;

/**
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SimpleCommand {

	private final String name;
	private final Executor executor;
	private final String description;
	private final CommandManager commandManager;
	private final String[] alias;
	private final Method method;
	
	public SimpleCommand(String name, Executor executor, String description,CommandManager commandManager,String[] alias, Method method){
		this.name = name;
		this.executor = executor;
		this.description = description;
		this.commandManager = commandManager;
		this.alias = alias;
		this.method = method;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Executor getExecutor() {
		return executor;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public boolean hasAlias(){
		return alias.length > 0;
	}
	
	public String[] getAlias() {
		return alias;
	}
	
	public String getAliasString(){
		if(!hasAlias()) return "";
		StringBuilder builder = new StringBuilder();
		for(String alias : this.alias){
			if(builder.length() > 0) builder.append(", ");
			builder.append(alias);
		}
		return builder.toString();
	}
	
	public Method getMethod() {
		return method;
	}
}
