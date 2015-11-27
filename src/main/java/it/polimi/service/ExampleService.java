
package it.polimi.service;

import java.util.List;
import it.polimi.searchbean.ExampleSearchBean;

public interface ExampleService {


    public List<it.polimi.domain.Example> findById(Integer ExampleId);

    public List<it.polimi.domain.Example> find(ExampleSearchBean Example);

    public void deleteById(Integer ExampleId);

    public it.polimi.domain.Example insert(it.polimi.domain.Example Example);

    public it.polimi.domain.Example update(it.polimi.domain.Example Example);

}
