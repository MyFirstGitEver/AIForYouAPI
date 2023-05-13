package com.example.AIForYouAPI.dtos;

import com.example.AIForYouAPI.entities.ShareEntity;
import com.example.AIForYouAPI.entities.UserEntity;

import java.util.Date;

public class ShareDTO {
    private String tenNguoiGui, anhNguoiGui, tenProject;
    private Integer idProject;

    private Date sentDate;

    public ShareDTO() {

    }

    public ShareDTO(ShareEntity share, String tenProject, UserEntity nguoiGui) {
        this.tenNguoiGui = nguoiGui.getTenDn();
        this.anhNguoiGui = nguoiGui.getAvatar();
        this.tenProject = tenProject;
        this.idProject = share.getIdProject();
        this.sentDate = share.getSentDate();
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public String getAnhNguoiGui() {
        return anhNguoiGui;
    }

    public String getTenProject() {
        return tenProject;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public void setAnhNguoiGui(String anhNguoiGui) {
        this.anhNguoiGui = anhNguoiGui;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public void setTenProject(String tenProject) {
        this.tenProject = tenProject;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}