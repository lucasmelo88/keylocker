package br.com.lucassmelo.keylocker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormattedDateTime {

  public LocalDateTime getFormattedDateTimeNow() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = dateTimeFormatter.format(LocalDateTime.now());
    return LocalDateTime.parse(formattedDate, dateTimeFormatter);
  }

}
