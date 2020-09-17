package com.assignment.newsportal.controller;

import com.assignment.newsportal.dto.request.HashtagDTO;
import com.assignment.newsportal.dto.request.TopicDTO;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.repo.HashtagRepo;
import com.assignment.newsportal.service.HashtagService;
import com.assignment.newsportal.util.HashtagUtil;
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
public class HashtagController {

    @Autowired
    HashtagService hashtagService;

    @Autowired
    HashtagRepo hashtagRepo;

    @Autowired
    HashtagDTO hashtagDTO;

    @Autowired
    HashtagUtil hashtagUtil;

    @PostMapping(value = "topic/{topicId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> createHashtag(@RequestBody @Valid HashtagDTO hashtagDTO,
                                           @PathVariable @NotNull Long topicId) {
        Hashtag hashtag = hashtagService.createHashtag(hashtagDTO, topicId);
        return new ResponseEntity<>(hashtagUtil.convertToDTO(hashtag), HttpStatus.OK);
    }

    @GetMapping(value = "/hashtags")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getAllHashtags(Pageable pageable) {
        Page<Hashtag> hashtagList = hashtagService.getHashtags(pageable);
        List<HashtagDTO> hashtagDTOS = hashtagList.stream().map(hashtag -> hashtagUtil.convertToDTO(hashtag)).collect(Collectors.toList());
        return new ResponseEntity<>(hashtagDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/hashtag/{hashtagId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long hashtagId) {
        hashtagService.remove(hashtagId);
        return ResponseEntity.ok(new MessageResponse("Hashtag deleted."));
    }

    @PutMapping(value = "/hashtag/{hashtagId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateHashtag(@PathVariable @NotNull Long hashtagId, @RequestBody HashtagDTO hashtagDTO) {
        Hashtag hashtag = hashtagService.updateHashtag(hashtagId, hashtagDTO);

        return new ResponseEntity<>(hashtagUtil.convertToDTO(hashtag), HttpStatus.OK);


    }
}

