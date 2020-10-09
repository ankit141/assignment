package com.assignment.newsportal.controller;

import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.HashtagDTO;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.security.jwt.JwtUtils;
import com.assignment.newsportal.service.HashtagService;
import com.assignment.newsportal.util.HashtagUtil;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HashtagController {

    @Autowired
    HashtagService hashtagService;

    @Autowired
    HashtagUtil hashtagUtil;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping(value = "topic/{topicId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> createHashtag(@RequestBody @Valid HashtagDTO hashtagDTO,
                                           @PathVariable @NotNull Long topicId) {
        Long userId= jwtUtils.getSubject();
        Hashtag hashtag = hashtagService.createHashtag(hashtagDTO, topicId,userId);
        return new ResponseEntity<>(hashtagUtil.convertToDTO(hashtag), HttpStatus.OK);
    }

    @GetMapping(value = "/hashtags")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getAllHashtags(@RequestParam(value = "page",defaultValue = "0")int page,
                                            @RequestParam(value = "size",defaultValue = "10")int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Hashtag> hashtagList = hashtagService.getHashtags(pageable);
        if(hashtagList.isEmpty()){
            if(page==0)
                throw new NotFoundException("No Hashtags Present.");
            throw new NotFoundException("No Results in page "+page);
        }
        List<HashtagDTO> hashtagDTOS = hashtagList.stream().map(hashtag -> hashtagUtil.convertToDTO(hashtag)).collect(Collectors.toList());
        return new ResponseEntity<>(hashtagDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/hashtag/{hashtagId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long hashtagId) {
        Long userId= jwtUtils.getSubject();
        hashtagService.remove(hashtagId,userId);
        return new ResponseEntity<>(new MessageResponse("Hashtag deleted"),HttpStatus.OK);
    }

    @PutMapping(value = "/hashtag/{hashtagId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateHashtag(@PathVariable @NotNull Long hashtagId, @RequestBody @Valid HashtagDTO hashtagDTO) {

        Long userId= jwtUtils.getSubject();
        Hashtag hashtag = hashtagService.updateHashtag(hashtagId, hashtagDTO,userId);

        return new ResponseEntity<>(hashtagUtil.convertToDTO(hashtag), HttpStatus.OK);


    }
}





