package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.InvalidRequestException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.HashtagDTO;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.PostHashtagMap;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.repo.HashtagRepo;
import com.assignment.newsportal.repo.PostHashtagRepo;
import com.assignment.newsportal.repo.PostRepo;
import com.assignment.newsportal.repo.TopicRepo;
import com.assignment.newsportal.util.HashtagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class HashtagService {

    @Autowired
    HashtagRepo hashtagRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    PostHashtagRepo postHashtagRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    HashtagUtil hashtagUtil;

    public Hashtag createHashtag(HashtagDTO hashtagDTO, Long topicId,Long userId) {

        String hashtagName=hashtagDTO.getHashtag();

        if(!hashtagName.startsWith("#"))
            throw new InvalidRequestException("Hashtag name should start with #");

        Topic topic= topicRepo.findBytopicid(topicId).orElse(null);

        if(topic==null||!topic.getActive()){
            throw new NotFoundException("Topic with id "+topicId+" doesn't exist.");
        }
        Hashtag hashtag= hashtagRepo.findByHashtag(hashtagName).orElse(null);
        if((hashtag!=null)&&(hashtag.getActive())){
            throw new DuplicateDataException("Hashtag already present.");
        }

        else if((hashtag!=null)&&(!hashtag.getActive())&&(hashtag.getTopicId()==topicId)){
            hashtag.setActive(true);
            hashtag.setCreatedBy(userId);
            hashtag.setUpdatedBy(userId);
            return hashtagRepo.save(hashtag);
        }

        hashtag=new Hashtag(topicId,hashtagName,true);
        hashtag.setCreatedBy(userId);
        hashtag.setUpdatedBy(userId);
        return hashtagRepo.save(hashtag);
    }

    public Page<Hashtag> getHashtags(Pageable pageable) {

        return hashtagRepo.getActiveHashtags(pageable);
    }

    @Transactional
    public void remove(Long hashtagId,Long userId){

        Hashtag hashtag = hashtagRepo.findByHashtagId(hashtagId).orElse(null);
        if(hashtag==null||!hashtag.getActive())
            throw new NotFoundException("Hashtag Not Found with Id: " + hashtagId);



        List<PostHashtagMap> postHashtagMapList=postHashtagRepo.findByHashtagId(hashtagId);
        for(PostHashtagMap p : postHashtagMapList){
            Post post=postRepo.findByPostId(p.getPostId()).orElse(null);
            Set<String> hashtags=post.getHashtags();
            hashtags.remove(hashtag.getHashtag());
            post.setHashtags(hashtags);
            post.setUpdatedBy(userId);
            postRepo.save(post);
            postHashtagRepo.delete(p);
        }
        hashtag.setActive(false);
        hashtag.setUpdatedBy(userId);
        hashtagRepo.save(hashtag);
    }

    @Transactional
    public Hashtag updateHashtag(Long hashtagId, HashtagDTO hashtagDTO,Long userId) {


        Hashtag hashtag= hashtagRepo.findByHashtagId(hashtagId).orElse(null);
        if(hashtag==null||!hashtag.getActive())
            throw new NotFoundException("Hashtag Not Found with Id: " + hashtagId);

        String name=hashtagDTO.getHashtag();

        if(!name.startsWith("#"))
            throw new InvalidRequestException("Hashtag name should start with #");

        Hashtag h=hashtagRepo.findByHashtag(name).orElse(null);
        if(h!=null)
            throw new DuplicateDataException("Hashtag is already present.");


        List<PostHashtagMap> postHashtagMapList=postHashtagRepo.findByHashtagId(hashtagId);

        for(PostHashtagMap p : postHashtagMapList){
            Post post=postRepo.findByPostId(p.getPostId()).orElse(null);
            Set<String> hashtags=post.getHashtags();
            hashtags.remove(hashtag.getHashtag());
            hashtags.add(name);
            post.setHashtags(hashtags);
            post.setUpdatedBy(userId);
            postRepo.save(post);
        }

        postHashtagRepo.update(hashtagId,name,userId);
        hashtag.setHashtag(name);
        hashtag.setUpdatedBy(userId);
        return hashtagRepo.save(hashtag);
    }
    }

