
package it.generated.service;

import java.util.List;
import it.generated.repository.ExampleRepository;
import it.generated.repository.PlaceRepository;
import it.generated.searchbean.ExampleSearchBean;
import it.generated.service.ExampleService;
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
    public List<it.generated.domain.Example> findById(Integer exampleId) {
        return exampleRepository.findByExampleId(exampleId);
    }

    @Override
    public List<it.generated.domain.Example> find(ExampleSearchBean example) {
        return exampleRepository.findByExampleIdAndExampleDateAndAgeAndMaleAndPlaceAndExampleTypeAndPlaceName(example.getExampleId(),it.polimi.utils.Utility.formatDate(example.getExampleDate()),example.getAge(),example.getMale(),example.getPlaceList()==null? null :example.getPlaceList().get(0),example.getExampleType().getValue(),example.getPlacePlaceName());
    }

    @Override
    public void deleteById(Integer exampleId) {
        exampleRepository.delete(exampleId);
        return;
    }

    @Override
    public it.generated.domain.Example insert(it.generated.domain.Example example) {
        return exampleRepository.save(example);
    }

    @Override
    @Transactional
    public it.generated.domain.Example update(it.generated.domain.Example example) {
        if (example.getPlaceList()!=null)
        for (it.generated.domain.Place place: example.getPlaceList())
        {
        place.setExample(example);
        }
        it.generated.domain.Example returnedExample=exampleRepository.save(example);
         return returnedExample;
    }

}
