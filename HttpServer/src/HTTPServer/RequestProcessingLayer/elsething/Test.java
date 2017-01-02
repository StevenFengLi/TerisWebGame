package HTTPServer.RequestProcessingLayer.elsething;

import HTTPServer.BusinessLogicLayer.Entity.*;
import HTTPServer.BusinessLogicLayer.Exception.*;
import HTTPServer.DataAccessLayer.Entity.DAO.SquareMessageDAO;
import HTTPServer.DataAccessLayer.Entity.DAOFactory.DAOFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        try {
            /* // 添加用户
            UserList userList = new UserList();
            User user = userList.addUser("15876845653", "19830330", "a");
            System.out.println(user.getUserID());*/

            /* //验证密码
            UserList userList = new UserList();
            User user = userList.getUserByUserPhoneNumber("13531587099");
            System.out.println(user.validatePassword("0330"));*/

            /* //得到好友列表
            UserList userList = new UserList();
            User user = userList.getUserByUserID(6);
            List<Friend> friendList = user.getFriendList();
            for (Friend friend : friendList) {
                System.out.println(friend.getUserID());
            }*/

            /* //添加好友
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            user.addFriendByFriendUserID(5);*/

            /* //删除好友
            UserList userList = new UserList();
            User user = userList.getUserByUserID(6);
            user.deleteFriendByFriendUserID(4);*/

            /* //发送私信
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            PersonalMessage personalMessage = user.sendPersonalMessage(5, "1", new Date());
            System.out.println(personalMessage.getPersonalMessageTime());*/

            /* //获取私信
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            List<PersonalMessage> personalMessageList = user.getPersonalMessageListByAnotherUserID(5);
            for (PersonalMessage personalMessage : personalMessageList) {
                System.out.println(personalMessage.getPersonalMessageTime());
            }*/

            /* //删除私信
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            user.deletePersonalMessageByPersonalMessageID(4);*/

            /* //创建群
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            List<Long> userIDList = new ArrayList();
            userIDList.add((long)6);
            user.createGroup("test", userIDList);*/

            /* //获取加入的群
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            List<Group> groupList = user.getGroupListJoined();
            for (Group group : groupList) {
                System.out.println(group.getGroupName());
            }*/

            /* //解散群
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.deleteGroupByGroupID(5);*/

            /* //获取成员列表
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            List<Member> memberList = user.getGroupJoinedMemberList(6);
            for (Member member : memberList) {
                System.out.println(member.getUserID());
            }*/

            /* //加群
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            user.joinGroupByGroupID(6);*/

            /* //退群
            UserList userList = new UserList();
            User user = userList.getUserByUserID(6);
            user.exitGroupByGroupID(3);*/

            /* //踢人
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.removeMemberFromGroup(6, 6);*/

            /* //发送群聊消息
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.sendGroupMessage(6, "hello, world!", new Date());*/

            /* // 获取群聊消息列表
            UserList userList = new UserList();
            User user = userList.getUserByUserID(6);
            List<GroupMessage> groupMessageList = user.getGroupMessageListByGroupID(3);
            for (GroupMessage groupMessage : groupMessageList) {
                System.out.println(groupMessage.getGroupMessageTime());
            }*/

            /* // 删除群聊消息
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            user.deleteGroupMessageByGroupMessageID(3, 1);*/

            /* //发送广场消息
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.sendSquareMessage("hello, world!", new Date());*/

            /* // 获取自己发送的广场消息
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            List<SquareMessage> squareMessageList = user.getSquareMessageSentList();
            for (SquareMessage squareMessage : squareMessageList) {
                System.out.println(squareMessage.getSquareMessageTime());
            }*/

            /* // 删除广场消息
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.deleteSquareMessageBySquareMessageID(13);*/

            /* //发送评论
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            user.sendComment(2, "hello, world!", new Date());*/

            /* // 发送回复
            UserList userList = new UserList();
            User user = userList.getUserByUserID(6);
            user.sendReply(2, 2, "hello, world!", new Date());*/

            /* // 删除评论
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            user.deleteCommentByCommentID(4);*/

            /* //根据群名获取群列表
            GroupList groupList = new GroupList();
            List<Group> groupList1 = groupList.getGroupListByGroupName("test");
            for (Group group : groupList1) {
                System.out.println(group.getGroupID());
            }*/

            /* // 获取十条旧广场消息
            Square square = new Square();
            List<SquareMessage> squareMessageList = square.getTenSquareMessagesBeforeTime(new Timestamp(new Date().getTime()));
            for (SquareMessage squareMessage : squareMessageList) {
                System.out.println(squareMessage.getSquareMessageTime());
            }*/

            /* // 获取更新的广场消息
            Square square = new Square();
            List<SquareMessage> squareMessageList = square.getSquareMessageListAfterTime(new Timestamp(new Date().getTime() - 1000000));
            for (SquareMessage squareMessage : squareMessageList) {
                System.out.println(squareMessage.getSquareMessageTime());
            }*/

            /* // 更新昵称
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.updateUserNickname("lll");*/

            /* // 更新生日
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.updateUserBirthday(new java.util.Date(1995 - 1900, 1 - 1, 1));*/

            /* // 更新用户性别
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.updateUserSex(User.UserUserSex.USER_USER_SEX_FEMALE);*/

            /* // 更改用户在群里的备注
            UserList userList = new UserList();
            User user = userList.getUserByUserID(5);
            user.updateMemberRemark(6, "test");*/

            /* // 移交群主权限给另一个群成员
            UserList userList = new UserList();
            User user = userList.getUserByUserID(4);
            user.transferHostRole(3, 5);*/
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
