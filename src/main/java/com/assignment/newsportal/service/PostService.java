package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.*;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.entity.*;
import com.assignment.newsportal.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    UserRepo userRepo;


    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public Page<Post> getPosts(Pageable pageable) {
        return postRepo.getActivePosts(pageable);
    }

    public void unpublish(Long postId, Long userId) {

        Post post=postRepo.findByPostId(postId).orElse(null);
        if(post==null||!post.getActive()){
            throw new NotFoundException("Post with id "+postId+" not found.");
        }
        User user= userRepo.findByUserId(userId).orElse(null);
        Long Id=findUserIdOfPost(postId);
        if((user.getRole()==ERole.ROLE_CONSUMER)&&(!userId.equals(Id)))
            throw new UnauthorisedException("Cannot delete other's posts");
        post.setActive(false);
        post.setUpdatedBy(userId);
        postRepo.save(post);
    }

    public Long findUserIdOfPost(Long postId) {
        return postRepo.findUserIdOfPost(postId).orElse(0L);

    }



    @Transactional
    public Post publish(PostDTO postDTO, Long userId) {

        if (postDTO.getTitle() == null)
            postDTO.setTitle("");
        if (postDTO.getBody() == null)
            postDTO.setBody("");
        Set<String> hashtags = postDTO.getHashtags();
        if (hashtags == null) {
            hashtags = new HashSet<>();
            postDTO.setHashtags(hashtags);
        }
        List<Hashtag> hashtagList = new ArrayList<>();
        for (String h : hashtags) {
            if (h.equals(""))
                throw new InvalidRequestException("Empty hashtags not allowed.");

            Hashtag hashtag = hashtagRepo.findByHashtag(h).orElse(null);
            if ((hashtag == null) || !hashtag.getActive())
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

        Post post = postRepo.findByPostId(postId).orElse(null);
        if (post == null || !post.getActive()) {
            throw new NotFoundException("Post with id " + postId + " not found.");
        }
        Long userId = post.getUserId();
        postHashtagRepo.deleteByPostId(postId);


        if (postDTO.getTitle() == null)
            postDTO.setTitle("");
        if (postDTO.getBody() == null)
            postDTO.setBody("");
        Set<String> hashtags = postDTO.getHashtags();
        if (hashtags == null) {
            hashtags = new HashSet<>();
            postDTO.setHashtags(hashtags);
        }
        List<Hashtag> hashtagList = new ArrayList<>();
        for (String h : hashtags) {

            if (h.equals(""))
                throw new InvalidRequestException("Empty hashtags not allowed.");

            Hashtag hashtag = hashtagRepo.findByHashtag(h).orElse(null);
            if ((hashtag == null) || !hashtag.getActive())
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

        if (searchVal.startsWith("#")) {
            Set<Long> uniquePostIds = postHashtagRepo.findByHashtag(searchVal.substring(1).toLowerCase());
            return postRepo.findByHashtag(uniquePostIds, pageable);

        }
        Set<Long> PostHashIds = postHashtagRepo.findByHashtag(searchVal.toLowerCase());
        Set<Long> PostTitleIds = postRepo.findByTitle(searchVal.toLowerCase());
        Set<Long> UniqueIds = Stream.concat(PostHashIds.stream(), PostTitleIds.stream())
                .collect(Collectors.toSet());
        return postRepo.getSearchedPosts(UniqueIds, pageable);

    }

    public Post upvote(Long postId, Long userId) {

        Post post = postRepo.findByPostId(postId).orElse(null);
        if (post == null || !post.getActive())
            throw new NotFoundException("Post with id " + postId + " doesn't exist");
        VoteStatus voteStatus = voteStatusRepo.find(userId, postId).orElse(null);

        if (voteStatus == null) {
            voteStatus = new VoteStatus(userId, postId, true, true, false);
            post.setUpvotes(post.getUpvotes() + 1);
            voteStatusRepo.save(voteStatus);

        } else {
            if (voteStatus.getUpvoted()) {
                throw new InvalidRequestException("You have already upvoted.");

            } else if (voteStatus.getDownvoted()) {
                voteStatus.setUpvoted(true);
                voteStatus.setDownvoted(false);
                post.setUpvotes(post.getUpvotes() + 1);
                post.setDownvotes(post.getDownvotes() - 1);
                voteStatusRepo.save(voteStatus);
                logger.info("Changed downvote to upvote");

            }

        }

        return postRepo.save(post);

    }

    public Post downvote(Long postId, Long userId) {

        Post post = postRepo.findByPostId(postId).orElse(null);
        if (post == null || !post.getActive())
            throw new NotFoundException("Post with id " + postId + " doesn't exist");
        VoteStatus voteStatus = voteStatusRepo.find(userId, postId).orElse(null);

        if (voteStatus == null) {
            voteStatus = new VoteStatus(userId, postId, true, false, true);
            post.setDownvotes(post.getDownvotes() + 1);
            voteStatusRepo.save(voteStatus);

        } else {

            if (voteStatus.getDownvoted()) {
                throw new InvalidRequestException("You have already downvoted.");

            } else if (voteStatus.getUpvoted()) {
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
}

