package com.sql_to_csv_conversion.sql_to_csv_conversion.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserStructure, Long> {
}
