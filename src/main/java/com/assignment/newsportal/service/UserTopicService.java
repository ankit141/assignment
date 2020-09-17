package com.assignment.newsportal.service;


import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.entity.UserTopicMap;
import com.assignment.newsportal.repo.TopicRepo;
import com.assignment.newsportal.repo.UserRepo;
import com.assignment.newsportal.repo.UserTopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserTopicService {

    @Autowired
    UserTopicRepo userTopicRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    UserRepo userRepo;

    public UserTopicMap add(Long userId, Long topicId) {
        Topic topic = topicRepo.findByTopicId(topicId).orElse(null);
        if (topic == null || !topic.getActive())
            throw new NotFoundException("Topic with id " + topicId + " doesn't exist.");

        UserTopicMap userTopicMap=userTopicRepo.find(userId, topicId).orElse(null);
        if((userTopicMap!=null)&&(userTopicMap.getIsActive()))

            throw new DuplicateDataException("Topic is already followed by you.");

        User user = userRepo.findByUserId(userId).orElse(null);
        userTopicMap = new UserTopicMap(userId, user.getName(), topicId, topic.getTopic(), true);
        return userTopicRepo.save(userTopicMap);
    }

    public Page<UserTopicMap> getTopics(Long userId, Pageable pageable) {
     return userTopicRepo.getTopics(userId,pageable);


    }

    public void remove(Long userId, Long topicId) {

        Topic topic=topicRepo.findByTopicId(topicId).orElse(null);
        if(topic==null||!(topic.getActive()))
            throw new NotFoundException("Topic with id "+topicId+" doesn't exist");
        UserTopicMap userTopicMap=userTopicRepo.find(userId, topicId).orElse(null);
        if(userTopicMap==null||!(userTopicMap.getIsActive()))
            throw new NotFoundException("Topic with id "+topicId+" is not followed by you.");
        userTopicMap.setIsActive(false);
        userTopicRepo.save(userTopicMap);
    }
}
