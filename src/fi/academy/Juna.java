package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class Juna {
        boolean cancelled;
        String commuterLineID;
        //LocalDate departureDate;  // Jackson ei oikein pidä Java 8 päivistä oletuksena
        Date departureDate;
        String operatorShortCode;
        int operatorUICCode;
        boolean runningCurrently;
        List<TimeTableRow> timeTableRows;
        Date timetableAcceptanceDate;
        String timetableType;
        String trainCategory;
        int trainNumber;
        String trainType;
        long version;
        String stationShortCode;
        int speed;
        Location location;

        @Override
        public String toString() {
            return "Juna{" + "cancelled=" + cancelled + ", commuterLineID='" + commuterLineID + '\'' + ", departureDate=" + departureDate + ", operatorShortCode='" + operatorShortCode + '\'' + ", operatorUICCode=" + operatorUICCode + ", runningCurrently=" + runningCurrently + ", timeTableRows=" + timeTableRows + ", timetableAcceptanceDate=" + timetableAcceptanceDate + ", timetableType='" + timetableType + '\'' + ", trainCategory='" + trainCategory + '\'' + ", trainNumber=" + trainNumber + ", trainType='" + trainType + '\'' + ", version=" + version + '}';
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }

        public String getCommuterLineID() {
            return commuterLineID;
        }

        public void setCommuterLineID(String commuterLineID) {
            this.commuterLineID = commuterLineID;
        }

        public Date getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(Date departureDate) {
            this.departureDate = departureDate;
        }

        public String getOperatorShortCode() {
            return operatorShortCode;
        }

        public void setOperatorShortCode(String operatorShortCode) {
            this.operatorShortCode = operatorShortCode;
        }

        public int getOperatorUICCode() {
            return operatorUICCode;
        }

        public void setOperatorUICCode(int operatorUICCode) {
            this.operatorUICCode = operatorUICCode;
        }

        public boolean isRunningCurrently() {
            return runningCurrently;
        }

        public void setRunningCurrently(boolean runningCurrently) {
            this.runningCurrently = runningCurrently;
        }

        public List<TimeTableRow> getTimeTableRows() {
            return timeTableRows;
        }

        public void setTimeTableRows(List<TimeTableRow> timeTableRows) {
            this.timeTableRows = timeTableRows;
        }

        public Date getTimetableAcceptanceDate() {
            return timetableAcceptanceDate;
        }

        public void setTimetableAcceptanceDate(Date timetableAcceptanceDate) {
            this.timetableAcceptanceDate = timetableAcceptanceDate;
        }

        public String getTimetableType() {
            return timetableType;
        }

        public void setTimetableType(String timetableType) {
            this.timetableType = timetableType;
        }

        public String getTrainCategory() {
            return trainCategory;
        }

        public void setTrainCategory(String trainCategory) {
            this.trainCategory = trainCategory;
        }

        public int getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(int trainNumber) {
            this.trainNumber = trainNumber;
        }

        public String getTrainType() {
            return trainType;
        }

        public void setTrainType(String trainType) {
            this.trainType = trainType;
        }

        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }

        public String getStationShortCode() {
            return this.stationShortCode;
        }

        public int getSpeed() {
            return this.speed;
        }

        public Location getLocation() {
            return this.location;
        }
    }


