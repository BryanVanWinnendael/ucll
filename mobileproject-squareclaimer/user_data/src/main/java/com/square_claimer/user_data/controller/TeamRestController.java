package com.square_claimer.user_data.controller;

import com.square_claimer.user_data.model.team.Team;
import com.square_claimer.user_data.service.TeamService;
import com.square_claimer.user_data.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value={"/api/team"})
public class TeamRestController extends BaseRestController{

    @Autowired
    private TeamService service;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Iterable<Team> getAll(){
        return service.getAll();
    }

    @PostMapping("/add")
    public Team addTeam(@RequestBody @Valid Team team){
        return service.addTeam(team);
    }

    @PostMapping("/join")
    public Team addUserToTeam(@RequestBody UserTeamMap userTeamMap){
        return service.addUserToTeam(
                userService.getById(userTeamMap.getUserId()),
                service.getById(userTeamMap.getTeamId())
        );
    }

    @Getter @Setter
    public static class UserTeamMap {
        private long userId;
        private long teamId;
    }
}
