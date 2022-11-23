package de.haizon.pixelcloud.api.template;

import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface ITemplate {

    UUID getUniqueId();

    String getName();

    TemplateType getTemplateType();

}
