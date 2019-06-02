package com.kaz.test;

import java.util.List;

public class DTOInfo {
    private String name;
    private List<PropertyInfo> propertiesInfo;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<PropertyInfo> getPropertiesInfo() {
        return propertiesInfo;
    }
    public void setPropertiesInfo(List<PropertyInfo> propertiesInfo) {
        this.propertiesInfo = propertiesInfo;
    }
}
