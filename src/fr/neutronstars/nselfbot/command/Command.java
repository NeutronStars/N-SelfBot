package fr.neutronstars.nselfbot.command;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */

@Documented
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

	/**
	 * Name of the Command
	 * @return String
	 * @since 1.0.0
	 */
	public String name();
	
	/**
	 * Description of the Command
	 * @return String
	 * @since 1.0.0
	 */
	public String description() default "hasn't description.";
	
	/**
	 * Executor type of the Command
	 * @return Executor
	 * @since 1.0.0
	 */
	public Executor type() default Executor.ALL;
	
	/**
	 * Alias of the Command
	 * @return String[]
	 * @since 1.0.0
	 */
	public String[] alias() default {};
	
	/**
	 * Who can execute command
	 * @author NeutronStars
	 * @version 1.0.0
	 * @since 1.0.0
	 */
	public enum Executor{
		USER, CONSOLE, ALL;
	}
}
