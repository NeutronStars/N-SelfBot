package fr.neutronstars.nselfbot.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.core.utils.SimpleLog;
import net.dv8tion.jda.core.utils.SimpleLog.LogListener;

/**
 * Console Logger
 * @author NeutronStars
 * @apiNote Origin N-Bot API
 * @version 1.0.0
 * @since 1.0
 */

public final class NSelfBotLogger implements LogListener{

	/**
	 * Main Logger
	 * @since 1.0.0
	 */
	private final static NSelfBotLogger LOGGER = newLogger();
	
	private static NSelfBotLogger newLogger(){
		try{
			return new NSelfBotLogger();
		}catch(IOException exception){}
		return null;
	}
	
	/**
	 * Get Logger
	 * @return {@link NSelfBotLogger}
	 * @since 1.0.0
	 */
	public static NSelfBotLogger getLogger() {
		return LOGGER;
	}
	
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private final String pattern = "[%1$s] %2$s : %3$s";
	private final BufferedWriter bufferedWriter;
	
	private NSelfBotLogger() throws IOException{
		File folder = new File("logs");
		if(!folder.exists()) folder.mkdirs();
		String time = nowTime();
		String fileName = time.split("-")[0].replace("/", "-");
		int number = 1;
		File file = null;
		do{
			file = new File(folder, fileName+"-"+number);
			number++;
		}while(file.exists());
		bufferedWriter = new BufferedWriter(new FileWriter(file));
		bufferedWriter.write(time);
		bufferedWriter.flush();
		SimpleLog.addListener(this);
	}
	
	/**
	 * Log info in the console.
	 * @param msg
	 * @since 1.0.0
	 */
	public void log(String msg){
		log(Level.INFO, msg);
	}
	
	/**
	 * Log info in the console but with level for indication.
	 * @param level
	 * @param msg
	 * @since 1.0.0
	 */
	public void log(Level level, String msg){
		log(level, msg, false);
	}
	
	private void log(Level level, String msg, boolean jda){
		if(level == Level.TRACE || level == Level.DEBUG) return;
		try{
			String line = String.format(pattern, new Object[]{nowTime(), level.toString(), msg});
			bufferedWriter.newLine();
			bufferedWriter.write(line);
			bufferedWriter.flush();
			if(!jda) System.out.println(line);
		}catch(IOException exception){
			exception.printStackTrace();
		}
	}
	
	/**
	 * Log a exception in the console.
	 * @param throwable
	 * @since 1.0.0
	 */
	public void logThrowable(Throwable throwable){
		logThrowable(throwable, false);
	}
	
	private void logThrowable(Throwable throwable, boolean jda){
		StringBuilder builder = new StringBuilder();
		builder.append(throwable);
        for (StackTraceElement traceElement : throwable.getStackTrace())
        	builder.append("\n\tat ").append(traceElement);
        log(Level.FATAL, builder.toString(), jda);
	}
	
	private String nowTime(){
		return simpleDateFormat.format(new Date());
	}

	public void onLog(SimpleLog log, SimpleLog.Level lvl, Object obj) {
		log(Level.valueOf(lvl.toString()), obj.toString(), true);
	}

	public void onError(SimpleLog log, Throwable throwable) {
		logThrowable(throwable, true);
	}
	
	public void close(){
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
