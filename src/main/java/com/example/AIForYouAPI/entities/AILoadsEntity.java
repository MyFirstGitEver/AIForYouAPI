package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ai_loads")
public class AILoadsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private int idProject;

    @JoinColumn(name = "id_model")
    @OneToOne
    private AIModelEntity aiModel;

    public AILoadsEntity() {

    }

    public int getIdProject() {
        return idProject;
    }

    public AIModelEntity getAiModel() {
        return aiModel;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setAiModel(AIModelEntity aiModel) {
        this.aiModel = aiModel;
    }
}