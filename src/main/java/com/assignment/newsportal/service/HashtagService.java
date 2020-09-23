package com.assignment.newsportal.service;

import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.MissingDetailException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.HashtagDTO;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.repo.HashtagRepo;
import com.assignment.newsportal.repo.TopicRepo;
import com.assignment.newsportal.util.HashtagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class HashtagService {

    @Autowired
    HashtagRepo hashtagRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    HashtagUtil hashtagUtil;

    public Hashtag createHashtag(HashtagDTO hashtagDTO, Long topicId) {
        if(hashtagDTO.getHashtag().equals(""))
            throw new MissingDetailException("Hashtag is compulsory.");
        Topic topic= topicRepo.findBytopicid(topicId).orElse(null);

        if(topic==null||!topic.getActive()){
            throw new NotFoundException("Topic with id "+topicId+" doesn't exist.");
        }
        Hashtag hashtag= hashtagRepo.findByHashtag(hashtagDTO.getHashtag()).orElse(null);
        if((hashtag!=null)&&(hashtag.getActive()==true)){
            throw new DuplicateDataException("Hashtag already present.");
        }
        //hashtag=hashtagUtil.convertToEntity(hashtagDTO);
        hashtag=new Hashtag(topicId,hashtagDTO.getHashtag(),true);
        return hashtagRepo.save(hashtag);



    }

    public Page<Hashtag> getHashtags(Pageable pageable) {

        return hashtagRepo.getActiveHashtags(pageable);
    }

    public void remove(Long hashtagId) {

        Hashtag hashtag = hashtagRepo.findByHashtagId(hashtagId).orElse(null);
        if(hashtag==null||!hashtag.getActive())
            throw new NotFoundException("Hashtag Not Found with Id: " + hashtagId);
        hashtag.setActive(false);
        hashtagRepo.save(hashtag);
    }

    public Hashtag updateHashtag(Long hashtagId, HashtagDTO hashtagDTO) {

        Hashtag hashtag= hashtagRepo.findByHashtagId(hashtagId).orElse(null);
        if(hashtag==null||!hashtag.getActive())
            throw new NotFoundException("Hashtag Not Found with Id: " + hashtagId);
        if(hashtagDTO.getHashtag().equals("")){
            throw new MissingDetailException("Hashtag name is mandatory.");
        }
        if(hashtagRepo.existsByHashtag(hashtagDTO.getHashtag()))
            throw new DuplicateDataException("Hashtag is already present.");
        hashtag.setHashtag(hashtagDTO.getHashtag());
        return hashtagRepo.save(hashtag);
    }
    }

