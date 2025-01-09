package com.java.librarymanagement.booking.model;

import com.java.librarymanagement.utils.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import com.java.librarymanagement.users.model.User;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "bookings")
public class Booking extends AuditEntity {

    private static final Logger logger = LoggerFactory.getLogger(Booking.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKING_SEQUENCE")
    private Long id;

    @Column(name = "book_id")
    @NotNull(message = "bookId is required and cannot be null")
    private Long bookId;

    // Getters and setters for user (automatically handled by @Data annotation)
    @Setter
    @Getter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    @NotNull(message = "userId is required and cannot be empty")
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDateTime borrowedDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column
    private Double overDue = 0.0;

    @Column
    private Double fineAmount = 0.0;

    public void calculateFine() {
        if (dueDate == null) {
            logger.warn("Due date is null for booking ID: {}", id);
            return;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate dueLocalDate = dueDate.toLocalDate(); // Convert LocalDateTime to LocalDate

        if (currentDate.isAfter(dueLocalDate)) {
            long overdueDays = ChronoUnit.DAYS.between(dueLocalDate, currentDate);
            this.fineAmount = overdueDays * 5.0; // Example fine calculation
            this.overDue = (double) overdueDays;

            logger.info("Fine calculated for booking ID {}: {}", id, fineAmount);
        } else {
            this.fineAmount = 0.0;
            this.overDue = 0.0;
            logger.info("No fine for booking ID {}", id);
        }
    }
}
