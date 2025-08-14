package sparta.schedule.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule.dto.user.*;
import sparta.schedule.entity.User;
import sparta.schedule.global.PasswordEncoder;
import sparta.schedule.global.SessionConst;
import sparta.schedule.global.exception.UnAuthorizedException;
import sparta.schedule.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signup(final CreateUserRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 유저명입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getUsername(), request.getEmail(), encodedPassword);
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

    @Transactional(readOnly = true)
    public LoginResponseDto login(final LoginRequestDto request, final HttpServletRequest httpRequest) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnAuthorizedException("존재하지 않는 이메일입니다."));


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnAuthorizedException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER, user.getId());

        return LoginResponseDto.from(user);
    }
}
