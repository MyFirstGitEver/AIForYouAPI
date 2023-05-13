package com.example.AIForYouAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String tenProject, diaChiExcel;

    private Date thoiGianKhoiTao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_so_huu", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "id_so_huu")
    private Integer idSoHuu;

    public ProjectEntity() {

    }

    public Integer getId() {
        return id;
    }

    public String getTenProject() {
        return tenProject;
    }

    public String getDiaChiExcel() {
        return diaChiExcel;
    }

    public Integer getIdSoHuu() {
        return idSoHuu;
    }

    @JsonBackReference
    public UserEntity getUser() {
        return user;
    }

    public Date getThoiGianKhoiTao() {
        return thoiGianKhoiTao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTenProject(String tenProject) {
        this.tenProject = tenProject;
    }

    public void setIdSoHuu(Integer idSoHuu) {
        this.idSoHuu = idSoHuu;
    }

    public void setDiaChiExcel(String diaChiExcel) {
        this.diaChiExcel = diaChiExcel;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setThoiGianKhoiTao(Date thoiGianKhoiTao) {
        this.thoiGianKhoiTao = thoiGianKhoiTao;
    }
}