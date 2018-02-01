package java.edu.neu.csye6225.spring2018.service;

import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserService();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(UserServiceTest.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() {
        User user = new User("test123@qq.com", "test123");
        userService.save(user);
//        verify(userService, times(1).save(user));
    }

    @Test
    public void existsByEmail() {
        User user = new User("test123@qq.com", "test123");
        userService.save(user);
        when(userService.existsByEmail("test123@qq.com")).thenReturn(true);
    }

}