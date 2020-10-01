package com.assignment.newsportal.util;

import com.assignment.newsportal.dto.request.TopicDTO;
import com.assignment.newsportal.dto.request.UserDTO;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.entity.User;
import com.assignment.newsportal.entity.UserTopicMap;
import org.springframework.stereotype.Component;

@Component
public class UserTopicUtil {

    public TopicDTO convertToDTO(UserTopicMap userTopicMap) {

        return new TopicDTO(userTopicMap.getTopicId(),userTopicMap.getTopic());
    }
}
