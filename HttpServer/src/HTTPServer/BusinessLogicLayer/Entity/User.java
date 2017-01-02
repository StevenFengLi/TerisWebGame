package HTTPServer.BusinessLogicLayer.Entity;

import HTTPServer.BusinessLogicLayer.Exception.*;

import HTTPServer.DataAccessLayer.Entity.DAOFactory.*;
import HTTPServer.DataAccessLayer.Entity.DAO.*;
import HTTPServer.DataAccessLayer.Entity.DAOVO.*;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

public class User {
    // 验证密码
    public boolean validatePassword(String userPassword) {
        return userPassword.equals(this.userPassword);
    }

    // 获取用户头像
    public void getUserHeadShow() {

    }

    // 获取用户的好友列表
    public List<Friend> getFriendList() throws DataAccessLayerException {
        List<Friend> friendList = new ArrayList<>();

        try {
            FriendshipDAO friendshipDAO = DAOFactory.getFriendshipDAOInstance();
            UserDAO userDAO = DAOFactory.getUserDAOInstance();

            List<Long> friendUserIDList = friendshipDAO.getFriendUserIDListByUserID(this.userID);
            for (Long friendUserID : friendUserIDList) {
                UserVO userVO = userDAO.getUserByUserID(friendUserID);
                friendList.add(changeUserVOToFriend(userVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return friendList;
    }

    // 根据好友标识符添加好友，并返回该好友
    public Friend addFriendByFriendUserID(long friendUserID)
            throws FriendUserIDSameAsMineException, UserIDNotExistedException,
            FriendshipExistedException, DataAccessLayerException {
        Friend friend;

        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();
            FriendshipDAO friendshipDAO = DAOFactory.getFriendshipDAOInstance();

            if (friendUserID == this.userID) {
                throw new FriendUserIDSameAsMineException();
            }

            if (!userDAO.isUserIDExisted(friendUserID)) {
                throw new UserIDNotExistedException();
            }

            if (friendshipDAO.isFriendshipExisted(this.userID, friendUserID)) {
                throw new FriendshipExistedException();
            }

            friendshipDAO.addFriendship(this.userID, friendUserID);
            UserVO userVO = userDAO.getUserByUserID(friendUserID);
            friend = changeUserVOToFriend(userVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return friend;
    }

    // 根据好友标识符删除好友
    public void deleteFriendByFriendUserID(long friendUserID)
            throws UserIDNotExistedException, FriendshipNotExistedException, DataAccessLayerException {
        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();
            FriendshipDAO friendshipDAO = DAOFactory.getFriendshipDAOInstance();

            if (!userDAO.isUserIDExisted(friendUserID)) {
                throw new UserIDNotExistedException();
            }

            if (!friendshipDAO.isFriendshipExisted(this.userID, friendUserID)) {
                throw new FriendshipNotExistedException();
            }

            friendshipDAO.deleteFriendship(this.userID, friendUserID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 根据另一位用户的标识符获取与另一位用户的私信列表
    public List<PersonalMessage> getPersonalMessageListByAnotherUserID(long anotherUserID)
            throws UserIDNotExistedException, DataAccessLayerException {
        List<PersonalMessage> personalMessageList = new ArrayList<>();

        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();
            PersonalMessageDAO personalMessageDAO = DAOFactory.getPersonalMessageInstance();

            if (!userDAO.isUserIDExisted(anotherUserID)) {
                throw new UserIDNotExistedException();
            }

            List<PersonalMessageVO> personalMessageVOList = personalMessageDAO.getPersonalMessageListByTwoUserIDs(this.userID, anotherUserID);
            for (PersonalMessageVO personalMessageVO : personalMessageVOList) {
                personalMessageList.add(changePersonalMessageVOToPersonalMessage(personalMessageVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return personalMessageList;
    }

    // 发送私信信息给另一位用户
    public PersonalMessage sendPersonalMessage(long receiverID, String text, Date time)
            throws OutOfLengthException, TimeAfterCurrentException,
            UserIDNotExistedException, DataAccessLayerException {
        PersonalMessage personalMessage;

        if (text.length() > 1000)
            throw new OutOfLengthException();

        if (time.after(new Date()))
            throw new TimeAfterCurrentException();

        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();
            PersonalMessageDAO personalMessageDAO = DAOFactory.getPersonalMessageInstance();

            if (!userDAO.isUserIDExisted(receiverID)) {
                throw new UserIDNotExistedException();
            }

            PersonalMessageVO personalMessageVO = personalMessageDAO.addPersonalMessage(this.userID, receiverID,text, new Timestamp(time.getTime()));
            personalMessage = changePersonalMessageVOToPersonalMessage(personalMessageVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return personalMessage;
    }

    // 根据私信标识符删除一条私信
    public void deletePersonalMessageByPersonalMessageID(long personalMessageID)
            throws PersonalMessageIDNotExistedException, PersonalMessageSenderNotMatchedException,
            DataAccessLayerException {
        try {
            PersonalMessageDAO personalMessageDAO = DAOFactory.getPersonalMessageInstance();

            if (!personalMessageDAO.isPersonalMessageExisted(personalMessageID)) {
                throw new PersonalMessageIDNotExistedException();
            }

            PersonalMessageVO personalMessageVO = personalMessageDAO.getPersonalMessageByPersonalMessageID(personalMessageID);
            if (personalMessageVO.getSenderUserID() != this.userID) {
                throw new PersonalMessageSenderNotMatchedException();
            }

            personalMessageDAO.deletePersonalMessageByPersonalMessageID(personalMessageID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 获取加入的群的列表
    public List<Group> getGroupListJoined() throws DataAccessLayerException {
        List<Group> groupList = new ArrayList<>();

        try {
            MemberDAO memberDAO = DAOFactory.getMemberInstance();
            GroupDAO groupDAO = DAOFactory.getGroupInstance();

            List<Long> groupIDList = memberDAO.getGroupIDListByUserID(this.userID);
            for (Long groupID : groupIDList) {
                GroupVO groupVO = groupDAO.getGroupByGroupID(groupID);
                groupList.add(changeGroupVOToGroup(groupVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return groupList;
    }

    // 创建一个群
    public Group createGroup(String groupName, List<Long> userInvitedUserIDList)
            throws OutOfLengthException, UserIDNotExistedException, DataAccessLayerException {
        Group group;

        if (groupName.length() > 30)
            throw new OutOfLengthException();

        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();

            GroupVO groupVO = groupDAO.addGroup(groupName);
            group = changeGroupVOToGroup(groupVO);

            try {
                group.addMemberByMemberUserID(this.userID, Member.MemberRole.MEMBER_ROLE_HOST);
                for (Long userInvitedUserID : userInvitedUserIDList) {
                    group.addMemberByMemberUserID(userInvitedUserID, Member.MemberRole.MEMBER_ROLE_COMMON);
                }
            }
            catch (UserIDNotExistedException userIDNotExistedException) {
                groupDAO.deleteGroupByGroupID(group.getGroupID());
                throw new UserIDNotExistedException();
            }
            catch (MembershipExistedException | DataAccessLayerException exception) {
                groupDAO.deleteGroupByGroupID(group.getGroupID());
                throw new DataAccessLayerException();
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return group;
    }

    // 根据群标识符删除一个群
    public void deleteGroupByGroupID(long groupID)
            throws GroupIDNotExistedException, MembershipNotExistedException,
            NotHostException, DataAccessLayerException {
        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            MemberVO memberVO = memberDAO.getMemberByGroupIDAndUserID(groupID, this.userID);

            if (memberVO.getMemberRole() != MemberVO.DATABASE_MEMBER_ROLE_HOST) {
                throw new NotHostException();
            }

            groupDAO.deleteGroupByGroupID(groupID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 获取加入的群的成员列表
    public List<Member> getGroupJoinedMemberList(long groupID)
            throws GroupIDNotExistedException, MembershipNotExistedException, DataAccessLayerException {
        List<Member> memberList;

        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            GroupVO groupVO = groupDAO.getGroupByGroupID(groupID);
            Group group = changeGroupVOToGroup(groupVO);

            memberList = group.getMemberList();
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return memberList;
    }

    // 根据群标识符加入某个群
    public void joinGroupByGroupID(long groupID)
            throws GroupIDNotExistedException, MembershipExistedException, DataAccessLayerException {
        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            GroupVO groupVO = groupDAO.getGroupByGroupID(groupID);
            Group group = changeGroupVOToGroup(groupVO);

            try {
                group.addMemberByMemberUserID(this.userID, Member.MemberRole.MEMBER_ROLE_COMMON);
            }
            catch (MembershipExistedException membershipExistedException) {
                throw new MembershipExistedException();
            }
            catch (UserIDNotExistedException | DataAccessLayerException exception) {
                throw new DataAccessLayerException();
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 根据群标识符退出某个群
    public void exitGroupByGroupID(long groupID)
            throws GroupIDNotExistedException, MembershipNotExistedException,
            HostException, DataAccessLayerException {
        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            MemberVO memberVO = memberDAO.getMemberByGroupIDAndUserID(groupID, this.userID);

            if (memberVO.getMemberRole() == MemberVO.DATABASE_MEMBER_ROLE_HOST) {
                throw new HostException();
            }

            GroupVO groupVO = groupDAO.getGroupByGroupID(groupID);
            Group group = changeGroupVOToGroup(groupVO);

            try {
                group.deleteMemberByMemberUserID(this.userID);
            }
            catch (UserIDNotExistedException userIDNotExistedException) {
                throw new DataAccessLayerException();
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 踢出群里的一个成员
    public void removeMemberFromGroup(long groupID, long userID)
            throws GroupIDNotExistedException, UserIDSameAsMineException,
            MembershipNotExistedException, NotHostException, UserIDNotExistedException, DataAccessLayerException {
        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (this.userID == userID) {
                throw new UserIDSameAsMineException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            MemberVO memberVO = memberDAO.getMemberByGroupIDAndUserID(groupID, this.userID);

            if (memberVO.getMemberRole() != MemberVO.DATABASE_MEMBER_ROLE_HOST) {
                throw new NotHostException();
            }

            GroupVO groupVO = groupDAO.getGroupByGroupID(groupID);
            Group group = changeGroupVOToGroup(groupVO);

            group.deleteMemberByMemberUserID(userID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 更新在加入的群中的备注
    public void updateMemberRemark(long groupID, String memberRemark)
            throws GroupIDNotExistedException, MembershipNotExistedException,
            OutOfLengthException, DataAccessLayerException {
        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            MemberVO memberVO = memberDAO.getMemberByGroupIDAndUserID(groupID, this.userID);
            Member member = changeMemberVOToMember(memberVO);

            member.updateMemberRemark(memberRemark);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 移交群主权限
    public void transferHostRole(long groupID, long userID)
            throws GroupIDNotExistedException, UserIDSameAsMineException, MembershipNotExistedException,
            NotHostException, NotCommonException, UserIDNotExistedException, DataAccessLayerException {
        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (this.userID == userID) {
                throw new UserIDSameAsMineException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID) || !memberDAO.isMemberExistedInGroup(groupID, userID)) {
                throw new MembershipNotExistedException();
            }

            MemberVO memberVO1 = memberDAO.getMemberByGroupIDAndUserID(groupID, this.userID);
            MemberVO memberVO2 = memberDAO.getMemberByGroupIDAndUserID(groupID, userID);

            if (memberVO1.getMemberRole() != MemberVO.DATABASE_MEMBER_ROLE_HOST) {
                throw new NotHostException();
            }
            if (memberVO2.getMemberRole() != MemberVO.DATABASE_MEMBER_ROLE_COMMON) {
                throw new NotCommonException();
            }

            Member member1 = changeMemberVOToMember(memberVO1);
            Member member2 = changeMemberVOToMember(memberVO2);

            member1.updateMemberRole(Member.MemberRole.MEMBER_ROLE_COMMON);
            member2.updateMemberRole(Member.MemberRole.MEMBER_ROLE_HOST);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 获取某个加入的群的消息列表
    public List<GroupMessage> getGroupMessageListByGroupID(long groupID)
            throws GroupIDNotExistedException, MembershipNotExistedException, DataAccessLayerException {
        List<GroupMessage> groupMessageList = new ArrayList<>();

        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();
            GroupMessageDAO groupMessageDAO = DAOFactory.getGroupMessageInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            List<GroupMessageVO> groupMessageVOList = groupMessageDAO.getGroupMessageListByGroupID(groupID);
            for (GroupMessageVO groupMessageVO : groupMessageVOList) {
                groupMessageList.add(changeGroupMessageVOToGroupMessage(groupMessageVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return groupMessageList;
    }

    // 发送群聊消息
    public GroupMessage sendGroupMessage(long groupID, String text, Date time)
            throws OutOfLengthException, TimeAfterCurrentException, GroupIDNotExistedException,
            MembershipNotExistedException, DataAccessLayerException {
        GroupMessage groupMessage;

        if (text.length() > 1000)
            throw new OutOfLengthException();

        if (time.after(new Date()))
            throw new TimeAfterCurrentException();

        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();
            GroupMessageDAO groupMessageDAO = DAOFactory.getGroupMessageInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            GroupMessageVO groupMessageVO = groupMessageDAO.addGroupMessage(groupID, this.userID, text, new Timestamp(time.getTime()));
            groupMessage = changeGroupMessageVOToGroupMessage(groupMessageVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return groupMessage;
    }

    // 根据群聊消息标识符删除群聊消息
    public void deleteGroupMessageByGroupMessageID(long groupID, long groupMessageID)
            throws GroupIDNotExistedException, MembershipNotExistedException, GroupMessageIDNotExistedException,
            GroupMessageSenderNotMatchedException, DataAccessLayerException {
        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(groupID, this.userID)) {
                throw new MembershipNotExistedException();
            }

            GroupMessageDAO groupMessageDAO = DAOFactory.getGroupMessageInstance();

            if (!groupMessageDAO.isGroupMessageExisted(groupMessageID)) {
                throw new GroupMessageIDNotExistedException();
            }

            GroupMessageVO groupMessageVO = groupMessageDAO.getGroupMessageByGroupMessageID(groupMessageID);
            if (groupMessageVO.getSenderUserID() != this.userID) {
                throw new GroupMessageSenderNotMatchedException();
            }

            groupMessageDAO.deleteGroupMessageByGroupMessageID(groupMessageID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 获取发送的广场消息列表
    public List<SquareMessage> getSquareMessageSentList() throws DataAccessLayerException {
        List<SquareMessage> squareMessageList = new ArrayList<>();

        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            List<SquareMessageVO> squareMessageVOList = squareMessageDAO.getSquareMessageListBySenderID(this.userID);
            for (SquareMessageVO squareMessageVO : squareMessageVOList) {
                squareMessageList.add(changeSquareMessageVOToSquareMessage(squareMessageVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return squareMessageList;
    }

    // 发送广场消息
    public SquareMessage sendSquareMessage(String text, Date time)
            throws OutOfLengthException, TimeAfterCurrentException, DataAccessLayerException {
        SquareMessage squareMessage = null;

        if (text.length() > 1000)
            throw new OutOfLengthException();

        if (time.after(new Date()))
            throw new TimeAfterCurrentException();

        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            SquareMessageVO squareMessageVO = squareMessageDAO.addSquareMessage(this.userID, text, new Timestamp(time.getTime()));
            squareMessage = changeSquareMessageVOToSquareMessage(squareMessageVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return squareMessage;
    }

    // 根据广场消息标识符删除广场消息
    public void deleteSquareMessageBySquareMessageID(long squareMessageID)
            throws SquareMessageIDNotExistedException,
            SquareMessageSenderNotMatchedException, DataAccessLayerException {
        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            if (!squareMessageDAO.isSquareMessageExisted(squareMessageID)) {
                throw new SquareMessageIDNotExistedException();
            }

            SquareMessageVO squareMessageVO = squareMessageDAO.getSquareMessageBySquareMessageID(squareMessageID);
            if (squareMessageVO.getSenderUserID() != this.userID) {
                throw new SquareMessageSenderNotMatchedException();
            }

            squareMessageDAO.deleteSquareMessageBySquareMessageID(squareMessageID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 发送评论
    public Comment sendComment(long squareMessageID, String text, Date time)
            throws OutOfLengthException, TimeAfterCurrentException,
            SquareMessageIDNotExistedException, DataAccessLayerException {
        Comment comment;

        if (text.length() > 1000)
            throw new OutOfLengthException();

        if (time.after(new Date()))
            throw new TimeAfterCurrentException();

        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            if (!squareMessageDAO.isSquareMessageExisted(squareMessageID)) {
                throw new SquareMessageIDNotExistedException();
            }

            SquareMessageVO squareMessageVO = squareMessageDAO.getSquareMessageBySquareMessageID(squareMessageID);
            SquareMessage squareMessage = changeSquareMessageVOToSquareMessage(squareMessageVO);

            comment =  squareMessage.addComment(this.userID, text, time);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return comment;
    }

    // 发送回复
    public Comment sendReply(long squareMessageID, long commentRepliedID, String text, Date time)
            throws OutOfLengthException, TimeAfterCurrentException, SquareMessageIDNotExistedException,
            CommentIDNotExistedException, CommentRepliedIDNotIncludedException, DataAccessLayerException {
        Comment comment;

        if (text.length() > 1000)
            throw new OutOfLengthException();

        if (time.after(new Date()))
            throw new TimeAfterCurrentException();

        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            if (!squareMessageDAO.isSquareMessageExisted(squareMessageID)) {
                throw new SquareMessageIDNotExistedException();
            }

            SquareMessageVO squareMessageVO = squareMessageDAO.getSquareMessageBySquareMessageID(squareMessageID);
            SquareMessage squareMessage = changeSquareMessageVOToSquareMessage(squareMessageVO);

            comment =  squareMessage.addReply(this.userID, commentRepliedID, text, time);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return comment;
    }

    // 根据评论标识符删除评论
    public void deleteCommentByCommentID(long commentID)
            throws CommentIDNotExistedException, CommentSenderNotMatchedException, DataAccessLayerException {
        try {
            CommentDAO commentDAO = DAOFactory.getCommentInstance();

            if (!commentDAO.isCommentExisted(commentID)) {
                throw new CommentIDNotExistedException();
            }

            CommentVO commentVO = commentDAO.getCommentByCommentID(commentID);
            if (commentVO.getSenderUserID() != this.userID) {
                throw new CommentSenderNotMatchedException();
            }

            commentDAO.deleteCommentByCommentID(commentID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 更新用户昵称
    public void updateUserNickname(String userNickname) throws OutOfLengthException, DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        if (userNickname.length() > 1000)
            throw new OutOfLengthException();

        try {
            userDAO.updateUserNickname(this.userID, userNickname);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userNickname = userNickname;
    }

    // 更新用户手机号码
    public void updateUserPhoneNumber(String userPhoneNumber) throws FormatException, DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        if (userPhoneNumber.length() != 11)
            throw new FormatException();

        try {
            userDAO.updateUserPhoneNumber(this.userID, userPhoneNumber);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userPhoneNumber = userPhoneNumber;
    }

    // 更新用户密码
    public void updateUserPassword(String userPassword) throws OutOfLengthException, DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        if (userPassword.length() > 18)
            throw new OutOfLengthException();

        try {
            userDAO.updateUserPassword(this.userID, userPassword);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userPassword = userPassword;
    }

    // 更新用户头像 -- 未完成
    public void updateUserHeadShow() throws DataAccessLayerException {

    }

    // 更新用户生日日期
    public void updateUserBirthday(Date userBirthday) throws TimeAfterCurrentException, DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        if (userBirthday.after(new Date()))
            throw new TimeAfterCurrentException();

        try {
            userDAO.updateUserBirthday(this.userID, new java.sql.Date(userBirthday.getTime()));
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userBirthday = userBirthday;
    }

    // 更新用户性别
    public void updateUserSex(UserUserSex userSex) throws DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        try {
            switch (userSex) {
                case USER_USER_SEX_FEMALE:
                    userDAO.updateUserSex(this.userID, UserVO.DATABASE_USER_SEX_FEMALE);
                    break;
                case USER_USER_SEX_MALE:
                    userDAO.updateUserSex(this.userID, UserVO.DATABASE_USER_SEX_MALE);
                    break;
                case USER_USER_SEX_UNKNOWN:
                    userDAO.updateUserSex(this.userID, UserVO.DATABASE_USER_SEX_UNKNOWN);
                    break;
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userSex = userSex;
    }

    // 更新用户所在地省份
    public void updateUserAreaProvince(String userAreaProvince)
            throws OutOfLengthException, DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        if (userAreaProvince.length() > 30)
            throw new OutOfLengthException();

        try {
            userDAO.updateUserAreaProvince(this.userID, userAreaProvince);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userAreaProvince = userAreaProvince;
    }

    // 更新用户所在地城市
    public void updateUserAreaCity(String userAreaCity) throws OutOfLengthException, DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        if (userAreaCity.length() > 30)
            throw new OutOfLengthException();

        try {
            userDAO.updateUserAreaCity(this.userID, userAreaCity);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userAreaCity = userAreaCity;
    }

    // 更新用户个性签名
    public void updateUserDescription(String userDescription)
            throws OutOfLengthException, DataAccessLayerException {
        UserDAO userDAO = DAOFactory.getUserDAOInstance();

        if (userDescription.length() > 100)
            throw new OutOfLengthException();

        try {
            userDAO.updateUserDescription(this.userID, userDescription);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.userDescription = userDescription;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserHeadShowURL() {
        return userHeadShowURL;
    }

    public User setUserHeadShowURL(String userHeadShowURL) {
        this.userHeadShowURL = userHeadShowURL;
        return this;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public UserUserSex getUserSex() {
        return userSex;
    }

    public void setUserSex(UserUserSex userSex) {
        this.userSex = userSex;
    }

    public String getUserAreaProvince() {
        return userAreaProvince;
    }

    public void setUserAreaProvince(String userAreaProvince) {
        this.userAreaProvince = userAreaProvince;
    }

    public String getUserAreaCity() {
        return userAreaCity;
    }

    public void setUserAreaCity(String userAreaCity) {
        this.userAreaCity = userAreaCity;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    private Friend changeUserVOToFriend(UserVO userVO){
        Friend friend = new Friend();

        friend.setUserID(userVO.getUserID());
        friend.setUserNickname(userVO.getUserNickname());
        friend.setUserPhoneNumber(userVO.getUserPhoneNumber());
        friend.setUserHeadShowURL(userVO.getUserHeadShowURL());
        friend.setUserBirthday(userVO.getUserBirthday());
        if (userVO.getUserSex() == UserVO.DATABASE_USER_SEX_FEMALE) {
            friend.setUserSex(Friend.FriendUserSex.FRIEND_USER_SEX_FEMALE);

        } else if (userVO.getUserSex() == UserVO.DATABASE_USER_SEX_MALE) {
            friend.setUserSex(Friend.FriendUserSex.FRIEND_USER_SEX_MALE);

        } else if (userVO.getUserSex() == UserVO.DATABASE_USER_SEX_UNKNOWN) {
            friend.setUserSex(Friend.FriendUserSex.FRIEND_USER_SEX_UNKNOWN);
        }
        friend.setUserAreaProvince(userVO.getUserAreaProvince());
        friend.setUserAreaCity(userVO.getUserAreaCity());
        friend.setUserDescription(userVO.getUserDescription());

        return friend;
    }

    private PersonalMessage changePersonalMessageVOToPersonalMessage(PersonalMessageVO personalMessageVO){
        PersonalMessage personalMessage = new PersonalMessage();

        personalMessage.setPersonalMessageID(personalMessageVO.getPersonalMessageID());
        personalMessage.setSenderUserID(personalMessageVO.getSenderUserID());
        personalMessage.setReceiverUserID(personalMessageVO.getReceiverUserID());
        personalMessage.setPersonalMessageText(personalMessageVO.getPersonalMessageText());
        personalMessage.setPersonalMessageTime(personalMessageVO.getPersonalMessageTime());

        return personalMessage;
    }

    private GroupMessage changeGroupMessageVOToGroupMessage(GroupMessageVO groupMessageVO){
        GroupMessage groupMessage = new GroupMessage();

        groupMessage.setGroupMessageID(groupMessageVO.getGroupMessageID());
        groupMessage.setGroupID(groupMessageVO.getGroupID());
        groupMessage.setSenderUserID(groupMessageVO.getGroupMessageID());
        groupMessage.setGroupMessageText(groupMessageVO.getGroupMessageText());
        groupMessage.setGroupMessageTime(groupMessageVO.getGroupMessageTime());

        return groupMessage;
    }

    private Group changeGroupVOToGroup(GroupVO groupVO) {
        Group group = new Group();

        group.setGroupID(groupVO.getGroupID());
        group.setGroupName(groupVO.getGroupName());
        group.setGroupAnnouncement(groupVO.getGroupAnnouncement());

        return group;
    }

    private Member changeMemberVOToMember(MemberVO memberVO) {
        Member member = new Member();

        member.setGroupID(memberVO.getGroupID());
        member.setUserID(memberVO.getUserID());
        member.setMemberRemark(memberVO.getMemberRemark());
        if (memberVO.getMemberRole() == MemberVO.DATABASE_MEMBER_ROLE_HOST)
            member.setMemberRole(Member.MemberRole.MEMBER_ROLE_HOST);
        else if (memberVO.getMemberRole() == MemberVO.DATABASE_MEMBER_ROLE_COMMON)
            member.setMemberRole(Member.MemberRole.MEMBER_ROLE_COMMON);

        return member;
    }

    private SquareMessage changeSquareMessageVOToSquareMessage(SquareMessageVO squareMessageVO) {
        SquareMessage squareMessage = new SquareMessage();

        squareMessage.setSquareMessageID(squareMessageVO.getSquareMessageID());
        squareMessage.setSenderUserID(squareMessageVO.getSenderUserID());
        squareMessage.setSquareMessageText(squareMessageVO.getSquareMessageText());
        squareMessage.setSquareMessageTime(squareMessageVO.getSquareMessageTime());

        return squareMessage;
    }

    public enum UserUserSex {
        USER_USER_SEX_FEMALE, USER_USER_SEX_MALE, USER_USER_SEX_UNKNOWN
    }

    private long userID;
    private String userNickname;
    private String userPhoneNumber;
    private String userPassword;
    private String userHeadShowURL;
    private Date userBirthday;
    private UserUserSex userSex;
    private String userAreaProvince;
    private String userAreaCity;
    private String userDescription;
}
