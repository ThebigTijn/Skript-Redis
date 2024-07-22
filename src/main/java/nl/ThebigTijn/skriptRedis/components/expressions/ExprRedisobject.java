//package nl.ThebigTijn.skriptRedis.components.expressions;
//
//import ch.njol.skript.Skript;
//import ch.njol.skript.lang.Expression;
//import ch.njol.skript.lang.ExpressionType;
//import ch.njol.skript.lang.SkriptParser;
//import ch.njol.skript.lang.util.SimpleExpression;
//import ch.njol.util.Kleenean;
//import org.bukkit.event.Event;
//
//import javax.annotation.Nullable;
//
//
//public class ExprRedisobject extends SimpleExpression<String> {
//
//	static {
//		Skript.registerExpression(ExprRedisobject.class, String.class, ExpressionType.PROPERTY, "[the] redis object %string%");
//	}
//
//	@Override
//	public Class<? extends String> getReturnType() {
//		return String.class;
//	}
//
//	@Override
//	public boolean isSingle() {
//		return true;
//	}
//
//	@Override
//	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
//		return false;
//	}
//
//	@Override
//	public String toString(@Nullable Event event, boolean debug) {
//		return "redis object";
//	}
//
//	@Override
//	@Nullable
//	protected String[] get(Event event) {
//		return new String[]{"redis object"};
//	}
//}

