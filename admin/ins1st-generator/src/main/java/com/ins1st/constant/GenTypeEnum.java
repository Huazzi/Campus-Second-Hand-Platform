package com.ins1st.constant;

public enum GenTypeEnum {
    HTML("html"),
    JS("js");
    private String name;

    GenTypeEnum(String name) {

        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
