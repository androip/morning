package morning.service.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import morning.entity.TaskOverview;
import morning.event.Event;
import morning.event.EventListener;
import morning.exception.DBException;
import morning.repo.TaskOverviewDao;
import morning.vo.TASK_STATUS;

@Component
public class CreateNodeInstanceListener implements EventListener{

	
	private static Logger logger = LoggerFactory.getLogger(CreateNodeInstanceListener.class);
	
	@Autowired
	private TaskOverviewDao taskOverviewDao;
	
	@Override
	public void onEvent(Event event) throws DBException {
		logger.debug("On listener: {}",JSON.toJSONString(event));
		// 创建任务一览记录
		TaskOverview taskOV = new TaskOverview(event.getUserId(),
				event.getProcessTId(),
				event.getNodeInstanceId(),
				event.getCreateTime(),
				event.getNodeName(),
				event.getProcessName(),
				TASK_STATUS.Start.getCode());
		// 把新创建的节点消息保存到“任务一览”
		taskOverviewDao.save(taskOV);
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

}
