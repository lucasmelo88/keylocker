package br.com.lucassmelo.keylocker.controller;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.dto.KeyResponseDto;
import br.com.lucassmelo.keylocker.exception.InvalidKeyException;
import br.com.lucassmelo.keylocker.service.KeyLockerService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = KeyLockerController.BASE_PATH)
public class KeyLockerController {

  public static final String BASE_PATH = "/key-locker";

  @Autowired
  private KeyLockerService keyLockerService;

  @PostMapping(value = "/keys")
  public ResponseEntity<?> saveKey(
      @ApiParam(value = "keyRequestDto", required = true) @RequestBody KeyRequestDto keyRequestDto) {
    KeyResponseDto keyResponseDto = new KeyResponseDto();
    try {
      keyResponseDto = keyLockerService.processKey(keyRequestDto);
      return new ResponseEntity<>(keyResponseDto, HttpStatus.CREATED);
    } catch (InvalidKeyException ex) {
      return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
  }

}
