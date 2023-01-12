package br.com.lucassmelo.keylocker.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface KeysRepository extends MongoRepository<KeysInfo, String> {

  @Query("{'accountNumber': ?0}")
  List<KeysInfo> findByAccountNumber(final int accountNumber);

  @Query("{'value': ?0}")
  List<KeysInfo> findByKeyValue(final String keyValue);
}
