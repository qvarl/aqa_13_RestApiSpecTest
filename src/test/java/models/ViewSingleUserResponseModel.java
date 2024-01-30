package models;


import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class ViewSingleUserResponseModel {

    Data data;
    Support support;

    @lombok.Data
    public class Support {

        private String url, text;
    }

    @lombok.Data
    public class Data {

        private int id;
        private String email, avatar;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;
    }
}