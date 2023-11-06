package com.example.planmyday;
import android.os.Parcel;
import android.os.Parcelable;

public class Attraction implements Parcelable {
    private String name;
    private double longitude;
    private double latitude;
    private int time;
    private boolean atUSC;

    public Attraction(String name, double longitude, double latitude, int time, boolean atUSC){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.atUSC = atUSC;
    }

    private static final double Rad = 6371000.0;
    /**
     * Calculates the distance between two latitude/longitude points
     * in kilometers using the Haversine formula.
     */
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2)
                * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Rad * c;
    }

    public double distanceTo(Attraction other) {
        return haversine(this.latitude, this.longitude, other.latitude, other.longitude);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getLongitude() {return longitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public double getLatitude() {return latitude;}
    public void setLatitude(double latitude) {this.latitude = latitude;}
    public int getTime() {return time;}
    public void setTime(int time) {this.time = time;}
    public boolean isAtUSC() {return atUSC;}
    public void setAtUSC(boolean atUSC) {this.atUSC = atUSC;}

    // Parcelable implementation
    protected Attraction(Parcel in) {
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        time = in.readInt();
        atUSC = in.readByte() != 0;
    }

    public static final Creator<Attraction> CREATOR = new Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel in) {
            return new Attraction(in);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(time);
        dest.writeByte((byte) (atUSC ? 1 : 0));
    }
}

