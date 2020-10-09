package com.assignment.newsportal.controller;



import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.Exception.UnauthorisedException;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.dto.request.SearchRequest;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.repo.PostRepo;
import com.assignment.newsportal.repo.UserRepo;
import com.assignment.newsportal.security.jwt.JwtUtils;
import com.assignment.newsportal.service.PostService;
import com.assignment.newsportal.util.PostUtil;
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
public class PostController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PostService postService;

    @Autowired
    PostUtil postUtil;

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;






    @PostMapping(value = "/user/post")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> publish(@RequestBody @Valid PostDTO postDTO) {

        Long userId= jwtUtils.getSubject();

        Post post= postService.publish(postDTO,userId);

        return new ResponseEntity<>(postUtil.convertToDTO(post), HttpStatus.OK);

    }

    @GetMapping(value="/posts")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getAllPosts(@RequestParam(value = "page",defaultValue = "0")int page,
                                         @RequestParam(value = "size",defaultValue = "10")int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Post> postList = postService.getPosts(pageable);

        if (postList.isEmpty()) {
            if (page == 0)
                throw new NotFoundException("No posts present");
        throw new NotFoundException("No results in page " + page);
    }


        List<PostDTO> postDTOS =
                postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }

    @DeleteMapping(value="/post/{postId}/delete")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> unpublish(@PathVariable @NotNull Long postId){

        Long userId= jwtUtils.getSubject();

        postService.unpublish(postId,userId);

        return ResponseEntity.ok(new MessageResponse("Post deleted."));

    }


    @PutMapping(value="/post/{postId}/upvote")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<PostDTO> upvote(@PathVariable @NotNull Long postId){

        Long userId= jwtUtils.getSubject();

        Post post=postService.upvote(postId,userId);
        return new ResponseEntity<>(postUtil.convertToDTO(post), HttpStatus.OK);

    }

    @PutMapping(value="/post/{postId}/downvote")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<PostDTO> downvote(@PathVariable @NotNull Long postId){

        Long userId= jwtUtils.getSubject();
        Post post=postService.downvote(postId,userId);
        return new ResponseEntity<>(postUtil.convertToDTO(post), HttpStatus.OK);

    }


    @PutMapping(value="/post/{postId}/edit")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> updatePost(@PathVariable @NotNull Long postId,@RequestBody @Valid PostDTO postDTO ){

        Long userId= jwtUtils.getSubject();
        Long Id=postService.findUserIdOfPost(postId);
        if(userId.equals(Id)) {

            Post post = postService.update(postId, postDTO);
            return new ResponseEntity<>(postUtil.convertToDTO(post), HttpStatus.OK);
        }

        throw new UnauthorisedException("Cannot update other's posts");

    }

    @GetMapping(value="/post/search")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> searchPosts(@RequestBody @Valid SearchRequest searchRequest, @RequestParam(value = "page",defaultValue = "0")int page,
                                         @RequestParam(value = "size",defaultValue = "10")int size) {

        Pageable pageable=PageRequest.of(page,size);
        String searchVal= searchRequest.getSearch();
        Page<Post> postList = postService.search(searchVal,pageable);
        if(postList.isEmpty()) {

            if(page==0)
                throw new NotFoundException("No posts found for key " + searchVal);
            throw new NotFoundException("No results in page "+page);
        }
        List<PostDTO> postDTOS =
                postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }





}
