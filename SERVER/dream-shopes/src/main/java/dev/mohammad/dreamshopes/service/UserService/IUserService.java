package dev.mohammad.dreamshopes.service.UserService;

import dev.mohammad.dreamshopes.model.User;
import dev.mohammad.dreamshopes.request.CreateUserRequest;
import dev.mohammad.dreamshopes.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest user,  Long userId);
    void deleteUser(Long userId);


}
