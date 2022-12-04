package de.haizon.pixelcloud.api.modules;

import de.haizon.pixelcloud.api.services.version.GroupType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Module {

    String name();
    GroupType[] groupType() default GroupType.SERVER;
    String version() default "";
    String[] authors() default "";

}
