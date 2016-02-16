
package com.example.application.backend;

import org.springframework.data.jpa.repository.*;

public interface UnitDataRepository extends JpaRepository<UnitData, Long>, CustomUnitDataRepository {

}
