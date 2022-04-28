package com.utc.controller;

import com.utc.dto.HotelDTO;
import com.utc.entity.Hotel;
import com.utc.form.create.HotelCreateForm;
import com.utc.form.filter.HotelFilterForm;
import com.utc.form.update.HotelUpdateForm;
import com.utc.service.IHotelService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("UTCDemo/hotel")
public class HotelController {


    @Autowired
    private IHotelService hotelService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllListHotels(){
        List<Hotel> list = hotelService.findAllHotel();
        List<HotelDTO> hotelDTOS = modelMapper.map(list,new TypeToken<List<HotelDTO>>(){}.getType());

        return new ResponseEntity<>(hotelDTOS, HttpStatus.OK);
    }

    @GetMapping("/getByCity")
    public ResponseEntity<?> getHotelByCity(Pageable pageable, @RequestParam(name = "city") String city){
        Page<Hotel> page = hotelService.findAllHotelByCity(city, pageable);
        List<HotelDTO> hotelDTOList = modelMapper.map(page.getContent(),new TypeToken<List<HotelDTO>>(){}.getType());
        Page<HotelDTO> hotelDTOPage = new PageImpl<>(hotelDTOList,pageable,page.getTotalElements());

        return new ResponseEntity<>(hotelDTOPage,HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> findAllByPage(@RequestParam(name = "search",required = false) String search,Pageable pageable, HotelFilterForm form){
        Page<Hotel> pageHotel = hotelService.findAllHotelByPage(search, pageable, form);
        List<HotelDTO> hotelDTOList = modelMapper.map(pageHotel.getContent(),new TypeToken<List<HotelDTO>>(){}.getType());
        Page<HotelDTO> hotelDTOPage = new PageImpl<>(hotelDTOList,pageable,pageHotel.getTotalElements());

        return new ResponseEntity<>(hotelDTOPage,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody HotelCreateForm form){
        hotelService.createHotel(form);
        return new ResponseEntity<>("Create Hotel Success!!!",HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateHotel(@RequestParam(name = "id") int id,@RequestBody HotelUpdateForm form){
        boolean checkExits = hotelService.HotelExitsById(id);
        if (!checkExits){
            hotelService.updateHotel(id,form);
            return new ResponseEntity<>("Update Hotel Success!!!",HttpStatus.OK);
        }else {

            return new ResponseEntity<>("Hotel Doesn't Exits!!!",HttpStatus.OK);
        }

    }
}
