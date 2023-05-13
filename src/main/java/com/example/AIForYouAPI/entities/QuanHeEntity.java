package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "quan_he")
public class QuanHeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    private int idFirst;
    private int idSecond;

    private Date requestDate;
    private boolean accepted;

    public QuanHeEntity() {

    }

    public int getIdFirst() {
        return idFirst;
    }

    public int getIdSecond() {
        return idSecond;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
