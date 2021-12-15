package com.kh.spring.todo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.exception.DataAccessException;
import com.kh.spring.todo.model.dto.Todo;

public class TodoDao {

	JDBCTemplate template = JDBCTemplate.getInstance();

	//캘린더에 모든 요소 출력용
	public List<Todo> calendarList(int userIdx, Connection conn){
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		ArrayList<Todo> calendarList = new ArrayList<Todo>();	

		try {
			String query = " SELECT * FROM TODO WHERE USER_IDX = ? ";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();
		while(rset.next()) {
			calendarList.add(convertRowToTodo(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(rset, pstm);
		}
		
		return calendarList;
	}
	
	
	
	//일정 추가
	public int insertTodo(Todo todo, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		
		//System.out.println("################ @TodoDao - insertTodo");
		//System.out.println(todo.toString());
		

		try {
			String query = "INSERT INTO TODO(TODO_IDX, USER_IDX, START_DATE, END_DATE, TITLE, DONE, COLOR) "
							+ "VALUES(SEQ_TODO.nextval,?,?,?,?,?,?)";
			
			pstm = conn.prepareStatement(query);
		
			int index = 1;
			pstm.setInt(index++, todo.getUserIdx());
			pstm.setDate(index++, todo.getStartDate());
			pstm.setDate(index++, todo.getEndDate());
			pstm.setString(index++, todo.getTitle());
			pstm.setString(index++, todo.getDone());
			pstm.setString(index++, todo.getColor());

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
		
	}
	
	//일정 수정
	public int updateTodo(Todo todo, Connection conn) {		
		int res = 0;		
		PreparedStatement pstm = null;
		String query = "UPDATE TODO SET TITLE=?, START_DATE=?, END_DATE=?, COLOR=? WHERE TODO_IDX=?";
		
		try {
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, todo.getTitle());
			pstm.setDate(index++, todo.getStartDate());
			pstm.setDate(index++, todo.getEndDate());
			pstm.setString(index++, todo.getColor());	
			pstm.setInt(index++, todo.getTodoIdx());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(pstm);
		}
		
		return res;
	}
		
	//일정 삭제
	public int deleteTodo(Todo todo, Connection conn) {
		int res = 0;		
		PreparedStatement pstm = null;
		String query = "DELETE FROM TODO WHERE TODO_IDX = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, todo.getTodoIdx());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(pstm);
		}
		
		return res;
	}

	//오늘의 일정 출력
	public List<Todo> todoMain(int userIdx, Connection conn) {
		Todo todo = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		ArrayList<Todo> todayList = new ArrayList<Todo>();
		try {
			String query = " SELECT * "
						 + " FROM TODO "
						 + " WHERE USER_IDX = ? "
						 + " AND TRUNC(sysdate) >= START_DATE " 
						 + " AND TRUNC(sysdate) <= END_DATE ";	
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();

			while (rset.next()) {
				todo = convertRowToTodo(rset);
				todayList.add(todo);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);		
		} finally {
			template.close(rset, pstm);

		}
		return todayList;
	}
	


	//체크박스 값 변경 
	public int todoSave(int todoIdx, char done, Connection conn) {
		int res = 0;	
		PreparedStatement pstm = null;
		String query = "UPDATE TODO SET DONE = ? WHERE TODO_IDX = ?";
		
		//System.out.println("done : " + done);
		//체크여부 넣어주기
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, done);
			pstm.setInt(2, todoIdx);
			res = pstm.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(pstm);
		}
		
		return res;
	}			

	
	
	
	private Todo convertRowToTodo(ResultSet rset) throws SQLException {
		Todo todo = new Todo();
		todo.setTodoIdx(rset.getInt("todo_idx"));
		todo.setUserIdx(rset.getInt("user_idx"));
		todo.setStartDate(rset.getDate("start_date"));
		todo.setEndDate(rset.getDate("end_date"));
		todo.setTitle(rset.getString("title"));
		todo.setDone(rset.getString("done"));
		todo.setColor(rset.getString("color"));
		
		return todo;
	}



}
