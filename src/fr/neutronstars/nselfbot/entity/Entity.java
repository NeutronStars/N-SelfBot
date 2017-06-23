package fr.neutronstars.nselfbot.entity;

import net.dv8tion.jda.core.JDA;

/**
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Entity {

	public String getName();
	
	public JDA getJDA();
	
	public void sendMessage(String text);

	public void sendMessage(String format, Object... args);
	
	public String getAsMention();
}
