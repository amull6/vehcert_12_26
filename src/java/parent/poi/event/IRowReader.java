package parent.poi.event;

import java.util.List;
import java.util.Map;

public interface  IRowReader {
    public  Map<String, String> getRows(int curRow, List<String> rowlist);
}
