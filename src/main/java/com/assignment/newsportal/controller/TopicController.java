package com.assignment.newsportal.controller;


import com.assignment.newsportal.Exception.InvalidRequestException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.HashtagDTO;
import com.assignment.newsportal.dto.request.TopicDTO;
import com.assignment.newsportal.dto.request.TopicsDTO;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.security.jwt.JwtUtils;
import com.assignment.newsportal.service.TopicService;
import com.assignment.newsportal.util.HashtagUtil;
import com.assignment.newsportal.util.TopicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Autowired
    TopicUtil topicUtil;

    @Autowired
    TopicService topicService;

    @Autowired
    HashtagUtil hashtagUtil;

    @Autowired
    JwtUtils jwtUtils;



    @PostMapping(value="/create")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<TopicDTO> createTopic( @RequestBody @Valid TopicDTO topicDTO){
        Long userId= jwtUtils.getSubject();
      String topicName=topicDTO.getTopic();
      Topic topic=topicService.createTopic(topicName,userId);

      return new ResponseEntity<>(topicUtil.convertToDTO(topic), HttpStatus.OK);

    }

    @PostMapping(value="/createmul")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<TopicDTO>> createTopics( @RequestBody @Valid TopicsDTO topicsDTO){
        
        Long userId= jwtUtils.getSubject();
        Set<String> topics=topicsDTO.getTopics();
        if(topics==null)
            throw new InvalidRequestException("Please enter topics to add.");
        List<TopicDTO> topicDTOS=new ArrayList<>();

        for(String t: topics){
            if(t.equals(""))
                throw new InvalidRequestException("Please enter full topic names.");
            Topic topic= topicService.createTopic(t,userId);
            TopicDTO topicDTO= topicUtil.convertToDTO(topic);
            topicDTOS.add(topicDTO);
        }
        return new ResponseEntity<>(topicDTOS, HttpStatus.OK);

    }

    @GetMapping(value="/all")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getAllTopics(@RequestParam(value = "page",defaultValue = "0")int page,
                                          @RequestParam(value = "size",defaultValue = "10")int size){

        Pageable pageable= PageRequest.of(page,size);
        Page<Topic> topicList = topicService.getTopics(pageable);
        if(topicList.isEmpty()){

            if(topicList.isEmpty()){
                if(page==0)
                    throw new NotFoundException("No topics present");
                throw new NotFoundException("No results in page "+page);
            }
        }

        List<TopicDTO> topicDTOS = topicList.stream().map(topic -> topicUtil.convertToDTO(topic)).collect(Collectors.toList());
        return new ResponseEntity<>(topicDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value="/{topicId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteTopic(@PathVariable @NotNull Long topicId){
        Long userId= jwtUtils.getSubject();
        topicService.remove(topicId,userId);
        return new ResponseEntity<>(new MessageResponse("Topic deleted."),HttpStatus.OK);
    }

    @PutMapping(value="/{topicId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateTopic(@PathVariable @NotNull Long topicId, @RequestBody TopicDTO topicDTO){

        Long userId= jwtUtils.getSubject();
        Topic topic=topicService.updateTopic(topicId,topicDTO,userId);

        return new ResponseEntity<>(topicUtil.convertToDTO(topic),HttpStatus.OK);


    }

    @GetMapping(value="/{topicId}")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getTopicHashtag(@PathVariable @NotNull Long topicId, @RequestParam(value = "page",defaultValue = "0")int page,
                                             @RequestParam(value = "size",defaultValue = "10")int size){
        Pageable pageable=PageRequest.of(page,size);

        Page<Hashtag> hashtagList = topicService.getTopicHashtags(topicId,pageable);
        if(hashtagList.isEmpty()){
            if(page==0)
                throw new NotFoundException("No hashtags present");
            throw new NotFoundException("No results in page "+page);
        }
        List<HashtagDTO> hashtagDTOS = hashtagList.stream().map(hashtag -> hashtagUtil.convertToDTO(hashtag)).collect(Collectors.toList());
        return new ResponseEntity<>(hashtagDTOS, HttpStatus.OK);

    }
}
