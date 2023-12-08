package com.example.everyclub.controller;

import com.example.everyclub.dto.ReplyDTO;
import com.example.everyclub.dto.TeamDTO;
import com.example.everyclub.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/likeTeam/")
@Log4j2
@RequiredArgsConstructor
public class TeamLikeController {

    private final TeamService teamService;

    @GetMapping(value = "/{tno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getTeamLike(@PathVariable("tno") Long tno){

        return new ResponseEntity<>(teamService.getLikeTeam(tno), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody TeamDTO teamDTO){
        // DB의 댓글 테이블에 새로운 댓글 저장
        Long tno = teamService.updateLikeTeam(teamDTO);

        return new ResponseEntity<>(tno, HttpStatus.OK);
    }
}
