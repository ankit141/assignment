package com.assignment.newsportal.controller;


import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.MissingDetailException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.Exception.UnauthorisedException;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.dto.request.SearchRequest;
import com.assignment.newsportal.dto.request.UserDTO;
import com.assignment.newsportal.dto.response.MessageResponse;
import com.assignment.newsportal.entity.ERole;
import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.entity.VoteStatus;
import com.assignment.newsportal.repo.PostRepo;
import com.assignment.newsportal.repo.UserRepo;
import com.assignment.newsportal.security.jwt.JwtUtils;
import com.assignment.newsportal.service.PostService;
import com.assignment.newsportal.util.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
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
//        if(postDTO.getTitle().equals("")){
////            return ResponseEntity.badRequest().body(new MessageResponse("Error: Post title is mandatory."));
//            throw new MissingDetailException("Post title is incomplete");
//        }
//

        Post post= postService.publish(postDTO,userId);

        return new ResponseEntity<>(postUtil.convertToDTO(post), HttpStatus.OK);

    }

    @GetMapping(value="/posts")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> getAllPosts(@PageableDefault(size = 15) Pageable pageable) {
        Page<Post> postList = postService.getPosts(pageable);
        if(postList.isEmpty())
            return new ResponseEntity<>(new MessageResponse("No posts found"),HttpStatus.OK);
        List<PostDTO> postDTOS =
                postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }

    @DeleteMapping(value="/post/{postId}/delete")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> unpublish(@PathVariable @NotNull Long postId){

//        String jwt = token.substring(7);
        Post post=postRepo.findByPostId(postId).orElse(null);
        if(post==null||!post.getActive()){
            throw new NotFoundException("Post with id "+postId+" not found.");
        }
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
        Long userId= jwtUtils.getSubject();
        User user= userRepo.findByUserId(userId).orElse(null);
        Long Id=postService.findUserIdOfPost(postId);
        if((user.getRole()==ERole.ROLE_CONSUMER)&&(userId!=Id))
            throw new UnauthorisedException("Cannot delete other's posts");
        postService.unpublish(post,userId);

        return ResponseEntity.ok(new MessageResponse("Post deleted."));

    }

    @PutMapping(value="/post/{postId}/vote")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<PostDTO> vote(@PathVariable @NotNull Long postId,@RequestHeader("Vote") Long val){

        Long userId= jwtUtils.getSubject();


//        VoteStatus voteStatus=postService.vote(postId,val,userId);
        Post post=postService.vote(postId,val,userId);
//        Post post=postRepo.findByPostId(postId).orElse(null);
        return new ResponseEntity<>(postUtil.convertToDTO(post), HttpStatus.OK);

    }


    @PutMapping(value="/post/{postId}/edit")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('MODERATOR')")
    public ResponseEntity<?> updatePost(@PathVariable @NotNull Long postId,@RequestBody @Valid PostDTO postDTO ){

//        String jwt = token.substring(7);
//        if(postDTO.getTitle().equals("")){
//            throw new MissingDetailException("Post title is incomplete");
//        }
//        Post post=postRepo.findByPostId(postId).orElse(null);
//        if(post==null||!post.getActive()){
//            throw new NotFoundException("Post with id "+postId+" not found.");
//        }
//        Long userId = Long.valueOf(jwtUtils.getUserIdFromJwtToken(jwt));
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
    public ResponseEntity<?> searchPosts(@RequestBody @Valid SearchRequest searchRequest, Pageable pageable) {

        String searchVal= searchRequest.getSearch();
        Page<Post> postList = postService.search(searchVal,pageable);
        if(postList.isEmpty())
            throw new NotFoundException("No posts found for key "+searchVal);
        List<PostDTO> postDTOS =
                postList.stream().map(post -> postUtil.convertToDTO(post)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }





}
