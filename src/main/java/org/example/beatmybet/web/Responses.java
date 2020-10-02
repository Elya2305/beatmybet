package org.example.beatmybet.web;

public class Responses {

    public static <T> ApiResponse<T> okResponse(T body) {
        ApiResponse<T> apiResponse = new ApiResponse<>(ApiResponse.Status.OK);
        apiResponse.setBody(body);
        return apiResponse;
    }

    public static <T> ApiResponse<T> okResponse(T body, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>(ApiResponse.Status.OK);
        apiResponse.setBody(body);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static <T> ApiResponse<T> okResponse(String message) {
        ApiResponse<T> tApiResponse = new ApiResponse<>(ApiResponse.Status.OK);
        tApiResponse.setMessage(message);
        return tApiResponse;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>(ApiResponse.Status.ERROR);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(T body) {
        ApiResponse<T> apiResponse = new ApiResponse<>(ApiResponse.Status.ERROR);
        apiResponse.setBody(body);
        return apiResponse;
    }

    public static <T> ApiResponse<T> authError(T body) {
        ApiResponse<T> apiResponse = new ApiResponse<>(ApiResponse.Status.AUTH_ERROR);
        apiResponse.setBody(body);
        return apiResponse;
    }
}
