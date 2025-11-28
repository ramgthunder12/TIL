package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import dbquery.MemberRowMapper;

public class MemberDao {
	private JdbcTemplate jdbcTemplate;
	
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Member selectByEmail(String email) {
//		익명클래스방식으로 구현
//		List<Member> results = jdbcTemplate.query("select * from MEMBER where EMAIL = ?", 
//				new RowMapper<Member>() {
//					@Override
//					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//						Member member = new Member(
//								rs.getString("EMAIL"),
//								rs.getString("PASSWORD"),
//								rs.getString("NAME"),
//								rs.getTimestamp("REGDATE").toLocalDateTime());
//						
//						member.setId(rs.getLong("ID"));
//						return member;
//					}
//		}, email);
//		return results.isEmpty() ? null : results.get(0);
		
//		람다식 방식으로 구현
//		List<Member> results = jdbcTemplate.query(
//				"select * from MEMBER where EMAIL = ?",
//				(ResultSet rs, int rowNum) -> {
//					Member member = new Member(
//							rs.getString("EMAIL"),
//							rs.getString("PASSWORD"),
//							rs.getString("NAME"),
//							rs.getTimestamp("REFDATE").toLocalDateTime());
//					member.setId(rs.getLong("ID"));
//					return member;
//				},
//				email);
//		return results.isEmpty() ? null : results.get(0);
		
//		RowMappper인터페이스를 클래스로 구현
		List<Member> results = jdbcTemplate.query("select * from MEMBER where EMAIL = ?",
				new MemberRowMapper(), email);
		return results.isEmpty() ? null : results.get(0);
		
		//queryForObject를 사용해 email을 사용하는 0 또는 1건을 리턴 
//		Member member = jdbcTemplate.queryForObject("select * from Member where EMAIL = ?", new MemberRowMapper(), email);
//		return member;
	}
	
	public void insert(Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
//		익명클래스 구현 방식
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			@Override
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				PreparedStatement pstmt = con.prepareStatement("insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE)" + "values (?, ?, ?, ?)", new String[] {"ID"});
//				pstmt.setString(1, member.getEmail());
//				pstmt.setString(2, member.getPassword());
//				pstmt.setString(3, member.getName());
//				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
//				return pstmt;
//			}
//		}, keyHolder);
		
//		람다식 구현 방식
		jdbcTemplate.update((Connection con) -> {
			PreparedStatement pstmt = con.prepareStatement("insert into MEMBER(EMAIL, PASSWORD, NAME, REGDATE) " + "values(?, ?, ?, ?)", new String[] {"ID"});
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
			
			return pstmt;
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}
	
	public void update(Member member) {
//		jdbcTemplate.update("update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?", member.getName(), member.getPassword(), member.getEmail());
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						"insert into MEMBER(EMAIL, PASSWORD, NAME, REGDATE) values (?, ? , ?, ?)");
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
				
				return pstmt;
			}
		});
	}
	
	public List<Member> selectAll() {
//		//익명 클래스 방식으로 구현
//		List<Member> results = jdbcTemplate.query("select * from MEMBER", new RowMapper<Member>() {
//			@Override
//			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Member member = new Member(
//						rs.getString("EMAIL"),
//						rs.getString("PASSWORD"),
//						rs.getString("NAME"),
//						rs.getTimestamp("REGDATE").toLocalDateTime());
//				member.setId(rs.getLong("ID"));
//				return member;
//			}
//		});
		
		//람다식으로 구현
//		List<Member> results = jdbcTemplate.query("selet * from MEMBER", (ResultSet rs, int rowNum) -> {
//		Member member = new Member(
//				rs.getString("EMAIL"),
//				rs.getString("PASSWORD"),
//				rs.getString("NAME"),
//				rs.getTimestamp("REGDATE").toLocalDateTime());
//		member.setId(rs.getLong("ID"));
//		return member;});
		
		//RowMapper 구현 방식
		List<Member> results = jdbcTemplate.query("select * from MEMBER", new MemberRowMapper());
		
		return results;
	}
	
	public int count() {
//		List<Integer> results = jdbcTemplate.query("select count(*) from MEMBER", new RowMapper<Integer>() {
//			@Override
//			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return rs.getInt(1);
//			}
//		});
//		return results.get(0);
		Integer count = jdbcTemplate.queryForObject("select count(*) from Member", Integer.class);
		
		return count;
	}
}


