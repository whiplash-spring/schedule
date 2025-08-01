package sparta.schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule.dto.schedule.*;
import sparta.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @Valid @RequestBody CreateScheduleRequestDto createScheduleRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(createScheduleRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<GetScheduleListResponseDto>> getAllSchedules(@RequestParam(required = false) String author) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedules(author));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleByIdResponseDto> getScheduleById(@PathVariable Long scheduleId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getScheduleById(scheduleId));
        } catch (IllegalArgumentException e) { // 이렇게 구체적으로 에러를 반환해도 괜찮을까..? try-catch 자체가 지저분해 보이기도...
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleByIdResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequestDto updateScheduleRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, updateScheduleRequestDto));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody DeleteScheduleRequestDto deleteScheduleRequestDto) {
        scheduleService.deleteSchedule(scheduleId, deleteScheduleRequestDto);
        return ResponseEntity.ok().build();
    }
}
