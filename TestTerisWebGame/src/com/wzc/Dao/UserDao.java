package com.wzc.Dao;

import com.wzc.Entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserDao {
	public String findUsername(String username) {
		String psw = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://192.168.1.150:3306/game_user";
			String user = "myuser";
			String password = "FYwsLF1118!";
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from users where username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs == null) {
				return null;
			}
			if (rs.next()) {
				psw = rs.getString("psw");
			} else {
				psw = null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
			}
		}
		return psw;
	}

	public void addUser(String username, String psw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://192.168.1.150:3306/game_user";
			String user = "myuser";
			String password = "FYwsLF1118!";
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String sql = "INSERT INTO users VALUES(?,?,0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, psw);
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
			}
		}

	}

	public void addUserScore(String username, int score) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://192.168.1.150:3306/game_user";
			String user = "myuser";
			String password = "FYwsLF1118!";
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String sql = "UPDATE users set score=? where username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, score);
			pstmt.setString(2, username);
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
			}
		}
	}

	public ArrayList<User> getScoreRank() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> UserList = new ArrayList<User>();
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://192.168.1.150:3306/game_user";
			String user = "myuser";
			String password = "FYwsLF1118!";
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String sql = "SELECT username,score FROM users";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				User UserTemp =new User() ;
				UserTemp.setUsername(rs.getString("username"));
				UserTemp.setScore(rs.getInt("score"));
				UserList.add(UserTemp);
				UserTemp=null;
			}
			Collections.sort(UserList, new Comparator<User>() {
				public int compare(User o1, User o2) {
					if (o1.getScore() < o2.getScore())
						return 1;
					else if (o1.getScore() == o2.getScore()) {
						return 0;
					} else {
						return -1;
					}
				}

			});



		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
			}
		}
		return UserList;
	}
}
