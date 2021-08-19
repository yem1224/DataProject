package com.finda.server.mydata.auth.dto;

import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthProgressDto {
    private Long progressId;
    private List<Task> tasks;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Task {
        private String orgCode;
        private Status status;

        public Task(String orgCode, MydataAuthBaseEntity.AuthStatus status) {
            this.orgCode = orgCode;

            if (status.equals(MydataAuthBaseEntity.AuthStatus.AUTH00)) {
                this.status = Status.IN_PROGRESS;
            } else if (status.equals(MydataAuthBaseEntity.AuthStatus.AUTH01)) {
                this.status = Status.DONE;
            } else {
                this.status = Status.FAIL;
            }
        }
    }

    public static AuthProgressDto create(Long progressId, List<MydataAuthBaseEntity> mydataAuthBaseEntities) {
        List<Task> tasks = mydataAuthBaseEntities.stream()
                .map(e -> new Task(e.getOrgCode(), e.getAuthStatus()))
                .collect(Collectors.toList());

        return new AuthProgressDto(progressId, tasks);
    }

    public enum Status {
        IN_PROGRESS,
        DONE,
        FAIL
    }
}
