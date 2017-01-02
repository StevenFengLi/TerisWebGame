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

public class Square {
    // 获取比某个时间点早的10条广场消息
    public List<SquareMessage> getTenSquareMessagesBeforeTime(Date time) throws DataAccessLayerException {
        List<SquareMessage> squareMessageList = new ArrayList<>();

        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            List<SquareMessageVO> squareMessageVOList = squareMessageDAO.getTenSquareMessagesBeforeTime(new Timestamp(time.getTime()));
            for (SquareMessageVO squareMessageVO : squareMessageVOList) {
                squareMessageList.add(changeSquareMessageVOToSquareMessage(squareMessageVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return squareMessageList;
    }

    // 获取比某个时间点晚的所有广场消息
    public List<SquareMessage> getSquareMessageListAfterTime(Date time) throws DataAccessLayerException {
        List<SquareMessage> squareMessageList = new ArrayList<>();

        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            List<SquareMessageVO> squareMessageVOList = squareMessageDAO.getSquareMessageListAfterTime(new Timestamp(time.getTime()));
            for (SquareMessageVO squareMessageVO : squareMessageVOList) {
                squareMessageList.add(changeSquareMessageVOToSquareMessage(squareMessageVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return squareMessageList;
    }

    // 根据广场消息标识符获取某条广场消息
    public SquareMessage getSquareMessageBySquareMessageID(int squareMessageID)
            throws SquareMessageIDNotExistedException, DataAccessLayerException {
        SquareMessage squareMessage;

        try {
            SquareMessageDAO squareMessageDAO = DAOFactory.getSquareMessageInstance();

            if (!squareMessageDAO.isSquareMessageExisted(squareMessageID)) {
                throw new SquareMessageIDNotExistedException();
            }

            SquareMessageVO squareMessageVO =  squareMessageDAO.getSquareMessageBySquareMessageID(squareMessageID);
            squareMessage = changeSquareMessageVOToSquareMessage(squareMessageVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return squareMessage;
    }

    private SquareMessage changeSquareMessageVOToSquareMessage(SquareMessageVO squareMessageVO) {
        SquareMessage squareMessage = new SquareMessage();

        squareMessage.setSquareMessageID(squareMessageVO.getSquareMessageID());
        squareMessage.setSenderUserID(squareMessageVO.getSenderUserID());
        squareMessage.setSquareMessageText(squareMessageVO.getSquareMessageText());
        squareMessage.setSquareMessageTime(squareMessageVO.getSquareMessageTime());

        return squareMessage;
    }
}
