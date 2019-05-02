package morning.service.event.listener;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import morning.entity.TaskOverview;
import morning.event.Event;
import morning.event.EventListener;
import morning.repo.TaskOverviewDao;
import morning.service.event.TaskSubmitEvent;
import morning.vo.TASK_STATUS;

@Component
public class TaskSubmitListener implements EventListener {

	private static Logger logger = LoggerFactory.getLogger(TaskSubmitListener.class);
	
	@Autowired
	private TaskOverviewDao taskOverviewDao;
	
	@Override
	public void onEvent(Event event) throws Exception {
		logger.debug("On TaskSubmitListener: {}",JSON.toJSONString(event));
		TaskSubmitEvent submitEvent = (TaskSubmitEvent)event;
		List<String> toNodeTIds = submitEvent.getToNodeTids();
		// 创建任务一览记录---下一个Task节点
		List<TaskOverview> taskOtaskOVs = new ArrayList<TaskOverview>();
		for(String toTId : toNodeTIds) {
			TaskOverview taskOV = new TaskOverview(event.getUserId(),
					event.getProcessInstanceId(),
					null,						//下一个节点尚未创建
					event.getCreateTime(),
					event.getNodeName(),
					event.getProcessName(),
					TASK_STATUS.Ready.getCode(),//因此状态应为Ready
					toTId,						
					event.getProcessTId());
			taskOtaskOVs.add(taskOV);
		}
		// 把新Start节点消息保存到“任务一览”
		taskOverviewDao.save(taskOtaskOVs);
	}

	@Override
	public boolean isFailOnException() {
		return true;
	}

}
