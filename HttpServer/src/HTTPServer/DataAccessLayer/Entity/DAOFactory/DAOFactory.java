package HTTPServer.DataAccessLayer.Entity.DAOFactory;

import HTTPServer.DataAccessLayer.Entity.DAO.*;
import HTTPServer.DataAccessLayer.Entity.DAOImplement.*;

public class DAOFactory {
    public static UserDAO getUserDAOInstance() {
        return new UserDAOImplement();
    }

    public static FriendshipDAO getFriendshipDAOInstance() {
        return new FriendshipDAOImplement();
    }

    public static PersonalMessageDAO getPersonalMessageInstance() {
        return new PersonalMessageDAOImplement();
    }

    public static GroupDAO getGroupInstance() {
        return new GroupDAOImplement();
    }

    public static MemberDAO getMemberInstance() {
        return new MemberDAOImplement();
    }

    public static GroupMessageDAO getGroupMessageInstance() {
        return new GroupMessageDAOImplement();
    }

    public static SquareMessageDAO getSquareMessageInstance() {
        return new SquareMessageDAOImplement();
    }

    public static CommentDAO getCommentInstance() {
        return new CommentDAOImplement();
    }
}
