package fr.neutronstars.nselfbot.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */

final class NSelfBotClassLoader extends URLClassLoader{
	
	private NSelfBotPlugin plugin;
	
	protected NSelfBotClassLoader(String main, ClassLoader parent, File file) throws Exception{
		super(new URL[]{file.toURI().toURL()}, parent);
		Class<?> clazz = Class.forName(main, true, this);
		plugin = clazz.asSubclass(NSelfBotPlugin.class).newInstance();
		plugin.setPluginClassLoader(this);
	}
	
	public NSelfBotPlugin getPlugin() {
		return plugin;
	}
}
