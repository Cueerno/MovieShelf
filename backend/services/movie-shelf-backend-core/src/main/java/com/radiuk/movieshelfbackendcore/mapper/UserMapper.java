package com.radiuk.movieshelfbackendcore.mapper;

import com.radiuk.movieshelfbackendcore.dto.UserDto;
import com.radiuk.movieshelfbackendcore.dto.UserRegistrationDto;
import com.radiuk.movieshelfbackendcore.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    User registrationDtoToUser(UserRegistrationDto userRegistrationDto);

}
