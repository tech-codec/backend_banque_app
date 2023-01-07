package org.sid.banquetechcodec.dao;

import org.sid.banquetechcodec.entities.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureTokenRepository extends JpaRepository<SecureToken,Long> {
    SecureToken findByToken(final String token);
    Long removeByToken(String token);
}
