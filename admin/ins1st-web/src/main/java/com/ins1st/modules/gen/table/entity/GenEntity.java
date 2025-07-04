package com.ins1st.modules.gen.table.entity;

public class GenEntity {

    private String name;
    private String comment;
    private String dbType;
    private String javaType;
    private boolean keyFlag;
    private boolean isInsert;
    private boolean isEdit;
    private boolean isSelect;
    private boolean isList;

    public GenEntity(String name, String comment, String dbType, String javaType, boolean keyFlag, boolean isInsert, boolean isEdit, boolean isSelect, boolean isList) {
        this.name = name;
        this.comment = comment;
        this.dbType = dbType;
        this.javaType = javaType;
        this.keyFlag = keyFlag;
        this.isInsert = isInsert;
        this.isEdit = isEdit;
        this.isSelect = isSelect;
        this.isList = isList;
    }

    public GenEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean isKeyFlag() {
        return keyFlag;
    }

    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }

    public boolean isInsert() {
        return isInsert;
    }

    public void setInsert(boolean insert) {
        isInsert = insert;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }
}
