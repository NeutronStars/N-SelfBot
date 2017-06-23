package fr.neutronstars.nselfbot.entity;

/**
 * Entities who can execute commands.
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CommandSender {
	
	public String getName();
	
	public default boolean isConsoleEntity(){
		return false;
	}
	
	public default boolean isUserEntity(){
		return false;
	}
	
	public default ConsoleEntity getConsoleEntity(){
		return isConsoleEntity() ? (ConsoleEntity) this : null;
	}
	
	public default UserEntity getUserEntity(){
		return isUserEntity() ? (UserEntity) this : null;
	}
}