package nl.ThebigTijn.skriptRedis.components.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import nl.ThebigTijn.skriptRedis.components.RedisAPI;
import org.bukkit.event.Event;

import javax.annotation.Nullable;


public class ExprRedisobject extends SimpleExpression<String> {

	private Expression<String> keyExpr;

	static {
		Skript.registerExpression(ExprRedisobject.class, String.class, ExpressionType.PROPERTY, "[the] redis object %string%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
		keyExpr = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return keyExpr.toString(event, debug);
	}

	@Override
	@Nullable
	protected String[] get(Event event) {
		String key = keyExpr.getSingle(event);
		if (key == null) {
			return null;
		}
		String value = RedisAPI.get(key);
		return new String[]{value};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		String key = keyExpr.getSingle(e);
		if (key == null) {
			return;
		}
		switch (mode) {
			case SET:
				if (delta[0] instanceof String) {
					String value = (String) delta[0];
					RedisAPI.set(key, value);
				}
				break;
			case DELETE:
				RedisAPI.delete(key);
				break;
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}
}

