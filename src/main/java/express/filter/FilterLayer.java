package express.filter;

import express.Express;
import express.http.HttpRequestHandler;
import express.http.request.Request;
import express.http.response.Response;
import express.utils.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Simon Reinisch
 * <p>
 * Controller class for FilterLayer.
 */
public class FilterLayer<T extends HttpRequestHandler> {

    private final List<T> filter = Collections.synchronizedList(new ArrayList<>());

    public void add(T expressFilter) {
        this.filter.add(expressFilter);
    }

    public void add(int index, T expressFilter) {
        this.filter.add(index, expressFilter);
    }

    public void addAll(List<T> expressFilters) {
        this.filter.addAll(expressFilters);
    }

    public List<T> getFilter() {
        return filter;
    }

    void filter(Request req, Response res, Express express) {
        ListIterator<T> iter = this.filter.listIterator();

        while (!res.isClosed() && iter.hasNext()) {
            iter.next().handle(req, res);
        }
    }
}
