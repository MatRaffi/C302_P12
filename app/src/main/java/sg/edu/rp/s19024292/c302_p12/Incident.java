package sg.edu.rp.s19024292.c302_p12;

import java.util.Date;

public class Incident {

    String Type, Message;
    Double Latitude,Longitude;
    Date date;

    public Incident(String type, Double latitude, Double longitude, String message, Date date) {
        Type = type;
        Message = message;
        Latitude = latitude;
        Longitude = longitude;
        this.date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "Type='" + Type + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
