package io.github.williamandradesantana.ong_app_api.modules.projects.validators;

import io.github.williamandradesantana.ong_app_api.exceptions.BusinessException;
import io.github.williamandradesantana.ong_app_api.modules.projects.data.dto.ProjectRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.projects.repository.ProjectRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class ProjectValidator {

    private final ProjectRepository repository;

    public ProjectValidator(ProjectRepository repository) {
        this.repository = repository;
    }

    public void validateProject(ProjectRequestDTO requestDTO) {
        if (isStartDateInPast(requestDTO)) {
            throw new BusinessException("Start date must not be in the past");
        }

        if (isGoalAmountInvalid(requestDTO)) {
            throw new BusinessException("Goal amount must be greater than zero");
        }

        if (isEndDateBeforeStartDate(requestDTO)) {
            throw new BusinessException("End date must be after start date");
        }

        if (existsProjectWithSameNameAndPeriod(requestDTO))
            throw new BusinessException("A project with the same name and period already exists");
    }

    private boolean isStartDateInPast(ProjectRequestDTO requestDTO) {
        return requestDTO.getStartDate() != null &&
                requestDTO.getStartDate().isBefore(Instant.now());
    }

    private boolean isGoalAmountInvalid(ProjectRequestDTO requestDTO) {
        return requestDTO.getGoalAmount() == null ||
                requestDTO.getGoalAmount().compareTo(BigDecimal.ZERO) <= 0;
    }

    private boolean isEndDateBeforeStartDate(ProjectRequestDTO requestDTO) {
        return requestDTO.getStartDate() != null &&
                requestDTO.getEndDate() != null &&
                requestDTO.getEndDate().isBefore(requestDTO.getStartDate());
    }

    private boolean existsProjectWithSameNameAndPeriod(ProjectRequestDTO requestDTO) {

        if (requestDTO.getName() == null || requestDTO.getStartDate() == null || requestDTO.getEndDate() == null)
            return false;

        return repository.existsByNameAndStartDateAndEndDate(
                requestDTO.getName(),
                requestDTO.getStartDate(),
                requestDTO.getEndDate()
        );
    }
}
