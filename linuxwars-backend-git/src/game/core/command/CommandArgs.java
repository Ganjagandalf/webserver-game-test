package game.core.command;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import game.core.player.PlayerType;

@Retention(RUNTIME)
@Target(METHOD)
public @interface CommandArgs {
	boolean needsLogin() default false;
	PlayerType playertype() default PlayerType.USER;
}
