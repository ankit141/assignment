package com.assignment.newsportal;

public class Constants {
    public static final String EMAIL="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
//    public static final String EMAIL="/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/";
    public static final String URL="^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.)?$";
    public static final String PASSWORD= "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

}
