package panomete.poc.resemail.common.payload.response;

import panomete.poc.resemail.common.entity.Status;

public record ResponseDto<T>(
        Status status,
        T data,
        String message
) {
}
