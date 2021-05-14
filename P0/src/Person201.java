public class Person201 {

    private String myName;
    private double myLatitude;
    private double myLongitude;
    private String myPhrase;

    /**
     * Default constructor for Owen@Duke
     */
    public Person201(){
        // TODO change here
        myName = "Owen";
        myLatitude = 35.9312;
        myLongitude = -79.0058;
        myPhrase = "woto";
    }

    /**
     * Construct Person201 object with information
     * @param name typically first name of person
     * @param lat latitude, negative for southern hemisphere
     * @param lon longitude, negative for western hemisphere
     * @param phrase for person
     */
    public Person201(String name,
                     double lat, double lon,
                     String phrase) {
        myName = name;
        myLatitude = lat;
        myLongitude = lon;
        myPhrase = phrase;
    }

    /**
     * Returns the latitude as double, negative for below equator
     * @return this person's latitude
     */
    public double getLatitude(){
        return myLatitude;
    }

    /**
     * Returns the longitude as double, negative for west of prime meridian
     * @return this person's longitude
     */
    public double getMyLongitude(){
        return myLongitude;
    }

    /**
     * Returns phrase for this person.
     * @return phrase for this person.
     */
    public String getPhrase(){
        // TODO change here
        return myPhrase;
    }

    /**
     * Returns name of this person.
     * @return name of this person
     */
    public String getName(){
        // TODO change here
        return myName;
    }


    /**
     * Returns String using E/W for longitude, N/S for latitude
     */
    @Override
    public String toString(){
        String lats = ""+Math.abs(myLatitude);
        if (myLatitude < 0) {
            lats += "S";
        }
        else {
            lats += "N";
        }
        String lons = ""+Math.abs(myLongitude);
        if (myLongitude < 0) {
            lons += "W";
        }
        else {
            lons += "E";
        }
        return String.format("%s %s @ %s %s",myName,myPhrase,lats,lons);
    }

}
