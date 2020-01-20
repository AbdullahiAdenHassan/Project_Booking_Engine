package com.booking.project.bookingsystem.reservation.integration;

import com.booking.project.BookingEngineApp;
import com.booking.project.bookingsystem.reservation.model.Reservation;
import com.booking.project.bookingsystem.utils.Handler;
import org.json.simple.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingEngineApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private HttpHeaders headers;
    private String URI;
    private HttpEntity<Object> requestEntity;
    private HttpStatus statusForGet;
    private HttpStatus statusForPost;
    private JSONArray jsonFile;
    private List<Reservation> inputData;
    private List<Reservation> responseData;


    @Test
    public void test_update_One_Reservation_Into_DataBase() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Running test: " + new Throwable().getStackTrace()[0].getMethodName() + "\n");

        givenThatReservationTableIsEmpty();
        givenThatJsonFileContentsOneReservations();
        givenThatUserIsLoggedInToWebSite();
        givenThatContentTypeIsApplicationJson();
        whenReservationFileIsSent();
        whenReservationUpdateURIAddressIsGiven();
        whenReservationUpdateJsonFileIsGiven();
        whenReservationHasBeenUpdatedById();
        whenReservationDataIsReceived();
        thenVerifyThatDataBaseHasBeenUpdated();

        System.out.println("--------------------------------------------------------------------------");
    }

    @Test
    public void test_Adding_two_Reservations_Into_DataBase() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Running test: " + new Throwable().getStackTrace()[0].getMethodName() + "\n");

        givenThatReservationTableIsEmpty();
        givenThatUserIsLoggedInToWebSite();
        givenThatJsonFileContentsTwoReservations();
        givenThatContentTypeIsApplicationJson();
        whenReservationFileIsSent();
        whenReservationDataIsReceived();
        thenVerifyThatTwoReservationsExistInDataBase();

        System.out.println("--------------------------------------------------------------------------");
    }

    @Test
    public void test_Adding_One_Reservation_Into_DataBase() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Running test: " + new Throwable().getStackTrace()[0].getMethodName() + "\n");

        givenThatReservationTableIsEmpty();
        givenThatUserIsLoggedInToWebSite();
        givenThatJsonFileContentsOneReservations();
        givenThatContentTypeIsApplicationJson();
        whenReservationFileIsSent();
        whenReservationDataIsReceived();
        thenVerifyThatJsonFileIsEqualToDataBaseInfo();

        System.out.println("--------------------------------------------------------------------------");
    }

    @Test
    public void test_Http_status_For_Post_Methods() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Running test: " + new Throwable().getStackTrace()[0].getMethodName() + "\n");

        givenThatReservationTableIsEmpty();
        givenThatBookingEngineHasAPostMethodAndConsumesMediaTypeJson();
        whenTheApplicationPostAReservationIntoDataBase();
        thenVerifyThatPostStatusIs200();

        System.out.println("--------------------------------------------------------------------------");
    }

    @Test
    public void test_Http_Status_For_Get_Methods() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Running test: " + new Throwable().getStackTrace()[0].getMethodName() + "\n");

        givenThatBookingEngineHasAGetMethodAndConsumesMediaTypeJson();
        whenTheApplicationGetsAllReservationsFromTheDataBase();
        thenVerifyThatGetStatusIs200();

        System.out.println("--------------------------------------------------------------------------");
    }

    private void givenThatUserIsLoggedInToWebSite() {
        URI = "/addReservation";
    }

    private void givenThatJsonFileContentsTwoReservations() {
        String jsonPath = "src/test/resources/testDataTwoReservations"; // Two reservation
        jsonFile = Handler.jacksonReadingJsonFiles(jsonPath);
    }

    private void givenThatJsonFileContentsOneReservations() {
        String jsonPath = "src/test/resources/testDataOneReservation"; // One reservation
        jsonFile = Handler.jacksonReadingJsonFiles(jsonPath);
    }

    private void givenThatContentTypeIsApplicationJson() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private void givenThatReservationTableIsEmpty() {
        testRestTemplate.delete(Handler.formFullURLWithPort(port, "/deleteAll"));
    }

    private void givenThatBookingEngineHasAPostMethodAndConsumesMediaTypeJson() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI = "/addReservation";
    }

    private void whenReservationUpdateURIAddressIsGiven() {
        URI = "/updateReservation/9abacbae-348d-11ea-aec2-2e728ce88125";
    }

    private void whenReservationUpdateJsonFileIsGiven() {
        String jsonPath = "src/test/resources/testDataForUpdatingOneEntireReservation";
        jsonFile = Handler.jacksonReadingJsonFiles(jsonPath);
    }

    private void whenReservationHasBeenUpdatedById() {
        inputData = new ArrayList<>();
        for (Object reservation : jsonFile) {
            HttpEntity<Object> requestEntity = new HttpEntity<>(reservation, headers);

            ResponseEntity<Reservation> updateResponseEntity = testRestTemplate.exchange(
                    Handler.formFullURLWithPort(port, URI),
                    HttpMethod.PUT,
                    requestEntity,
                    Reservation.class);

            assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            inputData.add(Handler.convertDataToReservationObject(reservation));
        }
    }

    private void whenReservationFileIsSent() {
        inputData = new ArrayList<>();
        for (Object reservation : jsonFile) {
            HttpEntity<Object> requestEntity = new HttpEntity<>(reservation, headers);

            ResponseEntity<String> postResponseEntity = testRestTemplate.exchange(
                    Handler.formFullURLWithPort(port, URI),
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            inputData.add(Handler.convertDataToReservationObject(reservation));
        }

    }

    private void whenReservationDataIsReceived() {
        URI = "/getAllReservation";
        ResponseEntity<JSONArray> getResponseEntity = testRestTemplate
                .getForEntity(Handler.formFullURLWithPort(port, URI),
                        JSONArray.class);

        statusForGet = getResponseEntity.getStatusCode();
        assertThat(HttpStatus.OK).isEqualTo(statusForGet);

        responseData = new ArrayList<>();
        for (Object reservation : Objects.requireNonNull(getResponseEntity.getBody())) {
            responseData.add(Handler.convertDataToReservationObject(reservation));
        }
    }

    private void whenTheApplicationPostAReservationIntoDataBase() {
        String jsonPath = "src/test/resources/testDataOneReservation";
        JSONArray reservationList = Handler.jacksonReadingJsonFiles(jsonPath);
        requestEntity = new HttpEntity<>(reservationList.get(0), headers);
        ResponseEntity<String> postResponseEntity = testRestTemplate.exchange(
                Handler.formFullURLWithPort(port, URI),
                HttpMethod.POST,
                requestEntity,
                String.class);
        statusForPost = postResponseEntity.getStatusCode();
    }

    private void whenTheApplicationGetsAllReservationsFromTheDataBase() {
        requestEntity = new HttpEntity<>(headers);
        ResponseEntity<JSONArray> getResponseEntity = testRestTemplate
                .exchange(
                        Handler.formFullURLWithPort(port, URI),
                        HttpMethod.GET,
                        requestEntity,
                        JSONArray.class);
        statusForGet = getResponseEntity.getStatusCode();
    }

    private void thenVerifyThatDataBaseHasBeenUpdated() {
        assertThat("Adam").isEqualTo(responseData.get(0).getGuest().getFirst_name());
        assertThat("Svenson").isEqualTo(responseData.get(0).getGuest().getLast_name());
        assertThat(103).isEqualTo(responseData.get(0).getHotel_room().getRoom());
    }

    private void thenVerifyThatTwoReservationsExistInDataBase() {
        assertThat(2).isEqualTo(responseData.size());
        assertThat(inputData.size()).isEqualTo(responseData.size());
        assertThat(inputData.get(0).getReservation_id()).isEqualTo(responseData.get(0).getReservation_id());
        assertThat(inputData.get(1).getReservation_id()).isEqualTo(responseData.get(1).getReservation_id());
    }

    private void thenVerifyThatJsonFileIsEqualToDataBaseInfo() {
        assertThat(inputData.size()).isEqualTo(responseData.size());
        assertThat(inputData.get(0).getReservation_id()).isEqualTo(responseData.get(0).getReservation_id());
    }

    private void thenVerifyThatPostStatusIs200() {
        assertThat(HttpStatus.OK).isEqualTo(statusForPost);
    }

    private void givenThatBookingEngineHasAGetMethodAndConsumesMediaTypeJson() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI = "/getAllReservation";
    }

    private void thenVerifyThatGetStatusIs200() {
        assertThat(HttpStatus.OK).isEqualTo(statusForGet);
    }

    /*------old test------*/

    @Disabled
    public void testingGetAndPost() {

        String deleteAllURI = "/deleteAll";
        String postAtURI = "/addReservation";
        String getFromURI = "/getAllReservation";
        String jsonPath = "src/test/resources/testDataTwoReservations";
        JSONArray reservations = Handler.jacksonReadingJsonFiles(jsonPath);

        testRestTemplate.delete(Handler.formFullURLWithPort(port, deleteAllURI));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        inputData = new ArrayList<>();
        for (Object reservation : reservations) {
            HttpEntity<Object> requestEntity = new HttpEntity<>(reservation, headers);
            ResponseEntity<String> postResponseEntity = testRestTemplate.exchange(
                    Handler.formFullURLWithPort(port, postAtURI),
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            inputData.add(Handler.convertDataToReservationObject(reservation));
        }

        System.out.println("--------------------Get--------------------");

        ResponseEntity<JSONArray> getResponseEntity = testRestTemplate.getForEntity(Handler.formFullURLWithPort(port, getFromURI),
                JSONArray.class);

        responseData = new ArrayList<>();
        for (Object reservation : Objects.requireNonNull(getResponseEntity.getBody())) {
            responseData.add(Handler.convertDataToReservationObject(reservation));
        }
        responseData.forEach(System.out::println);

        assertThat(inputData.size()).isEqualTo(responseData.size());
        assertThat(inputData.get(0).getReservation_id()).isEqualTo(responseData.get(0).getReservation_id());
        assertThat(inputData.get(1).getReservation_id()).isEqualTo(responseData.get(1).getReservation_id());
    }

    @Disabled
    public void testStream() {
        String jsonPath = "src/test/resources/testDataTwoReservations";
        JSONArray reservations = Handler.jacksonReadingJsonFiles(jsonPath);
        System.out.println(reservations);

        String testData = "[{\"reservation_id\":\"9abacbae-348d-11ea-aec2-2e728ce88125\",\"hotel_chain\":\"Quality_Hotel\"}]";
        /*TODO
         *  1) read every line and get out value of example reservation_id
         *
         * */

        try {
            String data = new String(Files.readAllBytes(Path.of(jsonPath)));
            System.out.println(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> stream = Files.lines(Path.of(jsonPath))) {
            stream.filter(s -> s.endsWith("\","))
                    .map(String::toUpperCase)
                    .map(reservation -> reservation.startsWith("R"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
