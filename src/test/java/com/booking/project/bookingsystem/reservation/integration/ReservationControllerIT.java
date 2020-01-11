package com.booking.project.bookingsystem.reservation.integration;

import com.booking.project.BookingEngineApp;
import com.booking.project.bookingsystem.reservation.model.Reservation;
import com.booking.project.bookingsystem.utils.Handler;
import org.json.simple.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

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
    public void test_Adding_One_Reservation_File() throws IOException {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Running test: " + new Throwable().getStackTrace()[0].getMethodName() + "\n");

        givenThatReservationTableIsEmpty();
        givenThatUserIsLoggedInToWebSite();
        givenThatAJsonFileIsReadable();
        givenThatContentTypeIsApplicationJson();
        whenReservationFileIsSent();
        whenReservationDataIsReceived();
        thenJsonFileShouldEqualDataBaseInfo();

        System.out.println("--------------------------------------------------------------------------");
    }

    @Test
    public void test_Http_status_For_Post_Methods() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Running test: " + new Throwable().getStackTrace()[0].getMethodName() + "\n");

        givenThatReservationTableIsEmpty();
        givenThatBookingEngineHasAPostMethodAndConsumesMediaTypeJson();
        whenTheApplicationPostAReservtionIntoDataBase();
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

    private void givenThatAJsonFileIsReadable() {
        String jsonPath = "src/test/resources/testDataOne.json"; // One reservation
        jsonFile = Handler.jacksonReadingJsonFiles(jsonPath);
    }

    private void givenThatContentTypeIsApplicationJson() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
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

    private void thenJsonFileShouldEqualDataBaseInfo() {

        System.out.println("\n\n");
        System.out.println(inputData);
        System.out.println(responseData);
        assertThat(inputData.size()).isEqualTo(responseData.size());
        assertThat(inputData.get(0).getReservation_id()).isEqualTo(responseData.get(0).getReservation_id());

    }


    private void givenThatReservationTableIsEmpty() {
        testRestTemplate.delete(Handler.formFullURLWithPort(port, "/deleteAll"));
    }

    private void givenThatBookingEngineHasAPostMethodAndConsumesMediaTypeJson() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI = "/addReservation";
    }

    private void whenTheApplicationPostAReservtionIntoDataBase() {
        String jsonPath = "src/test/resources/testDataOne.json";
        JSONArray reservationList = Handler.jacksonReadingJsonFiles(jsonPath);
        requestEntity = new HttpEntity<>(reservationList.get(0), headers);
        ResponseEntity<String> postResponseEntity = testRestTemplate.exchange(
                Handler.formFullURLWithPort(port, URI),
                HttpMethod.POST,
                requestEntity,
                String.class);
        statusForPost = postResponseEntity.getStatusCode();
        System.out.println(statusForPost);
    }

    private void thenVerifyThatPostStatusIs200() {
        assertThat(HttpStatus.OK).isEqualTo(statusForPost);
    }

    private void givenThatBookingEngineHasAGetMethodAndConsumesMediaTypeJson() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI = "/getAllReservation";
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

    private void thenVerifyThatGetStatusIs200() {
        assertThat(HttpStatus.OK).isEqualTo(statusForGet);
    }

    /*Old!*/
    @Test
    public void testPostingApiCall() {
        System.out.println("---------Delete all data---------------------");
        String deleteAllURI = "/deleteAll";
        String postAtURI = "/addReservation";
        String getFromURI = "/getAllReservation";
        testRestTemplate.delete(Handler.formFullURLWithPort(port, deleteAllURI));

        System.out.println("--------------------Post--------------------");
        String jsonPath = "src/test/resources/testDataOne.json";

        JSONArray reservation1 = Handler.jacksonReadingJsonFiles(jsonPath);
        System.out.println(reservation1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(reservation1.get(0), headers);

        ResponseEntity<String> postResponseEntity = testRestTemplate.exchange(Handler.formFullURLWithPort(port, postAtURI),
                HttpMethod.POST,
                requestEntity,
                String.class);

        HttpStatus statusForPost = postResponseEntity.getStatusCode();
        System.out.println("status code - " + statusForPost);
        HttpHeaders responseHeaders = postResponseEntity.getHeaders();
        System.out.println("response header = " + responseHeaders);
        assertThat(HttpStatus.OK).isEqualTo(statusForPost);

        System.out.println("--------------------Get--------------------");

        ResponseEntity<String> getResponseEntity = testRestTemplate.exchange(Handler.formFullURLWithPort(port, getFromURI),
                HttpMethod.GET,
                requestEntity,
                String.class);

        HttpStatus statusForGet = getResponseEntity.getStatusCode();
        String user = getResponseEntity.getBody();
        System.out.println("request body = " + user);
        HttpHeaders postResponseHeaders = getResponseEntity.getHeaders();
        System.out.println("response header = " + responseHeaders);
        assertThat(HttpStatus.OK).isEqualTo(statusForGet);
    }

    @Test
    public void testGetApiCall() {

        String getFromURI = "/getAllReservation";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Reservation> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(Handler.formFullURLWithPort(port, getFromURI),
                HttpMethod.GET,
                requestEntity,
                String.class);

        HttpStatus status = responseEntity.getStatusCode();
        System.out.println("status code - " + status);

        String user = responseEntity.getBody();
        System.out.println("request body = " + user);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response header = " + responseHeaders);
        assertThat(HttpStatus.OK).isEqualTo(status);
    }


    @Test
    public void test() {

        String deleteAllURI = "/deleteAll";
        String postAtURI = "/addReservation";
        String getFromURI = "/getAllReservation";
        String jsonPath = "src/test/resources/testDataTwo";
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
}
