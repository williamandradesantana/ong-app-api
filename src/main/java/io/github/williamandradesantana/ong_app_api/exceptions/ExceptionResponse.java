package io.github.williamandradesantana.ong_app_api.exceptions;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
