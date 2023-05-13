package com.example.AIForYouAPI.dtos;

import com.example.AIForYouAPI.entities.UserEntity;

import java.util.List;

public class UserDTO {
    private Integer id;
    private String tenDn, pass, avatar;

    private List<ProjectDTO> projects;

    public UserDTO(UserEntity user, List<ProjectDTO> projects) {
        id = user.getId();
        tenDn = user.getTenDn();
        pass = "";
        avatar = user.getAvatar();
        this.projects = projects;
    }

    public Integer getId() {
        return id;
    }

    public String getTenDn() {
        return tenDn;
    }

    public String getPass() {
        return pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTenDn(String tenDn) {
        this.tenDn = tenDn;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
