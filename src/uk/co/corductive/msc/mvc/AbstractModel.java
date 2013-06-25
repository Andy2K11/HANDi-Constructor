package uk.co.corductive.msc.mvc;

import javafx.scene.input.DataFormat;
import org.json.JSONObject;

public abstract class AbstractModel {
    
    public static final DataFormat dataFormat = new DataFormat("json");
    protected String name;
    
    protected AbstractModel() {
        super();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public abstract JSONObject getJSONObject();
}
