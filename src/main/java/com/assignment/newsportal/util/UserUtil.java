package com.assignment.newsportal.util;


import com.assignment.newsportal.dto.request.UserDTO;
import com.assignment.newsportal.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    

        @Autowired
        private ModelMapper modelMapper;

        public User convertToEntity(UserDTO userDTO) {
            return modelMapper.map(userDTO, User.class);
        }

        public UserDTO convertToDTO(User user) {
            return modelMapper.map(user, UserDTO.class);
        }

    }


