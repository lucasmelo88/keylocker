package br.com.lucassmelo.keylocker.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface KeysRepository extends MongoRepository<KeysInfo, String> {

  @Query("{'accountNumber': ?0}")
  List<KeysInfo> findByAccountNumber(final String accountNumber);

  @Query("{'accountNumber': ?0, 'keyType': ?1}")
  List<KeysInfo> findByAccountNumberAndKeyType(final String accountNumber, final String keyType);

  @Query("{'agencyNumber': ?0, 'accountNumber': ?1}")
  List<KeysInfo> findByAgencyAndAccountNumber(final String agencyNumber,
      final String accountNumber);

  @Query("{'value': ?0}")
  List<KeysInfo> findByKeyValue(final String keyValue);
}
