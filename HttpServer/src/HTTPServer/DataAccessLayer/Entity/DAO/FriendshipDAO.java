package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;

public interface FriendshipDAO {
    boolean isFriendshipExisted(long userID1, long userID2) throws DatabaseException;

    List<Long> getFriendUserIDListByUserID(long userID) throws DatabaseException;

    void addFriendship(long userID1, long userID2) throws DatabaseException;

    void deleteFriendship(long userID1, long userID2) throws DatabaseException;
}
