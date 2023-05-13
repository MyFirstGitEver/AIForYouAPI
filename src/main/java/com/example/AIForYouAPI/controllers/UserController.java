package com.example.AIForYouAPI.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.AIForYouAPI.dtos.ProjectDTO;

import com.example.AIForYouAPI.dtos.QuanHeDTO;
import com.example.AIForYouAPI.dtos.ShareDTO;
import com.example.AIForYouAPI.dtos.UserDTO;
import com.example.AIForYouAPI.entities.*;
import com.example.AIForYouAPI.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import projections.SenderWithMaxDate;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {
    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SubscriptionRepo subscriptionRepo;

    @Autowired
    SubscriptionReceiptsRepo subscriptionReceiptsRepo;

    @Autowired
    AIModelRepo aiModelRepo;

    @Autowired
    AIReceiptsRepo aiReceiptsRepo;

    @Autowired
    ShareRepo shareRepo;

    @Autowired
    AILoadsRepo aiLoadsRepo;

    @Autowired
    QuanHeRepo quanHeRepo;

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping("/login/{name}/{pass}")
    public ResponseEntity<UserDTO> login(
            @PathVariable("name") String userName,
            @PathVariable("pass") String password) {
        UserEntity user = userRepo.findByTenDn(userName);

        if(user == null || !BCrypt.verifyer().verify(password.toCharArray(), user.getPass()).verified) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<ProjectDTO> projectDTOS = new ArrayList<>();

        for(ProjectEntity project : user.getProjects()) {
            if(aiLoadsRepo.findByIdProject(project.getId()) == null) {
                projectDTOS.add(new ProjectDTO(project, ProjectDTO.ProjectType.STATS, null));
            }
            else {
                projectDTOS.add(new ProjectDTO(project, ProjectDTO.ProjectType.ML, null));
            }
        }

        return ResponseEntity.ok(new UserDTO(user, projectDTOS));
    }

    @GetMapping("/share/list/{name}")
    public ResponseEntity<QuanHeDTO[]> shareList(@PathVariable("name") String name) {
        int id = userRepo.findByTenDn(name).getId();

        List<QuanHeEntity> quanHes = quanHeRepo.findByIdFirstOrIdSecond(id, id);
        QuanHeDTO[] result = new QuanHeDTO[quanHes.size()];

        for(int i=0;i<quanHes.size();i++) {
            String tenA = userRepo.findById(quanHes.get(i).getIdFirst()).get().getTenDn();
            String tenB = userRepo.findById(quanHes.get(i).getIdSecond()).get().getTenDn();

            String tenNguoiNhan;
            int idNguoiNhan;
            if(name.equals(tenA)) {
                tenNguoiNhan = tenB;
                idNguoiNhan = quanHes.get(i).getIdSecond();
            }
            else {
                tenNguoiNhan = tenA;
                idNguoiNhan = quanHes.get(i).getIdFirst();
            }

            result[i] = new QuanHeDTO(idNguoiNhan, tenNguoiNhan);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/friends/{userName}")
    public ResponseEntity<ShareDTO[]> getFriendsBox(@PathVariable("userName") String userName) {
        int id = userRepo.findByTenDn(userName).getId();

        List<SenderWithMaxDate> temp = shareRepo.findAllLastShared(id);
        ShareEntity[] shares = new ShareEntity[temp.size()];

        for(int i=0;i<shares.length;i++) {
            shares[i] = shareRepo.findByIdNguoiGuiAndSentDate(
                    temp.get(i).getIdNguoiGui(), temp.get(i).getSentDate(), PageRequest.of(0, 1)).get(0);
        }

        ShareDTO[] result = new ShareDTO[shares.length];

        int index = 0;
        for(ShareEntity share : shares) {
            result[index] = fromShareEntityToDTO(share);
            index++;
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/notification/{userName}")
    public ResponseEntity<ShareDTO[]> getAllNotifications(@PathVariable("userName") String userName) {
        int id = userRepo.findByTenDn(userName).getId();

        List<ShareEntity> shares = shareRepo.findByIdNguoiNhanOrderBySentDateDesc(id);
        ShareDTO[] dtos = new ShareDTO[shares.size()];

        for(int i=0;i<dtos.length;i++) {
            dtos[i] = fromShareEntityToDTO(shares.get(i));
        }

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/buy/{code}/{userName}")
    public ResponseEntity<Boolean> buy(@PathVariable("code") int code, @PathVariable("userName") String userName) {
        int id = userRepo.findByTenDn(userName).getId();

        // 1 -> inf
        //or -inf -> -1
        if(code > 0) {
            SubscriptionEntity subscription = subscriptionRepo.findById(code - 1).get();
            subscriptionReceiptsRepo.save(new SubscriptionReceiptsEntity(subscription, id));
        }
        else {
            AIModelEntity aiModel = aiModelRepo.findById(-code - 1).get();
            aiReceiptsRepo.save(new AIReceiptsEntity(aiModel, id));
        }

        return ResponseEntity.ok(true);
    }

    @PostMapping("/save")
    public ResponseEntity<ProjectEntity> save(@RequestBody ProjectEntity project) {
        if(project.getId() != null && !projectRepo.findById(project.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

        return ResponseEntity.ok(projectRepo.save(project));
    }

    @MessageMapping("/share")
    public void share(@Payload ShareEntity share) {
        if(share.getIdProject() == null || projectRepo.findById(share.getIdProject()).isEmpty()) {
            template.convertAndSendToUser(Integer.toString(share.getIdNguoiGui()), "/work/",
                    "?/???#23");
        }

        shareRepo.save(share);
        template.convertAndSendToUser(
                Integer.toString(share.getIdNguoiNhan()),
                "/work",
                fromShareEntityToDTO(share));
    }

    private ShareDTO fromShareEntityToDTO(ShareEntity share) {
        UserEntity nguoGui = userRepo.findById(share.getIdNguoiGui()).get();

        String tenProject = projectRepo.findById(share.getIdProject()).get().getTenProject();

        return new ShareDTO(share, tenProject, nguoGui);
    }
}