package com.agent.application.controller;

import com.agent.application.dto.AddCommentDTO;
import com.agent.application.model.Comment;
import com.agent.application.model.CommentInterview;
import com.agent.application.model.CommentSalary;
import com.agent.application.service.intereface.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> addCompanyComment(@RequestBody @Valid AddCommentDTO dto) throws Exception {
        try {
            commentService.addComment(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/company/{companyId}")
    public ResponseEntity<?> getAllCompanyCommentsByCompanyId(@PathVariable Long companyId) throws Exception {
        List<Comment> commentsDto = commentService.findAllByCompanyId(companyId);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/company/{companyId}/positions")
    public ResponseEntity<?> getPositionsByCompanyId(@PathVariable Long companyId) throws Exception {
        List<String> positionsName = commentService.getPositionsByCompanyId(companyId);
        return new ResponseEntity<>(positionsName, HttpStatus.OK);
    }


    @PostMapping(value = "/salary" )
    public ResponseEntity<?> addSalaryComment(@RequestBody @Valid CommentSalary reqDto) throws Exception {

        try {
            commentService.addCommentSalary(reqDto, reqDto.getCompany().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/salary/company/{companyId}")
    public ResponseEntity<?> getAllSalaryCommentsByCompanyId(@PathVariable Long companyId) throws Exception {
        List<CommentSalary> commentsDto = commentService.getAllSalaryCommentsByCompanyId(companyId);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/interview" )
    public ResponseEntity<?> addInterviewComment(@RequestBody @Valid CommentInterview reqDto) throws Exception {
        try {
            commentService.addCommentInterview(reqDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/interview/company/{companyId}")
    public ResponseEntity<?> getAllInterviewCommentsByCompanyId(@PathVariable Long companyId) throws Exception {
        List<CommentInterview> commentsDto = commentService.getAllInterviewCommentsByCompanyId(companyId);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }
}
