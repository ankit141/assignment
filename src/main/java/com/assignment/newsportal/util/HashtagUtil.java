package com.assignment.newsportal.util;

import com.assignment.newsportal.dto.request.HashtagDTO;
import com.assignment.newsportal.dto.request.PostDTO;
import com.assignment.newsportal.entity.Hashtag;
import com.assignment.newsportal.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HashtagUtil {
    @Autowired
    private ModelMapper modelMapper;

    public Hashtag convertToEntity(HashtagDTO hashtagDTO) {
        return modelMapper.map(hashtagDTO, Hashtag.class);
    }

    public HashtagDTO convertToDTO(Hashtag hashtag) {
        return modelMapper.map(hashtag, HashtagDTO.class);
    }


}