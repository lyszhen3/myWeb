package com.lin.shiro;

import com.lin.data.beans.User;
import com.lin.data.examples.UserExample;
import com.lin.data.mappers.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by pc on 2017-10-24.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class MyselfRealm extends AuthorizingRealm {

    UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andLoginNameEqualTo(uptoken.getUsername())
                .andLoginPassEqualTo(String.valueOf(uptoken.getPassword()));
        List<User> users = userMapper.selectByExample(userExample);

        if (users.isEmpty()) {
            throw new UnknownAccountException("用户不存在");
        }
        User user = users.get(0);
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setUserId(user.getUserId());
        shiroUser.setUserName(user.getUserName());

        return new SimpleAuthenticationInfo(shiroUser, user.getLoginPass(), getName());
    }

    public static void main(String[] args) {
    }
}
