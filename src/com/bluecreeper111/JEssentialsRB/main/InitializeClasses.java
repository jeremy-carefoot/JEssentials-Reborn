package com.bluecreeper111.JEssentialsRB.main;

import com.bluecreeper111.JEssentialsRB.commands.Afk;
import com.bluecreeper111.JEssentialsRB.commands.Back;
import com.bluecreeper111.JEssentialsRB.commands.Broadcast;
import com.bluecreeper111.JEssentialsRB.commands.ChatClear;
import com.bluecreeper111.JEssentialsRB.commands.Clear;
import com.bluecreeper111.JEssentialsRB.commands.DelHome;
import com.bluecreeper111.JEssentialsRB.commands.Enchant;
import com.bluecreeper111.JEssentialsRB.commands.Enderchest;
import com.bluecreeper111.JEssentialsRB.commands.Exp;
import com.bluecreeper111.JEssentialsRB.commands.Feed;
import com.bluecreeper111.JEssentialsRB.commands.Fly;
import com.bluecreeper111.JEssentialsRB.commands.Gamemode;
import com.bluecreeper111.JEssentialsRB.commands.God;
import com.bluecreeper111.JEssentialsRB.commands.Hat;
import com.bluecreeper111.JEssentialsRB.commands.Heal;
import com.bluecreeper111.JEssentialsRB.commands.Home;
import com.bluecreeper111.JEssentialsRB.commands.Item;
import com.bluecreeper111.JEssentialsRB.commands.JEReborn;
import com.bluecreeper111.JEssentialsRB.commands.JHelp;
import com.bluecreeper111.JEssentialsRB.commands.Kit;
import com.bluecreeper111.JEssentialsRB.commands.Me;
import com.bluecreeper111.JEssentialsRB.commands.More;
import com.bluecreeper111.JEssentialsRB.commands.Motd;
import com.bluecreeper111.JEssentialsRB.commands.Repair;
import com.bluecreeper111.JEssentialsRB.commands.SetHome;
import com.bluecreeper111.JEssentialsRB.commands.SetSpawn;
import com.bluecreeper111.JEssentialsRB.commands.SetWorldSpawn;
import com.bluecreeper111.JEssentialsRB.commands.Skull;
import com.bluecreeper111.JEssentialsRB.commands.Spawn;
import com.bluecreeper111.JEssentialsRB.commands.Speed;
import com.bluecreeper111.JEssentialsRB.commands.Sudo;
import com.bluecreeper111.JEssentialsRB.commands.Time;
import com.bluecreeper111.JEssentialsRB.commands.Weather;
import com.bluecreeper111.JEssentialsRB.commands.Workbench;
import com.bluecreeper111.JEssentialsRB.tabcompleters.ChatClearTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.DelHomeTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.EnchantTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.ExpTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.GamemodeTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.HatTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.HomeTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.JERebornTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.JHelpTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.MotdTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.RepairTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.SetSpawnTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.SpeedTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.TimeTC;
import com.bluecreeper111.JEssentialsRB.tabcompleters.WeatherTC;

public class InitializeClasses {
	
	
	// Class and methods are dedicated to creating instances of classes for initialization
	// Methods are called in Main class onEnable()
	
	public final static void initializeCommandClasses() {
		new Heal();
		new Feed();
		new Clear();
		new JHelp();
		new Back();
		new Fly();
		new Broadcast();
		new ChatClear();
		new Enderchest();
		new God();
		new Hat();
		new Workbench();
		new JEReborn();
		new Weather();
		new Time();
		new Gamemode();
		new Sudo();
		new Speed();
		new Skull();
		new More();
		new Me();
		new Motd();
		new Exp();
		new Enchant();
		new Spawn();
		new SetSpawn();
		new SetWorldSpawn();
		new SetHome();
		new DelHome();
		new Home();
		new Item();
		new Afk();
		new Repair();
		new Kit();
	}
	
	public final static void initializeTabCompletion() {
		new ChatClearTC();
		new JHelpTC();
		new HatTC();
		new JERebornTC();
		new WeatherTC();
		new TimeTC();
		new GamemodeTC();
		new SpeedTC();
		new MotdTC();
		new ExpTC();
		new EnchantTC();
		new SetSpawnTC();
		new DelHomeTC();
		new HomeTC();
		new RepairTC();
		// KitTC is initialized in KitModule class (so it can have a KitModule instance)
	}
	
}
