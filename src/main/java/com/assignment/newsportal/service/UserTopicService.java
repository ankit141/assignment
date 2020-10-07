package com.assignment.newsportal.service;


import com.assignment.newsportal.Exception.DuplicateDataException;
import com.assignment.newsportal.Exception.NotFoundException;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.entity.UserTopicMap;
import com.assignment.newsportal.repo.TopicRepo;
import com.assignment.newsportal.repo.UserRepo;
import com.assignment.newsportal.repo.UserTopicRepo;
import com.assignment.newsportal.security.jwt.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class UserTopicService {

    @Autowired
    UserTopicRepo userTopicRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    UserRepo userRepo;

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    public UserTopicMap add(Long userId, Long topicId) {
//        Topic topic = topicRepo.findBytopicid(topicId).orElse(null);
//        if (topic == null || !topic.getActive())
//            throw new NotFoundException("Topic with id " + topicId + " doesn't exist.");
//
//        UserTopicMap userTopicMap=userTopicRepo.find(userId, topicId).orElse(null);
//        if((userTopicMap!=null)&&(userTopicMap.getIsActive())) {
//            logger.info("Topic {} is already followed by you.", );
//            return null;
//        }
//
//        if((userTopicMap!=null)&&(userTopicMap.getIsActive()==false)) {
//
//            userTopicMap.setIsActive(true);
//            return userTopicRepo.save(userTopicMap);
//
//        }
//        User user = userRepo.findByUserId(userId).orElse(null);
//        userTopicMap = new UserTopicMap(userId, user.getName(), topicId, topic.getTopic(), true);
//        return userTopicRepo.save(userTopicMap);
//    }

    public UserTopicMap add(Long userId, String name) {
        if(name.equals("")) {
            logger.error("Topic {} has no name", name);
            return null;
        }
        Topic topic = topicRepo.findBytopic(name).orElse(null);
        if (topic == null || !topic.getActive())
            logger.info("Topic {} does not exist", name);

        UserTopicMap userTopicMap=userTopicRepo.findbyname(userId, name).orElse(null);
        if((userTopicMap!=null)&&(userTopicMap.getIsActive())) {

            logger.info("Topic {} is already followed by you.", name);
            return null;
        }
        if((userTopicMap!=null)&&(userTopicMap.getIsActive()==false)) {

            userTopicMap.setIsActive(true);
            userTopicMap.setCreatedBy(userId);
            userTopicMap.setUpdatedBy(userId);
            return userTopicRepo.save(userTopicMap);

        }


        User user = userRepo.findByUserId(userId).orElse(null);
        userTopicMap = new UserTopicMap(userId, user.getName(), topic.getTopicId(),name, true);
        userTopicMap.setCreatedBy(userId);
        userTopicMap.setUpdatedBy(userId);
        return userTopicRepo.save(userTopicMap);
    }
    public Page<UserTopicMap> getTopics(Long userId, Pageable pageable) {
     return userTopicRepo.getTopics(userId,pageable);


    }

    public void remove(Long userId, Long topicId) {


        Topic topic=topicRepo.findBytopicid(topicId).orElse(null);
        if(topic==null||!(topic.getActive()))
            throw new NotFoundException("Topic with id "+topicId+" doesn't exist");
        UserTopicMap userTopicMap=userTopicRepo.find(userId, topicId).orElse(null);
        if(userTopicMap==null||!(userTopicMap.getIsActive()))
            throw new NotFoundException("Topic with id "+topicId+" is not followed by you.");
        userTopicMap.setIsActive(false);
        userTopicMap.setUpdatedBy(0L);
        userTopicRepo.save(userTopicMap);
    }
}
