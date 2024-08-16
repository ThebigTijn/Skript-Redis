package nl.ThebigTijn.skriptRedis.components;

import nl.ThebigTijn.skriptRedis.SkriptRedis;

import java.util.Set;

public class RedisAPI {

	public static void set(String key, String value) {
		SkriptRedis.getJedis().set(key, value);
	}

	public static Object get(String key) {
		return SkriptRedis.getJedis().get(key);
	}

	public static void delete(String key) {
		SkriptRedis.getJedis().del(key);
	}

	public static Set<String> keys(String pattern) {
		return SkriptRedis.getJedis().keys(pattern);
	}


}
