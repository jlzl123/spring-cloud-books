package cn.lsh.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lsh.dao.UserMapper;
import cn.lsh.entity.User;
import cn.lsh.entity.UserCriteria;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserInfoByName(String name) {
		// TODO Auto-generated method stub
		LOGGER.info("查询{}用户的信息",name);
		UserCriteria userCriteria=new UserCriteria();
		UserCriteria.Criteria criteria=userCriteria.createCriteria();
		criteria.andNameEqualTo(name);
		List<User> users=userMapper.selectByExample(userCriteria);
		return (users!=null) ? (!users.isEmpty()?users.get(0):null) : null;
	}

}
