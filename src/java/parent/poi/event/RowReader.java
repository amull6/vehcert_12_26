package parent.poi.event;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowReader implements IRowReader {
	private List<String> labels=new ArrayList<String>();
	private Map<String,Object> params=new HashMap<String,Object>();
	public RowReader(List<String> labels, Map<String,Object> params) {
		super();
		this.labels = labels;
        this.params = params;
	}

	@Override
	public Map<String, String> getRows(int curRow,
			List<String> rowlist) {

         Integer startRow=(Integer)params.get("startRow") ;
         Map<String, String> result=null;
         if(startRow!=null&&startRow>curRow){

         }else{
             result=new HashMap<String, String>();
             for(int i=0;i<labels.size();i++){
                 if(i<rowlist.size()){
                     result.put(labels.get(i), rowlist.get(i));
                 }else{
                     break;
                 }
             }
         }
	     return result;
	}


}
