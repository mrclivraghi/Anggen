
package it.polimi.service;

import java.util.List;
import it.polimi.repository.ExampleRepository;
import it.polimi.repository.PlaceRepository;
import it.polimi.searchbean.ExampleSearchBean;
import it.polimi.service.ExampleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExampleServiceImpl
    implements ExampleService
{

    @org.springframework.beans.factory.annotation.Autowired
    public ExampleRepository exampleRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public PlaceRepository example_placeRepository;

    @Override
    public List<it.polimi.domain.Example> findById(Integer exampleId) {
        return exampleRepository.findByExampleId(exampleId);
    }

    @Override
    public List<it.polimi.domain.Example> find(ExampleSearchBean example) {
        return exampleRepository.findByMaleAndAgeAndExampleDateAndExampleIdAndPlaceAndExampleTypeAndPlaceName(example.getMale(),example.getAge(),it.polimi.utils.Utility.formatDate(example.getExampleDate()),example.getExampleId(),example.getPlaceList()==null? null :example.getPlaceList().get(0), (example.getExampleType()==null)? null : example.getExampleType().getValue(),example.getPlacePlaceName());
    }

    @Override
    public void deleteById(Integer exampleId) {
        exampleRepository.delete(exampleId);
        return;
    }

    @Override
    public it.polimi.domain.Example insert(it.polimi.domain.Example example) {
        return exampleRepository.save(example);
    }

    @Override
    @Transactional
    public it.polimi.domain.Example update(it.polimi.domain.Example example) {
        if (example.getPlaceList()!=null)
        for (it.polimi.domain.Place place: example.getPlaceList())
        {
        place.setExample(example);
        }
        it.polimi.domain.Example returnedExample=exampleRepository.save(example);
         return returnedExample;
    }

}
