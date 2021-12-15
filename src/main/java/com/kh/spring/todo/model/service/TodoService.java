package com.kh.spring.todo.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.todo.model.dao.TodoDao;
import com.kh.spring.todo.model.dto.Todo;

public class TodoService {

	private TodoDao todoDao = new TodoDao();
	private JDBCTemplate template = JDBCTemplate.getInstance();
	
	//모든 일정 조회
	public List<Todo> calendarList(int userIdx){
		Connection conn = template.getConnection();
		List<Todo> calendarList = null;
		try {
	
			calendarList = todoDao.calendarList(userIdx, conn);
	
		} finally {
			template.close(conn);
		}
	
		return calendarList;
	}
		
	
	//일정 등록	
	public int insertTodo(Todo todo) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			res = todoDao.insertTodo(todo, conn);
			
			if( res != 0 )
				template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
		//System.out.println(res);
		return res;
		
	}


	//일정수정 
	public int modifyTodo(Todo todo) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			res = todoDao.updateTodo(todo, conn);
			
			if( res != 0 )
				template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
		//System.out.println(res);
		return res;
						
	}


	//일정 삭제
	public int deleteTodo(Todo todo) {
		Connection conn = template.getConnection();
		int res = 0;
		
		try {
			res = todoDao.deleteTodo(todo,conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			e.printStackTrace(); //디버깅용
		}finally {
			template.close(conn);
		}
		
		return res;
		
		
	}


	//todo-main(오늘의 일정)
	public List<Todo> todoMain(int userIdx) {
		List<Todo> todayList = null;
		Connection conn = template.getConnection();

		try {
			todayList = todoDao.todoMain(userIdx, conn);

		} finally {
			template.close(conn);
		}
		return todayList;
	}

	
	//체크박스 체크된 값 0,1로
	public int todaySave(int todoIdx, char done) {
		int res = 0;
		Connection conn = template.getConnection();
		try {
			res = todoDao.todoSave(todoIdx, done, conn);
			if( res != 0 )
				template.commit(conn);
		}catch (Exception e) {
			template.rollback(conn);
			e.printStackTrace();
		} finally {
			template.close(conn);
		}
		return res;
	}

}

