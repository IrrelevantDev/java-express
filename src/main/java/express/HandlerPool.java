package express;

import com.sun.net.httpserver.HttpExchange;
import express.filter.FilterLayerHandler;
import express.http.response.Response;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerPool extends ThreadPoolExecutor {
    public HandlerPool(int size) {
        super(Math.min(size, 500), 500, 60, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        this.setThreadFactory(runnable -> new Thread(runnable) {{
            setName(String.format("Express Async Request Handler #%s", getPoolSize()));
            setDaemon(true);
        }});
    }

    public void execute(Express express, HttpExchange exchange) {
        super.execute(() -> express.handler.handle(exchange,express));
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        if (throwable != null) {
            Express.log.error("Exception in asynchronous task", throwable);
        }
    }

}
