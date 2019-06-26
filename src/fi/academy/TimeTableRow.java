package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.format.DateTimeFormatter;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableRow {
    //edited by samu
    boolean trainStopping; //Pysähtyykö juna liikennepaikalla
    String stationShortCode; //Aseman lyhennekoodi
    //    stationcUICCode: 1-9999  Aseman UIC-koodi
    //    countryCode: “FI”, “RU”
    String type; // Aina joko “ARRIVAL” tai “DEPARTURE”. Pysähdyksen tyyppi.
    //    commercialStop: boolean  Onko pysähdys kaupallinen. Annettu vain pysähdyksille, ei läpiajoille. Mikäli junalla on osaväliperumisia, saattaa viimeinen perumista edeltävä pysähdys jäädä virheellisesti ei-kaupalliseksi.
    //            commercialTrack: string  Suunniteltu raidenumero, jolla juna pysähtyy tai jolta se lähtee. Raidenumeroa ei saada junille, joiden lähtöön on vielä yli 10 päivää. Operatiivisissa häiriötilanteissa raide voi olla muu.
    boolean cancelled; //Totta, jos lähtö tai saapuminen on PERUTTU!
    Date scheduledTime; //Aikataulun mukainen pysähtymis- tai lähtöaika , esim: 2019-06-26T07:18:30.000Z

    public boolean isTrainStopping() {
        return trainStopping;
    }

    public void setTrainStopping(boolean trainStopping) {
        this.trainStopping = trainStopping;
    }

    public String getStationShortCode() {
        return stationShortCode;
    }

    public void setStationShortCode(String stationShortCode) {
        this.stationShortCode = stationShortCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Date getScheduledTime() {

        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}

/*
Tällä voi muuttaa ajan tulostuksen siistimpään muotoon!
   Thu Jun 27 15:57:42 EEST 2019
    LocalDateTime myDateObj = LocalDateTime.now();
    System.out.println("Before formatting: " + myDateObj);
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    String formattedDate = myDateObj.format(myFormatObj);
    System.out.println("After formatting: " + formattedDate);
}
Tai tällä??
Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String s = formatter.format(date);

 */