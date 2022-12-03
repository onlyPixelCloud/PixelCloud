package de.haizon.pixelcloud.api.services.impl;

import de.haizon.pixelcloud.api.template.ITemplate;
import de.haizon.pixelcloud.api.template.TemplateType;

import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class TemplateImpl implements ITemplate {

    private String uuid;
    private String name;
    private TemplateType templateType;

    private TemplateImpl(){

    }

    public TemplateImpl(String uuid, String name, TemplateType templateType) {
        this.uuid = uuid;
        this.name = name;
        this.templateType = templateType;
    }

    @Override
    public String getUniqueId() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TemplateType getTemplateType() {
        return templateType;
    }
}
