package webmagic.pipeline;

import webmagic.ResultItems;
import webmagic.Task;

/**
 * Pipeline is the persistent and offline process part of crawler.<br>
 * The api Pipeline can be implemented to customize ways of persistent.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 * @see ConsolePipeline
 * @see FilePipeline
 */
public interface Pipeline<T> {

    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task task
     */
    public void process(ResultItems resultItems, Task task);

}
