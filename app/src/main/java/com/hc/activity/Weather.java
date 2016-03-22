package com.hc.activity;

public class Weather {

    private Weatherinfo weatherinfo;

    public void setWeatherinfo(Weatherinfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public Weatherinfo getWeatherinfo() {
        return weatherinfo;
    }

    public static class Weatherinfo {
        private String city;
        private String temp;
        private String WD;
        private String time;

        public void setCity(String city) {
            this.city = city;
        }


        public void setTemp(String temp) {
            this.temp = temp;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }


        public void setTime(String time) {
            this.time = time;
        }


        public String getCity() {
            return city;
        }

        public String getTemp() {
            return temp;
        }

        public String getWD() {
            return WD;
        }

        public String getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "Weatherinfo{" +
                    "city='" + city + '\'' +
                    ", temp='" + temp + '\'' +
                    ", WD='" + WD + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }
}
