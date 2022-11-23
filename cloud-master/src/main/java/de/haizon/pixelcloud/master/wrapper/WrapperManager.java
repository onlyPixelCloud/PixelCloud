package de.haizon.pixelcloud.master.wrapper;

import de.haizon.pixelcloud.api.wrapper.IWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class WrapperManager {

    private final List<IWrapper> wrappers;

    public WrapperManager() {
        this.wrappers = new ArrayList<>();
    }

    public List<IWrapper> getWrappers() {
        return wrappers;
    }
}
