package app.gunjan.entity;

import java.io.Serializable;

public class ShowInterestModel implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShowInterestModel(String name,String id) {
        this.name = name;
        this.id = id;
    }

    private String name;
    private String id;
}
