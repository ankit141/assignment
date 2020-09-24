package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.entity.VoteStatus;
import com.assignment.newsportal.repo.PostRepo;
import com.assignment.newsportal.repo.VoteStatusRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    VoteStatusRepo voteStatusRepo;

    private final Logger logger= LoggerFactory.getLogger(PostService.class);

    public Post publish(Post post){
        post.setDraft(false); post.setActive(true);
        post.setUpvotes(0L);post.setDownvotes(0L);
        Long userId=post.getUserId();
        post.setCreatedBy(userId);
        post.setUpdatedBy(userId);
        return postRepo.save(post);
    }

    public Page<Post> getPosts(Pageable pageable) {
        return postRepo.getActivePosts(pageable);
    }

    public void unpublish(Post post,Long userId) {

//        Post post=postRepo.findByPostId(postId).orElse(null);
//        if(post==null||!post.getActive()){
//            throw new NotFoundException("Post with id "+postId+" not found.");
//        }
        post.setActive(false);
        post.setUpdatedBy(userId);
        postRepo.save(post);
    }

    public Long findUserIdOfPost(Long postId) {
        return postRepo.findUserIdOfPost(postId).orElse(0L);

    }

//    public Post upvote(Long postId) {
//        Post post=postRepo.findByPostId(postId).orElse(null);
//        if(post==null||!post.getActive())
//            throw new NotFoundException("Post with id "+postId+" doesn't exist");
//
//        //Long upvotes=post.getUpvotes();
//        post.setUpvotes(post.getUpvotes()+1);
//        return postRepo.save(post);
//    }

//    public Post downvote(Long postId) {
//        Post post=postRepo.findByPostId(postId).orElse(null);
//        if(post==null||!post.getActive())
//            throw new NotFoundException("Post with id "+postId+" doesn't exist");
//
//        //Long upvotes=post.getUpvotes();
//        post.setDownvotes((post.getDownvotes()+1));
//        return postRepo.save(post);
//    }

    public Post update(Post post, PostDTO postDTO) {
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());
//        post.setUpdatedBy(post.getUserId());

       return postRepo.save(post);
    }

    public Post vote(Long postId, Long val, Long userId) {
        Post post = postRepo.findByPostId(postId).orElse(null);
        if (post == null || !post.getActive())
            throw new NotFoundException("Post with id " + postId + " doesn't exist");
        VoteStatus voteStatus= voteStatusRepo.find(userId,postId).orElse(null);
        if(voteStatus==null) {
            voteStatus=new VoteStatus(userId,postId,true,false,false);
//            voteStatusRepo.save(voteStatus);
            if (val == 1) {
                voteStatus.setUpvoted(true);
                voteStatus.setDownvoted(false);
                post.setUpvotes(post.getUpvotes()+1);
                voteStatusRepo.save(voteStatus);

            }
              else if(val==-1) {

                voteStatus.setUpvoted(false);
                voteStatus.setDownvoted(true);
                post.setDownvotes(post.getDownvotes()+1);
                voteStatusRepo.save(voteStatus);


              }
              else{
                  throw new IllegalArgumentException("Options are 1 for upvote and -1 for downvote.");
            }
        }
        else if(voteStatus!=null){

            if(val==1&&voteStatus.getUpvoted()) {
                logger.error("You have already upvoted.");

            }
            else if(val==1&&(voteStatus.getDownvoted())){
                voteStatus.setUpvoted(true);
                voteStatus.setDownvoted(false);
                post.setUpvotes(post.getUpvotes()+1);
                post.setDownvotes(post.getDownvotes()-1);
                voteStatusRepo.save(voteStatus);
                logger.info("Changed downvote to upvote");

            }
            else if((val==-1)&&(voteStatus.getDownvoted())){
                logger.error("You have already downvoted.");

            }
            else if((val==-1)&&(voteStatus.getUpvoted())){
                voteStatus.setUpvoted(false);
                voteStatus.setDownvoted(true);
                post.setUpvotes(post.getUpvotes()-1);
                post.setDownvotes(post.getDownvotes()+1);
                voteStatusRepo.save(voteStatus);
                logger.info("Changed upvote to downvote.");


            }

        }

           return post;


    }


    }

