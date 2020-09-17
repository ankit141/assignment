package com.assignment.newsportal.util;

import com.assignment.newsportal.dto.request.TopicDTO;
import com.assignment.newsportal.dto.request.UserDTO;
import com.assignment.newsportal.entity.Topic;
import com.assignment.newsportal.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicUtil {


    @Autowired
    private ModelMapper modelMapper;

    public Topic convertToEntity(TopicDTO topicDTO) {
        return modelMapper.map(topicDTO, Topic.class);
    }

    public TopicDTO convertToDTO(Topic topic) {
        return modelMapper.map(topic, TopicDTO.class);
    }

}
