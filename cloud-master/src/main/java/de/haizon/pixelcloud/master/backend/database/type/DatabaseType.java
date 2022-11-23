package de.haizon.pixelcloud.master.backend.database.type;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public enum DatabaseType {

    VARCHAR("varchar", 255),
    INT("int", 0),
    LONGBLOB("LONGBLOB", 0);

    final String name;
    final int length;

    DatabaseType(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String append(){
        return name + (length == 0 ? "" : "(" + length + ")");
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

}
