package rs.webshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rs.webshop.domain.User;
import rs.webshop.dto.user.CreateUserCmd;
import rs.webshop.dto.user.UpdateUserCmd;
import rs.webshop.dto.user.UserInfo;
import rs.webshop.dto.user.UserResult;
import rs.webshop.dto.user.UserView;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "payPalAccount", source = "payPalAccount")
  User createUserCmdToUser(CreateUserCmd cmd);

  List<UserResult> listUserToListUserResult(List<User> users);

  @Mapping(target = "payPalAccountInfo", source = "payPalAccount")
  UserInfo userToUserInfo(User user);

  void updateUserCmdToUser(@MappingTarget User user, UpdateUserCmd cmd);
}
