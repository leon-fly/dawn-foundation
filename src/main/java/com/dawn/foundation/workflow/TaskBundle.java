package com.dawn.foundation.workflow;

import com.dawn.foundation.util.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.util.LinkedList;
import java.util.List;

public class TaskBundle {

    private Logger logger = LoggerFactory.getLogger(TaskBundle.class);

    private List<Task> workItems;

    private TaskMessage message;

    public static TaskBundle create(TaskMessage message) {
        return new TaskBundle(message);
    }

    private TaskBundle(TaskMessage message) {
        this.message = message;

        this.workItems = new LinkedList<>();
    }

    public <T extends Task> TaskBundle register(Class<T> clazz) {
        Task worker = ApplicationContextUtils.getBean(
                Introspector.decapitalize(clazz.getSimpleName()), Task.class);

        this.workItems.add(worker);

        return this;
    }

    public void execute() throws Exception {
        for (Task workItem : this.workItems) {
            logger.debug(String.format("Task(%s) is launched.", workItem.getClass()));
            workItem.execute(this.message);
            logger.debug(String.format("Task(%s) is finished.", workItem.getClass()));
        }
    }

}
