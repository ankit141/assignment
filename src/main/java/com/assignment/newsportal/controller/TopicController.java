package com.assignment.newsportal.controller;


import com.assignment.newsportal.dto.request.HashtagDTO;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.dto.request.TopicDTO;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.service.TopicService;
import com.assignment.newsportal.util.HashtagUtil;
import com.assignment.newsportal.util.TopicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
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


    @PostMapping(value="/create")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<TopicDTO> createTopic(@Valid @RequestBody TopicDTO topicDTO){
      Topic topic=topicUtil.convertToEntity(topicDTO);
      topic=topicService.createTopic(topic);

      return new ResponseEntity<>(topicUtil.convertToDTO(topic), HttpStatus.OK);

    }

    @GetMapping(value="/all")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getAllTopics(Pageable pageable){
        Page<Topic> topicList = topicService.getTopics(pageable);
        List<TopicDTO> topicDTOS = topicList.stream().map(topic -> topicUtil.convertToDTO(topic)).collect(Collectors.toList());
        return new ResponseEntity<>(topicDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value="/{topicId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteTopic(@PathVariable @NotNull Long topicId){
        topicService.remove(topicId);
        return ResponseEntity.ok(new MessageResponse("Topic deleted."));
    }

    @PutMapping(value="/{topicId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateTopic(@PathVariable @NotNull Long topicId, @RequestBody TopicDTO topicDTO){
        Topic topic=topicService.updateTopic(topicId,topicDTO);

        return new ResponseEntity<>(topicUtil.convertToDTO(topic),HttpStatus.OK);


    }

    @GetMapping(value="/{topicId}")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getTopicHashtag(@PathVariable @NotNull Long topicId, Pageable pageable){

        Page<Hashtag> hashtagList = topicService.getTopicHashtags(topicId,pageable);
        List<HashtagDTO> hashtagDTOS = hashtagList.stream().map(hashtag -> hashtagUtil.convertToDTO(hashtag)).collect(Collectors.toList());
        return new ResponseEntity<>(hashtagDTOS, HttpStatus.OK);

    }
}
