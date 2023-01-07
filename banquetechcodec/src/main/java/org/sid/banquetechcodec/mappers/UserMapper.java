package org.sid.banquetechcodec.mappers;

import org.mapstruct.Mapper;
import org.sid.banquetechcodec.dto.UserRequestDTO;
import org.sid.banquetechcodec.dto.UserResponseDTO;
import org.sid.banquetechcodec.entities.User;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestDTOToUser(UserRequestDTO userRequestDTO);
    UserResponseDTO userToUserResponseDTO(User user);
}
