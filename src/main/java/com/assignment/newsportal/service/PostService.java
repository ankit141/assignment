package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PostService {

    @Autowired
    PostRepo postRepo;

    public Post publish(Post post){
        post.setDraft(false); post.setActive(true);
        post.setUpvotes(0L);post.setDownvotes(0L);
        return postRepo.save(post);
    }

    public Page<Post> getPosts(Pageable pageable) {
        return postRepo.getActivePosts(pageable);
    }

    public void unpublish(Post post) {

//        Post post=postRepo.findByPostId(postId).orElse(null);
//        if(post==null||!post.getActive()){
//            throw new NotFoundException("Post with id "+postId+" not found.");
//        }
        post.setActive(false);
        postRepo.save(post);
    }

    public Long findUserIdOfPost(Long postId) {
        return postRepo.findUserIdOfPost(postId).orElse(0L);

    }

    public Post upvote(Long postId) {
        Post post=postRepo.findByPostId(postId).orElse(null);
        if(post==null||!post.getActive())
            throw new NotFoundException("Post with id "+postId+" doesn't exist");

        //Long upvotes=post.getUpvotes();
        post.setUpvotes(post.getUpvotes()+1);
        return postRepo.save(post);
    }

    public Post downvote(Long postId) {
        Post post=postRepo.findByPostId(postId).orElse(null);
        if(post==null||!post.getActive())
            throw new NotFoundException("Post with id "+postId+" doesn't exist");

        //Long upvotes=post.getUpvotes();
        post.setDownvotes((post.getDownvotes()+1));
        return postRepo.save(post);
    }

    public Post update(Post post, PostDTO postDTO) {
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());

       return postRepo.save(post);
    }
}
