package models;

import lombok.Data;

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
        private String email, first_name, last_name, avatar;
    }
}