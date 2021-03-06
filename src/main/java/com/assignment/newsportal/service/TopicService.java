package com.assignment.newsportal.service;


import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.TopicDTO;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Topic;

import com.assignment.newsportal.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TopicService {

    @Autowired
    TopicRepo topicRepo;


    public Topic createTopic(String t,Long userId) {

        Topic topic = topicRepo.findBytopic(t).orElse(null);
        if ((topic != null)&&(topic.getActive())) {
            throw new DuplicateDataException("Topic is already present.");

        }
        else if((topic!=null)&&(!topic.getActive())) {
            topic.setActive(true);
            topic.setCreatedBy(userId);
            topic.setUpdatedBy(userId);
            return topicRepo.save(topic);
        }
        Topic newTopic=new Topic();
        newTopic.setTopic(t);
        newTopic.setActive(true);
        newTopic.setCreatedBy(userId);
        newTopic.setUpdatedBy(userId);
        return topicRepo.save(newTopic);
    }

    public Page<Topic> getTopics(Pageable pageable) {
        return topicRepo.getActiveTopics(pageable);
    }



    public void remove(Long topicId,Long userId) {
        Topic topic = topicRepo.findBytopicid(topicId).orElse(null);
        if(topic==null||!topic.getActive())
                throw new NotFoundException("Topic Not Found with Id: " + topicId);
        topic.setActive(false);
        topic.setUpdatedBy(userId);
        topicRepo.save(topic);
    }

    public Topic updateTopic(Long topicId, TopicDTO topicDTO,Long userId) {
        Topic topic= topicRepo.findBytopicid(topicId).orElse(null);
        if(topic==null||!topic.getActive())
            throw new NotFoundException("Topic Not Found with Id: " + topicId);

        if(topic.getTopic().equals(topicDTO.getTopic()))
            throw new DuplicateDataException("Topic is already present.");
        topic.setTopic(topicDTO.getTopic());
        topic.setUpdatedBy(userId);
        return topicRepo.save(topic);
    }

    public Page<Hashtag> getTopicHashtags(Long topicId, Pageable pageable) {
        Topic topic=topicRepo.findBytopicid(topicId).orElse(null);
        if(topic==null||!topic.getActive())
            throw new NotFoundException("Topic with id "+topicId+" does not exist");
        return topicRepo.getTopicHashtags(topicId, pageable);
    }
}
