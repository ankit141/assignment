package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.InvalidRequestException;
import com.assignment.newsportal.Exception.MissingDetailException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.entity.*;
import com.assignment.newsportal.repo.HashtagRepo;
import com.assignment.newsportal.repo.PostHashtagRepo;
import com.assignment.newsportal.repo.PostRepo;
import com.assignment.newsportal.repo.VoteStatusRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    VoteStatusRepo voteStatusRepo;

    @Autowired
    HashtagRepo hashtagRepo;

    @Autowired
    PostHashtagRepo postHashtagRepo;


    private final Logger logger = LoggerFactory.getLogger(PostService.class);

//    public Post publish(Post post){
//        post.setDraft(false); post.setActive(true);
//        post.setUpvotes(0L);post.setDownvotes(0L);
//        Long userId=post.getUserId();
//        post.setCreatedBy(userId);
//        post.setUpdatedBy(userId);
//        return postRepo.save(post);
//    }

    public Page<Post> getPosts(Pageable pageable) {
        return postRepo.getActivePosts(pageable);
    }

    public void unpublish(Post post, Long userId) {

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

    @Transactional
    public Post vote(Long postId, Long val, Long userId) {
        Post post = postRepo.findByPostId(postId).orElse(null);
        if (post == null || !post.getActive())
            throw new NotFoundException("Post with id " + postId + " doesn't exist");
        VoteStatus voteStatus = voteStatusRepo.find(userId, postId).orElse(null);

        if((val!=1)&&(val!=-1))
        throw new InvalidRequestException("Options are 1 for upvote and -1 for downvote.");

        if (voteStatus == null) {
            voteStatus = new VoteStatus(userId, postId, true, false, false);

            if (val == 1) {
                voteStatus.setUpvoted(true);
                voteStatus.setDownvoted(false);
                post.setUpvotes(post.getUpvotes() + 1);
                voteStatusRepo.save(voteStatus);

            }
            else if (val == -1) {

                voteStatus.setUpvoted(false);
                voteStatus.setDownvoted(true);
                post.setDownvotes(post.getDownvotes() + 1);
                voteStatusRepo.save(voteStatus);


            }
//            else {
//                throw new InvalidRequestException("Options are 1 for upvote and -1 for downvote.");
//            }
        } else if (voteStatus != null) {

            if (val == 1 && voteStatus.getUpvoted()) {
                throw new InvalidRequestException("You have already upvoted.");

            } else if (val == 1 && (voteStatus.getDownvoted())) {
                voteStatus.setUpvoted(true);
                voteStatus.setDownvoted(false);
                post.setUpvotes(post.getUpvotes() + 1);
                post.setDownvotes(post.getDownvotes() - 1);
                voteStatusRepo.save(voteStatus);
                logger.info("Changed downvote to upvote");

            } else if ((val == -1) && (voteStatus.getDownvoted())) {
                throw new InvalidRequestException("You have already downvoted.");

            } else if ((val == -1) && (voteStatus.getUpvoted())) {
                voteStatus.setUpvoted(false);
                voteStatus.setDownvoted(true);
                post.setUpvotes(post.getUpvotes() - 1);
                post.setDownvotes(post.getDownvotes() + 1);
                voteStatusRepo.save(voteStatus);
                logger.info("Changed upvote to downvote.");


            }

        }

        return postRepo.save(post);


    }

    @Transactional
    public Post publish(PostDTO postDTO, Long userId) {

        if(postDTO.getTitle().equals("")){
            throw new MissingDetailException("Post title is incomplete");
        }
        Set<String> hashtags = postDTO.getHashtags();
        List<Hashtag> hashtagList = new ArrayList<>();
        for (String h : hashtags) {
            Hashtag hashtag = hashtagRepo.findByHashtag(h).orElse(null);
            if ((hashtag == null) || (hashtag.getActive() == false))
                throw new NotFoundException("Hashtag " + h + " does not exist.");
            hashtagList.add(hashtag);
        }

        Post post = new Post(postDTO.getTitle(), postDTO.getBody(), hashtags, 0L, 0L, false, true, userId);
        post.setCreatedBy(userId);
        post.setUpdatedBy(userId);
        post = postRepo.save(post);

        Long postId = post.getPostId();

        for (Hashtag hashtag : hashtagList) {
            PostHashtagMap postHashtagMap = new PostHashtagMap(postId, hashtag.getHashtagId(), hashtag.getHashtag(), hashtag.getTopicId(), userId);
            postHashtagMap.setCreatedBy(userId);
            postHashtagMap.setUpdatedBy(userId);
            postHashtagRepo.save(postHashtagMap);
        }


        return post;
    }

    @Transactional
    public Post update(Long postId, PostDTO postDTO) {

//        Long postId = post.getPostId();

        if(postDTO.getTitle().equals("")){
            throw new MissingDetailException("Post title is incomplete");
        }
        Post post=postRepo.findByPostId(postId).orElse(null);
        if(post==null||!post.getActive()){
            throw new NotFoundException("Post with id "+postId+" not found.");
        }
        Long userId = post.getUserId();
        postHashtagRepo.deleteByPostId(postId);
        Set<String> hashtags = postDTO.getHashtags();
        List<Hashtag> hashtagList = new ArrayList<>();
        for (String h : hashtags) {
            Hashtag hashtag = hashtagRepo.findByHashtag(h).orElse(null);
            if ((hashtag == null) || (hashtag.getActive() == false))
                throw new NotFoundException("Hashtag " + h + " does not exist.");
            hashtagList.add(hashtag);
        }

        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());
        post.setHashtags(hashtags);
        post = postRepo.save(post);


        for (Hashtag hashtag : hashtagList) {
            PostHashtagMap postHashtagMap = new PostHashtagMap(postId, hashtag.getHashtagId(), hashtag.getHashtag(), hashtag.getTopicId(), userId);
            postHashtagMap.setCreatedBy(userId);
            postHashtagMap.setUpdatedBy(userId);
            postHashtagRepo.save(postHashtagMap);
        }

        return post;
    }

    public Page<Post> search(String searchVal, Pageable pageable) {
        if (searchVal.equals(""))
            throw new MissingDetailException("Search value not provided");
        if (searchVal.startsWith("#")) {
            return postRepo.findByHashtag(searchVal, pageable);
        }
        return postRepo.findByTitle(searchVal, pageable);
    }

}

