package express;

import express.http.response.Response;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerPool extends ThreadPoolExecutor {
    public HandlerPool(int size) {
        super(size, 20000, 60, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        this.setThreadFactory(runnable -> new Thread(runnable) {{
            setName(String.format("Express Async Request Handler #%s", getPoolSize()));
            setDaemon(true);
        }});
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        if (throwable != null) {
            Express.log.error("Exception in asynchronous task", throwable);
        }
    }

}
