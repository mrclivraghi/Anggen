
package it.generated.service;

import java.util.List;
import it.generated.searchbean.ExampleSearchBean;

public interface ExampleService {


    public List<it.generated.domain.Example> findById(Integer ExampleId);

    public List<it.generated.domain.Example> find(ExampleSearchBean Example);

    public void deleteById(Integer ExampleId);

    public it.generated.domain.Example insert(it.generated.domain.Example Example);

    public it.generated.domain.Example update(it.generated.domain.Example Example);

}
