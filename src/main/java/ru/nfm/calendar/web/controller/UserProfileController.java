package ru.nfm.calendar.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.service.UserProfileService;

import java.net.URI;

@RestController
@RequestMapping(value = UserProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class UserProfileController {
    static final String REST_URL = "/profiles";
    private final UserProfileService userProfileService;

    @PostMapping
    @Operation(summary = "Создаёт UserProfile для авторизованного пользователя",
            description = "Возвращает UserProfileDto, либо 403, либо 400 при неправильной валидации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = UserProfileDto.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Validation fail")
    })
    public ResponseEntity<UserProfileDto> createUserProfile(@AuthenticationPrincipal User user,
                                                            @Valid @RequestBody UserProfileDto userProfileDto) {
        var response = userProfileService.setupUserProfile(user.getId(), userProfileDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping
    public ResponseEntity<UserProfileDto> updateUserProfile(@AuthenticationPrincipal User user,
                                                            @Valid @RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(user.getId(), userProfileDto));
    }
}
