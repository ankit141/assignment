package com.assignment.newsportal.service;


import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.MissingDetailException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.dto.request.TopicDTO;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Topic;

import com.assignment.newsportal.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Component;



@Component
public class TopicService {

    @Autowired
    TopicRepo topicRepo;


    public Topic createTopic(Topic topic) {
        if (topic.getTopic().equals(""))
            throw new MissingDetailException("Topic is mandatory.");
        Topic topic1 = topicRepo.findByTopic(topic.getTopic()).orElse(null);
        if (topic1 == null || !topic1.getActive()) {
            topic.setActive(true);
            return topicRepo.save(topic);
        }
        else
        throw new DuplicateDataException("Topic is already present.");
    }

    public Page<Topic> getTopics(Pageable pageable) {
        return topicRepo.getActiveTopics(pageable);
    }



    public void remove(Long topicId) {
        Topic topic = topicRepo.findByTopicId(topicId).orElse(null);
        if(topic==null||!topic.getActive())
                throw new NotFoundException("Topic Not Found with Id: " + topicId);
        topic.setActive(false);
        topicRepo.save(topic);
    }

    public Topic updateTopic(Long topicId, TopicDTO topicDTO) {
        Topic topic= topicRepo.findByTopicId(topicId).orElse(null);
        if(topic==null||!topic.getActive())
            throw new NotFoundException("Topic Not Found with Id: " + topicId);
        if(topicDTO.getTopic().equals("")){
            throw new MissingDetailException("Topic is mandatory.");
        }
        if(topic.getTopic().equals(topicDTO.getTopic()))
            throw new DuplicateDataException("Topic is already present.");
        topic.setTopic(topicDTO.getTopic());
        return topicRepo.save(topic);
    }

    public Page<Hashtag> getTopicHashtags(Long topicId, Pageable pageable) {
        Topic topic=topicRepo.findByTopicId(topicId).orElse(null);
        if(topic==null||!topic.getActive())
            throw new NotFoundException("Topic with id "+topicId+" does not exist");
        return topicRepo.getTopicHashtags(topicId, pageable);
    }
}
