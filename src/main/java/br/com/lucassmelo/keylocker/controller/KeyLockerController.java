package br.com.lucassmelo.keylocker.controller;

import br.com.lucassmelo.keylocker.dto.CreateKeyResponseDto;
import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.exception.InvalidKeyException;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.service.KeyLockerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = KeyLockerController.BASE_PATH)
public class KeyLockerController {

  public static final String BASE_PATH = "/key-locker";

  @Autowired
  private KeyLockerService keyLockerService;

  @PostMapping(value = "/keys")
  @ApiOperation("Create new Pix Key")
  public ResponseEntity<?> saveKey(
      @ApiParam(value = "keyRequestDto", required = true) @RequestBody KeyRequestDto keyRequestDto) {
    CreateKeyResponseDto keyResponseDto = new CreateKeyResponseDto();
    try {
      keyResponseDto = keyLockerService.processKey(keyRequestDto);
      return new ResponseEntity<>(keyResponseDto, HttpStatus.CREATED);
    } catch (InvalidKeyException ex) {
      return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
  }

  @GetMapping(value = "/keys")
  @ApiOperation("Find pix key by agency number and account number")
  public ResponseEntity<List<KeysInfo>> updateKey(
      @RequestParam(value = "agencyNumber") String agencyNumber,
      @RequestParam(value = "accountNumber") String accountNumber) {

    return new ResponseEntity<>(
        keyLockerService.getKeysByAgencyAndAccountNumber(agencyNumber, accountNumber),
        HttpStatus.OK);
  }

}
