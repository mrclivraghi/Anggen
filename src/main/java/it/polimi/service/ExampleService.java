
package it.polimi.service;

import java.util.List;
import it.polimi.model.Example;

public interface ExampleService {


    public List<Example> findById(Integer exampleId);

    public List<Example> find(Example example);

    public void deleteById(Integer exampleId);

    public Example insert(Example example);

    public Example update(Example example);

}
