package dev.mohammad.dreamshopes.service.UserService;

import dev.mohammad.dreamshopes.exception.AlreadyExistsException;
import dev.mohammad.dreamshopes.exception.ResourceNotFoundException;
import dev.mohammad.dreamshopes.model.User;
import dev.mohammad.dreamshopes.repository.UserRepository;
import dev.mohammad.dreamshopes.request.CreateUserRequest;
import dev.mohammad.dreamshopes.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req ->{
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Email  " +  request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest rqquest, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser->{
                    existingUser.setFirstName(rqquest.getFirstName());
                    existingUser.setLastName(rqquest.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(()-> new ResourceNotFoundException("The user is not found"));
    }

    @Override
    public void deleteUser(Long userId) {
    userRepository.findById(userId)
            .ifPresentOrElse(userRepository::delete, ()->{
                throw new ResourceNotFoundException("User " + userId + " not found");});
    }
}
