package com.booking.project.bookingsystem.reservation;

import com.booking.project.bookingsystem.reservation.model.HotelChain;
import com.booking.project.bookingsystem.reservation.model.Reservation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration()
@WebMvcTest(value= ReservationController.class)
class ReservationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReservationService reservationService;


    @Disabled
    void testGetAllReservations() throws Exception {
        UUID reservation_id = UUID.fromString("28748b6b-1286-49a9-9cf6-0e03c48ac0ec");
        Reservation mockReservation1 = new Reservation(reservation_id,
                HotelChain.Comfort_Hotel,
                101,
                "abdullahi",
                "hassan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05"));

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(mockReservation1);

        Mockito.when(reservationService.getAllReservations()).thenReturn(reservationList);

        String URI = "localhost:8080/api/reservations/allReservations";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getRequest());
        String expected = "{reservation_id:28748b6b-1286-49a9-9cf6-0e03c48ac0ec," +
                "hotel_chain:Comfort_Hotel,"+
                "hotel_room:101,"+
                "first_name:abdullahi,"+
                "last_name:hassan,"+
                "arrival_date:2022-01-03,"+
                "departure_date:2022-01-05}";
        JSONAssert.assertEquals(expected,result.getRequest().getContentAsString(),false);


    }


    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     */
    private String mapToJson(Object reservation) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(reservation);
    }



    /* Mockito.when(reservationService.getAllReservations()).thenReturn(reservationList);
        String URI = "api/reservations/allReservations";
        String data = new String (Files.readAllBytes(Paths.get("src/test/java/resources/testDataWithOnePerson.json")));
        System.out.println(data);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(data);


        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(reservationList);
        String outputJson = result.getRequest().getContentAsString();
        //this.mockMvc.perform(requestBuilder).andExpect(status().isOk());
        assertThat(outputJson).isEqualTo(expectedJson);*/

}