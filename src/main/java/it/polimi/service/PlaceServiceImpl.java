
package it.polimi.service;

import java.util.List;
import it.polimi.repository.PlaceRepository;
import it.polimi.searchbean.PlaceSearchBean;
import it.polimi.service.PlaceService;
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
    public List<it.polimi.domain.Place> findById(Integer placeId) {
        return placeRepository.findByPlaceId(placeId);
    }

    @Override
    public List<it.polimi.domain.Place> find(PlaceSearchBean place) {
        return placeRepository.findByPlaceIdAndPlaceNameAndExample(place.getPlaceId(),place.getPlaceName(),place.getExample());
    }

    @Override
    public void deleteById(Integer placeId) {
        placeRepository.delete(placeId);
        return;
    }

    @Override
    public it.polimi.domain.Place insert(it.polimi.domain.Place place) {
        return placeRepository.save(place);
    }

    @Override
    @Transactional
    public it.polimi.domain.Place update(it.polimi.domain.Place place) {
        it.polimi.domain.Place returnedPlace=placeRepository.save(place);
        if (place.getExample()!=null)
        {
        List<it.polimi.domain.Place> placeList = placeRepository.findByExample( place.getExample());
        if (!placeList.contains(returnedPlace))
        placeList.add(returnedPlace);
        returnedPlace.getExample().setPlaceList(placeList);
        }
         return returnedPlace;
    }

}
