package br.com.lucassmelo.keylocker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormattedDateTime {

  public FormattedDateTime() {
  }

  public String getFormattedDateTimeNow() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    return LocalDateTime.now().format(dateTimeFormatter);
  }

}
