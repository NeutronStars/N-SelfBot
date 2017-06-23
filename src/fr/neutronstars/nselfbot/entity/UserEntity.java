package fr.neutronstars.nselfbot.entity;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;

/**
 * User Discord.
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UserEntity extends DiscordEntity, CommandSender{
	
	public String getDiscriminator();

	public String getAvatarId();

	public String getAvatarUrl();

	public String getDefaultAvatarId();

	public String getDefaultAvatarUrl();

	public String getEffectiveAvatarUrl();

	List<Guild> getMutualGuilds();

	ChannelEntity getPrivateChannel();

	public default boolean isUserEntity() {
		return true;
	}

}
