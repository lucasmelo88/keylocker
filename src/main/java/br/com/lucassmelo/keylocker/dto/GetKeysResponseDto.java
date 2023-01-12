package br.com.lucassmelo.keylocker.dto;

import br.com.lucassmelo.keylocker.repository.KeysInfo;
import java.util.ArrayList;
import java.util.List;

public class GetKeysResponseDto {

  private List<KeysInfo> keysInfoList = new ArrayList<>();

  public GetKeysResponseDto() {
  }

  public List<KeysInfo> getKeysInfoList() {
    return keysInfoList;
  }

  public void setKeysInfoList(List<KeysInfo> keysInfoList) {
    this.keysInfoList = keysInfoList;
  }
}
