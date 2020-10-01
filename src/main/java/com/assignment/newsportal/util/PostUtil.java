package com.assignment.newsportal.util;

import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.dto.request.UserDTO;
import com.assignment.newsportal.entity.Post;
import com.assignment.newsportal.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostUpdate;

@Component
public class PostUtil {
    @Autowired
    private ModelMapper modelMapper;

    public Post convertToEntity(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }

    public PostDTO convertToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }


}
