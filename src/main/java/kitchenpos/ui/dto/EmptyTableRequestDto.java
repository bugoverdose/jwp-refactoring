package kitchenpos.ui.dto;

import kitchenpos.application.dto.request.EmptyTableDto;

public class EmptyTableRequestDto {

    private Boolean empty;

    public EmptyTableRequestDto() {
    }

    public EmptyTableDto toEmptyTableDto(Long orderTableId) {
        return new EmptyTableDto(orderTableId, empty);
    }

    public Boolean getEmpty() {
        return empty;
    }
}
