package com.Tipper.TipperHotelMongo.entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Bookings {
    @Id
    private String Id;

    @NotBlank(message = "CheckInDate")
    private String checkInDate;

    @NotBlank(message = "CheckOutDate")
    private String checkOutDate;

    @Min(value = 1, message= "No of Adults should not be less than 1")
    private int noOfAdults;


    private int noOfChildren;

    private int totalNumOfGuest;

    private String bookingConfirmationCode;

   @DBRef
   private User user;

   @DBRef
   private Rooms room;

   public void calculateTotalNumOfGuest(){
       this.totalNumOfGuest = this.noOfAdults + this.noOfChildren;
   }

    public void setNumOfAdults(int noOfAdults){
       this.noOfAdults = noOfAdults;
       calculateTotalNumOfGuest();
   }

    public void setNumOfChildren(int noOfChildren){
       this.noOfChildren = noOfAdults;
       calculateTotalNumOfGuest();
   }

    @Override
    public String toString(){
        return "Bookings{" +
               "id=" + '\''+
               ",checkOutDate='"+checkOutDate+
               ",checkInDate='"+checkInDate+
               ",noOfAdults='"+noOfAdults+
               ",noOfChildren='"+noOfChildren+
               ",totalNumOfGuest='"+totalNumOfGuest+
               ",bookingConfirmationCode='"+bookingConfirmationCode+'\''+
    '}';


    }

}

