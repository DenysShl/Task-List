package org.example.tasklist.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.repository.TaskRepository;
import org.example.tasklist.repository.UserRepository;
import org.example.tasklist.security.JwtTokenProvider;
import org.example.tasklist.security.JwtUserDetailsService;
import org.example.tasklist.service.ImageService;
import org.example.tasklist.service.TaskService;
import org.example.tasklist.service.UserService;
import org.example.tasklist.service.impl.AuthServiceImpl;
import org.example.tasklist.service.impl.ImageServiceImpl;
import org.example.tasklist.service.impl.TaskServiceImpl;
import org.example.tasklist.service.impl.UserServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final AuthenticationManager authenticationManager;

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "dmdqYmhqbmttYmNhamNjZWhxa25hd2puY2xhZWtic3ZlaGtzYmJ1dg=="
        );
        return jwtProperties;
    }

    @Bean
    public UserDetailsService userDetailsService(final UserRepository userRepository) {
        return new JwtUserDetailsService(userService(userRepository));
    }

    @Bean
    public MinioClient minioClient() {
        return Mockito.mock(MinioClient.class);
    }

    @Bean
    public MinioProperties minioProperties() {
        MinioProperties properties = new MinioProperties();
        properties.setBucket("images");
        return properties;
    }

//    @Bean
//    public Configuration configuration() {
//        return Mockito.mock(Configuration.class);
//    }
//
//    @Bean
//    public JavaMailSender mailSender() {
//        return Mockito.mock(JavaMailSender.class);
//    }
//
//    @Bean
//    @Primary
//    public MailServiceImpl mailService() {
//        return new MailServiceImpl(configuration(), mailSender());
//    }

    @Bean
    @Primary
    public ImageService imageService() {
        return new ImageServiceImpl(minioProperties(), minioClient());
    }

    @Bean
    public JwtTokenProvider tokenProvider(final UserRepository userRepository) {
        return new JwtTokenProvider(jwtProperties(),
                userDetailsService(userRepository),
                userService(userRepository));
    }

    @Bean
    @Primary
    public UserService userService(final UserRepository userRepository) {
        return new UserServiceImpl(
                userRepository,
                testPasswordEncoder()
        );
    }

    @Bean
    @Primary
    public TaskService taskService(final TaskRepository taskRepository) {
        return new TaskServiceImpl(taskRepository, userService(userRepository), imageService());
    }

    @Bean
    @Primary
    public AuthServiceImpl authService(
            final UserRepository userRepository,
            final AuthenticationManager authenticationManager
    ) {
        return new AuthServiceImpl(
                authenticationManager,
                userService(userRepository),
                tokenProvider(userRepository)
        );
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public TaskRepository taskRepository() {
        return Mockito.mock(TaskRepository.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }
}
