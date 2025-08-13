package sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule.dto.user.CreateUserRequestDto;
import sparta.schedule.dto.user.UpdateUserRequestDto;
import sparta.schedule.dto.user.UserResponseDto;
import sparta.schedule.entity.User;
import sparta.schedule.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(final CreateUserRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 유저명입니다.");
        }

        User user = new User(request.getUsername(), request.getEmail());
        userRepository.save(user);

        return UserResponseDto.from(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(final Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto updateUser(final Long userId, final UpdateUserRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 유저명입니다.");
        }

        user.update(request.getUsername(), request.getEmail());
        return UserResponseDto.from(user);
    }

    @Transactional
    public void deleteUser(final Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        userRepository.delete(user);
    }
}
