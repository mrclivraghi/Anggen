
package it.polimi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.polimi.model.Photo;
import it.polimi.service.PhotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    public PhotoService photoService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "photo";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Photo photo) {
        List<Photo> photoList;
        photoList=photoService.find(photo);
        getRightMapping(photoList);
        return ResponseEntity.ok().body(photoList);
    }

    @ResponseBody
    @RequestMapping(value = "/{photoId}", method = RequestMethod.GET)
    public ResponseEntity getphotoById(
        @PathVariable
        String photoId) {
        List<Photo> photoList=photoService.findById(Long.valueOf(photoId));
        getRightMapping(photoList);
        return ResponseEntity.ok().body(photoList);
    }

    @ResponseBody
    @RequestMapping(value = "/{photoId}", method = RequestMethod.DELETE)
    public ResponseEntity deletephotoById(
        @PathVariable
        String photoId) {
        photoService.deleteById(Long.valueOf(photoId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertphoto(
        @RequestBody
        Photo photo) {
        Photo insertedphoto=photoService.insert(photo);
        getRightMapping(insertedphoto);
        return ResponseEntity.ok().body(insertedphoto);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatephoto(
        @RequestBody
        Photo photo) {
        Photo updatedphoto=photoService.update(photo);
        getRightMapping(updatedphoto);
        return ResponseEntity.ok().body(updatedphoto);
    }

    private List<Photo> getRightMapping(List<Photo> photoList) {
        for (Photo photo: photoList)
        {
        getRightMapping(photo);
        }
        return photoList;
    }

    private void getRightMapping(Photo photo) {
        if (photo.getSeedQuery()!=null)
        {
        if (photo.getSeedQuery().getMountain()!=null)
        {
        if (photo.getSeedQuery().getMountain().getSeedQueryList()!=null)
        {
        photo.getSeedQuery().getMountain().setSeedQueryList(null);
        }
        }
        if (photo.getSeedQuery().getPhotoList()!=null)
        {
        photo.getSeedQuery().setPhotoList(null);
        }
        }
    }
    

}
