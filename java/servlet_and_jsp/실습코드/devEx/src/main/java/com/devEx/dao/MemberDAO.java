package com.devEx.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.devEx.vo.MemberVO;

public class MemberDAO {
	private static MemberDAO dao = new MemberDAO();
	private MemberDAO() {
	}
	
	public static MemberDAO getInstance() {
		return dao;
	}
	
	public Connection connect() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "hr", "hr");
		} catch(Exception ex) {
			System.out.println("MemberDAO connect()오류 발생" + ex);
		}
		return connection;
	}
	
	public void close(Connection connection, PreparedStatement ps, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch(Exception ex) {
				System.out.println("오류 발생" + ex);
			}
		}
		close(connection, ps);
	}
	
	public void close(Connection connection, PreparedStatement ps) {
		if(ps != null) {
			try {
				ps.close();
			} catch(Exception ex) {
				System.out.println("오류 발생" + ex);
			}
		}
		if(connection != null) {
			try {
				connection.close();
			} catch(Exception ex) {
				System.out.println("오류 발생" + ex);
			}
		}
	}
	
	public void memberInsert(MemberVO member) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connect();
			pstmt = connection.prepareStatement("insert into member values(?, ?, ?, ?)");
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getMail());
			pstmt.executeUpdate();
		} catch(Exception ex) {
			System.out.println("오류 발생" + ex);
		} finally {
			close(connection, pstmt);
		}
	}
	
	public MemberVO memberSearch(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		MemberVO member = null;
		try {
			connection = connect();
			pstmt = connection.prepareStatement("select * from member where id =?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setId(rs.getString(1));
				member.setPasswd(rs.getString(2));
				member.setName(rs.getString(3));
				member.setMail(rs.getString(4));
			}
		} catch(Exception ex) {
			System.out.println("MemberDAO memberSearch()오류 발생" + ex);
		} finally {
			close(connection, pstmt);
		}
		return member;
	}
	
	public void memberUpdate(MemberVO member) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connect();
			pstmt = connection.prepareStatement("update member set passwd = ?, name = ?, mail = ?");
			pstmt.setString(1,  member.getPasswd());
			pstmt.setString(2,  member.getName());
			pstmt.setString(3,  member.getMail());
			pstmt.executeUpdate();
		} catch(Exception ex) {
			System.out.println("dao memberUpdate() 오류 발생" + ex);
		} finally {
			close(connection, pstmt);
		}
	}
	
	public void memberDelete(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = connect();
			pstmt = connection.prepareStatement("delete from member where id=?");
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch(Exception ex) {
			System.out.println("dao memberDelete() 오류 발생" + ex);
		} finally {
			close(connection, pstmt);
		}
	}
	
	public ArrayList<MemberVO> memberList() {
		ArrayList<MemberVO> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		MemberVO member = null;
		try {
			connection = connect();
			pstmt = connection.prepareStatement("select * from member");
			resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				member = new MemberVO();
				member.setId(resultSet.getString(1));
				member.setPasswd(resultSet.getString(2));
				member.setName(resultSet.getString(3));
				member.setMail(resultSet.getString(4));
				list.add(member);
			}
		} catch(Exception ex) {
			System.out.println("dao memberList() 오류 발생" + ex);
		} finally {
			close(connection, pstmt, resultSet);
		}
		
		return list;
	}
}
