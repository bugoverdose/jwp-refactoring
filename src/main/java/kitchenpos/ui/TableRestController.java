package kitchenpos.ui;

import kitchenpos.application.TableService;
import kitchenpos.application.dto.TableDto;
import kitchenpos.application.dto.UpdateGuestNumberDto;
import kitchenpos.ui.dto.EmptyTableRequestDto;
import kitchenpos.ui.dto.TableGuestNumberRequestDto;
import kitchenpos.ui.dto.TableRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TableRestController {

    private final TableService tableService;

    @PostMapping("/api/tables")
    public ResponseEntity<TableDto> create(@RequestBody final TableRequestDto requestBody) {
        final TableDto created = tableService.create(requestBody.toCreateTableDto());
        final URI uri = URI.create("/api/tables/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping("/api/tables")
    public ResponseEntity<List<TableDto>> list() {
        return ResponseEntity.ok().body(tableService.list());
    }

    @PutMapping("/api/tables/{orderTableId}/empty")
    public ResponseEntity<TableDto> changeEmpty(@PathVariable final Long orderTableId,
                                                @RequestBody final EmptyTableRequestDto requestBody) {
        return ResponseEntity.ok().body(tableService.changeEmpty(requestBody.toEmptyTableDto(orderTableId)));
    }

    @PutMapping("/api/tables/{orderTableId}/number-of-guests")
    public ResponseEntity<TableDto> changeNumberOfGuests(@PathVariable final Long orderTableId,
                                                         @RequestBody final TableGuestNumberRequestDto requestBody) {
        UpdateGuestNumberDto updateGuestNumberDto = requestBody.toUpdateGuestNumberDto(orderTableId);
        return ResponseEntity.ok().body(tableService.changeNumberOfGuests(updateGuestNumberDto));
    }
}
