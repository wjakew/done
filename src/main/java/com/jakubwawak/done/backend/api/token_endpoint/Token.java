/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api.token_endpoint;

/**
 * Object for storing information about a token
 */
public class Token {

    public String token_code;
    public String token_time;
    public String api_key_corelated;


    public Token(String token_code, String token_time, String api_key_corelated){
        this.token_code = token_code;
        this.token_time = token_time;
        this.api_key_corelated = api_key_corelated;
    }
}
