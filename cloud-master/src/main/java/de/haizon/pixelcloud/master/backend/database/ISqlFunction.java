package de.haizon.pixelcloud.master.backend.database;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@FunctionalInterface
public interface ISqlFunction<I, O> {

    O apply(I i);

}
