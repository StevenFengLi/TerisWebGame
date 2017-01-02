package HTTPServer.BusinessLogicLayer.Entity;

import HTTPServer.BusinessLogicLayer.Exception.*;

import HTTPServer.DataAccessLayer.Entity.DAOFactory.*;
import HTTPServer.DataAccessLayer.Entity.DAO.*;
import HTTPServer.DataAccessLayer.Entity.DAOVO.*;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

public class UserList {
    // 添加用户
    public User addUser(String userPhoneNumber, String userPassword, String userNickname)
            throws UserPhoneNumberExistedException, DataAccessLayerException {
        User user;

        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();

            if (userDAO.isUserPhoneNumberExisted(userPhoneNumber)) {
                throw new UserPhoneNumberExistedException();
            }

            UserVO userVO =  userDAO.addUser(userPhoneNumber, userPassword, userNickname);
            user = changeUserVOToUser(userVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return user;
    }

    // 根据用户手机号码得到用户
    public User getUserByUserPhoneNumber(String userPhoneNumber)
            throws UserPhoneNumberNotExistedException, DataAccessLayerException {
        User user;

        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();

            if (!userDAO.isUserPhoneNumberExisted(userPhoneNumber)) {
                throw new UserPhoneNumberNotExistedException();
            }

            UserVO userVO =  userDAO.getUserByUserPhoneNumber(userPhoneNumber);
            user = changeUserVOToUser(userVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return user;
    }

    // 根据用户标识符得到用户
    public User getUserByUserID(long userID)
            throws UserIDNotExistedException, DataAccessLayerException {
        User user;

        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();

            if (!userDAO.isUserIDExisted(userID)) {
                throw new UserIDNotExistedException();
            }

            UserVO userVO =  userDAO.getUserByUserID(userID);
            user = changeUserVOToUser(userVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return user;
    }

    private User changeUserVOToUser(UserVO userVO){
        User user = new User();

        user.setUserID(userVO.getUserID());
        user.setUserNickname(userVO.getUserNickname());
        user.setUserPhoneNumber(userVO.getUserPhoneNumber());
        user.setUserPassword(userVO.getUserPassword());
        user.setUserHeadShowURL(userVO.getUserHeadShowURL());
        user.setUserBirthday(userVO.getUserBirthday());
        if (userVO.getUserSex() == UserVO.DATABASE_USER_SEX_FEMALE) {
            user.setUserSex(User.UserUserSex.USER_USER_SEX_FEMALE);

        } else if (userVO.getUserSex() == UserVO.DATABASE_USER_SEX_MALE) {
            user.setUserSex(User.UserUserSex.USER_USER_SEX_MALE);

        } else if (userVO.getUserSex() == UserVO.DATABASE_USER_SEX_UNKNOWN) {
            user.setUserSex(User.UserUserSex.USER_USER_SEX_UNKNOWN);
        }
        user.setUserAreaProvince(userVO.getUserAreaProvince());
        user.setUserAreaCity(userVO.getUserAreaCity());
        user.setUserDescription(userVO.getUserDescription());

        return user;
    }
}
