package com.example.AIForYouAPI.dtos;

import com.example.AIForYouAPI.entities.ProjectEntity;

import java.util.Date;

public class ProjectDTO {

    public enum ProjectType {
        STATS,
        ML,
        PIE,
        HIST1,
        HIST2,
        SCATTER,
        SEGMENTS
    }

    private Integer id;
    private String name, diaChiExcel, sharedByName;
    private ProjectType type;
    private Date date;

    public ProjectDTO() {

    }

    public ProjectDTO(ProjectEntity project, ProjectType type, String sharedByName) {
        id = project.getId();
        name = project.getTenProject();
        diaChiExcel = project.getDiaChiExcel();
        date = project.getThoiGianKhoiTao();

        this.sharedByName = sharedByName;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProjectType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public String getDiaChiExcel() {
        return diaChiExcel;
    }

    public String getSharedByName() {
        return sharedByName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDiaChiExcel(String diaChiExcel) {
        this.diaChiExcel = diaChiExcel;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}