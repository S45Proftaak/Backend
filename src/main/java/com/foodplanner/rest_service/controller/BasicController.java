package com.foodplanner.rest_service.controller;


import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


// Plaats boven elke class die functies bevat een @Controller tag.
// testController is de path naar deze class dus als je de url opzoekt doe je localhost:8020/testController/....
@RestController("testController")
@CrossOrigin
public class BasicController {


    @Autowired
    private UserRepository repo;
    /*
        Dit is een simpele get request.
        De GetMapping is zodat spring weet dat dit een get request is en de value is het pad naar deze functie.
        De mediatype geeft aan wat je terugkrijgt maar kan je weglaten.
        Door dat je responsebody aangeeft weet spring dat deze functie iets teruggeeft namelijk een string.
        je kan hier ook een model terug geven dan convert spring het naar json.
        De requestParam geeft aan dat deze call een parameter wil.
        Je hebt ook RequestBody dan vraag je dus om json dit doe je eigenlijk alleen bij post of put.
        Je hebt dus ook nog PostMapping en PutMapping voor de andere protocollen.
     */
    @GetMapping(value = "getID", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getid(@RequestParam String id){
        return id;
    }
}
