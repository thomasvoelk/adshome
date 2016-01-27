
package com.example.application.backend;

import org.springframework.data.jpa.repository.*;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
