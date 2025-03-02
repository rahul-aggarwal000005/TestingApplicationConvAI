package com.testing.application.TestingApplication.dto;


public record ResponseWrapper<T>(String errorMessage, T data) {
}