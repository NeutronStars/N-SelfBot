# __**[** *N-SelfBot API* **]**__

N-SelfBot API is an __*open-source project*__ using **JDA** which will allow you **create/personalize** your bots simplest through a plugin system.

For use it download the [latest release](https://github.com/NeutronStars/N-SelfBot/releases) and execute the following command `java -jar N-SelfBot-VERSION-withDependencies.jar` a **config** folder will appear who contains an **info.txt** file. Open it and insert your bot **Token**, now you can re-execute the previous command, folders are going to generate. When you want to stop the selfbot, just print `stop` in the console.

For create a **plugin**, add the **N-SelfBot API** on your project libraries, your main class will need to extends **NSelfBotPlugin** who contains `onLoad()` and `onDisable()` methods with `@Override` annotation.

```java
public class MyPlugin extends NSelfBotPlugin{

  @Override
  public void onLoad(){
     NSelfBotLogger.getLogger().log("MyPlugin is loaded.");
  }

  @Override
  public void onDisable(){
     NSelfBotLogger.getLogger().log("MyPlugin is disabled.");
  }
}
```

You can too create commands, create a class who implements **CommandManager**  and insert methods with **@Command** annotation like this.

```java
import fr.neutronstars.nselfbot.entity.User;
import fr.neutronstars.nselfbot.entity.Channel;
import fr.neutronstars.nselfbot.entity.Message;

public class MyCommand implements CommandManager{

  @Command(name="stop",description="Stop the SelfBot.",type=Executor.CONSOLE)
  public void onStop(){
     NSelfBot.getNSelfBot().getJDA().shutdown(false);
     System.exit(0);
  }
  @Command(name="info",description="Shows the bot informations.",type=Executor.USER)
  public void onInfo(User user, Channel channel, Message message){
      //Your Code.
  }
}
```

and register your command class in the `onLoad()` method like this.

```java
public class MyPlugin extends NSelfBotPlugin{

  @Override
  public void onLoad(){
     NSelfBotLogger.getLogger().log("MyPlugin is loaded.");
     super.registerCommand(new MyCommand());
  }
}
```

To ensure that your plugin is **valid** you will also have to add a **plugin.txt** at your root.

```
main=packages.MainClass
name=My Plugin
version=0.0.1-SNAPSHOT
```

Generate your `.jar` and put it in the **plugins** folder.
