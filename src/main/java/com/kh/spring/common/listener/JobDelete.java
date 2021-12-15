
package com.kh.spring.common.listener;

import org.quartz.Job; 
import org.quartz.JobExecutionContext; 
import org.quartz.JobExecutionException;

import com.kh.spring.mentoring.model.dao.MentoringDao;

public class JobDelete implements Job {

	@Override 
	public void execute(JobExecutionContext context) throws JobExecutionException { 
		MentoringDao mDao = new MentoringDao();
		//apply_history에서 삭제
		mDao.deleteAh(); 
		//mentoring_history에서 삭제
		mDao.deleteMh();
		//mentoring_history에서 기한이 지난 멘토링 상태 변경
		mDao.updateMh();
	} 

}
 