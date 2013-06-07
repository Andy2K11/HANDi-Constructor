package development.mvc;

import org.json.JSONObject;

public abstract class AbstractModel {
    
    protected AbstractModel() {
        super();
    }
    
    public abstract JSONObject getJSONObject();
}
