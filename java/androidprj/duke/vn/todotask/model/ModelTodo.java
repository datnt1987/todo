package androidprj.duke.vn.todotask.model;

import java.util.UUID;

/**
 * Created by datnt on 2/22/2016.
 */
public class ModelTodo {
    private String id;
    private String name;
    private int priority;

    public ModelTodo(){
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
