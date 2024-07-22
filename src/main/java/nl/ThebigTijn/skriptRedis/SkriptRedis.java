package nl.ThebigTijn.skriptRedis;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.SkriptAddon;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPooled;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;


public final class SkriptRedis extends JavaPlugin {

	public final static Logger LOGGER = Bukkit.getLogger();

	@Getter
	private FileConfiguration config;
	@Getter
	public static JedisPooled Jedis;

	@Override
	public void onEnable() {

		// Load config
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		File configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			saveResource("config.yml", false);
		}
		config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/config.yml"));
		String host = config.getString("host");
		int port = config.getInt("port");
		boolean debug = config.getBoolean("debug");

		// debug
		if (debug) {
			getLogger().info("Debug mode is enabled");
		}

		// Connect to Redis server
		Jedis = new JedisPooled(host, port);
		if (debug) {
			getLogger().info("Host: " + host);
			getLogger().info("Port: " + port);
		}
		verifyRedis();

		// Register Skript Addon
		try {
			SkriptAddon addonInstance = Skript.registerAddon(this);
			addonInstance.loadClasses("nl.ThebigTijn.skriptRedis", "skript");
		} catch (SkriptAPIException e) {
			error("Skript-Redis loaded after Skript has already finished registering addons.");
			Bukkit.getPluginManager().disablePlugin(this);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {
		Jedis.close();
	}

	public void saveConfig(FileConfiguration config, String name) {
		File configFile = new File(getDataFolder() + "/" + name);
		try {
			if (!configFile.exists()) {
				getDataFolder().mkdirs();
				configFile.createNewFile();
			}
			config.save(configFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void error(String error) {
		LOGGER.severe("[Skript-Redis] " + error);
	}

	private void verifyRedis() {
		String state = Jedis.ping();
		if (Objects.equals(state, "PONG")) {
			getLogger().info("Connected to Redis server");
		} else {
			error("Failed to connect to Redis server");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
}



