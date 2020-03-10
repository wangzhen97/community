package life.wz.community.service;

import life.wz.community.mapper.UserMapper;
import life.wz.community.model.User;
import life.wz.community.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public User createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users =userMapper.selectByExample(userExample);
        User userDB=users.get(0);
        if(userDB==null){
            BeanUtils.copyProperties(user,userDB);
            userDB.setGmtCreate(System.currentTimeMillis());
            userDB.setGmtModified(user.getGmtCreate());
            userMapper.insert(userDB);
        }else{
            userDB.setGmtModified(System.currentTimeMillis());
            userDB.setToken(user.getToken());
            userDB.setAvatarUrl(user.getAvatarUrl());
            userDB.setName(user.getName());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(userDB.getId());
            userMapper.updateByExampleSelective(userDB, example);
        }
        return userDB;
    }
}
