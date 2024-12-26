package com.java.librarymanagement.booking.model;

import com.java.librarymanagement.utils.AuditEntity;
import com.java.librarymanagement.users.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static jakarta.persistence.FetchType.LAZY;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "bookings")
public class Booking extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKING_SEQUENCE")
    private Long id;

    @Column(name = "book_id")

    @NotNull(message = "bookId is required and cannot be null")
    private Long bookId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    @NotBlank(message = "userId is required and cannot be empty")
    private User user;

    @Column
    private Date borrowedDate;

    @Column
    private Date dueDate;

    @Column
    private Double overDue = 0D;

    // Fine related field
    @Column
    private Double fineAmount = 0D;

    private Long bookId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User users;

    @Column
    private BigDecimal price;

    @Column
    private LocalDate borrowedDate;

    @Setter
    @Getter
    @Column
    private LocalDate dueDate;

    @Column
    private int overDue;

    // Fine related field
    @Column
    private BigDecimal fineAmount = BigDecimal.ZERO;

    // Fine calculation logic
    public void calculateOverdueAndFine(LocalDate currentDate) {
        if (currentDate.isAfter(dueDate)) {
            // Calculate overdue days
            this.overDue = (int) ChronoUnit.DAYS.between(dueDate, currentDate);

            // Fine calculation (e.g., $1 per day overdue)
            BigDecimal dailyFine = new BigDecimal("1.00"); // Fine per day
            this.fineAmount = dailyFine.multiply(new BigDecimal(overDue));
        } else {
            this.overDue = 0;
            this.fineAmount = BigDecimal.ZERO; // No fine if not overdue
        }
    }
}
