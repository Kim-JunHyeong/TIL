package examples.spring.demowebmvc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Event {

    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    private Integer limit;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
