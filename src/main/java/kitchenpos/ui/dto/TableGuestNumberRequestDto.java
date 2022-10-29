package kitchenpos.ui.dto;

import kitchenpos.application.dto.request.UpdateGuestNumberDto;

public class TableGuestNumberRequestDto {

    private Integer numberOfGuests;

    public TableGuestNumberRequestDto() {
    }

    public UpdateGuestNumberDto toUpdateGuestNumberDto(Long orderTableId) {
        return new UpdateGuestNumberDto(orderTableId, numberOfGuests);
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }
}
