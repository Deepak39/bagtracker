package DistanceCalculator;

public class DistanceCalculator {

        private static Double EARTH_RADIUS = 6371.00 * 1000; // Radius in meters default

        public static Double DistanceCalculator(Double lat1, Double lon1, Double lat2,   Double lon2){

            //formula to calculate distance between two gps cordinates

            Double Radius = DistanceCalculator.EARTH_RADIUS; //6371.00;
            Double dLat = DistanceCalculator.toRadians(lat2-lat1);
            Double dLon = DistanceCalculator.toRadians(lon2-lon1);
            Double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(DistanceCalculator.toRadians(lat1)) *   Math.cos(DistanceCalculator.toRadians(lat2)) *
                            Math.sin(dLon/2) * Math.sin(dLon/2);
            Double c = 2 * Math.asin(Math.sqrt(a));

            return Radius * c;	//in meters
        }

        // This code converts the degree to radians
        public static Double toRadians(Double degree){
            // Value degree * Pi/180
            Double res = degree * 3.1415926 / 180;
            return res;
        }
}

