package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.configuration.PriceConfiguration;
import com.foodplanner.rest_service.configuration.WritePropperties;
import com.foodplanner.rest_service.dtos.administration.request.UpdatePriceDTO;
import com.foodplanner.rest_service.endpoints.AdministrationEndpoints;
import com.foodplanner.rest_service.exceptions.FileNotWritable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = AdministrationEndpoints.BASE)
//@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdministrationController {

    @Autowired
    private PriceConfiguration priceConfiguration;

    @PutMapping(value = AdministrationEndpoints.UPDATE_PRICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePrice(@RequestBody UpdatePriceDTO priceDTO) throws FileNotWritable {
        priceConfiguration.setPrice(priceDTO.getPrice());
        WritePropperties.writePropsToFile("configuration.price", priceDTO.getPrice(), "settings.properties");
    }

}
