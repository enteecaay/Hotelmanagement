/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLayer.Service;

import Application.UI.Menu;
import Application.Utilities.DataInput;
import BussinessLayer.Components.DataValidation;
import BussinessLayer.Components.SearchData;
import BussinessLayer.Entity.HotelInfomation;
import DataLayer.ProductDao.HotelDao;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author NguyenDuy
 */
public class HotelService implements IHotelService {

    private ArrayList<HotelInfomation> arrHotel;
    private DataInput di;
    private DataValidation dv;
    private HotelDao hd;
    private SearchData sd;
    private Menu mn;

    public HotelService() throws EOFException {
        arrHotel = new ArrayList<>();
        di = new DataInput();
        dv = new DataValidation();
        hd = new HotelDao();
        sd = new SearchData();
        mn = new Menu();
        try {
            hd.loadDataFromFile(arrHotel, "Hotel.dat");
        } catch (Exception e) {
            System.out.println("List empty");
        }

        if (arrHotel.isEmpty()) {
            System.out.println("Empty list, add new one");
            addHotel();
        }
    }

    String blank(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += " ";
        }
        return result;
    }

    public void graphic(HotelInfomation hotel) {

    String address = hotel.getHotel_Address();
    
    Pattern pattern = Pattern.compile("(.{0,30})\\b"); 
    Matcher matcher = pattern.matcher(address);

    System.out.println("---------------------------------------------------------------------------------------------------------------");
    
    // In nhóm đầu tiên 
    if(matcher.find()) {
        System.out.printf("|%9s|%17s|%25s|%30s|%11s|%7s star|\n",
                hotel.getHotel_Id(), 
                hotel.getHotel_Name(),
                hotel.getHotel_Room_Available(),
                matcher.group(1), hotel.getHotel_Phone(), hotel.getHotel_Rating()); 
    }
    
    // In các nhóm còn lại
    while(matcher.find()) {
        // Kiểm tra xem có phải nhóm rỗng không
        if(!matcher.group(1).equals("")) {  
            System.out.printf("|%9s|%17s|%25s|%30s|%11s|%12s|\n",
                    "", "", "", 
                    matcher.group(1), "", "");
        }
    }
        System.out.println("---------------------------------------------------------------------------------------------------------------");

}

    @Override
    public void addHotel() {
        String hotel_Id;
        String hotel_Name;
        int hotel_Room_Available;
        String hotel_Address;
        String hotel_Phone;
        int hotel_Rating;
        boolean choice = true;
        while (choice) {
            hotel_Id = dv.inputHotelID(arrHotel);
            hotel_Name = di.inputStringPattern("Enter name of hotel: ", "^[a-zA-Z\\s]+$");
            hotel_Room_Available = di.inputInteger("Enter the number of available rooms", 0, 10000);
            hotel_Address = di.inputStringCanBlank("Enter hotel address: ");
            hotel_Phone = di.inputStringPattern("Enter hotel phone number(like 0xxxxxxxx)", "0\\d{9}");
            hotel_Rating = di.inputInteger("Enter hotel rating(0-5)", 0, 5);
            arrHotel.add(new HotelInfomation(hotel_Id, hotel_Name, hotel_Room_Available, hotel_Address, hotel_Phone, hotel_Rating));
            hd.saveDataFromFile(arrHotel, "Hotel.dat");
            choice = di.inputYN("Do you want to continue(Y/N): ");
        }
    }

    @Override
    public void checkExitsHotel() {
        String id = di.inputStringNotEmpty("Enter id of hotel you want to check: ");
        ArrayList<HotelInfomation> arrTemp = new ArrayList<>();
        hd.loadDataFromFile(arrTemp, "Hotel.dat");
        HotelInfomation hi = sd.searchHotelByID(arrTemp, id);
        if (hi != null) {
            System.out.println("Exist Hotel");
            graphic(hi);
        } else {
            System.out.println("“No Hotel Found!");
        }
    }

    @Override
    public void updateHotelInfomation() {
        System.out.println("Enter product you want to update: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        HotelInfomation hotel = sd.searchHotelByID(arrHotel, id);
        String hotel_Name;
        int hotel_Room_Available;
        String hotel_Address;
        String hotel_Phone;
        int hotel_Rating;
        boolean check = true;
        boolean choice = true;
        if (hotel != null) {
            System.out.println("Found! Here is product: ");
            graphic(hotel);
            hotel_Name = dv.inputNameUD("Enter name you want to update: ", hotel);
            hotel_Room_Available = dv.inputHotelAvailableUD("Enter available of hotel update: ", 0, 10000, hotel);
            hotel_Address = dv.inputAddressUD("Enter hotel address you want to update: ", hotel);
            hotel_Phone = dv.inputPhoneUD("Enter phone number you want to update: ", hotel);
            hotel_Rating = dv.inputHotelRatingUD("Enter hotel rating you want to update: ", 0, 5, hotel);
            arrHotel.set(arrHotel.indexOf(hotel), new HotelInfomation(id, hotel_Name, hotel_Room_Available, hotel_Address, hotel_Phone, hotel_Rating));
            System.out.println("Here is hotel after update: ");
            System.out.println(hotel);
            hd.saveDataFromFile(arrHotel, "Hotel.dat");
        } else {
            System.out.println("Not found!");
        }
    }

    @Override
    public void deleteHotel() {
        System.out.println("Enter id of product you want to delete: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        HotelInfomation hotel = sd.searchHotelByID(arrHotel, id);
        boolean choice = true;
        if (hotel != null) {
            System.out.println("Found! Here is product: ");
            graphic(hotel);
            choice = di.inputYN("You really want to delete(Y/N): ");
            if (choice) {
                arrHotel.remove(hotel);
                System.out.println("Delete successfully!");
            }
        } else {
            System.out.println("Not found!");
        }
        hd.saveDataFromFile(arrHotel, "Hotel.dat");
    }

    @Override
    public void searchHotel() {
        ArrayList<String> ops = new ArrayList<>();
        int choice;
        ArrayList<HotelInfomation> arrTemp = arrHotel;
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the information you want to search for flights: ");
        ops.add("Search by Hotel_ID");
        ops.add("Search by Hotel_Name");
        ops.add("Exit");
        do {
            boolean check = false;
            choice = mn.int_getChoiceString(ops, 1, 3);
            switch (choice) {
                case 1:
                    String id = sc.nextLine();
                    for (HotelInfomation hotelInfomation : arrTemp) {
                        if (hotelInfomation.getHotel_Id().contains(id)) {
                            graphic(hotelInfomation);
                            check = true;
                        }
                    }
                    if (!check) {
                        System.out.println("Can't found id" + id);
                    }
                    break;
                case 2:
                    String name = sc.nextLine();

                    for (HotelInfomation hotelInfomation : arrTemp) {
                        if (hotelInfomation.getHotel_Name().contains(name)) {
                            graphic(hotelInfomation);
                            check = true;
                        }
                    }
                    if (!check) {
                        System.out.println("Can't found name" + name);
                    }
                    break;

                default:
                    System.out.println("Bye!");
            }
        } while (!(choice < 1 || choice > 2));
    }

    @Override
    public void displayHotel() {
        Collections.sort(arrHotel);
        System.out.printf("|%9s|%17s|%25s|%30s|%11s|%10s|\n", "Hotel_ID", "Hotel_Name", "Hotel_Room_Available", "Hotel_Address", "Hotel_Phone", "Hotel_Rating");
        for (HotelInfomation hotelInfomation : arrHotel) {
            graphic(hotelInfomation);
        }
    }

}
