package be.pxl.services.repository;

import be.pxl.services.domain.Logging;
import jdk.javadoc.doclet.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends JpaRepository<Logging, Long> {
}
