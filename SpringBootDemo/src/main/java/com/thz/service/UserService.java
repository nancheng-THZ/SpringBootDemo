package com.thz.service;

import com.thz.entity.User;
import com.thz.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * @Author:thz
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Resource
    RedisTemplate redisTemplate;



    public User Sel(int id){

        String key = "id" + id ;
        ValueOperations<String, User> operations = redisTemplate.opsForValue();

        //缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey) {
            User user = operations.get(key);
            LOGGER.info("UserService.Sel:从缓存中获取了用户 >> " + user.toString());
            return user;
        }
        //从DB中获取城市信息
        User user = userMapper.Sel(id);
        //插入缓存
        operations.set(key,user,10, TimeUnit.SECONDS);
        LOGGER.info("UserService.Sel:从数据库中获取了用户 >> " + user.toString());
        return user;
    }


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public User queryById(Integer id) {
        String key = "id" + id ;
        ValueOperations<String, User> operations = redisTemplate.opsForValue();

        //缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey) {
            User user = operations.get(key);
            LOGGER.info("UserService.Sel:从缓存中获取了用户 >> " + user.toString());
            return user;
        }
        //从DB中获取城市信息
        User user = userMapper.queryById(id);
        //插入缓存
        operations.set(key,user,10, TimeUnit.SECONDS);
        LOGGER.info("UserService.Sel:从数据库中获取了用户 >> " + user.toString());
        return user;
    }


    public List<User> findAll(){
        return userMapper.findAll();
    }

    public List<User> queryByUserName(String userName) {
        return this.userMapper.queryByUserName(userName);
    }


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    public int insert(User user) {
        return this.userMapper.insert(user);
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    public int update(User user) {
        int result = userMapper.update(user);
        //如果缓存存在，删除缓存
        String key = "id" + user.getId();
        boolean haskey = redisTemplate.hasKey(key);
        if(haskey) {
            redisTemplate.delete(key);
            LOGGER.info("UserService.update:从缓存中删除了用户 >>" + user.toString());
        }
//        return this.userMapper.update(user);
        return result;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer id) {
        Boolean flag = this.userMapper.deleteById(id) > 0;
        //如果缓存存在，删除缓存
        String key = "id" + id;
        boolean haskey = redisTemplate.hasKey(key);
        if(haskey) {
            redisTemplate.delete(key);
            LOGGER.info("UserService.update:从缓存中删除了用户,ID为： >>" + id);
        }
        return flag;
    }

}

