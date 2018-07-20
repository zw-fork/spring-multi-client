package com.github.model;

import java.io.Serializable;

/**
 * Created by leolin on 12/20/2017.
 */
public class CourseType implements Serializable{

    private static final long serialVersionUID = 4538869914494589496L;
    private Long id;

    private Integer typeName;

    private Integer status;

    public Integer getTypeName() {
        return typeName;
    }

    public void setTypeName(Integer typeName) {
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseType{" +
                "id=" + id +
                ", typeName=" + typeName + "}";
    }
}
