
package it.generated.service;

import java.util.List;
import it.generated.repository.PlaceRepository;
import it.generated.searchbean.PlaceSearchBean;
import it.generated.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceServiceImpl
    implements PlaceService
{

    @Autowired
    public PlaceRepository placeRepository;

    @Override
    public List<it.generated.domain.Place> findById(Integer placeId) {
        return placeRepository.findByPlaceId(placeId);
    }

    @Override
    public List<it.generated.domain.Place> find(PlaceSearchBean place) {
        return placeRepository.findByPlaceIdAndPlaceNameAndExample(place.getPlaceId(),place.getPlaceName(),place.getExample());
    }

    @Override
    public void deleteById(Integer placeId) {
        placeRepository.delete(placeId);
        return;
    }

    @Override
    public it.generated.domain.Place insert(it.generated.domain.Place place) {
        return placeRepository.save(place);
    }

    @Override
    @Transactional
    public it.generated.domain.Place update(it.generated.domain.Place place) {
        it.generated.domain.Place returnedPlace=placeRepository.save(place);
        if (place.getExample()!=null)
        {
        List<it.generated.domain.Place> placeList = placeRepository.findByExample( place.getExample());
        if (!placeList.contains(returnedPlace))
        placeList.add(returnedPlace);
        returnedPlace.getExample().setPlaceList(placeList);
        }
         return returnedPlace;
    }

}
