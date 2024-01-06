/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLayer.Components;

import BussinessLayer.Entity.HotelInfomation;
import java.util.ArrayList;

/**
 *
 * @author NguyenDuy
 */
public class SearchData {
    public HotelInfomation searchHotelByID(ArrayList<HotelInfomation> arr, String id){
        for (HotelInfomation hotelInfomation : arr) {
            if(id.equals(hotelInfomation.getHotel_Id())){
                return hotelInfomation;
            }
        }
        return null;
    }
}
