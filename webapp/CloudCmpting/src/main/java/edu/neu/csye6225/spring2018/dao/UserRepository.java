package edu.neu.csye6225.spring2018.dao;

import edu.neu.csye6225.spring2018.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findByEmailAndPassword(String email, String password);
    public boolean existsByEmail(String email);
    public String getByPassword(String password);
    public User findByEmail(String email);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE user c SET c.aboutMe = :aboutMe, c.imageFilePath = :imageFilePath WHERE c.id = :id")
    public int updateUser(@Param("id") int id, @Param("aboutMe") String aboutMe, @Param("imageFilePath") String imageFilePath);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE user c SET c.imageFilePath = :imageFilePath WHERE c.id = :id")
    public int updateUserImage(@Param("id") int id, @Param("imageFilePath") String imageFilePath);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE user c SET c.aboutMe = :aboutMe WHERE c.id = :id")
    public int updateaboutMe(@Param("id") int id,@Param("aboutMe") String aboutMe);


//    @Query("delete from user u where u.imageFilePath = :imageFilePath")
//    void delete(int id);

}