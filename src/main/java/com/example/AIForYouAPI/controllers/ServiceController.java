package com.example.AIForYouAPI.controllers;

import com.example.AIForYouAPI.dtos.ProjectDTO;
import com.example.AIForYouAPI.dtos.ReceiptDTO;
import com.example.AIForYouAPI.dtos.ServiceDTO;
import com.example.AIForYouAPI.entities.*;
import com.example.AIForYouAPI.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/projects/")
public class ServiceController {
    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SubscriptionReceiptsRepo subscriptionReceiptsRepo;

    @Autowired
    AIReceiptsRepo aiReceiptsRepo;

    @Autowired
    AILoadsRepo aiLoadsRepo;

    @Autowired
    SubscriptionRepo subscriptionRepo;

    @Autowired
    AIModelRepo aiModelRepo;

    @GetMapping("load/{id}/{requester}")
    public ResponseEntity<ProjectDTO> loadProject(@PathVariable("id") int id, @PathVariable("requester") int requester) {
        if(projectRepo.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProjectEntity project = projectRepo.findById(id).get();
        String sharedByName = project.getUser().getId() == requester ? null : project.getUser().getTenDn();

        if(aiLoadsRepo.findByIdProject(project.getId()) == null) {
            return ResponseEntity.ok(new ProjectDTO(project, ProjectDTO.ProjectType.STATS, sharedByName));
        }

        return ResponseEntity.ok(new ProjectDTO(project, ProjectDTO.ProjectType.ML, sharedByName));
    }

    @GetMapping("/history/purchase/{userName}")
    public ResponseEntity<ReceiptDTO[]> purchase(@PathVariable("userName") String userName) {
        int id = userRepo.findByTenDn(userName).getId();

        List<SubscriptionReceiptsEntity> subscriptionList = subscriptionReceiptsRepo.findByIdUser(id);
        List<AIReceiptsEntity> models = aiReceiptsRepo.findByIdNguoiMua(id);

        ReceiptDTO[] receipts = new ReceiptDTO[subscriptionList.size() + models.size()];

        int index = 0;
        for(SubscriptionReceiptsEntity subscriptionReceiptsEntity : subscriptionList) {
            String ten = subscriptionRepo.findById(subscriptionReceiptsEntity.getIdSubs()).get().getTenSubscription();
            receipts[index] = new ReceiptDTO(ten, subscriptionReceiptsEntity.getReceiptDate(),
                    subscriptionReceiptsEntity.getEndDate().getTime() <= new Date().getTime(), true);

            index++;
        }

        for(AIReceiptsEntity modelReceipt : models) {
            String tenModel = aiModelRepo.findById(modelReceipt.getIdModel()).get().getTenModel();
            receipts[index] = new ReceiptDTO(tenModel, modelReceipt.getReceiptDate(), false, false);
            index++;
        }

        return ResponseEntity.ok(receipts);
    }


    @GetMapping("/unpurchased/{userName}")
    public ResponseEntity<ServiceDTO[]> getUnpurchasedServices(@PathVariable("userName") String userName) {
        int userId = userRepo.findByTenDn(userName).getId();

        List<AIModelEntity> models = aiModelRepo.findAllUnpurchasedModels(userId);
        List<SubscriptionEntity> subscriptions = subscriptionRepo.findAllUnpurchasedSubs(userId);

        ServiceDTO[] services = new ServiceDTO[models.size() + subscriptions.size()];

        int index = 0;

        for(AIModelEntity model : models) {
            services[index] = new ServiceDTO(model);
            index++;
        }

        for(SubscriptionEntity subscription : subscriptions) {
            services[index] = new ServiceDTO(subscription);
            index++;
        }

        return ResponseEntity.ok(services);
    }

    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity<Boolean> deleteProject(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        if(projectRepo.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProjectEntity project = projectRepo.findById(id).get();
        if(!project.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        projectRepo.deleteById(id);
        return ResponseEntity.ok(true);
    }
}