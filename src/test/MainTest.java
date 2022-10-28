import com.darkneees.soapfrontservice.entity.Role;
import com.darkneees.soapfrontservice.entity.Social;
import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.mapper.ListUserMapper;
import com.darkneees.soapfrontservice.service.RoleServiceImpl;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import users_soap.api.UserInfo;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {

    UserServiceImpl userService = UserServiceImpl.getInstance();
    RoleServiceImpl roleService = new RoleServiceImpl();

    @Test
    public void getAllUsers(){
        List<User> users = userService.getAllUsers().join();
        System.out.println(users.size());
    }

    @Test
    public void getUserByUsername() {
        User user = userService.getUserByUsername("dark").join();
        System.out.println(user);
    }

    @Test
    public void addUser() {
        User user = new User();

        user.setUsername("dark11");
        user.setPassword("pass");
        user.setFirstName("Andrey11");
        user.setSecondName("Nesterov11");
        user.setAge(10);

//        user.getRoleSet().add(new Role(1L));
//        user.getSocialSet().add(new Social("VK", "VK"));

        userService.addUser(user).join();
    }

    @Test
    public void deleteRoleUser() {
        userService.deleteRoleUser("darkneees", 1).join();
    }

    @Test
    public void addRoleUser(){
        userService.addRoleUser("darkneees", 1).join();
        userService.addRoleUser("darkneees", 2).join();
    }

    @Test
    public void editUser() {
        User user = new User();

        user.setUsername("dark11");
        user.setPassword("Nesterov");
        user.setFirstName("dsadas");
        user.setSecondName("pass");
        user.setAge(20);

        userService.editUser(user).join();
    }

    @Test
    public void deleteSocial() {
        userService.deleteSocialUser("dark11", "VK").join();
    }

    @Test
    public void addSocial() {
        userService.addSocialUser("dark11", new Social("VK", "VK")).join();
    }

    @Test
    public void deleteUser() {
        userService.deleteUserByUsername("dark11").join();
    }

    @Test
    public void addRole(){
        Role role = new Role();

        role.setId(5L);
        role.setNameRole("PAR");
        role.setPrettyNameRole("PARROT");
        roleService.addRole(role).join();
    }

    @Test
    public void getAllRoles() {
        List<Role> roles = roleService.getAllRoles().join();
        System.out.println(roles);
    }

    @Test
    public void addMoreUsers() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            Runnable runnable = () -> {
                User user = new User();
                user.setUsername("dark_" + finalI);
                user.setFirstName("Andrey_" + finalI);
                user.setSecondName("Nesterov_" + finalI);
                user.setPassword("Pass_" + finalI);
                user.setAge(finalI);

                user.setRoleSet(Set.of(new Role(1L), new Role(2L)));
                userService.addUser(user).join();
                latch.countDown();
            };
            executorService.submit(runnable);
        }
        try {
            latch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    @Test
    public void testWithMapper(){

        List<User> users = userService.getAllUsers().join();
        List<UserInfo> userInfos = ListUserMapper.INSTANCE.toListUserInfos(users);
        System.out.println(userInfos.size());

    }

    @Test
    public void testWithoutMapper(){
        List<User> users = userService.getAllUsers().join();
        List<UserInfo> userInfos = users.parallelStream().map((user) -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(user.getUsername());
            userInfo.setFirstName(user.getFirstName());
            userInfo.setPassword(user.getPassword());
            userInfo.setSecondName(user.getSecondName());
            userInfo.setAge(user.getAge());

            return userInfo;
        }).toList();
        System.out.println(userInfos.size());
    }


}
