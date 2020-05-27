package com.dawn.foundation.workflow;

public interface Task<V extends TaskMessage> {

    void execute(V message) throws Exception;

}
