
package it.polimi.controller.test;

import java.util.List;
import it.polimi.model.test.Place;
import it.polimi.service.test.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    public PlaceService placeService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "place";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Place place) {
        List<Place> placeList;
        placeList=placeService.find(place);
        getRightMapping(placeList);
        return ResponseEntity.ok().body(placeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.GET)
    public ResponseEntity getplaceById(
        @PathVariable
        String placeId) {
        List<Place> placeList=placeService.findById(java.lang.Long.valueOf(placeId));
        getRightMapping(placeList);
        return ResponseEntity.ok().body(placeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteplaceById(
        @PathVariable
        String placeId) {
        placeService.deleteById(java.lang.Long.valueOf(placeId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertplace(
        @RequestBody
        Place place) {
        Place insertedplace=placeService.insert(place);
        getRightMapping(insertedplace);
        return ResponseEntity.ok().body(insertedplace);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateplace(
        @RequestBody
        Place place) {
        Place updatedplace=placeService.update(place);
        getRightMapping(updatedplace);
        return ResponseEntity.ok().body(updatedplace);
    }

    private List<Place> getRightMapping(List<Place> placeList) {
        for (Place place: placeList)
        {
        getRightMapping(place);
        }
        return placeList;
    }

    private void getRightMapping(Place place) {
        if (place.getOrder()!=null)
        {
        if (place.getOrder().getPerson()!=null)
        {
        }
        if (place.getOrder().getPlaceList()!=null)
        {
        place.getOrder().setPlaceList(null);
        }
        }
    }

}
