package cn.newbeedaly.activiti.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

import java.io.Serializable;

/**
 * @author newbeedaly
 */
@Slf4j
public class GlobalActivitiEventListener implements ActivitiEventListener, Serializable {

    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {
            case PROCESS_COMPLETED:
                processComplete(event);
                break;
            case TASK_CREATED:
                taskCreated(event);
                break;
            case TASK_COMPLETED:
                taskComplete(event);
                break;
            case PROCESS_CANCELLED:
                taskCancelled(event);
                break;
            default:
                break;
        }
    }

    private void taskCancelled(ActivitiEvent event) {
        log.info("taskCancelled");
    }

    private void taskComplete(ActivitiEvent event) {
        log.info("taskComplete");
    }

    private void taskCreated(ActivitiEvent event) {
        log.info("taskCreated");
    }

    private void processComplete(ActivitiEvent event) {
        log.info("processComplete");
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
