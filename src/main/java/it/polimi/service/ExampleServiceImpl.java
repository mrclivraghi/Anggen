
package it.polimi.service;

import java.util.List;
import it.polimi.model.Example;
import it.polimi.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExampleServiceImpl
    implements ExampleService
{

    @Autowired
    public ExampleRepository exampleRepository;

    @Override
    public List<Example> findById(Integer exampleId) {
        return exampleRepository.findByExampleId(exampleId);
    }

    @Override
    public List<Example> find(Example example) {
        return exampleRepository.findByExampleIdAndNameAndEtaAndMaleAndBirthDateAndBirthTime(example.getExampleId(),example.getName(),example.getEta(),example.getMale(),it.polimi.utils.Utility.formatDate(example.getBirthDate()),example.getBirthTime());
    }

    @Override
    public void deleteById(Integer exampleId) {
        exampleRepository.delete(exampleId);
        return;
    }

    @Override
    public Example insert(Example example) {
        return exampleRepository.save(example);
    }

    @Override
    @Transactional
    public Example update(Example example) {
        Example returnedExample=exampleRepository.save(example);
         return returnedExample;
    }

}
