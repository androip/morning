package morning.service.event.listener;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import morining.event.EVENT_TYPE;
import morining.event.Event;
import morining.event.EventListener;
import morining.event.EventListenerPipeline;
import morining.event.ProcessEventSupport;
import morning.entity.TaskOverview;
import morning.exception.DBException;
import morning.repo.TaskOverviewDao;
import morning.service.event.ProcessStartEvent;
import morning.vo.TASK_STATUS;
/**
 * 流程开始事件处理
 * @author jp
 */
@Component
public class ProcessStartListener implements EventListener{

	private static Logger logger = LoggerFactory.getLogger(ProcessStartListener.class);
	@Autowired
	private ProcessEventSupport eventSupport;
	private EventListenerPipeline pipeline;
	@Autowired
	private TaskOverviewDao taskOverviewDao;
	@Override
	public void onEvent(Event event) throws DBException {
		logger.debug("On listener: {}",JSON.toJSONString(event));
		ProcessStartEvent startevent = (ProcessStartEvent) event;
		List<String> toNodeTIds = startevent.getToNodeTids();
		// 创建任务一览记录---Start之后的第一个Task节点
		List<TaskOverview> taskOtaskOVs = new ArrayList<TaskOverview>();
		for(String toId:toNodeTIds) {
			TaskOverview taskOV = new TaskOverview(event.getUserId(),
					event.getProcessInstanceId(),
					null,						//Task节点尚未创建
					event.getCreateTime(),
					event.getNodeName(),
					event.getProcessName(),
					TASK_STATUS.Ready.getCode(),//因此状态应为Ready
					toId,						
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

	@Override
	synchronized public EventListenerPipeline pipeline() {
		if(pipeline == null) {
			pipeline = new EventListenerPipeline();
		}
		return this.pipeline;
	}

	@Override
	public void init()  {
		eventSupport.registerListener(this,EVENT_TYPE.proc_start);
	}

}
