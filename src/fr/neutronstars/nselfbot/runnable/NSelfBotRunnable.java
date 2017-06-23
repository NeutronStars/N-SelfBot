package fr.neutronstars.nselfbot.runnable;

import fr.neutronstars.nselfbot.NSelfBot;

/**
 * Class Runnable.
 * 1 Ticks = 1/10s
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class NSelfBotRunnable implements Runnable{

	private final long ticksUpdate;
	private boolean running, delete;
	private long ticks;
	
	public NSelfBotRunnable(long ticks){
		ticksUpdate = ticks;
		NSelfBot.getNSelfBot().registerRunnable(this);
	}
	
	public final boolean isRunning() {
		return running;
	}
	
	public final boolean canUpdate(){
		return isRunning() && ticks >= ticksUpdate;
	}
	
	public final void update(){
		if(canUpdate()){
			ticks = 0;
			run();
		}
	}
	
	public final void addTicks(){
		if(isRunning()) ticks++;
	}
	
	public final void start(){
		running = true;
	}
	
	public final void stop(){
		running = false;
	}
	
	public final void cancel(){
		stop();
		close();
	}
	
	public final boolean isClosed(){
		return delete;
	}
	
	public final void close(){
		delete = true;
	}
}
