/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application.UI;

import BussinessLayer.Entity.HotelInfomation;
import BussinessLayer.Service.HotelService;
import DataLayer.ProductDao.HotelDao;
import java.io.EOFException;
import java.util.ArrayList;

/**
 *
 * @author NguyenDuy
 */
public class HotelManagement {

    public static void main(String[] args) throws EOFException {
        ArrayList<String> ops = new ArrayList<>();
        Menu menu = new Menu();
        HotelService hs = new HotelService();
        int choice;
        ops.add("Adding new Hotel");
        ops.add("Checking exits Hotel");
        ops.add("Updating Hotel information");
        ops.add("Deleting Hotel");
        ops.add("Searching Hotel.");
        ops.add("Displaying a hotel list");
        ops.add("Orther: Exit");
        do {
            choice = menu.int_getChoiceString(ops, 1, 7);
            switch (choice) {
                case 1:
                    hs.addHotel();
                    break;
                case 2:
                    hs.checkExitsHotel();
                    break;
                case 3:
                    hs.updateHotelInfomation();
                    break;
                case 4:
                    hs.deleteHotel();
                    break;
                case 5:
                    hs.searchHotel();
                    break;
                case 6:
                    hs.displayHotel();
                    break;
                default:
                    System.out.println("Bye!");
            }
        } while (!(choice < 1 || choice > 6));
    }
}
