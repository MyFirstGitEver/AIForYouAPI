package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "share")
public class ShareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;

    private int idNguoiNhan, idNguoiGui;
    private Date sentDate;
    private Integer idProject;

    public ShareEntity() {

    }
    public int getIdNguoiNhan() {
        return idNguoiNhan;
    }

    public int getIdNguoiGui() {
        return idNguoiGui;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setIdNguoiNhan(int idNguoiNhan) {
        this.idNguoiNhan = idNguoiNhan;
    }

    public void setIdNguoiGui(int idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}