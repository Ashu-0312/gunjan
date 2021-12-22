package app.gunjan.activities;

public class Model {
    private String name;

    public Model(String name, Boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    private Boolean isSelected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
