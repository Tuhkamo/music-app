package hh.music.app.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) // JUnit5 eli Jupiter
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;


	@Test
	public void contextLoads() {
		assertThat(userRepository).isNotNull();
	}
	
	@Test
    public void testFindByUsername() {
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPasswordHash("testpassword");
        testUser.setRole("testrole");
        userRepository.save(testUser);
        
        testUser = userRepository.findByUsername("falseuser");
        assertThat(testUser).isNull();

        testUser = userRepository.findByUsername("testuser");

        assertThat(testUser).isNotNull();
        assertThat("testuser".equals(testUser.getUsername()));
        assertThat("testpassword".equals(testUser.getPasswordHash()));
        assertThat("testrole".equals(testUser.getRole()));
    }
	
}
