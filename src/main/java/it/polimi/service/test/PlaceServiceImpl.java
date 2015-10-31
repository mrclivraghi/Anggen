
package it.polimi.service.test;

import java.util.List;
import it.polimi.model.test.Place;
import it.polimi.repository.test.PlaceRepository;
import it.polimi.searchbean.test.PlaceSearchBean;
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
    public List<Place> findById(Long placeId) {
        return placeRepository.findByPlaceId(placeId);
    }

    @Override
    public List<Place> find(PlaceSearchBean place) {
        return placeRepository.findByPlaceIdAndDescriptionAndOrder(place.getPlaceId(),place.getDescription(),place.getOrder());
    }

    @Override
    public void deleteById(Long placeId) {
        placeRepository.delete(placeId);
        return;
    }

    @Override
    public Place insert(Place place) {
        return placeRepository.save(place);
    }

    @Override
    @Transactional
    public Place update(Place place) {
        Place returnedPlace=placeRepository.save(place);
        if (place.getOrder()!=null)
        {
        List<Place> placeList = placeRepository.findByOrder( place.getOrder());
        if (!placeList.contains(returnedPlace))
        placeList.add(returnedPlace);
        returnedPlace.getOrder().setPlaceList(placeList);
        }
         return returnedPlace;
    }

}
